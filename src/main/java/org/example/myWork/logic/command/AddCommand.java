package org.example.myWork.logic.command;

import org.example.myWork.logic.IErrorHandler;
import org.example.myWork.logic.TaskDao;
import org.example.myWork.model.Task;
import org.example.myWork.parser.CommandDescription;


public class AddCommand extends BaseDao {
    public static final String COMMAND = "add";

    protected AddCommand(TaskDao taskDao, IErrorHandler IErrorHandler) {
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
