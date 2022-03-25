package org.example.myWork.controller;

import lombok.var;
import org.example.myWork.dto.TaskDto;
import org.example.myWork.logic.TaskDao;
import org.example.myWork.model.Task;
import org.example.myWork.model.User;
import org.example.myWork.service.ShellTaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.HttpStatus;

import javax.validation.Valid;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TaskControllerTest {

    private TaskDao taskDao;
    private TaskController taskController;

    @BeforeEach
    public void setUp() throws Exception {
        taskDao = mock(TaskDao.class);
        taskController = new TaskController((ShellTaskService) taskDao);
    }

    @Test
    public void create() {
        @Valid TaskDto task = new TaskDto();
        User owner = new User();
        var res = taskController.create(task, owner);
//        assertEquals(owner, task.getOwner());
//
//        verify(taskDao).save(task);
        verifyNoMoreInteractions(taskDao);

        assertEquals(task, res.getBody());
        assertEquals(HttpStatus.CREATED, res.getStatusCode());
        assertNotNull(res.getHeaders().getLocation());
        var actualLocation = res.getHeaders().getLocation().toString();
        assertEquals("/task/" + task.getId(), actualLocation);

    }
}