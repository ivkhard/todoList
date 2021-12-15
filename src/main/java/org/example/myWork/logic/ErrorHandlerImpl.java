package org.example.myWork.logic;

import org.springframework.stereotype.Component;

@Component
public class ErrorHandlerImpl implements IErrorHandler {
    private boolean helper;

    @Override
    public void handle(String error) {
        System.err.println(error);
        if (helper) {
            return;
        }
        System.out.println("Допустимые команды: \n" +
                "\tadd <описание задачи>\n" +
                "\tprint [all]\n" +
                "\tsearch <строка поиска задачи>\n" +
                "\ttoggle <идентификатор задачи>\n" +
                "\tdelete <идентификатор задачи>\n" +
                "\tedit <идентификатор задачи>\n" +
                "\tquit");
        helper = true;
    }
}
