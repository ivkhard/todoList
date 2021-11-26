package org.example.myWork.logic.command;

import org.example.myWork.logic.ErrorHandler;
import org.example.myWork.logic.TaskDao;
import org.example.myWork.model.Task;
import org.example.myWork.parser.CommandDescription;

import java.util.Optional;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.MatchResult;

public class EditCommand extends BaseDaoCommand implements Consumer<CommandDescription> {
    public static final String COMMAND = "edit";
    protected EditCommand(TaskDao taskDao, ErrorHandler errorHandler) {
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
            errorHandler.handle("Задачи с таким id не существует");
        } else {
            return;
        }
    }
}
