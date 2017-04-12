import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;


/**
 * Created by zhang on 16-11-28.
 */
public class ChatSocket{

    public ChatSocket(Socket socket) throws IOException {
        new SocketReader(socket).start();
        new SocketWriter(socket).start();
    }
}

class SocketReader extends Thread {
    private Socket socket;
    private DataInputStream inputStream;
    public SocketReader(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Receive: " + inputStream.readUTF());
            } catch (IOException e) {
//                e.printStackTrace();
                log.println(socket.getInetAddress() + " Disconnect");
                return;
            }
        }
    }
}

class SocketWriter extends  Thread {
    private Socket socket;
    private DataOutputStream outputStream;
    public SocketWriter(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        Scanner in = new Scanner(System.in);
        try {
            while (true){
                outputStream.writeUTF(in.nextLine());
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
