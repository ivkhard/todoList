package org.example.myWork.logic.command;

import org.example.myWork.logic.IErrorHandler;
import org.example.myWork.logic.ITaskDao;
import org.example.myWork.parser.CommandDescription;

import java.util.function.Consumer;

public abstract class BaseCommand implements Consumer<CommandDescription> {
    protected final ITaskDao taskDao;
    protected final IErrorHandler errorHandler;

    protected BaseCommand(ITaskDao taskDao, IErrorHandler errorHandler) {
        this.taskDao = taskDao;
        this.errorHandler = errorHandler;
    }
}
