package Commands;

import App.Receiver;

/**
 *  Команда remove_key
 */
public class RemoveKeyCommand extends Command {

    @Override
    public String execute(String[] arguments) {
        if (arguments.length<needArguments()) return "У команды должны быть аргументы!";
        try {
            return receiver.remove(Long.parseLong(arguments[0]), user);
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
        return "Удалить элемент коллекции по его ключу.";
    }

    @Override
    public String commandName() {
        return "remove_key";
    }
}
