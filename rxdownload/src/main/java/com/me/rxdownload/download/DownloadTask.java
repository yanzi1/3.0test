package com.me.rxdownload.download;

import com.me.rxdownload.db.IDownloadDB;
import com.me.rxdownload.entity.DownloadBean;
import com.me.rxdownload.entity.DownloadBundle;
import com.me.rxdownload.entity.DownloadEvent;
import com.me.rxdownload.entity.DownloadStatus;
import com.me.rxdownload.utils.L;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

import io.reactivex.disposables.Disposable;
import io.reactivex.processors.FlowableProcessor;

import static com.me.rxdownload.entity.DownloadStatus.DOWNLOADING;
import static com.me.rxdownload.entity.DownloadStatus.ERROR;
import static com.me.rxdownload.entity.DownloadStatus.FINISH;
import static com.me.rxdownload.entity.DownloadStatus.PAUSE;
import static com.me.rxdownload.utils.RxUtils.createProcessor;

/**
 * Item 下载整合
 * Created by yunfei on 17-3-25.
 */

public class DownloadTask {

    private DownloadApi mDownloadApi;
    private IDownloadDB downloadDB;
    private volatile boolean isCancel = false;

    private DownloadBundle downloadBundle;
    private List<ItemTask> itemTasks;
    private DownloadEvent event;
    private AtomicLong completeSize;
    private AtomicLong failSize;
    private TaskObserver taskObserver;
    /**
     * 用于通知下载状态
     */
    private FlowableProcessor<DownloadEvent> processorEvent;

    public DownloadTask(DownloadBundle downloadBundle) {
        this.downloadBundle = downloadBundle;
    }

    public void init(DownloadApi downloadApi) {
        this.mDownloadApi = downloadApi;
        itemTasks = new ArrayList<>();
        taskObserver = new TaskObserver();
    }

    /**
     * 下载开始
     */
    public  void startDownload(final Semaphore semaphore, Executor executor) throws InterruptedException {

        for (ItemTask task : itemTasks) {
            task.startDownload(executor,semaphore, taskObserver);
            if (isCancel) {
                break;
            }
        }
    }

    private List<DownloadBean> getUnFinished(List<DownloadBean> downloadList) {
        List<DownloadBean> unDownload = new ArrayList<>();
        for (DownloadBean bean : downloadList) {
            if (!bean.isFinished()) {
                unDownload.add(bean);
            }
        }
        return unDownload;
    }

    public void init(Map<String, DownloadTask> taskMap,
                     Map<String, FlowableProcessor<DownloadEvent>> processorMap, IDownloadDB downloadDB) {
        DownloadTask task = taskMap.get(downloadBundle.getKey());
        if (task == null) {
            taskMap.put(downloadBundle.getKey(), this);
        } else {
            if (task.isCancel) {
                DownloadBundle oldBundle = task.downloadBundle;
                this.downloadBundle.init(oldBundle);
                isCancel = false;
                taskMap.put(this.downloadBundle.getKey(), this);
            } else {
                //已经存在
                throw new IllegalArgumentException("已经存在了  " + downloadBundle.getKey());
            }
        }


        processorEvent = createProcessor(downloadBundle.getKey(), processorMap);
        this.downloadDB = downloadDB;
    }

    public void insertOrUpdate() {
        if (downloadDB.existsDownloadBundle(downloadBundle.getKey())) {
            downloadBundle.init(downloadDB.getDownloadBundle(downloadBundle.getKey()));
        } else {
            downloadDB.insertDownloadBundle(downloadBundle);
        }
    }

    public void pause() {
        //从数据库查询出它的 下载进度再设置 暂停
        for (int i = 0; i < itemTasks.size(); i++) {
            itemTasks.get(i).pause();
        }
        isCancel = true;
        downloadDB.pause(downloadBundle.getKey());
        if (processorEvent != null) {
            DownloadEvent downloadEvent = downloadDB.selectBundleStatus(downloadBundle.getKey());
            downloadEvent.setStatus(PAUSE);
            processorEvent.onNext(downloadEvent);
        }
    }

