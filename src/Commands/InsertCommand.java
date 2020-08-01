package Commands;

import App.Receiver;

import java.io.IOException;

/**
 *  Команда insert
 */
public class InsertCommand extends Command {
    public InsertCommand(){}
    public InsertCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public String execute(String[] arguments) {
        if (arguments.length<needArguments()) return "У команды должны быть аргументы!";
        try {
            return receiver.insert(Long.parseLong(arguments[0]));
        } catch (IllegalArgumentException e) {
        }
        return "Неправильный ввод аргумента!";
    }

    @Override
    public int needArguments() {
        return 1;
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
