package me.zhang.art.ipc.serialize;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by zhang on 16-6-10.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 3050341842681801131L;

    public int userId;
    public String userName;
    public boolean isMale;

    public User(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", isMale=" + isMale +
                '}';
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 序列化过程
        User user = new User(0, "jake", true);
        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(System.getProperty("user.home") + "/Desktop/cache.txt")
        );
        out.writeObject(user);
        out.close();

        // 反序列化过程
        ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(System.getProperty("user.home") + "/Desktop/cache.txt")
        );
        User newUser = (User) in.readObject();
        in.close();

        System.out.println(newUser);
    }
}
