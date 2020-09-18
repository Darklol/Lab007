package Commands;

/**
 *  Команда remove_if_greater_key
 */
public class RemoveGreaterKeyCommand extends Command {

    @Override
    public String execute(String[] arguments) {
        if (arguments.length<needArguments()) return "У команды должны быть аргументы!";
        try {
            return receiver.removeGreaterKey(Long.parseLong(arguments[0]), user);
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
        return "Удалить из коллекции все элементы, ключ которых превышает заданный.";
    }

    @Override
    public String commandName() {
        return "remove_greater_key";
    }
}
