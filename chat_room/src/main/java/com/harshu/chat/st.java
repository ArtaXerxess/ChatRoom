package com.harshu.chat;

class foo implements Runnable{
    int num = 0;

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            num = num + 1;
        }    
    }
    public int getid(){
        return num;
    }
}

public class st {
    public static void main(String[] args) {
        foo f = new foo();
        Thread thread = new Thread(f);
        thread.start();
        while (true) {
            System.out.println(f.getid());
            if (!thread.isAlive()) {
                break;
            }
        }        
    }
}
