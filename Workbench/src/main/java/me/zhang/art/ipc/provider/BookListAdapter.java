package me.zhang.art.ipc.provider;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.zhang.art.ipc.parcel.Book;
import me.zhang.workbench.R;

/**
 * Created by Li on 6/18/2016 7:01 PM.
 */
public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.VHolder> {

    private List<Book> bookList;

    public BookListAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public BookListAdapter.VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false));
    }

    @Override
    public void onBindViewHolder(BookListAdapter.VHolder holder, int position) {
        holder.bind(bookList.get(position));
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    static class VHolder extends RecyclerView.ViewHolder {

        Book book;
        TextView tv_book_id;
        TextView tv_book_name;

        public void bind(Book book) {
            this.book = book;

            tv_book_id.setText(String.valueOf(book.bookId));
            tv_book_name.setText(book.bookName);
        }

        public VHolder(View itemView) {
            super(itemView);

            tv_book_id = (TextView) itemView.findViewById(R.id.tv_book_id);
            tv_book_name = (TextView) itemView.findViewById(R.id.tv_book_name);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // TODO: 6/18/2016 pop up update dialog
                    return true;
                }
            });
        }

    }

}
