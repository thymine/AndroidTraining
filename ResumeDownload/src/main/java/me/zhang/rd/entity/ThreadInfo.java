package me.zhang.rd.entity;

/**
 * Created by zhang on 15-6-14 上午9:28.
 * <p>
 * 线程信息实体类
 */
public class ThreadInfo {

    private int id;
    private String url;
    private long start;
    private long end;
    private long progress;

    public ThreadInfo() {
    }

    public ThreadInfo(int id, String url, long start, long end, long progress) {
        this.id = id;
        this.url = url;
        this.start = start;
        this.end = end;
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

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "ThreadInfo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", progress=" + progress +
                '}';
    }
}
