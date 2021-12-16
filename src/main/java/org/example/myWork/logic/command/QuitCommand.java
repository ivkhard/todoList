package org.example.myWork.logic.command;

import org.example.myWork.logic.IErrorHandler;
import org.example.myWork.logic.ITaskDao;
import org.example.myWork.parser.CommandDescription;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component("quit")
public class QuitCommand extends BaseCommand {

    protected QuitCommand(ITaskDao taskDao, IErrorHandler errorHandler) {
        super(taskDao, errorHandler);
    }

    @Override
    public void accept(CommandDescription commandVariables) {
        System.exit(0);
    }
}

