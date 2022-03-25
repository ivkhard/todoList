package com.ext.myWork.dto;

import lombok.Data;

@Data
public class ExtTaskDto {
    private Long id;
    private String description;
    private TaskStatus taskStatus;
}
