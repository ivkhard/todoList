package org.example.myWork.parser;

import lombok.*;
import org.springframework.stereotype.Component;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Component
public class CommandDescription {
    private String name;
    private long taskId;
    private String text;
    private String args;
}
