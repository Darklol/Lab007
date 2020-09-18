package Server;

import App.Receiver;
import java.io.IOException;
import java.sql.SQLException;

public class ServerMain {

        public static void main(String[] args) {
            try {
                if (args.length == 0) throw new IllegalArgumentException();
                int port = Integer.parseInt(args[0]);
                Receiver receiver = new Receiver();
                receiver.getCollectionFromDataBase();
                System.out.println("Серверное приложение запущено...");
                Server server = new Server(receiver);
                server.connect(port);
                server.run();
            } catch (IllegalArgumentException e) {
                System.out.println("Для запуска введите порт в виде аргумента командной строки!");
            } catch (IOException e) {
                System.out.println("Проблемы с подключением...");
            } catch (SQLException e) {
                System.out.println("Что-то пошло не так с загрузкой коллекции.");
                e.printStackTrace();
            }
        }
}
