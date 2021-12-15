package org.example.myWork.logic;

import org.example.myWork.model.Task;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class TaskPrinter implements ITaskPrinter {
    @Override
    public void print(Stream<Task> tasks) {
        tasks.forEach(TaskPrinter::printTask);
    }

    public static void printTask(Task task) {
        System.out.printf("%s. [%s] %s\n", task.getId(),
                task.isDone() ? "x" : " ",
                task.getDescription());
    }
}