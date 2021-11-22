package org.example.myWork;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.regex.MatchResult;
import java.util.stream.Stream;

public class ToDoList {

    static Map<String, Task> tasks = new LinkedHashMap<>();

    static int ID = 0;
    private static boolean helper = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String action = scanner.next();
            if (action.equals("add")) {
                add(scanner);
            } else if (action.equals("print")) {
                print(scanner);
            } else if (action.equals("search")) {
                search(scanner);
            } else if (action.equals("toggle")) {
                toggle(scanner);
            } else if (action.equals("delete")) {
                delete(scanner);
            } else if (action.equals("edit")) {
                edit(scanner);
            }else if (action.equals("quit")) {
                quit();
            } else {
                System.out.println("Данной команды не существует");
                help();
            }
        }
    }

    private static void edit() {
    }

    private static void add(Scanner scanner) {
        String line = scanner.nextLine().trim();
        if (line.length() == 0) {
            System.out.println("Введите описание задачи");
            help();
            return;
        }
        ID++;
        tasks.put(String.valueOf(ID), Task.of(line));
    }

    private static void print(Scanner scanner) {
        String line = scanner.nextLine().trim();
        boolean all = line.equals("all");
        if (line.length() > 0 && !all) {
            wrongArgument();
            return;
        }
        Stream<Map.Entry<String, Task>> stream = tasks.entrySet().stream();
        if (!all) {
            stream = stream.filter(e -> !e.getValue().isDone());
        }
        stream.forEach(ToDoList::printTask);
    }

    private static void search(Scanner scanner) {
        String line = scanner.nextLine().trim();
        if (line.length() == 0) {
            System.out.println("Введите строку поиска задачи");
            return;
        }
        tasks.entrySet().stream()
                .filter(e -> e.getValue().getDescription().contains(line))
                .forEach(ToDoList::printTask);
    }

    public static void printTask(Map.Entry<String, Task> entry) {
        System.out.printf("%s, [%s] %s\n", entry.getKey(),
                entry.getValue().isDone() ? "x" : " ",
                entry.getValue().getDescription());
    }

    private static void toggle(Scanner scanner) {
        simpleAction(scanner, id -> {
            Task task = tasks.get(id);
            if (task == null) {
                return false;
            }
            task.setDone(!task.isDone());
            return true;
        });
    }

    private static void delete(Scanner scanner) {
        simpleAction(scanner, id -> tasks.remove(id) != null);
    }

    private static void simpleAction(Scanner scanner, Predicate<String> action) {
        String id = scanner.nextLine().trim();
        if (id.length() == 0) {
            System.out.println("Не указан id задачи");
            return;
        }
        if (!action.test(id)) {
            wrongTaskId();
        }
    }

    private static void edit(Scanner scanner) {
        String line = scanner.findInLine("(\\d+)\\s+(\\S+.*)");
        if (line == null) {
            System.out.println("Не указан id задачи");
            help();
        }
        MatchResult matchResult = scanner.match();
        String id = matchResult.group(1);
        Task task = new Task();
        if (task != null) {
            task.setDescription(matchResult.group(2));
        } else {
            wrongTaskId();
        }
    }

    private static void quit() {
        System.exit(0);
    }

    private static void help() {
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

    private static void wrongArgument() {
        System.out.println("Недопустимый аргумент");
        help();
    }

    private static void wrongTaskId() {
        System.out.println("Задачи с таким id не существует");
    }
}


