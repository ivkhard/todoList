import java.util.Scanner;

public class ToDoList {
    private static String description = null;
    private static boolean done = false;
    static final int ID = 1;
    private static boolean helper = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String action = scanner.next();
            if (action.equals("add")) {
                add(scanner);
            } else if (action.equals("print")) {
                print(scanner);
            } else if (action.equals("toggle")) {
                toggle(scanner);
            } else if (action.equals("quit")) {
                quit();
            } else {
                System.out.println("Данной команды не существует");
                help();
            }
        }
    }

    private static void add(Scanner scanner) {
        String line = scanner.nextLine().trim();
        if (line.length() == 0) {
            System.out.println("Введите описание задачи");
            help();
            return;
        }
        description = line;
        done = false;
    }

    //вывод списка дел
    private static void print(Scanner scanner) {
        String line = scanner.nextLine().trim();
        boolean all = line.equals("all");
        if (line.length() > 0 && !all) {
            wrongArgument();
            return;
        }
        if (description != null && !done || all) {
            System.out.printf("%d. [%s] %s\n", ID, done ? "x" : " ", description);
        }
    }

    //переключение состояния дела - выполнено / не выпослено
    private static void toggle(Scanner scanner) {
        String line = scanner.nextLine().trim();
        if (line.length() == 0) {
            System.out.println("Не указан id задачи");
            help();
            return;
        }
        int id;
        try {
            id = Integer.parseInt(line);
        } catch (NumberFormatException ex) {
            wrongArgument();
            return;
        }
        if (description == null && id != ID) {
            System.out.println("Задачи с таким id не существует");
            help();
            return;
        }
        done = !done;
    }

    //завершение программы
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
                "\ttoggle <идентификатор задачи>\n" +
                "\tquit");
        helper = true;
    }

    private static void wrongArgument() {
        System.out.println("Недопустимый аргумент");
        help();
    }
}


