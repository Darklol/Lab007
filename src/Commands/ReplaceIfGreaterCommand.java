package Commands;

/**
 *  Команда replace_if_greater
 */
public class ReplaceIfGreaterCommand extends Command {

    @Override
    public String execute(String[] arguments) {
        if (arguments.length<needArguments()) return "У команды должны быть аргументы!";
        try {
            return receiver.replaceGreater(Long.parseLong(arguments[0]), user);
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
        return "Заменить значение по ключу, если новое значение больше старого.";
    }

    @Override
    public String commandName() {
        return "replace_if_greater";
    }
}
