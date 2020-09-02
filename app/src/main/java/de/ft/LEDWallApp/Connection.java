package de.ft.LEDWallApp;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection {
    public static PrintWriter printwriter = null;


    public static void connect(final String IP, final int port) throws IOException {
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    Socket socket=new Socket(IP, port);
                    printwriter =  new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        thread.start();
    }
    public static void send(final String s){
        while(printwriter==null);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    printwriter.println(s);
                    printwriter.flush();
                }catch (Exception e){}
            }
        };
        thread.start();

    }
}
