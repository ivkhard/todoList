package org.example.myWork.parser;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommandDescription {
    private final String name;
    private final long taskId;
    private final String text;
    private final String args;
}
