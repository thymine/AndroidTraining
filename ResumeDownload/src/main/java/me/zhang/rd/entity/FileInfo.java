package me.zhang.rd.entity;

import java.io.Serializable;

/**
 * Created by zhang on 15-6-14 上午9:26.
 * <p>
 * 文件信息实体类
 */
public class FileInfo implements Serializable {

    private int id;
    private String url;
    private String name;
    private long length;
    private long progress;

    public FileInfo() {
    }

    public FileInfo(int id, String url, String name, long length, long progress) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.length = length;
        this.progress = progress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", length=" + length +
                ", progress=" + progress +
                '}';
    }
}
