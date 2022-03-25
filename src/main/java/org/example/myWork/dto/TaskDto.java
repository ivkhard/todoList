package org.example.myWork.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TaskDto {

    private String id;

    @NotBlank
    private String description;

    private boolean done;
}
