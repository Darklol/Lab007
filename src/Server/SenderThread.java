package Server;

import App.Response;
import Util.SerializationManager;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

@AllArgsConstructor
public class SenderThread extends Thread {
    private DatagramChannel datagramChannel;
    private SocketAddress socketAddress;
    private Response response;

    @SneakyThrows
    @Override
    public void run() {
        try {
            byte[] data = SerializationManager.writeObject(response);
            ByteBuffer byteBuffer = ByteBuffer.wrap(data);
            datagramChannel.send(byteBuffer, socketAddress);
        } catch (IOException e){
            datagramChannel.send(ByteBuffer.wrap(SerializationManager.writeObject(response)), socketAddress);
        }
    }
}
