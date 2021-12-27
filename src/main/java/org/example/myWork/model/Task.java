package org.example.myWork.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table (name = "Task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String description;

    private boolean done;

    public static Task of(String description) {
        Task task = new Task();
        task.setDescription(description);
        return task;
    }
}
