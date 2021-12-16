package org.example.myWork.logic.command;

import org.example.myWork.logic.IErrorHandler;
import org.example.myWork.logic.ITaskDao;
import org.example.myWork.model.Task;
import org.example.myWork.parser.CommandDescription;
import org.springframework.stereotype.Component;

@Component("add")
public class AddCommand extends BaseCommand {

    protected AddCommand(ITaskDao taskDao, IErrorHandler IErrorHandler) {
        super(taskDao, IErrorHandler);
    }

    @Override
    public void accept(CommandDescription commandDescription) {
        String args = commandDescription.getArgs();
        if (args == null || args.isEmpty()) {
            errorHandler.handle("Необходимо ввести описание задачи");
        } else {
            taskDao.save(Task.of(args));
        }
    }
}
