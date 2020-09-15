package Client;

import App.Request;
import App.Response;
import Util.SerializationManager;
import Data.DragonValidator;
import App.RegisteredCommands;

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
    public final int BUFFER_SIZE = 65536;
    private boolean isConnected;

    /**
     * стандартный конструктор, устанавливающий экземпляр ресивера и инициализирующий коллекцию команд
     * @param
     */
    public Client(){
        commands = new RegisteredCommands();
    }

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
        String[] arguments = new String[input.length-1];
        System.arraycopy(input, 1, arguments, 0, arguments.length);
        if (input[0].equals("exit")) {
            System.out.println("Завершение работы клиента.");
            System.exit(0);
        }
        if (!commands.getCommandsName().containsKey(input[0])){
            System.out.println("Такой команды не существует. Проверьте правильность ввода команды.");
            return;}
        DragonValidator validator = new DragonValidator();
        validator.setId((long)-1);
        if (commands.getCommandsWithDragons().contains(input[0])
                && arguments.length>0) {
            try {
                validator.setId(Long.parseLong(arguments[0]));
                validator.validate(new Scanner(System.in), System.out);
            } catch (IllegalArgumentException e) {
            }
        }
        Request request = new Request(commands.getCommandsName().get(input[0]), arguments);
        if (!validator.getId().equals((long)-1)) request.setValidator(validator);
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
    } catch (IOException e) {
        System.out.println("Сервер в данный момент недоступен...");
        isConnected = socket.isConnected();
    } catch (ClassNotFoundException e) {
        System.out.println("Клиент ждал ответ в виде Response, а получил что-то непонятное...");
    }

    }


    public boolean isConnected() {
        return isConnected;
    }
}
