package me.zhang.art.ipc.parcel;

import android.content.Context;
import android.os.RemoteException;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Li on 6/16/2016 9:33 PM.
 */
public class BindOkStatus implements BindStatus {

    private IBookManager mBookManager;

    public BindOkStatus(IBookManager manager) {
        this.mBookManager = manager;
    }

    @Override
    public void performAddBook(Context context) {
        Book book = new Book("Book " + System.currentTimeMillis(), System.currentTimeMillis());
        try {
            mBookManager.addBook(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void performGetBookList(Context context) {
        try {
            List<Book> bookList = mBookManager.getBookList();
            if (bookList != null) {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < bookList.size(); i++) {
                    builder.append(bookList.get(i).bookName);
                    if (i != bookList.size() - 1) {
                        builder.append(", ");
                    }
                }
                Toast.makeText(context, builder.toString(), Toast.LENGTH_SHORT).show();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
