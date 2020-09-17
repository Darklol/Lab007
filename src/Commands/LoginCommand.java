package Commands;

import java.util.Scanner;

public class LoginCommand extends Command{

    @Override
    public String execute(String[] arguments) {
        try {
            return receiver.login(user);
        } catch (IllegalArgumentException e) {
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

