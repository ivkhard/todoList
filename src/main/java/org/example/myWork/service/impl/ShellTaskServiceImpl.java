package org.example.myWork.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.myWork.dto.TaskDto;
import org.example.myWork.model.User;
import org.example.myWork.service.CustomTaskService;
import org.example.myWork.service.ShellTaskService;
import org.example.myWork.service.TaskService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShellTaskServiceImpl implements ShellTaskService {

    private final List<CustomTaskService> taskServices;

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
        for (CustomTaskService custom : taskServices) {
            try {
                result.addAll(custom.find(query, done, user));
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
