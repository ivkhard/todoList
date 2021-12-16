package org.example.myWork.logic;

import org.example.myWork.logic.command.BaseCommand;
import org.example.myWork.logic.command.ICommandFactory;
import org.example.myWork.parser.CommandDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class CommandConsumer implements Consumer<CommandDescription> {
    private final ICommandFactory commandFactory;
    private final IErrorHandler errorHandler;

    @Autowired
    public CommandConsumer(ICommandFactory commandFactory, IErrorHandler errorHandler) {
        this.commandFactory = commandFactory;
        this.errorHandler = errorHandler;
    }

    @Override
    public void accept(CommandDescription commandDescription) {
        if (commandDescription != null) {
            BaseCommand command = commandFactory.getCommand(commandDescription.getName());
            if (command == null) {
                errorHandler.handle("Неизвестная команда");
            } else {
                command.accept(commandDescription);
            }
        }
    }
}
