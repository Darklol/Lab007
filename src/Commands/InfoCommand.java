package Commands;

import App.Receiver;

/**
 *  Команда info
 */
public class InfoCommand extends Command {
    public InfoCommand(){}
    public InfoCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public String execute(String[] arguments) {
        return receiver.info();
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
