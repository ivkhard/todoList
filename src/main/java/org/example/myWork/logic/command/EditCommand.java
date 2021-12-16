package org.example.myWork.logic.command;

import org.example.myWork.logic.IErrorHandler;
import org.example.myWork.logic.ITaskDao;
import org.example.myWork.model.Task;
import org.example.myWork.parser.CommandDescription;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("edit")
public class EditCommand extends BaseCommand {

    protected EditCommand(ITaskDao taskDao, IErrorHandler errorHandler) {
        super(taskDao, errorHandler);
    }

    @Override
    public void accept(CommandDescription commandDescription) {
        String text = commandDescription.getText();
        long taskId = commandDescription.getTaskId();
        if (text == null || text.isEmpty() || taskId == 0) {
            errorHandler.handle("Не указан id задачи");
            return;
        }
        Optional<Task> task = taskDao.get(taskId);
        if (task.isPresent()) {
            task.get().setDescription(text);
        } else {
            errorHandler.handle("Задачи с таким id не существует");;
        }
    }
}
