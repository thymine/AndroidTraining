package me.zhang.art.ipc.parcel;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Li on 6/11/2016 11:12 AM.
 */
public class RemoteService extends Service {

    private List<Book> bookList = new LinkedList<>();

    private final IBookManager.Stub mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            if (book != null) {
                bookList.add(book);
            }
        }

        @Override
        public String sayHello() throws RemoteException {
            return "Welcome to visit server via AIDL";
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
