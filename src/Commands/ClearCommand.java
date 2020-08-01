package Commands;

import App.Receiver;

/**
 *  Команда clear
 */
public class ClearCommand extends Command {
    public ClearCommand(){}
    public ClearCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public String execute(String[] arguments) {
        return receiver.clear();
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
