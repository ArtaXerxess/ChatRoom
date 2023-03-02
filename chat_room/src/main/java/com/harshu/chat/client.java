package com.harshu.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

class receiver extends Thread {
    private Socket client = null;

    public receiver(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            DataInputStream din = new DataInputStream(client.getInputStream());
            String message = "";
            while (true) {
                message = din.readUTF();
                System.out.println(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class sender extends Thread {
    private Socket client = null;
    private String name;

    public sender(Socket client, String name) {
        this.client = client;
        this.name = name;
    }

    @Override
    public void run() {
        try {
            Scanner scan = new Scanner(System.in);
            DataOutputStream dout = new DataOutputStream(client.getOutputStream());
            String message = "";
            System.out.print("You may begin chatting");
            while (true) {

                message = name + " : " + scan.nextLine();
                if (message.equals("!exit")) {
                    break;
                }
                dout.writeUTF(message);
                dout.flush();
            }
            dout.close();
            scan.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

public class client {
    public static void main(String[] args) {
        /* Enter you name here before running -> */String name = "Harsh"; 
        try {
            Socket client = new Socket("localhost", 9999);
            receiver receiverthread = new receiver(client);
            receiverthread.start();
            sender senderthread = new sender(client, name);
            senderthread.start();
            if (!receiverthread.isAlive() && !senderthread.isAlive()) {
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
