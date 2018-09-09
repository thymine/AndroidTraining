// IOnNewBookArrivedListener.aidl
package me.zhang.art.ipc.parcel;

import me.zhang.art.ipc.parcel.Book;

interface IOnNewBookArrivedListener {

    void onNewBookArrived(in Book newBook);

}
