package Commands;

import App.Receiver;

/**
 *  Команда min_by_name
 */
public class MinByNameCommand extends Command {

    @Override
    public String execute(String[] arguments) {
        if (arguments.length<needArguments()) return "У команды должны быть аргументы!";
        return receiver.minByName(user);
    }

    @Override
    public int needArguments() {
        return 0;
    }

    @Override
    public String manual() {
        return "Вывести любой объект из коллекции, значения поля name которого является минимальным.";
    }

    @Override
    public String commandName() {
        return "min_by_name";
    }
}
