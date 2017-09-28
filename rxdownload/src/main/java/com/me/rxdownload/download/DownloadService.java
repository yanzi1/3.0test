package com.me.rxdownload.download;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.me.rxdownload.R;
import com.me.rxdownload.db.DownloadDao;
import com.me.rxdownload.db.IDownloadDB;
import com.me.rxdownload.entity.DownloadBundle;
import com.me.rxdownload.entity.DownloadEvent;
import com.me.rxdownload.entity.DownloadStatus;
import com.me.rxdownload.utils.FileUtils;
import com.me.rxdownload.utils.L;

import org.reactivestreams.Subscription;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.schedulers.Schedulers;

import static com.me.rxdownload.entity.DownloadStatus.DELETED;
import static com.me.rxdownload.entity.DownloadStatus.ERROR;
import static com.me.rxdownload.entity.DownloadStatus.FINISH;
import static com.me.rxdownload.entity.DownloadStatus.PAUSE;
import static com.me.rxdownload.entity.DownloadStatus.QUEUE;
import static com.me.rxdownload.utils.RxUtils.createProcessor;

public class DownloadService extends Service {
    private static final String TAG = "Download Service";

    public static final String INTENT_KEY = "com.dongao.rxdownload.max_download_number";
    private static final int NOTIFICATION_ID = 99999;

    private DownloadBinder mBinder;
    /**
     * 下载队列
     */
    private BlockingQueue<DownloadTask> downloadQueue;
    /**
     * 下载任务
     */
    private Map<String, DownloadTask> taskMap;
    /**
     * 缓存
     */
    private Map<String, FlowableProcessor<DownloadEvent>> processorMap;
    /**
     * 数据库
     */
    private IDownloadDB mDownloadDB;
    //控制线程的信号量
    private Semaphore semaphore;
    private Disposable disposable;
    private NotificationManager mNotificationManager;
    private Disposable downloadIngsubscribe;
    private Executor executor;
    private Disposable downloadingDis;

    public DownloadService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new DownloadBinder();
        downloadQueue = new LinkedBlockingQueue<>();
        taskMap = new ConcurrentHashMap<>();
        processorMap = new ConcurrentHashMap<>();
        mDownloadDB = DownloadDao.getSingleton(this);
        executor = Executors.newFixedThreadPool(2,new ThreadFactory() {
            private AtomicInteger count = new AtomicInteger();
            @Override
            public Thread newThread(@android.support.annotation.NonNull final Runnable r) {
                final int c = count.incrementAndGet();
                Log.i(TAG,"new Thread ++++ " + c);
                return new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG,"start +++ "+c);
                        r.run();
                        Log.i(TAG,"end +++ "+c);
                    }
                }, "download ++++++ " + c);
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //只会执行一次
        L.i(TAG, "onStartCommand Service");
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //暂停数据
        mDownloadDB.pauseAll();

        if (intent != null) {
            int maxDownloadNumber = intent.getIntExtra(INTENT_KEY, 5);
            semaphore = new Semaphore(maxDownloadNumber);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    void showNotification(boolean isDwonloading) {
        Notification notification = null;
        Intent nfIntent = new Intent();
        nfIntent.setClassName(this, "com.dongao.kaoqian.phone.views.download.MyDownloadActivity");
        nfIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Notification.Builder builder = new Notification.Builder(this.getApplicationContext()); //获取一个Notification构造器


            builder
                    .setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0)) // 设置PendingIntent
