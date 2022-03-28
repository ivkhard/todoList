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

    @DeleteMapping("/{id}")
    void deleteTask(@PathVariable(value = "id") String id);

    @PatchMapping("/{id}")
    ExtTaskDto editTask(@PathVariable(value = "id") String id, @RequestParam(value = "description") String description);
}
