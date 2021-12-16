package org.example.myWork.logic;

import org.example.myWork.model.Task;

import java.util.Optional;
import java.util.stream.Stream;

public interface ITaskPrinter {
    void print(Stream<Task> tasks);

    void print(Optional<Task> task);
}
