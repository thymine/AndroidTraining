package me.zhang.laboratory.ui.bean;

import androidx.annotation.NonNull;

import java.util.List;

public class PartBean {

    public String videoPath;//录像路径
    public String coverPath;//封面图
    public long duration;   //时长 ms

    public boolean isCurrent;//是否是当前录制的
    public boolean isRecord; //是否录制

    public transient long remakeDuration; // 重新录制的时间
    public int remakeCurrent = -1; // 记录重录当前分段索引
    public List<PartBean> remakeParts; // 重拍的子分段

    public long getAllRemakePartsDuration() {
        long allRemakePartsDuration = 0;
        if (remakeParts != null) {
            for (PartBean remakePart : remakeParts) {
                allRemakePartsDuration += remakePart.duration;
            }
        }
        return allRemakePartsDuration;
    }

    public void copy(@NonNull PartBean partBean) {
        videoPath = partBean.videoPath;
        coverPath = partBean.coverPath;
        duration = partBean.duration;
    }

    public void reset() {
        videoPath = null;
        coverPath = null;
        isRecord = false;
        duration = 0;
        remakeParts = null;
    }
}
