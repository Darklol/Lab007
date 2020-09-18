package Server;

import App.Receiver;
import App.Request;
import App.Response;
import Commands.Command;
import Util.SerializationManager;
import Util.UserValidator;
import lombok.AllArgsConstructor;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;

@AllArgsConstructor
public class HandlerThread extends Thread {
    private Receiver receiver;
    private byte[] data;
    private SocketAddress socketAddress;
    private DatagramChannel datagramChannel;

    @Override
    public void run() {
        try {
            Request request = SerializationManager.readObject(data);
            Command command = request.getCommand();
            command.setReceiver(receiver);
            receiver.setValidator(request.getValidator());
            String[] arguments = request.getArguments();
            UserValidator user = command.getUser();
            System.out.println("Сервер получил команду " + command.commandName());
            String result = null;
            try {
                result = command.execute(arguments);
                System.out.println("Команда " + command + " выполнена, посылаю ответ клиенту...");
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            Response response = new Response(result);
            new SenderThread(datagramChannel, socketAddress, response).start();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            System.out.println("Клиент покинул сессию, завершение работы потока обработки "+ this.getName());
        }
    }
}
