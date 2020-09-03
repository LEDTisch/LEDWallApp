package de.ft.LEDWallApp;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Connection {
    public static PrintWriter printwriter = null;
    public static boolean sending=false;

    public static ArrayList<String>tosent=new ArrayList<>();

    public static Timer timer = new Timer();

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

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                while(printwriter==null);
                if(tosent.size()>0) {
                    printwriter.println(tosent.get(tosent.size() - 1));
                    tosent.remove(tosent.get(tosent.size() - 1));
                    printwriter.flush();
                }
            }
        },0,10);


    }
    public static void send(final String s){
tosent.add(0,s);

    }

}
