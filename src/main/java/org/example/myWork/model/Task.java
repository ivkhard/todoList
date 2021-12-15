package org.example.myWork.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class Task {
    private static final int DEFAULT_ID = -1;
    private int id = DEFAULT_ID;
    private String description;
    private boolean done;

    public static Task of(String description) {
        Task task = new Task();
        task.setDescription(description);
        return task;
    }
}
