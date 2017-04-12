package com.zhang;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class Main{
    private static Socket socket;
    private static BufferedReader reader;
    private static BufferedWriter writer;

    public static void main(String[] args) throws IOException {
        System.out.print("Service address: ");
        Scanner in = new Scanner(System.in);
        String ServiceIp = in.nextLine();
        System.out.print("Port: ");
        int ServicePort = in.nextInt();
        socket = new Socket(ServiceIp, ServicePort);
        new Thread(new SocketListener(socket)).start();
        new Thread(new SocketWriter(socket)).start();
    }
}

class SocketListener implements Runnable{
    private Socket socket;
    private DataInputStream is = null;
    public SocketListener(Socket socket) throws IOException {
        this.socket = socket;
        is = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("接收到消息：" + is.readUTF());
            } catch (IOException e) {
                log.println("服务器已关闭！");
                System.exit(0);
            }
        }
    }
}

class SocketWriter implements Runnable{
    private Socket socket;
    private DataOutputStream os = null;
    @Override
    public void run() {
        try {
            System.out.println("已成功建立连接！");
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            toolkit.beep();
            while (true) {
                Scanner in = new Scanner(System.in);
                String string = in.nextLine();
                os.writeUTF(string);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SocketWriter(Socket socket) throws IOException {
        this.socket = socket;
        os = new DataOutputStream(socket.getOutputStream());
    }
}
