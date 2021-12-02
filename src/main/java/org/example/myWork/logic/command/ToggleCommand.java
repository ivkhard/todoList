package org.example.myWork.logic.command;

import org.example.myWork.logic.IErrorHandler;
import org.example.myWork.logic.TaskDao;
import org.example.myWork.model.Task;
import org.example.myWork.parser.CommandDescription;


import java.util.Optional;


public class ToggleCommand extends BaseDao {
    public static final String COMMAND = "toggle";

    protected ToggleCommand(TaskDao taskDao, IErrorHandler errorHandler) {
        super(taskDao, errorHandler);
    }

    @Override
    public void accept(CommandDescription commandDescription) {
        Optional<Task> t = taskDao.get(commandDescription.getTaskId());
        if (t.isPresent()) {
            Task task = t.get();
            task.setDone(!task.isDone());
        }
    }
}
