package org.example.myWork.service;

import org.example.myWork.dto.TaskDto;
import org.example.myWork.model.User;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    void deleteById(String id);

    Optional<TaskDto> findById(String id);

    List<TaskDto> find(String query, boolean done, User user);

    void save(TaskDto task, User user);

    void update(TaskDto t);
}
