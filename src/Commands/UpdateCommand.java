package Commands;

/**
 *  Команда update
 */
public class UpdateCommand extends Command {

    @Override
    public String execute(String[] arguments) {
        if (arguments.length<needArguments()) return "У команды должны быть аргументы!";
        try {
            return receiver.update(Long.parseLong(arguments[0]),user);
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
        return "Обновить значение элемента, ID которого равен заданному.";
    }

    @Override
    public String commandName() {
        return "update";
    }
}