//         .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_large))// 设置下拉列表中的图标(大图标)
                    .setContentTitle(isDwonloading?"正在后台下载":"下载已经暂停")// 设置下拉列表里的标题

                    .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
                    .setContentText("会计云课堂") // 设置上下文内容
                    .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间
            notification = builder.build(); // 获取构建好的Notification
        } else {
            notification = new Notification.Builder(this.getApplicationContext())
                    .setContentText(isDwonloading?"正在后台下载":"下载已经暂停")
                    .setContentText("会计云课堂") // 设置上下文内容
                    .setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0)) // 设置PendingIntent
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .getNotification();
        }
        if (isDwonloading){

            startForeground(NOTIFICATION_ID, notification);// 开始前台服务
        }else {
            stopForeground(false);
        }

        mNotificationManager.notify(NOTIFICATION_ID, notification);
    }

    void hideNotification() {
        stopForeground(false);
        mNotificationManager.cancel(NOTIFICATION_ID);
    }


    @Override
    public IBinder onBind(Intent intent) {
        L.i(TAG, "binding Service");
        startDispatch();
        return mBinder;
    }

    private void startDispatch() {

        disposable = Observable.create(new ObservableOnSubscribe<DownloadTask>() {
            @Override
            public void subscribe(ObservableEmitter<DownloadTask> emitter) throws Exception {
                DownloadTask task;
                while (!emitter.isDisposed()) {
                    boolean download = false;
                    try {
                        task = downloadQueue.take();
                        download = task.prepareStart();
                    } catch (InterruptedException e) {
                        continue;
                    }
                    if (download) {
                        emitter.onNext(task);
                    }
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.from(executor)).subscribe(new Consumer<DownloadTask>() {
            @Override
            public void accept(DownloadTask task) throws Exception {
                checkDownload();
                task.startDownload(semaphore,executor);
                if (downloadingDis!=null && !downloadingDis.isDisposed()){
                    downloadingDis.dispose();
                    downloadingDis = null;
                }
                downloadingDis = createProcessor(task.getKey(), processorMap)
                        .doOnSubscribe(new Consumer<Subscription>() {
                            @Override
                            public void accept(@NonNull Subscription subscription) throws Exception {

                            }
                        })
                        .subscribe(new Consumer<DownloadEvent>() {
                            @Override
                            public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
                                if (downloadEvent.getStatus() == FINISH || downloadEvent.getStatus() == DELETED) {
                                    checkDownload();
                                }
                            }
                        });

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                L.e(TAG, throwable + "");
            }
        });
    }


    void checkDownload()
    {
        boolean isDwonloading = false;
        DownloadTask temp = null;
        int finishedCount = 0;
        for (DownloadTask downloadTask:taskMap.values()){
            if (downloadTask.getDownloadBundle().getStatus()!=DownloadStatus.FINISH){

                if (!downloadTask.isCancel() ) {
                    isDwonloading = true;
                    if (temp==null){
                        temp = downloadTask;
                    }
                }
            }else {
                finishedCount ++;
            }
        }
        if (finishedCount < taskMap.size()){
            showNotification(isDwonloading);
        }else {
            hideNotification();
        }

        if (temp !=null){
            if (downloadIngsubscribe != null && !downloadIngsubscribe.isDisposed()){
                downloadIngsubscribe.dispose();
            }
            downloadIngsubscribe = createProcessor(temp.getDownloadBundle().getKey(), processorMap).subscribe(new Consumer<DownloadEvent>() {
                @Override
                public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
                    if (downloadEvent.getStatus() == FINISH || downloadEvent.getStatus() == PAUSE || downloadEvent.getStatus()==ERROR) {
                        checkDownload();
                    }
                }
            });
        }
    }

    public class DownloadBinder extends Binder {
        public DownloadService getService() {
            return DownloadService.this;
        }
    }

    public synchronized void addTask(DownloadTask downloadTask) throws InterruptedException {
        //先判断 任务栈 TODO
        //初始化
        DownloadTask task = taskMap.get(downloadTask.getKey());

        //有任务已经在运行
        if (task != null) {
            if (task.isCancel()) {
                downloadTask.init(taskMap, processorMap, mDownloadDB); //替换了新的Task
                downloadTask.insertOrUpdate();
                DownloadEvent downloadEvent = new DownloadEvent();
                downloadEvent.setCompletedSize(task.getCompleteSize());
                downloadEvent.setTotalSize(downloadTask.getTotalSize());
                downloadEvent.setStatus(QUEUE);
                createProcessor(downloadTask.getKey(), processorMap).onNext(downloadEvent);
                downloadQueue.put(downloadTask);
                return;
            }
        } else {
            DownloadEvent downloadEvent = mDownloadDB.selectBundleStatus(downloadTask.getKey());
            if (downloadEvent.getStatus() == FINISH) {
                createProcessor(downloadTask.getKey(), processorMap).onNext(downloadEvent);
            } else {
                downloadTask.init(taskMap, processorMap, mDownloadDB);
                downloadTask.insertOrUpdate();
                downloadEvent.setCompletedSize(downloadTask.getCompleteSize());
                downloadEvent.setTotalSize(downloadTask.getTotalSize());
                downloadEvent.setStatus(QUEUE);
                createProcessor(downloadTask.getKey(), processorMap).onNext(downloadEvent);
                downloadQueue.put(downloadTask);
            }
        }


    }

    public synchronized void pause(String key) {
        DownloadTask downloadTask = taskMap.get(key);
        if (downloadTask != null) {
            downloadTask.pause();
        }
    }

    public FlowableProcessor<DownloadEvent> getDownloadEvent(String key) {
        FlowableProcessor<DownloadEvent> processor = createProcessor(key, processorMap);
        DownloadTask task = taskMap.get(key);
        if (task == null) {
            //判断是否有数据库 是否有文件
            DownloadEvent downloadEvent = mDownloadDB.selectBundleStatus(key);
            if (downloadEvent.getTotalSize() == -1) { //数据库没有数据
                downloadEvent.setStatus(DELETED);
                downloadEvent.setCompletedSize(0);
                downloadEvent.setTotalSize(100);
            } else if (downloadEvent.getStatus() == FINISH) {
                downloadEvent.setStatus(FINISH);
            } else {
                downloadEvent.setStatus(PAUSE);
            }
            processor.onNext(downloadEvent);
        }

        return processor;
    }

    @Override
    public void onDestroy() {
        L.i(TAG, "onDestroy");
        mDownloadDB.closeDataBase();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    public Observable<List<DownloadBundle>> getAllDownloadBundle() {
        return Observable.just(1).map(new Function<Integer, List<DownloadBundle>>() {
            @Override
            public List<DownloadBundle> apply(@NonNull Integer integer) throws Exception {
                return mDownloadDB.getAllDownloadBundle();
            }
        });
    }

    /**
     * 暂停所有
     */
    public void pauseAll() {
        for (DownloadTask task : taskMap.values()) {
            task.pause();
        }
    }

    public void startAll() throws InterruptedException {
        for (DownloadTask task : taskMap.values()) {
            if (!task.isFinished()) {
                addTask(task);
            }
        }
    }

    public void startList(DownloadTask... tasks) throws InterruptedException {
        for (DownloadTask task : tasks) {
            addTask(task);
        }
    }

    public boolean delete(String key) {
        DownloadTask task = taskMap.get(key);


//        if (task != null && !task.isFinished() && !task.isCancel()) {
        if (task != null) {
            task.pause();
            taskMap.remove(key);
        }
        DownloadBundle downloadBundle = mDownloadDB.getDownloadBundle(key);

        if (downloadBundle != null && mDownloadDB.delete(key)) {
            L.e("Delete " + downloadBundle.getPath());
            FileUtils.deleteDir(downloadBundle.getPath());
        } else {
            return false;
        }
        DownloadEvent downloadEvent = new DownloadEvent();
        downloadEvent.setStatus(DELETED);
        createProcessor(key, processorMap).onNext(downloadEvent);
        return true;
    }
}
