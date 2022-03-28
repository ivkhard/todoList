package org.example.myWork.service;

import com.ext.myWork.dto.ExtTaskDto;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.example.myWork.dto.TaskDto;
import org.example.myWork.mapper.ExtTaskMapper;
import org.example.myWork.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.example.myWork.mapper.ExtTaskMapper.ID_PREFIX;

@Service
@RequiredArgsConstructor
public class RestTaskServiceImpl implements CustomTaskService {

    private final RestTaskDao restTaskDao;
    private final ExtTaskMapper taskMapper;

    @Override
    public boolean supports(String taskId) {
        return taskId != null && taskId.startsWith(ID_PREFIX);
    }

    @Override
    public void deleteById(String id) {
        restTaskDao.deleteTask(stripId(id));
    }

    @Override
    public Optional<TaskDto> findById(String id) {
        String extId = stripId(id);
        return restTaskDao.getList().getTasks().stream()
                .filter(dto -> extId.equals(dto.getTaskId()))
                .map(taskMapper::toInternal)
                .findFirst();
    }

    @Override
    public List<TaskDto> find(String query, boolean done, User user) {
        Stream<ExtTaskDto> externalTaskDtos = restTaskDao.getList().getTasks().stream();
        if (Strings.isNotBlank(query)) {
            externalTaskDtos = externalTaskDtos.filter(t -> t.getDescription().contains(query));
        }
        return externalTaskDtos.map(taskMapper::toInternal).collect(Collectors.toList());
    }

    @Override
    public void save(TaskDto task, User user) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(TaskDto t) {
        restTaskDao.editTask(stripId(t.getId()), t.getDescription());
    }

    private static String stripId (String id) {
        return id.substring(ID_PREFIX.length());
    }
}
