package org.example.myWork.logic.command;

import org.example.myWork.logic.ErrorHandler;
import org.example.myWork.logic.TaskDao;
import org.example.myWork.logic.TaskIdValidator;
import org.example.myWork.model.Task;
import org.example.myWork.parser.CommandDescription;


import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;

public class ToggleCommand extends BaseDaoCommand implements Consumer<CommandDescription> {
    public static final String COMMAND = "toggle";

    protected ToggleCommand(TaskDao taskDao, ErrorHandler errorHandler) {
        super(taskDao, errorHandler);
    }

    @Override
    public void accept(CommandDescription commandDescription) {
        Optional<Task> t = taskDao.get(commandDescription.getTaskId());
        if (t.isPresent()) {
            Task task = t.get();
            task.setDone(!task.isDone());
        }
    }
}
