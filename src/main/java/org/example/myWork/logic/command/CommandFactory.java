package org.example.myWork.logic.command;

import org.example.myWork.logic.*;
import org.example.myWork.parser.CommandDescription;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Component
public class CommandFactory implements Supplier<Map<String, Consumer<CommandDescription>>> {
    private final Map<String, Consumer<CommandDescription>> commands;

    public CommandFactory(final IErrorHandler errorHandler) {
        Map<String, Consumer<CommandDescription>> map = new HashMap<>();
        ITaskDao taskDao = new TaskDao();
        ITaskPrinter taskPrinter = new TaskPrinter();
        Predicate<CommandDescription> taskIdValidator = new TaskIdValidator(errorHandler);

        Consumer<CommandDescription> command = new AddCommand(taskDao, errorHandler);
        map.put(AddCommand.COMMAND, command);

        command = new DeleteCommand(taskDao, errorHandler);
        map.put(DeleteCommand.COMMAND, command);

        command = new QuitCommand();
        map.put(QuitCommand.COMMAND, command);

        command = new ToggleCommand(taskDao, errorHandler);
        map.put(ToggleCommand.COMMAND, command);

        command = new EditCommand(taskDao, errorHandler);
        map.put(EditCommand.COMMAND, command);

        command = new SearchCommand(taskDao, errorHandler, taskPrinter);
        map.put(SearchCommand.COMMAND, command);

        command = new PrintCommand(taskDao, errorHandler, taskPrinter);
        map.put(PrintCommand.COMMAND, command);

        this.commands = Collections.unmodifiableMap(map);
    }

    @Override
    public Map<String, Consumer<CommandDescription>> get() {
        return commands;
    }
}
