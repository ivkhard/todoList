package com.ext.myWork.dto;

import lombok.Data;

@Data
public class ExtTaskDto {
    private String taskId;
    private String description;
    private Boolean closed;
}
