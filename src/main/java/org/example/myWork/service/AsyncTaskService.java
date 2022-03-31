package org.example.myWork.service;

import org.example.myWork.dto.TaskDto;
import org.example.myWork.model.User;

import java.util.List;
import java.util.concurrent.Future;

public interface AsyncTaskService {

    Future<List<TaskDto>> findAsync(String query, boolean done, User user);
}
