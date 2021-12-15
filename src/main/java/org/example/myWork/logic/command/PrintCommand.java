package org.example.myWork.logic.command;

import org.example.myWork.logic.IErrorHandler;
import org.example.myWork.logic.ITaskDao;
import org.example.myWork.logic.ITaskPrinter;
import org.example.myWork.model.Task;
import org.example.myWork.parser.CommandDescription;
import org.springframework.stereotype.Component;


import java.util.stream.Stream;

@Component
public class PrintCommand extends BaseCommand {
    public static final String COMMAND = "print";

    private final ITaskPrinter printer;

    protected PrintCommand(ITaskDao taskDao, IErrorHandler errorHandler, ITaskPrinter printer) {
        super(taskDao, errorHandler);
        this.printer = printer;
    }

    @Override
    public void accept(CommandDescription commandDescription) {
        String line = commandDescription.getArgs();
        boolean all = false;
        if (line != null) {
            all = line.equals("all");
            if (!all) {
                errorHandler.handle("Недопустимый аргумент команды");
            }
        }
        Stream<Task> tasks = taskDao.find(null, !all);
        printer.print(tasks);
    }
}

