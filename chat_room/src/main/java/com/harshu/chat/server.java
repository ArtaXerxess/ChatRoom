package com.harshu.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

class Guddu implements Runnable {
    private ArrayList<Socket> connectionList = null;
    private Socket client = null;
    private DataOutputStream dout = null;
    private DataInputStream din = null;

    public Guddu(Socket client, ArrayList<Socket> connectionList) {
        this.client = client;
        this.connectionList = connectionList;
    }

    public void broadcast(String message) {
        for (Socket socket : connectionList) {
            if (socket == client) {
                continue;
            }
            try {
                dout = new DataOutputStream(socket.getOutputStream());
                dout.writeUTF(message);
                dout.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        try {
            din = new DataInputStream(client.getInputStream());
            String message = "";
            while (true) {
                message = din.readUTF();
                if (message.contains("!exit")) {
                    broadcast("a participant has left the chat! ");
                    break;
                }
                broadcast(message);
                System.out.println(message);
            }
            din.close();
            dout.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class server {
    private static ArrayList<Socket> connectionList = new ArrayList<>();
    public static void main(String[] args) {
        try {
            System.out.println("Initializing Server at address " + Inet4Address.getLocalHost() + "...");
        } catch (UnknownHostException ue) {
            ue.printStackTrace();
        }
        System.out.println("Server is ready\nWaiting for people to show up!... ");
        while (true) {
            /* Come any time... */
            try (ServerSocket myserver = new ServerSocket(9999)) {
                Socket client = myserver.accept();
                if (client.isConnected()) {
                    System.out.println("We received a new connection! : "+client.getRemoteSocketAddress());
                    connectionList.add(client);
                    Guddu harshu = new Guddu(client, connectionList);
                    Thread harshuThread = new Thread(harshu);
                    harshuThread.start();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}