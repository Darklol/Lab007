package Commands;

import App.Receiver;

import java.util.Scanner;

public class LoginCommand extends Command{
    public LoginCommand(){}
    public LoginCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public String execute(String[] arguments) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя пользователя:\n");
        String username = scanner.nextLine();
        System.out.println("Введите пароль:\n");
        String password = scanner.nextLine();
        try {
            return receiver.testLogin(username,password);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "Неправильный ввод аргумента!";
        }

    }

    @Override
    public int needArguments() {
        return 0;
    }

    @Override
    public String manual() {
        return "Добавить новый элемент с заданным ключом.";
    }

    @Override
    public String commandName() {
        return "login";
    }


}

