package Server;

import App.Receiver;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;

public class ServerMain {

        public static void main(String[] args) {
            try {
                if (args.length == 0) throw new IllegalArgumentException();
                int port = Integer.parseInt(args[0]);
                Receiver receiver = new Receiver();
                try {
                receiver.getFile("input.json");
                } catch (IOException  e) {
                    System.out.println("Коллекция не была загружена.\n" +
                            "Возможные причины: файл по данному пути не найден, нет прав на чтение из директории. \n\n" +
                            "Запуск программы без данных из файла.");
                } catch (JsonSyntaxException e) {
                    System.out.println("Коллекция не была загружена.\nОшибка чтения синтаксиса Json.\n" +
                            "Данные в файле не соответствуют данным коллекции, либо коллекция содержи два " +
                            "элемента с одинаковыми ключами.\n\n" +
                            "Запуск программы без данных из файла.\n");
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Коллекци не была загружена.\n" +
                            "Отсутствуют аргументы командной строки.\n" +
                            "Запуск программы без данных из файла");
                }
                System.out.println("Серверное приложение запущено...");
                Server server = new Server(receiver);
                server.connect(port);
                server.run();
            } catch (IllegalArgumentException e) {
                System.out.println("Для запуска введите порт в виде аргумента командной строки!");
            } catch (IOException e) {
                System.out.println("Проблемы с подключением...");
            }
        }
}
