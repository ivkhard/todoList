package org.example.myWork.controller;

import org.example.myWork.dto.TaskDto;
import org.example.myWork.model.User;
import org.example.myWork.service.ShellTaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import javax.validation.Valid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class TaskControllerTest {

    private ShellTaskService taskService;

    private TaskController taskController;

    @BeforeEach
    public void setUp() throws Exception {
        taskService = mock(ShellTaskService.class);
        taskController = new TaskController(taskService);
    }

    @Test
    public void create() {
        @Valid TaskDto task = new TaskDto();
        User owner = new User();
        var res = taskController.create(task, owner);

        verify(taskService).save(task, owner);
        verifyNoMoreInteractions(taskService);

        assertEquals(task, res.getBody());
        assertEquals(HttpStatus.CREATED, res.getStatusCode());
        assertNotNull(res.getHeaders().getLocation());
        var actualLocation = res.getHeaders().getLocation().toString();
        assertEquals("/task/" + task.getId(), actualLocation);

    }
}