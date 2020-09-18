package Commands;

/**
 *  Команда print_field_ascending_description
 */
public class PrintAscendingDescCommand extends Command {

    @Override
    public String execute(String[] arguments) {
        return receiver.printFieldAscendDesc(user);
    }

    @Override
    public int needArguments() {
        return 0;
    }

    @Override
    public String manual() {
        return "Вывести значения всех элементов поля description в порядке возрастания.";
    }

    @Override
    public String commandName() {
        return "print_field_ascending_description";
    }
}
