package org.example.myWork.logic;

import org.example.myWork.parser.CommandDescription;
import org.springframework.stereotype.Component;


import java.util.function.Predicate;

@Component
public class TaskIdValidator implements Predicate<CommandDescription> {
    private final IErrorHandler errorHandler;

    public TaskIdValidator(IErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    @Override
    public boolean test(CommandDescription commandDescription) {
        if (commandDescription.getTaskId() == 0) {
            errorHandler.handle("Не указан id задачи");
            return false;
        }
        return true;
    }
}
