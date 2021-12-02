package org.example.myWork;

import org.example.myWork.logic.CommandConsumer;
import org.example.myWork.logic.IErrorHandler;
import org.example.myWork.logic.ErrorHandlerImpl;
import org.example.myWork.logic.command.CommandFactory;
import org.example.myWork.parser.CommandDescription;
import org.example.myWork.parser.CommandParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ToDoList {

    public static void main(String[] args) {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        final Function<String, CommandDescription> parser = new CommandParser();
        final IErrorHandler IErrorHandler = new ErrorHandlerImpl();
        final Supplier<Map<String, Consumer<CommandDescription>>> commandFactory = new CommandFactory(IErrorHandler);
        final Consumer<CommandDescription> consumer = new CommandConsumer(commandFactory, IErrorHandler);
        reader.lines().map(parser).forEach(consumer);
    }
}


