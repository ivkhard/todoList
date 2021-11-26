package org.example.myWork.logic;

public class ErrorHandlerImpl implements ErrorHandler {
    private boolean helper;

    @Override
    public void handle(String error) {
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
