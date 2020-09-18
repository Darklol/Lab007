package Commands;

public class RegisterCommand extends Command{

    @Override
    public String execute(String[] arguments) {
        try {
            return receiver.registration(user);
        } catch (IllegalArgumentException e) {
            return "Неправильный ввод аргумента!";
        }

    }

    @Override
    public int needArguments() {
        return 0;
    }

    @Override
    public String manual() {
        return "Создать новую учётную запись";
    }

    @Override
    public String commandName() {
        return "register";
    }


}

