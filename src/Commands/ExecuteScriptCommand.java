package Commands;

import java.io.IOException;

/**
 * Команда execute
 */
public class ExecuteScriptCommand extends Command{

    @Override
    public String execute(String[] arguments) {
        if (arguments.length<needArguments()) return "У команды должны быть аргументы!";
        try {
            return receiver.executeScript(arguments[0], user);
        } catch (IllegalArgumentException e) {
        } catch (IOException e){
        }
        return "Неправильный ввод аргумента!";
    }

    @Override
    public int needArguments() {
        return 1;
    }

    @Override
    public String manual() {
        return "Считать и исполнить скрипт из указанного файла.";
    }

    @Override
    public String commandName() {
        return "execute_script";
    }


}

