package org.example.myWork.logic.command;

import org.example.myWork.logic.IErrorHandler;
import org.example.myWork.logic.ITaskDao;
import org.example.myWork.parser.CommandDescription;
import org.springframework.stereotype.Component;

@Component("delete")
public class DeleteCommand extends BaseCommand {

    protected DeleteCommand(ITaskDao taskDao, IErrorHandler errorHandler) {
        super(taskDao, errorHandler);
    }

    @Override
    public void accept(CommandDescription commandDescription) {
        taskDao.delete((int) commandDescription.getTaskId());
    }
}
