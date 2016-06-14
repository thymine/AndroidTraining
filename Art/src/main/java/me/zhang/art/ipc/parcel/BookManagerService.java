package me.zhang.art.ipc.parcel;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Li on 6/11/2016 11:12 AM.
 */
public class BookManagerService extends Service {

    private static final String TAG = BookManagerService.class.getSimpleName();
    public static final int ONE_MINUTE = 1000; // 1s

    private AtomicBoolean isServiceDestroyed = new AtomicBoolean(false);
    private List<Book> bookList = new LinkedList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> callbackList = new RemoteCallbackList<>();

    private final IBookManager.Stub mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            if (book != null) {
                bookList.add(book);
                Log.i(TAG, "addBook: book added");
            }
        }

        @Override
        public String sayHello() throws RemoteException {
            return "Welcome to visit server via AIDL";
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            callbackList.register(listener);
            int count;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                count = callbackList.getRegisteredCallbackCount();
            } else {
                count = callbackList.beginBroadcast();
                callbackList.finishBroadcast();
            }
            Log.i(TAG, "registerListener: listener added, count: " + count);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            callbackList.unregister(listener);
            int count;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                count = callbackList.getRegisteredCallbackCount();
            } else {
                count = callbackList.beginBroadcast();
                callbackList.finishBroadcast();
            }
            Log.i(TAG, "unregisterListener: listener removed, count: " + count);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!isServiceDestroyed.get()) {
                    try {
                        Thread.sleep(10 * ONE_MINUTE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Book newBook = new Book(System.currentTimeMillis(), "Book→" + System.currentTimeMillis());
                    try {
                        onNewBookArrived(newBook);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void onNewBookArrived(Book newBook) throws RemoteException {
        bookList.add(newBook);

        Log.i(TAG, "onNewBookArrived: notify listeners");
        final int n = callbackList.beginBroadcast();
        for (int i = 0; i < n; i++) {
            callbackList.getBroadcastItem(i).onNewBookArrived(newBook);
        }
        callbackList.finishBroadcast();
    }

    @Override
    public void onDestroy() {
        isServiceDestroyed.set(true);
    }
}
