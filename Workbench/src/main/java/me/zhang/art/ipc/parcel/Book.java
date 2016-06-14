package me.zhang.art.ipc.parcel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhang on 16-6-10.
 */
public class Book implements Parcelable {

    public long bookId;
    public String bookName;

    public Book(String bookName, long bookId) {
        this.bookName = bookName;
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.bookId);
        dest.writeString(this.bookName);
    }

    public Book() {
    }

    protected Book(Parcel in) {
        this.bookId = in.readLong();
        this.bookName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
