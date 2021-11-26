package org.example.myWork.logic;

import org.example.myWork.model.Task;

import java.util.stream.Stream;

public interface TaskPrinterInt {
    void print(Stream<Task> tasks);
}
