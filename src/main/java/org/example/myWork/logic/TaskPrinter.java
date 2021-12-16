package org.example.myWork.logic;

import org.example.myWork.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Stream;

@Component
public class TaskPrinter implements ITaskPrinter {

    private final IErrorHandler errorHandler;

    @Autowired
    public TaskPrinter(IErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    @Override
    public void print(Stream<Task> tasks) {
        tasks.forEach(TaskPrinter::printTask);
    }

    @Override
    public void print(Optional<Task> task) {
        if (task.isPresent()) {
            printTask(task.get());
        } else {
            errorHandler.handle("Задача не найдена");
        }
    }

    public static void printTask(Task task) {
        System.out.printf("%s. [%s] %s\n", task.getId(),
                task.isDone() ? "x" : " ",
                task.getDescription());
    }
}
