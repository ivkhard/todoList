package org.example.myWork.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.myWork.dto.TaskDto;
import org.example.myWork.logic.TaskDao;
import org.example.myWork.mapper.TaskMapper;
import org.example.myWork.model.Task;
import org.example.myWork.model.User;
import org.example.myWork.service.CustomTaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@RequiredArgsConstructor
@Service
public class TaskServiceImpl implements CustomTaskService {

    private final TaskDao taskDao;
    private final TaskMapper taskMapper;

    @Override
    public void deleteById(String id) {
        taskDao.deleteById(parseInt(id));
    }

    @Override
    public Optional<TaskDto> findById(String id) {
        return taskDao.findById(parseInt(id)).map(taskMapper::toTaskDto);
    }

    @Override
    public List<TaskDto> find(String query, boolean done, User user) {
        return taskDao.findAllFiltered(query, done, user).stream()
                .map(taskMapper::toTaskDto)
                .collect(Collectors.toList());
    }

    @Override
    public void save(TaskDto taskDto, User user) {
        Task task = taskMapper.toTask(taskDto);
        task.setOwner(user);
        taskDao.save(task);
        taskDto.setId(String.valueOf(task.getId()));
    }

    @Override
    public void update(TaskDto taskDto) {
        Task task = taskDao.findById(parseInt(taskDto.getId())).get();
        taskMapper.updateFromDto(taskDto, task);
        taskDao.save(task);
    }

    @Override
    public boolean supports(String taskId) {
        return taskId == null || taskId.matches("\\d+");
    }
}
