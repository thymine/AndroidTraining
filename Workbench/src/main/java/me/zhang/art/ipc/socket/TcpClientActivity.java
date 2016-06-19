package me.zhang.art.ipc.socket;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.zhang.workbench.R;

import static android.view.View.FOCUS_DOWN;

/**
 * Created by Li on 6/19/2016 1:24 PM.
 */
public class TcpClientActivity extends AppCompatActivity {

    private static final String TAG = TcpClientActivity.class.getSimpleName();

    private static final int MESSAGE_RECEIVE_NEW_MSG = 1;
    private static final int MESSAGE_SOCKET_CONNECTED = 2;

    private ScrollView mMessagesScrollView;
    private LinearLayout mMessagesLinearLayout;
    private Button mSendButton;
    private EditText mMessageEditText;

    private Intent startTcpServer;

    private PrintWriter mPrintWriter;
    private Socket mClientSocket;

    private LayoutInflater mInflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcp_client);
        mInflater = LayoutInflater.from(getApplicationContext());

        mMessagesScrollView = (ScrollView) findViewById(R.id.messagesScrollView);
        mMessagesLinearLayout = (LinearLayout) findViewById(R.id.messagesLinearLayout);
        mSendButton = (Button) findViewById(R.id.sendButton);
        mMessageEditText = (EditText) findViewById(R.id.messageEditText);

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsg();
            }
        });

        startTcpServer = new Intent("me.zhang.art.ipc.socket.TCP_SERVER");
        startService(startTcpServer);

        new Thread(runnable).start();
    }

    private void sendMsg() {
        // send message here
        final String msg = mMessageEditText.getText().toString();
        if (!TextUtils.isEmpty(msg) && mPrintWriter != null) {
            mPrintWriter.println(msg);
            mMessageEditText.setText("");

            String time = formatDateTime(System.currentTimeMillis());
            String sendMsg = time + ", " + msg;
            // show send message
            @SuppressLint("InflateParams") View view = mInflater.inflate(R.layout.item_message_send, null);
            TextView sendText = (TextView) view.findViewById(R.id.sendTextView);
            sendText.setText(sendMsg);
            mMessagesLinearLayout.addView(sendText);
            mMessagesScrollView.post(new Runnable() {
                @Override
                public void run() {
                    mMessagesScrollView.fullScroll(FOCUS_DOWN);
                }
            });
        }
    }

    private String formatDateTime(long time) {
        return new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date(time));
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_RECEIVE_NEW_MSG: {
                    String receivedMsg = (String) msg.obj;
                    @SuppressLint("InflateParams") View view = mInflater.inflate(R.layout.item_message_receive, null);
                    TextView receiveText = (TextView) view.findViewById(R.id.receiveTextView);
                    receiveText.setText(receivedMsg);
                    mMessagesLinearLayout.addView(receiveText);

                    mMessagesScrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            mMessagesScrollView.fullScroll(FOCUS_DOWN);
                        }
                    });
                    break;
                }
                case MESSAGE_SOCKET_CONNECTED: {
                    mSendButton.setEnabled(true);
                    break;
                }
                default:
                    break;
            }
            return false;
        }
    });

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Socket socket = null;
            while (socket == null) {
                try {
                    socket = new Socket("localhost", 8688);
                    mClientSocket = socket;
                    mPrintWriter = new PrintWriter(
                            new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                            true
                    );
                    mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED);
                    Log.i(TAG, "run: connect server success");
                } catch (IOException e) {
                    SystemClock.sleep(1000);
                    Log.i(TAG, "run: connect tcp server failed, retry...");
                }
            }

            try {
                // 接收服务器端的消息
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                while (!TcpClientActivity.this.isFinishing()) {
                    String msg = br.readLine();
                    Log.i(TAG, "run: receive, " + msg);
                    if (msg != null) {
                        String time = formatDateTime(System.currentTimeMillis());
                        String showedMsg = time + ", " + msg;
                        mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, showedMsg).sendToTarget();
                    }
                }
                Log.i(TAG, "run: quit");
                mPrintWriter.close();
                br.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onDestroy() {
        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        stopService(startTcpServer);
        super.onDestroy();
    }

}
