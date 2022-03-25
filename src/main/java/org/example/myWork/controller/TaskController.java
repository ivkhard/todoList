package org.example.myWork.controller;

import lombok.RequiredArgsConstructor;
import org.example.myWork.dto.TaskDto;
import org.example.myWork.model.DescriptionHolder;
import org.example.myWork.model.StatusHolder;
import org.example.myWork.model.User;
import org.example.myWork.service.ShellTaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final ShellTaskService taskService;

    @GetMapping
    public List<TaskDto> task(@RequestParam(name = "q", required = false) String query,
                              @RequestParam(name = "all", required = true) Boolean all,
                              @AuthenticationPrincipal User user) {
        return taskService.find(query, !all, user);
    }

    @PostMapping
    public ResponseEntity<TaskDto> create(@RequestBody @Valid TaskDto task, @AuthenticationPrincipal User user) {
        taskService.save(task, user);
        return ResponseEntity.created(URI.create("/task/" + task.getId())).body(task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        taskService.deleteById(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") String id, @RequestBody @Valid DescriptionHolder description) {
        return processWithId(id, t -> {
            t.setDescription(description.getDescription());
            taskService.update(t);
        });
    }

    @PatchMapping("/{id}/done")
    public ResponseEntity<String> toggle(@PathVariable("id") String id, @RequestBody @Valid StatusHolder status) {
        return processWithId(id, t -> t.setDone(status.isDone()));
    }

    private ResponseEntity<String> processWithId(String id, Consumer<TaskDto> consumer) {
        Optional<TaskDto> task = taskService.findById(id);
        if (task.isPresent()) {
            TaskDto t = task.get();
            consumer.accept(t);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
