package org.example.myWork.logic;

import com.ext.myWork.dto.ExtTaskDto;
import org.example.myWork.config.FeignConfig;
import com.ext.myWork.dto.response.ExtTaskResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "myWork", url = "${myWork.ext.url}", configuration = FeignConfig.class)
public interface RestTaskDao {

    @GetMapping("/task")
    ExtTaskResponse getList();

    @DeleteMapping("/task/{task_id}")
    void deleteTask(@PathVariable(value = "task_id") String id);

    @PatchMapping("/task/{task_id}")
    ExtTaskDto changeTask(@PathVariable(value = "task_id") String id, @RequestParam(value = "description") String description, Boolean closed);
}
