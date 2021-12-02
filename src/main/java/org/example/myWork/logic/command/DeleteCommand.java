package org.example.myWork.logic.command;

import org.example.myWork.logic.IErrorHandler;
import org.example.myWork.logic.TaskDao;
import org.example.myWork.parser.CommandDescription;




public class DeleteCommand extends BaseDao {
    public static final String COMMAND = "delete";

    protected DeleteCommand(TaskDao taskDao, IErrorHandler errorHandler) {
        super(taskDao, errorHandler);
    }

    @Override
    public void accept(CommandDescription commandDescription) {
        taskDao.delete((int) commandDescription.getTaskId());
    }
}
