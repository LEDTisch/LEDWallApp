package de.ft.LEDWallApp;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

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
    private static Socket socket;

    public static void connect(final String IP, final int port, final MainActivity instace) throws IOException {
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {

                    socket = new Socket(IP, port);
                    printwriter =  new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    instace.setConnectbuttontext("Trennen");
                    instace.setConnected(true);
                } catch (Throwable e) {



                    postToastMessage("Fehler bei der Verbindung",instace);

                     e.printStackTrace();

                    instace.setConnectbuttontext("Verbinden");
                    instace.setConnected(false);

                }

            }
        };
        thread.start();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    while (printwriter == null) ;
                    if (tosent.size() > 0) {
                        printwriter.println(tosent.get(tosent.size() - 1));
                        tosent.remove(tosent.get(tosent.size() - 1));
                        printwriter.flush();
                    }
                }catch (Exception e)  {
                    instace.setConnectbuttontext("Verbinden");
                    instace.setConnected(false);

                    postToastMessage("Fehler beim Senden",instace);

                    try {
                        socket.close();
                    }catch (Exception n) {

                    }
                    e.printStackTrace();
                }
            }
        },0,10);


    }
    public static void send(final String s){
tosent.add(0,s);

    }

    public static void end() {
        try {
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void postToastMessage(final String message, final MainActivity instance) {
        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(instance, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
