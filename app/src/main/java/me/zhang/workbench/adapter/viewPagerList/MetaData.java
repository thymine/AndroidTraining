package me.zhang.workbench.adapter.viewPagerList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Zhang on 12/31/2016 10:28 AM.
 */
public class MetaData implements Parcelable {

    String name;

    public MetaData(String name) {
        this.name = name;
    }

    protected MetaData(Parcel in) {
        name = in.readString();
    }

    public static final Creator<MetaData> CREATOR = new Creator<MetaData>() {
        @Override
        public MetaData createFromParcel(Parcel in) {
            return new MetaData(in);
        }

        @Override
        public MetaData[] newArray(int size) {
            return new MetaData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
