package Commands;

/**
 *  Команда help
 */
public class HelpCommand extends Command {

    @Override
    public String execute(String[] arguments) {
        return receiver.help(user);
    }

    @Override
    public int needArguments() {
        return 0;
    }

    @Override
    public String manual() {
        return "Вывести справку по доступным командам.";
    }

    @Override
    public String commandName() {
        return "help";
    }
}
