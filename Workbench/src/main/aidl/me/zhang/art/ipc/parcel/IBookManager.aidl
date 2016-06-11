// IBookManager.aidl
package me.zhang.art.ipc.parcel;

import me.zhang.art.ipc.parcel.Book;

interface IBookManager {

    List<Book> getBookList();

    void addBook(in Book book);

    String sayHello();

}
