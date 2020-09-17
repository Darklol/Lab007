package Commands;

import App.Receiver;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Scanner;

public class RegisterCommand extends Command{

    @Override
    public String execute(String[] arguments) {
        try {
            return receiver.registration(user);
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
        return "register";
    }


}

