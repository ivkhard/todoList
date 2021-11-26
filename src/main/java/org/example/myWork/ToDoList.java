package org.example.myWork;

import org.example.myWork.logic.CommandConsumer;
import org.example.myWork.logic.ErrorHandler;
import org.example.myWork.logic.ErrorHandlerImpl;
import org.example.myWork.logic.command.CommandFactory;
import org.example.myWork.parser.CommandDescription;
import org.example.myWork.parser.CommandParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class ToDoList {

    public static void main(String[] args) {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        final CommandParser parser = new CommandParser();
        final ErrorHandler errorHandler = new ErrorHandlerImpl();
        final CommandFactory commandFactory = new CommandFactory(errorHandler);
        final Consumer<CommandDescription> consumer = new CommandConsumer(commandFactory, errorHandler);
        reader.lines().map(parser).forEach(consumer);
    }
}


