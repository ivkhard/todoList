package org.example.myWork.logic.command;

import org.example.myWork.parser.CommandDescription;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class QuitCommand implements Consumer<CommandDescription> {
    public static final String COMMAND = "quit";

    @Override
    public void accept(CommandDescription commandVariables) {
        System.exit(0);

    }
}

