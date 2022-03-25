package org.example.myWork.mapper;

import org.example.myWork.dto.TaskDto;
import org.example.myWork.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto toTaskDto(Task task);

    Task toTask(TaskDto taskDto);

    void updateFromDto(TaskDto taskDto, @MappingTarget Task task);
}
