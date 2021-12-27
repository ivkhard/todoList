package org.example.myWork.controller;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.example.myWork.logic.ICustomTaskDao;
import org.example.myWork.logic.ITaskDao;
import org.example.myWork.model.DescriptionHolder;
import org.example.myWork.model.StatusHolder;
import org.example.myWork.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Data
@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class TaskController {

    private final ITaskDao taskDao;
    private final ICustomTaskDao iCustomTaskDao;

    @GetMapping
    public List<Task> task(@RequestParam(name = "q", required = false) String query, @RequestParam(name = "all", required = true) Boolean all) {
        return iCustomTaskDao.findAllFiltered(query, !all);
    }

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody @Valid Task task) {
        taskDao.save(task);
        return ResponseEntity.created(URI.create("/task/" + task.getId())).body(task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        taskDao.delete(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") int id, @RequestBody @Valid DescriptionHolder description) {
        return processWithId(id, t -> t.setDescription(description.getDescription()));
    }

    @PatchMapping("/{id}/done")
    public ResponseEntity<String> toggle(@PathVariable("id") int id, @RequestBody @Valid StatusHolder status) {
        return processWithId(id, t -> t.setDone(status.isDone()));
    }

    private ResponseEntity<String> processWithId(int id, Consumer<Task> consumer) {
        Optional<Task> task = Optional.ofNullable(taskDao.findOne(id));
        if (task.isPresent()) {
            Task t = task.get();
            consumer.accept(task.get());
            taskDao.save(t);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
