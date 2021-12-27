package org.example.myWork.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DescriptionHolder {
    @NotBlank
    private String description;
}
