package org.example.myWork.logic.command;

import org.example.myWork.logic.IErrorHandler;
import org.example.myWork.logic.ITaskDao;
import org.example.myWork.parser.CommandDescription;




public class DeleteCommand extends BaseCommand {
    public static final String COMMAND = "delete";

    protected DeleteCommand(ITaskDao taskDao, IErrorHandler errorHandler) {
        super(taskDao, errorHandler);
    }

    @Override
    public void accept(CommandDescription commandDescription) {
        taskDao.delete((int) commandDescription.getTaskId());
    }
}
