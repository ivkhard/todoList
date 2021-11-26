package org.example.myWork.logic.command;

import org.example.myWork.CommandInt;
import org.example.myWork.logic.ErrorHandler;
import org.example.myWork.logic.TaskDao;

public abstract class BaseDaoCommand {
    protected final TaskDao taskDao;
    protected final ErrorHandler errorHandler;

    protected BaseDaoCommand(TaskDao taskDao, ErrorHandler errorHandler) {
        this.taskDao = taskDao;
        this.errorHandler = errorHandler;
    }
}
