package org.example.myWork.logic.command;

public interface ICommandFactory {
    BaseCommand getCommand(String name);
}
