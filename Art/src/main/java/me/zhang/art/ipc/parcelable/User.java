package me.zhang.art.ipc.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zhang on 16-6-10.
 */
public class User implements Parcelable {

    public int userId;
    public String userName;
    public boolean isMale;

    public Book book;

    public User(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    protected User(Parcel in) {
        this.userId = in.readInt();
        this.userName = in.readString();
        this.isMale = in.readInt() == 1;
        this.book = in.readParcelable(Thread.currentThread().getContextClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.userName);
        dest.writeInt(isMale ? 1 : 0);
        dest.writeParcelable(this.book, 0);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
