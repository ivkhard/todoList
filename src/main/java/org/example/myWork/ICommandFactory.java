package org.example.myWork;

import org.example.myWork.parser.CommandDescription;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface ICommandFactory {
    ICommand getCommand(String command);
}
