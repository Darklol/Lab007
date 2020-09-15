package Server;

import App.*;
import Commands.*;
import Util.SerializationManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Scanner;

/**
 * По паттерну "команда"
 * Класс, вызывающий команды
 */
public class Server {

    private DatagramChannel channel;
    private SocketAddress address;
    private byte[] buffer;
    private Receiver receiver;
    private RegisteredCommands commands;
    public final int BUFFER_SIZE = 65536;

    /**
     * стандартный конструктор, устанавливающий экземпляр ресивера и инициализирующий коллекцию команд
     * @param receiver
     */
    public Server(Receiver receiver){
        this.receiver = receiver;
        commands = new RegisteredCommands();
        buffer = new byte[BUFFER_SIZE];
    }


    public void connect(int port) throws IOException {
        address = new InetSocketAddress(port);
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.bind(address);
    }


    public void run() {
        try {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
                do {
                    if (System.in.available()>0) {switch (scanner.nextLine().trim()){
                        case "exit": System.out.println("Завершение работы сервера");
                                    System.exit(0);
                        case "save": SaveCommand save = new SaveCommand(receiver);
                                    save.execute(new String[1]);
                    }}
                    address = channel.receive(byteBuffer);
                } while (address == null);
                Request request = SerializationManager.readObject(buffer);
                System.out.println("Сервер получил команду " + request.getCommand().commandName());
                Command command = request.getCommand();
                command.setReceiver(receiver);
                receiver.setValidator(request.getValidator());
                String result = command.execute(request.getArguments());
                System.out.println("Команда " + command + " выполнена, посылаю ответ клиенту...");
                Response response = new Response(result);
                byte[] answer = SerializationManager.writeObject(response);
                byteBuffer = ByteBuffer.wrap(answer);
                channel.send(byteBuffer, address);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Сервер ожидает команду, а клиент отправляет нечто неизвестное...");
        } catch (IOException e) {
            System.out.println("Проблемы с подключением...");
            e.printStackTrace();
        } catch (ClassCastException e) {
            System.out.println("Сервер ожидал команду, а получил что-то не то...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
