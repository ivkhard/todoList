package org.example.myWork.mapper;

import com.ext.myWork.dto.ExtTaskDto;
import org.example.myWork.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExtTaskMapper {
    static final String ID_PREFIX = "EXT-";

    @Mapping(target = "id", expression = "java(ID_PREFIX + dto.getTaskId())")
    @Mapping(target = "done", expression = "java(dto.getClosed())")
    TaskDto toInternal(ExtTaskDto dto);
}
