package org.example.myWork.logic.command;

import org.example.myWork.logic.ErrorHandler;
import org.example.myWork.logic.TaskDao;
import org.example.myWork.logic.TaskPrinter;
import org.example.myWork.model.Task;
import org.example.myWork.parser.CommandDescription;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class PrintCommand extends BaseDaoCommand implements Consumer<CommandDescription> {
    public static final String COMMAND = "print";

    private final TaskPrinter printer;

    protected PrintCommand(TaskDao taskDao, ErrorHandler errorHandler, TaskPrinter printer) {
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

