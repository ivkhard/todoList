package org.example.myWork.logic.command;

import org.example.myWork.logic.IErrorHandler;
import org.example.myWork.logic.ITaskDao;
import org.example.myWork.logic.ITaskPrinter;
import org.example.myWork.model.Task;
import org.example.myWork.parser.CommandDescription;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("print")
public class PrintCommand extends BaseCommand {

    private final ITaskPrinter printer;

    protected PrintCommand(ITaskDao taskDao, IErrorHandler errorHandler, ITaskPrinter printer) {
        super(taskDao, errorHandler);
        this.printer = printer;
    }

    @Override
    public void accept(CommandDescription commandDescription) {
        String line = commandDescription.getArgs();
        try {
            if (line.equals("all")) {
                printer.print(taskDao.find(null, false));
            } else {
                int index = Integer.parseInt(line);
                Optional<Task> tasks = taskDao.get(index);
                printer.print(tasks);
            }
        } catch (NumberFormatException e) {
            errorHandler.handle("Недопустимый аргумент команды");
        }
    }
}

