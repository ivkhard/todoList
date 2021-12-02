package org.example.myWork.logic.command;

import org.example.myWork.logic.IErrorHandler;
import org.example.myWork.logic.TaskDao;
import org.example.myWork.parser.CommandDescription;

import java.util.function.Consumer;

public abstract class BaseDao implements Consumer<CommandDescription> {
    protected final TaskDao taskDao;
    protected final IErrorHandler errorHandler;

    protected BaseDao(TaskDao taskDao, IErrorHandler errorHandler) {
        this.taskDao = taskDao;
        this.errorHandler = errorHandler;
    }
}
