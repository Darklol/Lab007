package Commands;

import App.Receiver;

import java.io.IOException;

/**
 *  Команда insert
 */
public class InsertCommand extends Command {

    @Override
    public String execute(String[] arguments) {
        if (arguments.length<needArguments()) return "У команды должны быть аргументы!";
        try {
            return receiver.insert(user);
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
        return "insert";
    }


}
