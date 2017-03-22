package me.zhang.workbench.view;

/**
 * Created by zhangxiangdong on 2017/3/22.
 */
public interface TallyCounter {

    /**
     * Reset the counter.
     */
    void reset();

    /**
     * Increment the counter.
     */
    void increment();

    /**
     * @return The current count of the counter.
     */
    int getCount();

    /**
     * Set the counter value.
     */
    void setCount(int count);

}
