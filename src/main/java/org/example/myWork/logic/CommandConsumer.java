package org.example.myWork.logic;

import lombok.RequiredArgsConstructor;
import org.example.myWork.parser.CommandDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Component

public class CommandConsumer implements Consumer<CommandDescription> {
    private final Map<String, Consumer<CommandDescription>> commands;
    private final IErrorHandler errorHandler;

    @Autowired
    public CommandConsumer(Supplier<Map<String, Consumer<CommandDescription>>> commandsSupplier, IErrorHandler errorHandler) {
        commands = commandsSupplier.get();
        this.errorHandler = errorHandler;
    }

    @Override
    public void accept(CommandDescription commandDescription) {
        Consumer<CommandDescription> consumer = null;
        if (commandDescription != null) {
            consumer = commands.get(commandDescription.getName());
        }
        if (consumer != null) {
            consumer.accept(commandDescription);
        } else {
            errorHandler.handle("Неизвестная команда");
        }
    }
}
