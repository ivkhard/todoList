package org.example.myWork;

import lombok.Data;

@Data
public class Task {
    private String description;
    public boolean done;

    public static Task of (String description) {
        Task task = new Task();
        task.setDescription(description);
        return task;
    }
}
