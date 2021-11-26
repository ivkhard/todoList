package org.example.myWork.logic.command;

import org.example.myWork.logic.ErrorHandler;
import org.example.myWork.logic.TaskDao;
import org.example.myWork.logic.TaskIdValidator;
import org.example.myWork.parser.CommandDescription;


import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class DeleteCommand extends BaseDaoCommand implements Consumer<CommandDescription> {
    public static final String COMMAND = "delete";

    protected DeleteCommand(TaskDao taskDao, ErrorHandler errorHandler) {
        super(taskDao, errorHandler);
    }

    @Override
    public void accept(CommandDescription commandDescription) {
        taskDao.delete((int) commandDescription.getTaskId());
    }
}
