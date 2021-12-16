package org.example.myWork.logic.command;

import org.example.myWork.logic.IErrorHandler;
import org.example.myWork.logic.ITaskDao;
import org.example.myWork.logic.ITaskPrinter;
import org.example.myWork.model.Task;
import org.example.myWork.parser.CommandDescription;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component("search")
public class SearchCommand extends BaseCommand{
    private final ITaskPrinter printer;

    public SearchCommand(ITaskDao taskDao, IErrorHandler errorHandler, ITaskPrinter printer) {
        super(taskDao, errorHandler);
        this.printer = printer;
    }

    @Override
    public void accept(CommandDescription commandDescription) {
        String line = commandDescription.getArgs();
        if (line != null && line.length() == 0) {
            errorHandler.handle("Необходимо ввести строку поиска задачи");
        }
        Stream<Task> tasks = taskDao.find(line, false);
        printer.print(tasks);
    }
}
