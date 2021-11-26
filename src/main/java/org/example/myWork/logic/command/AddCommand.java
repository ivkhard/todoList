package org.example.myWork.logic.command;

import org.example.myWork.logic.ErrorHandler;
import org.example.myWork.logic.TaskDao;
import org.example.myWork.model.Task;
import org.example.myWork.parser.CommandDescription;

import java.util.Scanner;
import java.util.function.Consumer;

public class AddCommand extends BaseDaoCommand implements Consumer<CommandDescription> {
    public static final String COMMAND = "add";

    protected AddCommand(TaskDao taskDao, ErrorHandler errorHandler) {
        super(taskDao, errorHandler);
    }

    @Override
    public void accept(CommandDescription commandDescription) {
        String args = commandDescription.getArgs();
        if (args == null || args.isEmpty()) {
            errorHandler.handle("Необходимо ввести описание задачи");
        } else {
            taskDao.save(Task.of(args));
        }
    }
}
