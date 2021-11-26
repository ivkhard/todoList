package org.example.myWork.logic.command;

import org.example.myWork.CommandInt;
import org.example.myWork.logic.ErrorHandler;
import org.example.myWork.logic.TaskDao;
import org.example.myWork.ToDoList;
import org.example.myWork.logic.TaskPrinter;
import org.example.myWork.model.Task;
import org.example.myWork.parser.CommandDescription;


import java.util.Scanner;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class SearchCommand extends BaseDaoCommand implements Consumer<CommandDescription> {
    public static final String COMMAND = "search";
    private final TaskPrinter printer;
    public SearchCommand(TaskDao taskDao, ErrorHandler errorHandler, TaskPrinter printer) {
        super(taskDao, errorHandler);
        this.printer = printer;
    }

    @Override
    public void accept(CommandDescription commandDescription) {
        String line = commandDescription.getArgs();
        if (line != null && line.length() == 0) {
            errorHandler.handle("Необходимо ввести строку поиска задачи");
        }
        Stream<Task> tasks = taskDao.find(line, false);
    }
}
