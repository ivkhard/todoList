package org.example.myWork.parser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Builder
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Component
public class CommandDescription {
    private String name;
    private long taskId;
    private String text;
    private String args;
}
