package me.zhang.workbench.thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import java.util.LinkedList;

/**
 * Created by zhangxiangdong on 2017/4/18.
 */
public class Logger {

    static final String MESSAGE_LOG = "log";
    static final int WHAT_LOG = -1;
    private LinkedList<String> queue = new LinkedList<>();
    static final int MAX_QUEUE_SIZE = 50;
    private static final int MAX_THREAD_COUNT = 10;
    private Handler handler;

    public Logger(Handler handler) {
        this.handler = handler;
    }

    public void start() {
        // Creates the Loop as a Runnable
        Runnable task = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String message = pullMessage();
                    Log.d(Thread.currentThread().getName(), message);
                    // Do another processing
                    Message msg = handler.obtainMessage();
                    msg.what = WHAT_LOG;
                    Bundle data = new Bundle();
                    data.putString(MESSAGE_LOG, message);
                    msg.setData(data);

                    SystemClock.sleep(1000);

                    handler.sendMessage(msg);
                }
            }
        };

        // Create a Group of Threads for processing
        for (int i = 0; i < MAX_THREAD_COUNT; i++) {
            new Thread(task).start();
        }
    }

    // Pulls a message from the queue
    // Only returns when a new message is retrieves
    // from the queue.
    private synchronized String pullMessage() {
        while (queue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException ignored) {
            }
        }
        return queue.pop();
    }

    // Push a new message to the tail of the queue if
    // the queue has available positions
    public synchronized void pushMessage(String logMsg) {
        if (queue.size() < MAX_QUEUE_SIZE) {
            queue.push(logMsg);
            notifyAll();
        }
    }

}
