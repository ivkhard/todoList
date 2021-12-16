package org.example.myWork.logic;

import lombok.RequiredArgsConstructor;
import org.example.myWork.logic.command.BaseCommand;
import org.example.myWork.model.Task;
import org.example.myWork.parser.CommandDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
public class CommandConsumer implements Consumer<CommandDescription> {
    private final Map<String, BaseCommand> commands;
    private final TaskPrinter printer;
    private final IErrorHandler errorHandler;

    @Autowired
    public CommandConsumer(Map<String, BaseCommand> commands, TaskPrinter printer, IErrorHandler errorHandler) {
        this.commands = commands;
        this.printer = printer;
        this.errorHandler = errorHandler;
    }

    @Override
    public void accept(CommandDescription commandDescription) {
        BaseCommand command = null;
        if (commandDescription != null) {
            command = commands.get(commandDescription.getName());
            command.accept(commandDescription);
        }
        if (command == null) {
            errorHandler.handle("Неизвестная команда");
        }
    }
}
