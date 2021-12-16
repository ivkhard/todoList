package org.example.myWork.logic.command;

import org.example.myWork.logic.IErrorHandler;
import org.example.myWork.logic.ITaskDao;
import org.example.myWork.model.Task;
import org.example.myWork.parser.CommandDescription;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("toggle")
public class ToggleCommand extends BaseCommand {

    protected ToggleCommand(ITaskDao taskDao, IErrorHandler errorHandler) {
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
