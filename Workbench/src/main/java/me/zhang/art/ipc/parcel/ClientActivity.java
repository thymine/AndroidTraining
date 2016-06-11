package me.zhang.art.ipc.parcel;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.VERTICAL;

/**
 * Created by Li on 6/11/2016 11:34 AM.
 */
public class ClientActivity extends AppCompatActivity {

    private static final String TAG = ClientActivity.class.getSimpleName();

    private IBookManager manager;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout container = new LinearLayout(this);
        container.setOrientation(VERTICAL);

        Button addBookButton = new Button(this);
        addBookButton.setText("Add book");
        addBookButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book("Book " + System.currentTimeMillis(), System.currentTimeMillis());
                try {
                    manager.addBook(book);
                    Toast.makeText(ClientActivity.this, "Book added", Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        Button getBookListButton = new Button(this);
        getBookListButton.setText("Get book list");
        getBookListButton.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        getBookListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<Book> bookList = manager.getBookList();
                    if (bookList != null) {
                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < bookList.size(); i++) {
                            builder.append(bookList.get(i).bookName);
                            if (i != bookList.size() - 1) {
                                builder.append(", ");
                            }
                        }
                        Log.i(TAG, "onClick: Book List: " + builder.toString());
                        Toast.makeText(ClientActivity.this, "See all added books in logcat", Toast.LENGTH_SHORT).show();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        container.addView(addBookButton);
        container.addView(getBookListButton);

        setContentView(container);

    }

    @Override
    protected void onResume() {
        super.onResume();
        bindService(new Intent("me.zhang.art.ipc.parcel.RemoteService"), connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(connection);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            manager = IBookManager.Stub.asInterface(service);

            try {
                Toast.makeText(ClientActivity.this, manager.sayHello(), Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            manager = null;
        }
    };

}
