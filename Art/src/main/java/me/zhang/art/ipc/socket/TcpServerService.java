package me.zhang.art.ipc.socket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by Li on 6/19/2016 1:05 PM.
 */
public class TcpServerService extends Service {

    private static final String TAG = TcpServerService.class.getSimpleName();
    private boolean mIsServcieDestroyed = false;

    private Random random = new Random();
    private String[] mPredefinedMessages = {
            "Hello, i'm server",
            "Nice to meet you!",
            "Can i ask you some questions?",
            "I'm really really tired, could you help me?",
            "Hi, buddy~",
            "Give me five dollars!"
    };

    @Override
    public void onCreate() {
        new Thread(runnable).start();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            ServerSocket serverSocket;
            try {
                serverSocket = new ServerSocket(8688); // listen port 8688
            } catch (IOException e) {
                Log.e(TAG, "run: Establish Tcp server failed, port: 8688");
                e.printStackTrace();
                return;
            }

            while (!mIsServcieDestroyed) {
                try {
                    // response to client
                    final Socket clientSocket = serverSocket.accept();
                    Log.i(TAG, "run: accepted");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                responseClient(clientSocket);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private void responseClient(Socket clientSocket) throws IOException {
        // accept client message
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        // send to client
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                clientSocket.getOutputStream()
        )), true); // true: auto flush

        out.println("Welcome to server!");

        while (!mIsServcieDestroyed) {

            String msg = in.readLine();
            Log.i(TAG, "responseClient: message from client: " + msg);

            if (msg == null) {
                break; // client disconnected
            }

            int idx = random.nextInt(mPredefinedMessages.length);
            String message = mPredefinedMessages[idx]; // random response message

            SystemClock.sleep(1000);
            out.println(message);

            Log.i(TAG, "responseClient: send message, " + message);
        }

        Log.i(TAG, "responseClient: client quit");

        // close stream
        out.close();
        in.close();

        clientSocket.close();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mIsServcieDestroyed = true;
    }
}
