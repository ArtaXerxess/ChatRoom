package com.filetransfer;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

public class server {

    public static void main(String[] args) {
        try (ServerSocket harshu = new ServerSocket(9999)) {
            System.out.println("Server bound at (localhost)" + Inet4Address.getLocalHost().getHostAddress());
            Socket guddu = harshu.accept();
            if (guddu.isConnected()) {
                System.out.println("Connected to client : " + guddu.getRemoteSocketAddress());
                int bytes = 0;
                FileOutputStream fileOutputStream = new FileOutputStream("recvtestfile.txt");
                DataInputStream dataInputStream = new DataInputStream(guddu.getInputStream());
                long size = dataInputStream.readLong(); // read file size
                byte[] buffer = new byte[4 * 1024];
                while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length,size))) != -1) {
                    fileOutputStream.write(buffer, 0, bytes);
                    size -= bytes;
                }
                System.out.println("File is Received");
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
