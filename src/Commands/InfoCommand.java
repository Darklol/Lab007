package Commands;

/**
 *  Команда info
 */
public class InfoCommand extends Command {

    @Override
    public String execute(String[] arguments) {
        return receiver.info(user);
    }

    @Override
    public int needArguments() {
        return 0;
    }

    @Override
    public String manual() {
        return "Вывести информацию о коллекции.";
    }

    @Override
    public String commandName() {
        return "info";
    }
}
