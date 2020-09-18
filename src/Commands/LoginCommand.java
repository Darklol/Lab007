package Commands;

public class LoginCommand extends Command{

    @Override
    public String execute(String[] arguments) {
        try {
            return receiver.login(user);
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
        return "Войти в учётную запись";
    }

    @Override
    public String commandName() {
        return "login";
    }


}

