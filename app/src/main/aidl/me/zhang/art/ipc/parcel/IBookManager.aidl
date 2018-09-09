// IBookManager.aidl
package me.zhang.art.ipc.parcel;

import me.zhang.art.ipc.parcel.Book;
import me.zhang.art.ipc.parcel.IOnNewBookArrivedListener;

interface IBookManager {

    List<Book> getBookList();

    void addBook(in Book book);

    String sayHello();

    void registerListener(IOnNewBookArrivedListener listener);

    void unregisterListener(IOnNewBookArrivedListener listener);

}
