package com.me.rxdownload.entity;

import static com.me.rxdownload.entity.DownloadStatus.DOWNLOADING;
import static com.me.rxdownload.entity.DownloadStatus.ERROR;
import static com.me.rxdownload.entity.DownloadStatus.FINISH;
import static com.me.rxdownload.entity.DownloadStatus.PAUSE;
import static com.me.rxdownload.entity.DownloadStatus.QUEUE;

/**
 * 用于传递 下载的状态
 * Created by yunfei on 17-3-25.
 */

public class DownloadEvent {
    private long totalSize = -1;
    private long completedSize;
    private int status = QUEUE;
    private double speed;//速度

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public long getCompletedSize() {
        return completedSize;
    }

    public void setCompletedSize(long completedSize) {
        this.completedSize = completedSize;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        String showStatus = "";
        switch (status) {
            case DOWNLOADING:
                showStatus = "下载中";
                break;
            case PAUSE:
                showStatus = "暂停";
                break;
            case QUEUE:
                showStatus = "等待中";
                break;
            case FINISH:
                showStatus = "完成";
                break;
            case ERROR:
                showStatus = "错误";
        }

        return "DownloadEvent{"
                + "totalSize="
                + totalSize
                + ", completedSize="
                + completedSize
                + ", speed="
                + speed
                + ", status="
                + showStatus
                + '}';
    }
}
