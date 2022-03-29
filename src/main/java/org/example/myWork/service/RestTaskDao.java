package org.example.myWork.service;

import com.ext.myWork.dto.ExtTaskDto;
import org.example.myWork.config.FeignConfig;
import org.example.myWork.model.ExtTaskResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "myWork", url = "${myWork.ext.url}", configuration = FeignConfig.class)
public interface RestTaskDao {

    @GetMapping("/task")
    ExtTaskResponse getList();

    @DeleteMapping("/task/{task_id}")
    void deleteTask(@PathVariable(value = "task_id") String id);

    @PatchMapping("/task/{task_id}")
    ExtTaskDto changeTask(@PathVariable(value = "task_id") String id, @RequestParam(value = "description") String description, Boolean closed);
}
