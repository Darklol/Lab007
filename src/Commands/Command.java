package Commands;

import App.Receiver;
import Data.DragonValidator;

import java.io.Serializable;

/**
 *  Абстрактный класс Команда
 *  Описывает общее поведение всех команд
 */
public abstract class Command implements Serializable {
    /**
     * абстрактный класс команды
     * определяет общее поведение всех команд
     */
    protected Receiver receiver;

    public abstract String execute(String[] arguments);

    public Command(){};

    public Command(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Метод, который показывает, сколько аргуметов требуется команду
     *
     * @return
     */
    public abstract int needArguments();

    /**
     * Метод возвращающий описание команды
     * Нужен для команды help
     *
     * @return
     */
    public abstract String manual();

    /**
     * Метод, возвращающий строковое имя команды
     *
     * @return
     */
    public abstract String commandName();

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }
}
