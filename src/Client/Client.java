package Client;

import App.Request;
import App.Response;
import Commands.Command;
import Util.SerializationManager;
import Data.DragonValidator;
import App.RegisteredCommands;
import Util.UserValidator;
import java.io.IOException;
import java.net.*;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * По паттерну "команда"
 * Класс, вызывающий команды
 */
public class Client {

    private Scanner scanner = new Scanner(System.in);
    private RegisteredCommands commands;
    private DatagramSocket socket;
    private SocketAddress socketAddress;
    private UserValidator user = null;
    public final int BUFFER_SIZE = 65536;
    private boolean isConnected;

    /**
     * стандартный конструктор, устанавливающий экземпляр ресивера и инициализирующий коллекцию команд
     *
     * @param
     */

    public void connect(String host, int port) throws IOException {
        try {
            InetAddress address = InetAddress.getByName(host);
            if (address == null) throw new NullPointerException();
            System.out.println("Подключение к " + address);
            socketAddress = new InetSocketAddress(address, port);
            socket = new DatagramSocket();
            socket.connect(socketAddress);
        } catch (NullPointerException e) {
            System.out.println("Введенного адреса не существует!");
        }
    }


    public void inputSendAndGetAnswer() throws IOException {
        try {
            String string = null;
            try {
                string = scanner.nextLine().trim();
            } catch (NoSuchElementException e) {
                System.out.println("Why are you so cruel...");
                System.exit(0);
            }
            String[] input = string.split("\\s+");
            String[] arguments = new String[input.length - 1];
            System.arraycopy(input, 1, arguments, 0, arguments.length);
            if (input[0].equals("exit")) {
                System.out.println("Завершение работы клиента.");
                Request exit_request = new Request(null, null);
                byte[] requestInBytes = SerializationManager.writeObject(exit_request);
                DatagramPacket packet = new DatagramPacket(requestInBytes, requestInBytes.length, socketAddress);
                socket.send(packet);
                System.exit(0);
            }
            if (!RegisteredCommands.getCommandsName().containsKey(input[0])) {
                System.out.println("Такой команды не существует. Проверьте правильность ввода команды.");
                return;
            }
            DragonValidator validator = new DragonValidator();
            if (RegisteredCommands.getCommandsWithDragons().contains(input[0])) {
                try {
                    validator.validate(new Scanner(System.in), System.out);
                } catch (IllegalArgumentException e) {
                    System.out.println("Что-то пошло не так...");
                }
            }
            if (RegisteredCommands.getAuthorizationCommands().contains(input[0])) {
                boolean wrongInput = false;
                System.out.println("Введите имя пользователя: ");
                String username = null;
                String password = null;
                while (!wrongInput) {
                    username = scanner.nextLine();
                    if (username == null || username.length() > 20) {
                        System.out.println("Длина имени пользователя должна быть от 0 до 20 символов!");
                    } else {
                        wrongInput = true;
                    }
                }
                wrongInput = false;
                System.out.println("Введите пароль");
                while (!wrongInput) {
                    password = scanner.nextLine();
                    if (password == null || password.length() > 20) {
                        System.out.println("Длина пароля должна быть от 0 до 20 символов!");
                    } else {
                        wrongInput = true;
                    }
                }
                user = new UserValidator(username, password);
            }
            validator.setOwnerName(user.getUsername());
            Command command = RegisteredCommands.getCommandsName().get(input[0]);
            command.setUsername(user);
            Request request = new Request(command, arguments);
            request.setValidator(validator);
            byte[] requestInBytes = SerializationManager.writeObject(request);
            DatagramPacket packet = new DatagramPacket(requestInBytes, requestInBytes.length, socketAddress);
            socket.send(packet);
            System.out.println("Попытка отправки запроса...");
            byte[] answerInBytes = new byte[BUFFER_SIZE];
            packet = new DatagramPacket(answerInBytes, answerInBytes.length);
            socket.receive(packet);
            Response response = SerializationManager.readObject(answerInBytes);
            System.out.println("Получен ответ от сервера: ");
            System.out.println(response.getAnswer());
            if (response.getAnswer().equals("Ошибка входа! Неверный логин или пароль.")
                    || response.getAnswer().equals("Пользователь с таким именем уже зарегистрирован!"))
                user = null;
        } catch (IOException e) {
            System.out.println("Сервер в данный момент недоступен...");
            isConnected = socket.isConnected();
        } catch (ClassNotFoundException e) {
            System.out.println("Клиент ждал ответ в виде Response, а получил что-то непонятное...");
        } catch (NullPointerException e){
            System.out.println("Вы не можете отправлять команды на сервер, пока не авторизуетесь.");
        }

    }


    public boolean isConnected() {
        return isConnected;
    }
}
