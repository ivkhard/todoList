package org.example.myWork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.myWork.dto.TaskDto;
import org.example.myWork.model.User;
import org.example.myWork.service.AsyncTaskService;
import org.example.myWork.service.CustomTaskService;
import org.example.myWork.service.ShellTaskService;
import org.example.myWork.service.TaskService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
@Slf4j
public class ShellTaskServiceImpl implements ShellTaskService {

    private final List<CustomTaskService> taskServices;
    private final List<CustomTaskService> syncTaskServices;
    private final List<AsyncTaskService> asyncTaskServices;

    public ShellTaskServiceImpl(List<CustomTaskService> taskServices) {
        this.taskServices = Collections.unmodifiableList(taskServices);
        this.syncTaskServices = Collections.unmodifiableList(taskServices.stream().filter(s -> !testAsyncService(s)).collect(Collectors.toList()));
        this.asyncTaskServices = Collections.unmodifiableList(taskServices.stream().filter(ShellTaskServiceImpl::testAsyncService)
                .map(s -> (AsyncTaskService) s).collect(Collectors.toList()));
    }

    private static boolean testAsyncService(Object o) {
        return o instanceof AsyncTaskService;
    }

    @Override
    public void deleteById(String id) {
        getTaskService(id).deleteById(id);
    }

    @Override
    public Optional<TaskDto> findById(String id) {
        return getTaskService(id).findById(id);
    }

    @Override
    public List<TaskDto> find(String query, boolean done, User user) {
        List<TaskDto> result = new ArrayList<>();
        List<Future<List<TaskDto>>> futures = asyncTaskServices.stream().map(ts -> ts.findAsync(query, done, user)).collect(Collectors.toList());
        for (TaskService ts : syncTaskServices) {
            try {
                result.addAll(ts.find(query, done, user));
            } catch (Exception e) {
                log.warn("Error during loading tasks ", e);
            }
        }
        for (Future<List<TaskDto>> future : futures) {
            try {
                result.addAll(future.get());
            } catch (Exception e) {
                log.warn("Error during loading tasks ", e);
            }
        }
        return result;
    }

    @Override
    public void save(TaskDto task, User user) {
        getTaskService(task.getId()).save(task, user);
    }

    @Override
    public void update(TaskDto t) {
        getTaskService(t.getId()).update(t);
    }

    private TaskService getTaskService(String id) {
        for (CustomTaskService ts : taskServices) {
            if (ts.supports(id)) {
                return ts;
            }
        }
        throw new UnsupportedOperationException();
    }
}

