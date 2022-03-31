package com.ext.myWork.dto.response;

import com.ext.myWork.dto.ExtTaskDto;
import lombok.Data;

import java.util.List;

@Data
public class ExtTaskResponse {

    private List<ExtTaskDto> tasks;
}
