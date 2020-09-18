package Commands;

/**
 *  Команда print_unique_killer
 */
public class PrintUniqueKillerCommand extends Command {

    @Override
    public String execute(String[] arguments) {
        return receiver.printUniqueKiller(user);
    }

    @Override
    public int needArguments() {
        return 0;
    }

    @Override
    public String manual() {
        return "Вывести уникальные значения поля killer всех элементов коллекции.";
    }

    @Override
    public String commandName() {
        return "print_unique_killer";
    }
}
