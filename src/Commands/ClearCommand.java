package Commands;

/**
 *  Команда clear
 */
public class ClearCommand extends Command {

    @Override
    public String execute(String[] arguments) {
        return receiver.clear(user);
    }

    @Override
    public int needArguments() {
        return 0;
    }

    @Override
    public String manual() {
        return "Очистить коллекцию.";
    }

    @Override
    public String commandName() {
        return "clear";
    }
}
