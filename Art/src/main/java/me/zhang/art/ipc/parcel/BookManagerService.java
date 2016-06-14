package me.zhang.art.ipc.parcel;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
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
    private List<IOnNewBookArrivedListener> listenerList = new LinkedList<>();

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
            if (listenerList.contains(listener)) return;
            listenerList.add(listener);
            Log.i(TAG, "registerListener: listener added, size: " + listenerList.size());
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            if (listenerList.contains(listener)) {
                listenerList.remove(listener);
                Log.i(TAG, "unregisterListener: listener removed, size: " + listenerList.size());
            } else {
                Log.i(TAG, "unregisterListener: failed!");
            }
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
        for (IOnNewBookArrivedListener listener : listenerList) {
            listener.onNewBookArrived(newBook);
        }
    }

    @Override
    public void onDestroy() {
        isServiceDestroyed.set(true);
    }
}
