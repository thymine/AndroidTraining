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

    private IBookManager manager;
    private BindStatus bindStatus;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindStatus = new BindBeforeStatus(); // before bind

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(VERTICAL);

        Button addBookButton = new Button(this);
        addBookButton.setText("Add book");
        addBookButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindStatus.performAddBook(getApplicationContext());
            }
        });

        Button getBookListButton = new Button(this);
        getBookListButton.setText("Get book list");
        getBookListButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        getBookListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindStatus.performGetBookList(getApplicationContext());
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
        if (manager != null && manager.asBinder().isBinderAlive()) {
            try {
                manager.registerListener(onNewBookArrivedListener);
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
        if (manager != null && manager.asBinder().isBinderAlive()) {
            try {
                manager.unregisterListener(onNewBookArrivedListener);
                Log.i(TAG, "onPause: unregister listener");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(connection);
        super.onDestroy();
    }

    private Handler handler = new Handler(new Handler.Callback() {
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

    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.i(TAG, "binderDied: currentThread### " + Thread.currentThread().getName());

            if (manager == null) return;
            manager.asBinder().unlinkToDeath(deathRecipient, 0);
            manager = null;
            // 1. 重新绑定远程服务
            bind();
        }
    };

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected: currentThread### " + Thread.currentThread().getName());

            manager = IBookManager.Stub.asInterface(service);
            bindStatus = new BindOkStatus(manager); // switch to bind ok status

            try {
                service.linkToDeath(deathRecipient, 0); // 为binder设置“死亡代理”

                Toast.makeText(ClientActivity.this, manager.sayHello(), Toast.LENGTH_SHORT).show();
                manager.registerListener(onNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected: currentThread### " + Thread.currentThread().getName());
            manager = null;

            // 2. 重新绑定远程服务
//            bind();
        }
    };

    private IOnNewBookArrivedListener onNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            // 该方法运行于客户端的Binder线程池中，不能直接和UI交互
            handler.obtainMessage(MSG_NEW_BOOK_ARRIVED, newBook).sendToTarget();
        }
    };

    private void bind() {
        bindService(new Intent("me.zhang.art.ipc.parcel.RemoteService"), connection, BIND_AUTO_CREATE);
    }

}
