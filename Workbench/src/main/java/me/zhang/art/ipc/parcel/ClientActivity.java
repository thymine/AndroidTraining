package me.zhang.art.ipc.parcel;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by Li on 6/11/2016 11:34 AM.
 */
public class ClientActivity extends AppCompatActivity {

    private static final String TAG = ClientActivity.class.getSimpleName();
    private static final int MSG_NEW_BOOK_ARRIVED = 0x00000001;

    private IBookManager mBookManager;
    private BindStatus mBindStatus;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBindStatus = new BindBeforeStatus(); // before bind

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(VERTICAL);

        Button addBookButton = new Button(this);
        addBookButton.setText("Add book");
        addBookButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBindStatus.performAddBook(getApplicationContext());
            }
        });

        Button getBookListButton = new Button(this);
        getBookListButton.setText("Get book list");
        getBookListButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        getBookListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBindStatus.performGetBookList(getApplicationContext());
            }
        });

        container.addView(addBookButton);
        container.addView(getBookListButton);
        setContentView(container);

        bind();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register remote callback
        if (mBookManager != null && mBookManager.asBinder().isBinderAlive()) {
            try {
                mBookManager.registerListener(mOnNewBookArrivedListener);
                Log.i(TAG, "onResume: register listener");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // unregister remote callback
        if (mBookManager != null && mBookManager.asBinder().isBinderAlive()) {
            try {
                mBookManager.unregisterListener(mOnNewBookArrivedListener);
                Log.i(TAG, "onPause: unregister listener");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_NEW_BOOK_ARRIVED:
                    Book book = (Book) msg.obj;
                    Toast.makeText(ClientActivity.this, book.bookName, Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
        }
    });

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.i(TAG, "binderDied: currentThread### " + Thread.currentThread().getName());

            if (mBookManager == null) return;
            mBookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mBookManager = null;
            // 1. 重新绑定远程服务
            bind();
        }
    };

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected: currentThread### " + Thread.currentThread().getName());

            mBookManager = IBookManager.Stub.asInterface(service);
            mBindStatus = new BindOkStatus(mBookManager); // switch to bind ok status

            try {
                service.linkToDeath(mDeathRecipient, 0); // 为binder设置“死亡代理”

                Toast.makeText(ClientActivity.this, mBookManager.sayHello(), Toast.LENGTH_SHORT).show();
                mBookManager.registerListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected: currentThread### " + Thread.currentThread().getName());
            mBookManager = null;

            // 2. 重新绑定远程服务
//            bind();
        }
    };

    private IOnNewBookArrivedListener mOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            // 该方法运行于客户端的Binder线程池中，不能直接和UI交互
            mHandler.obtainMessage(MSG_NEW_BOOK_ARRIVED, newBook).sendToTarget();
        }
    };

    private void bind() {
        Intent serviceIntent = new Intent("me.zhang.art.ipc.parcel.RemoteService");
        serviceIntent.setPackage("me.zhang.art");
        bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);
    }

}
