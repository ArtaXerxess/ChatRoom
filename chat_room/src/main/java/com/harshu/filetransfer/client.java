package com.filetransfer;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class client {

    public static void main(String[] args) {
            
            try (Socket mai = new Socket("localhost", 9999))
            {
            /* Initialize socket and streams */
            String path = "C:\\Users\\WiiN10pro\\Desktop\\projects\\chat_room\\test.txt";
            File file = new File(path);
            FileInputStream fin = new FileInputStream(file);
            DataOutputStream dout = new DataOutputStream(mai.getOutputStream());
            /* upload file to server */
            int bytes = 0;
            byte[] buffer = new byte[4 * 1024];
            while ((bytes = fin.read(buffer)) != -1) {
                dout.write(buffer, 0, bytes);
                dout.flush();
            }
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
