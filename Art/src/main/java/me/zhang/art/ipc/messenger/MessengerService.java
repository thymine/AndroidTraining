package me.zhang.art.ipc.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Li on 6/11/2016 5:58 PM.
 */
public class MessengerService extends Service {

    private static class MessengerHandler extends Handler {

        private static final int MSG_FROM_CLIENT = 0x00000110;
        public static final String MSG = "msg";
        private static final String TAG = MessengerHandler.class.getSimpleName();
        public static final String REPLY = "reply";

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FROM_CLIENT:
                    Log.i(TAG, "handleMessage: (message from client) " + msg.getData().getString(MSG));

//                    Object obj = msg.obj;
//                    if (obj != null && obj instanceof Book) {
//                        Book book = (Book) obj;
//                        Log.i(TAG, "handleMessage: " + book);
//                    }

                    // Reply to client
                    Messenger client = msg.replyTo;
                    Message replyMsg = Message.obtain(null, MSG_FROM_CLIENT);
                    Bundle data = new Bundle();
                    data.putString(REPLY, "Hey, i hear it.");
                    replyMsg.setData(data);

                    try {
                        client.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private final Messenger messenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

}
