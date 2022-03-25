package org.example.myWork.service;

public interface CustomTaskService extends TaskService {

    boolean supports(String taskId);
}
