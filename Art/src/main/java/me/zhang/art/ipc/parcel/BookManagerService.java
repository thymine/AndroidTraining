package me.zhang.art.ipc.parcel;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Li on 6/11/2016 11:12 AM.
 */
public class BookManagerService extends Service {

    private static final String TAG = BookManagerService.class.getSimpleName();
    public static final int ONE_SECOND = 1000; // 1s

    private AtomicBoolean mIsServiceDestroyed = new AtomicBoolean(false);
    private List<Book> mBookList = new LinkedList<>();
    private RemoteCallbackList<IOnNewBookArrivedListener> mCallbackList =
            new RemoteCallbackList<>();

    private final IBookManager.Stub mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            // 方法运行于Binder线程池中，不需要额外的异步处理
            SystemClock.sleep(ONE_SECOND);
            if (book != null) {
                mBookList.add(book);
                Log.i(TAG, "addBook: book added");
            }
        }

        @Override
        public String sayHello() throws RemoteException {
            return "Welcome to visit server via AIDL";
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mCallbackList.register(listener);
            int count;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                count = mCallbackList.getRegisteredCallbackCount();
            } else {
                count = mCallbackList.beginBroadcast();
                mCallbackList.finishBroadcast();
            }
            Log.i(TAG, "registerListener: listener added, count: " + count);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
            mCallbackList.unregister(listener);
            int count;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                count = mCallbackList.getRegisteredCallbackCount();
            } else {
                count = mCallbackList.beginBroadcast();
                mCallbackList.finishBroadcast();
            }
            Log.i(TAG, "unregisterListener: listener removed, count: " + count);
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            String[] packages = getPackageManager().getPackagesForUid(getCallingUid());
            if (packages != null && packages.length > 0) {
                String packageName = packages[0];
                if (!packageName.startsWith("me.zhang")) {
                    return false;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
//        int check = checkCallingOrSelfPermission("me.zhang.art.permission.ACCESS_BOOK_SERVICE");
//        if (check == PackageManager.PERMISSION_DENIED) {
//            return null;
//        }
        return mBinder;
    }

    @Override
    public void onCreate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!mIsServiceDestroyed.get()) {
                    try {
                        Thread.sleep(10 * ONE_SECOND);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",
                            Locale.getDefault());
                    Book newBook = new Book(System.currentTimeMillis(),
                            "Book → " + sdf.format(new Date(System.currentTimeMillis())));
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
        mBookList.add(newBook);

        Log.i(TAG, "onNewBookArrived: notify listeners");
        final int n = mCallbackList.beginBroadcast();
        for (int i = 0; i < n; i++) {
            mCallbackList.getBroadcastItem(i).onNewBookArrived(newBook);
        }
        mCallbackList.finishBroadcast();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed.set(true);
    }
}
