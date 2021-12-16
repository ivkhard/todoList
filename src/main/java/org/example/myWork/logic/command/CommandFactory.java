package org.example.myWork.logic.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommandFactory implements ICommandFactory {

    private final Map<String, BaseCommand> commands;

    @Autowired
    public CommandFactory(Map<String, BaseCommand> commands) {
        this.commands = commands;
    }

    @Override
    public BaseCommand getCommand(String name) {
        return commands.get(name);
    }
}