    public boolean isFinished() {
        return downloadBundle.getStatus() == DownloadStatus.FINISH;
    }

    public boolean prepareStart() {
        //控制信号量
        event = new DownloadEvent();
        event.setCompletedSize(downloadBundle.getCompletedSize());
        event.setTotalSize(downloadBundle.getTotalSize());
        List<DownloadBean> downloadList = getUnFinished(downloadBundle.getDownloadList());
        if (downloadList.size() == 0) {
            //已经下载完毕 直接结束
            if (event.getCompletedSize() != event.getTotalSize()) {
                downloadBundle.setCompletedSize(downloadBundle.getTotalSize());
                downloadBundle.setStatus(FINISH);
                downloadDB.updateDownloadBundle(downloadBundle);
                event.setCompletedSize(event.getTotalSize());
            }
            event.setStatus(FINISH);
            processorEvent.onNext(event);
            return false;
        }
        //TODO 判断是否正确
        //int unDownloadSize = downloadList.size();

        completeSize = new AtomicLong(downloadBundle.getTotalSize() - downloadList.size());
        failSize = new AtomicLong(0);

        if (isCancel) {
            return false;
        }

        for (DownloadBean bean : downloadList) {
            itemTasks.add(new ItemTask(mDownloadApi, downloadDB, bean));
        }
        event.setStatus(DOWNLOADING);
        downloadBundle.setStatus(DOWNLOADING);
        downloadDB.updateDownloadBundle(downloadBundle);
        processorEvent.onNext(event);
        return true;
    }

    public long getTotalSize() {
        return downloadBundle.getTotalSize();
    }

    public String getWhere0() {
        return downloadBundle.getUserId() + "";
    }

    public class TaskObserver {

        public void onSubscribe(Disposable d) {
            //event.setStatus(DOWNLOADING);
            //processorEvent.onNext(event);
        }

        public void onNext(DownloadEvent downloadEvent) {
        }

        public void onError(Throwable e) {
            //错误++
            L.i("onError-----------" + e.toString());
            failSize.incrementAndGet();
            checkFinished();
        }

        public void onComplete(double speed) {
            //正确+
            completeSize.incrementAndGet();

            L.i("onComplete----------" + completeSize.longValue() + "----------------");
            if (!checkFinished() && !isCancel) {

//                long end = System.currentTimeMillis() - startTime;
//                double speed = totalSize * 1.0 / end * 1000;

                downloadBundle.setCompletedSize(completeSize.longValue());
                downloadBundle.setStatus(DOWNLOADING);
                event.setSpeed(speed);
                event.setCompletedSize(completeSize.longValue());
                event.setStatus(DOWNLOADING);

                downloadDB.updateDownloadBundle(downloadBundle);
                processorEvent.onNext(event);
            }
        }

        private boolean checkFinished() {
            if (failSize.longValue() + completeSize.longValue() == downloadBundle.getTotalSize()) {
                if (completeSize.get() == downloadBundle.getTotalSize()) {

                    downloadBundle.setCompletedSize(completeSize.longValue());
                    downloadBundle.setStatus(FINISH);


                    downloadDB.updateDownloadBundle(downloadBundle);

                    event.setCompletedSize(completeSize.longValue());
                    event.setStatus(FINISH);
                    processorEvent.onNext(event);
                } else {

                    downloadBundle.setStatus(ERROR);
                    downloadBundle.setCompletedSize(completeSize.longValue());
                    downloadDB.updateDownloadBundle(downloadBundle);

                    event.setStatus(ERROR);
                    event.setCompletedSize(completeSize.longValue());

                    processorEvent.onNext(event);
                }
                isCancel = true;
                return true;
            }
            return false;
        }
    }

    public String getKey() {
        return downloadBundle.getKey();
    }

    public boolean isCancel() {
        return isCancel;
    }

    public int getCompleteSize() {
        return Long.valueOf(downloadBundle.getCompletedSize()).intValue();
    }

    public DownloadBundle getDownloadBundle() {
        return downloadBundle;
    }


}
