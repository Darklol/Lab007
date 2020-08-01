package Commands;

import App.Receiver;

import java.io.IOException;

/**
 *  Команда save
 */
public class SaveCommand extends Command{
    public SaveCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public String execute(String[] arguments) {
        if (arguments.length >= needArguments()) {
            try {
                try {
                    receiver.save();
                    return "Коллекция успешно сохранена!";
                } catch (IOException e) {
                    System.out.println("Не удалось создать файл. Возможно, не хватает прав на запись в директории.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Коллекция повреждена! Сохранение невозможно.");
            }
        } else {
            System.out.println("Недостаточно аргументов для выполнения команды! " +
                    "(Требуемое количество: " + needArguments() + ")");
        }
        return "Ошибка при сохранении коллекции";
    }

    @Override
    public int needArguments() {
        return 0;
    }

    @Override
    public String manual() {
        return "Сохранить коллекцию в файл.";
    }

    @Override
    public String commandName() {
        return "save";
    }
}
