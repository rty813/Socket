import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.*;

/**
 * Created by zhang on 16-11-28.
 */
public class ServerListener extends Thread {
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            while (true) {
                Socket socket = serverSocket.accept();  //block
                System.out.println(socket.getInetAddress() + " connect");
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                toolkit.beep();
                new ChatSocket(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
