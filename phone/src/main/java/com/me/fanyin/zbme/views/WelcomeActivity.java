package com.me.fanyin.zbme.views;

import android.media.MediaPlayer;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.me.core.utils.CryptoUtils;
import com.me.core.utils.FileUtils;
import com.me.core.utils.NetWorkUtils;
import com.me.data.common.Statistics;
import com.me.data.local.SharedPrefHelper;
import com.me.data.local.WelcomeDB;
import com.me.data.model.BaseRes;
import com.me.data.model.WelcomeRes;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.ParamsUtils;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseActivity;
import com.me.fanyin.zbme.base.CommonWebViewBuyActivity;
import com.me.fanyin.zbme.widget.WelcomeVideoView;
import com.umeng.analytics.MobclickAgent;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class WelcomeActivity extends BaseActivity {
    private static final String RES_TYPE_IMAGE="1";
    private static final String RES_TYPE_VIDEO="2";
    @BindView(R.id.welcome_iv)
    ImageView welcome_iv;

    @BindView(R.id.videoview)
    WelcomeVideoView videoView;

    @BindView(R.id.welcome_ll)
    LinearLayout welcome_ll;

    @BindView(R.id.time_tv)
    TextView time_tv;

    Subscription subscr;

    String dir;

    private CompositeDisposable disposable=new CompositeDisposable();

    Subscription downloadSub;

    private WelcomeDB welcomeDB;
    private WelcomeRes welcomeRes;
    private boolean isGoMain;

    @Override
    public void initView() {
        welcome_iv.setClickable(false);
        videoView.setClickable(false);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                gotoActivity(MainActivity.class,true);
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                gotoActivity(MainActivity.class,true);
                return true;
            }
        });
    }

    @Override
    public void initData() {
        dir=this.getFilesDir().getAbsolutePath()+"/welcomeRes";
//        welcomeDB=new WelcomeDB();
//        welcomeRes = welcomeDB.find();

        requestWelcomeData();

        Observable.timer(2,TimeUnit.SECONDS,AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        if (SharedPrefHelper.getInstance().isFirstIn()){
                            //TODO 直接进入引导页
                            gotoActivity(GuideActivity.class,true);

                        }else{
//                            if (welcomeRes==null || TextUtils.isEmpty(welcomeRes.getPath())){
                            if (welcomeRes==null ||  "3".equals(welcomeRes.getStatus()+"") ||  TextUtils.isEmpty(welcomeRes.getSourceUrl())){
                                gotoActivity(MainActivity.class,true);
                            }else{
                                showImageOrVideo();
                            }
                        }
                    }
                });

    }

    public void showImageOrVideo(){
        if (RES_TYPE_IMAGE.equals(welcomeRes.getSourceType())){
            welcome_iv.setClickable(true);
            Glide.with(this).load(welcomeRes.getSourceUrl()).into(welcome_iv);
//            Glide.with(this).load(new File(welcomeRes.getPath())).into(welcome_iv);
        } else{
            videoView.setClickable(true);
            videoView.setVideoURI(Uri.parse(welcomeRes.getSourceUrl()));
            videoView.start();
            videoView.setVisibility(View.VISIBLE);
            welcome_iv.setVisibility(View.GONE);
        }
        welcome_ll.setVisibility(View.VISIBLE);
        final int take = Integer.parseInt(welcomeRes.getAdSeconds());
        Flowable.interval(0,1,TimeUnit.SECONDS)
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        subscr = subscription;
                    }
                })
                .take(take + 1)
                .map(new Function<Long,Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return take - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        time_tv.setText(aLong+"");
                        if(aLong == 0){
                            if (!isGoMain)
                                gotoActivity(MainActivity.class,true);
                        }
                    }
                });
    }


    private void requestWelcomeData() {
        Disposable subscribe = ApiService.requestWelcomeData(ParamsUtils.requestWelcomeData())
                .compose(RxUtils.<BaseRes<WelcomeRes>>ioMain())
                .subscribe(new Consumer<BaseRes<WelcomeRes>>() {
                    @Override
                    public void accept(@NonNull BaseRes<WelcomeRes> welcomeResBaseRes) throws Exception {
                        welcomeRes=welcomeResBaseRes.getObj();
//                        downloadWelcomeImageOrVideo(welcomeResBaseRes.getObj());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
        disposable.add(subscribe);
    }

    public void downloadWelcomeImageOrVideo(final WelcomeRes res){
        if (welcomeRes!=null && res.getAdVersion() .equals( welcomeRes.getAdVersion())){
            return;
        }

        if (RES_TYPE_VIDEO.equals(res.getSourceType()) && !NetWorkUtils.isWiFi(this)){
            return;
        }
        final String fileName = CryptoUtils.MD5(res.getSourceUrl())+ FileUtils.getFileEName(res.getSourceUrl());
        ApiService.downloadWelcomeImageOrVideo(res.getSourceUrl())
                .doOnNext(new Consumer<Response<ResponseBody>>() {
                    @Override
                    public void accept(@NonNull Response<ResponseBody> responseBodyResponse) throws Exception {
                        File oldfile = new File(dir, fileName);
                        if (oldfile.exists()){
                            if (oldfile.length() == responseBodyResponse.body().contentLength()){
                                res.setPath(oldfile.getAbsolutePath());
                                welcomeDB.updateBydeleteAll(res);
                                return;
                            }
                        }
                        if (responseBodyResponse.isSuccessful()){
                            InputStream is = responseBodyResponse.body().byteStream();
                            byte[] data=new byte[4096];
                            int length;
                            File file=new File(dir,fileName);
                            File parentFile=file.getParentFile();
                            if (!parentFile.exists()){
                                parentFile.mkdirs();
                            }
                            if (!file.exists()){
                                file.createNewFile();
                            }
                            FileOutputStream fos=new FileOutputStream(file);
                            while((length=is.read(data))!=-1){
                                fos.write(data,0,length);
                            }
                            fos.close();
                            is.close();

                            res.setPath(new File(dir,fileName).getAbsolutePath());
                            welcomeDB.updateBydeleteAll(res);
                        }
                    }
                })
                .compose(RxUtils.<Response<ResponseBody>>ioMain())
                .subscribeWith(new Subscriber<Response<ResponseBody>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        downloadSub=s;

                    }

                    @Override
                    public void onNext(Response<ResponseBody> responseBodyResponse) {
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        videoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }

    @OnClick({R.id.welcome_ll,R.id.videoview,R.id.welcome_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.welcome_ll:
                subscr.cancel();
                gotoActivity(MainActivity.class,true);
                break;
            case R.id.welcome_iv:
            case R.id.videoview:
                isGoMain =true;
                gotoActivity(MainActivity.class);
                if ("2".equals(welcomeRes.getType())){
                }else{
                    MobclickAgent.onEvent(this, Statistics.MAIN_ADVERTISEMENTPAGE);
                    CommonWebViewBuyActivity.startActivity(this, "活动详情", welcomeRes.getSourceLink());
                }
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
        if (downloadSub!= null)
            downloadSub.cancel();
        if (videoView!=null)
            videoView.stopPlayback();
        if (subscr != null )
            subscr.cancel();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.welcome_activity;
    }

}
