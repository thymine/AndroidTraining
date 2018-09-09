package me.zhang.art.ipc.provider;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import me.zhang.art.ipc.parcel.Book;
import me.zhang.workbench.R;

import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;

/**
 * Created by Li on 6/18/2016 6:49 PM.
 */
public class BookListFragment extends BaseFragment {

    private List<Book> bookList = new ArrayList<>();
    private RecyclerView.Adapter<BookListAdapter.VHolder> mAdapter;
    final Uri bookUri = Uri.parse("content://me.zhang.art.BOOK_PROVIDER/book");
    private ContentResolver mContentResolver;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    public static BookListFragment newInstance() {
        return new BookListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        RecyclerView rv_books = (RecyclerView) view.findViewById(R.id.rv_books);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rv_books.setLayoutManager(layoutManager);

        mContentResolver = mContext.getContentResolver();

        // retrieve books from ContentProvider
        mContentResolver.registerContentObserver(bookUri, false, mContentObserver);

        Cursor bookCursor = mContentResolver.query(bookUri, new String[]{"_id", "name"}, null, null, null);
        if (bookCursor != null) {
            while (bookCursor.moveToNext()) {
                Book book = new Book(bookCursor.getString(1), bookCursor.getLong(0));
                bookList.add(book);
            }
            bookCursor.close();
        }

        mAdapter = new BookListAdapter(bookList);
        rv_books.setAdapter(mAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // do delete operation
                mContentResolver.delete(bookUri, "_id = ?", new String[]{String.valueOf(bookList.get(viewHolder.getAdapterPosition()).bookId)});
            }
        });
        itemTouchHelper.attachToRecyclerView(rv_books);
    }

    private void createNewBook() {
        // create new book here
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mContext).inflate(R.layout.view_new_book, null);
        final EditText et_new_book_name = (EditText) view.findViewById(R.id.et_new_book_name);
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Create New Book")
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!et_new_book_name.getText().toString().trim().equals("")) {
                            ContentValues values = new ContentValues();
                            values.put("name", et_new_book_name.getText().toString().trim());
                            mContentResolver.insert(bookUri, values);
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();

        dialog.show();
    }

    private ContentObserver mContentObserver = new ContentObserver(mHandler) {
        @Override
        public void onChange(boolean selfChange) {
            // requery all data
            Cursor bookCursor = mContentResolver.query(bookUri, new String[]{"_id", "name"}, null, null, null);
            if (bookCursor != null) {
                bookList.clear();
                while (bookCursor.moveToNext()) {
                    Book book = new Book(bookCursor.getString(1), bookCursor.getLong(0));
                    bookList.add(book);
                }
                bookCursor.close();
            }

            // update UI
            mAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void onDestroy() {
        mContentResolver.unregisterContentObserver(mContentObserver);
        super.onDestroy();
    }

    @Override
    public void createNew() {
        createNewBook();
    }
}
