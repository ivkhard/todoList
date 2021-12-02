package org.example.myWork;

import java.util.Scanner;

public interface ICommand {
    String getCommandName();
    void process(Scanner scanner);
}
