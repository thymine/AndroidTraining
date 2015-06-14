package me.zhang.rd.db;

import java.util.List;

import me.zhang.rd.entity.ThreadInfo;

/**
 * Created by zhang on 15-6-14 下午3:03.
 * <p>
 * Definition of data access interface
 */
public interface IThreadDAO {

    /**
     * Add thread infomation
     *
     * @param info
     */
    public void insertThreadInfo(ThreadInfo info);

    /**
     * Remove thread infomation by thread id
     *
     * @param url
     * @param threadId
     */
    public void deleteThreadInfo(String url, int threadId);

    /**
     * Update download process
     *
     * @param url
     * @param threadId
     * @param progress
     */
    public void updateThreadInfo(String url, int threadId, long progress);

    /**
     * Get thread infomations via file url
     *
     * @param url
     * @return
     */
    public List<ThreadInfo> getThreadInfos(String url);

    /**
     * Returns a boolean indicating whether this thread infomation
     * already stored in the database
     *
     * @param url
     * @param threadId
     * @return
     */
    public boolean exists(String url, int threadId);

}
