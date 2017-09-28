package com.me.fanyin.zbme.views.course;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.me.core.exception.ApiException;
import com.me.core.utils.NetWorkUtils;
import com.me.core.utils.ToastBarUtils;
import com.me.data.event.LoginSuccessEvent;
import com.me.data.event.SystemChangeEvent;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.main.MainTypeBean;
import com.me.data.model.main.MainTypeDetailBean;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;
import com.me.data.remote.utils.ParamsUtils;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseActivity;
import com.me.fanyin.zbme.views.user.LoginActivity;
import com.me.fanyin.zbme.widget.CommonToolbar;
import com.me.fanyin.zbme.widget.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.reactivestreams.Subscription;

import java.net.UnknownHostException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by mayunfei on 17-8-31.
 */

public class SystemChangeActivity extends BaseActivity implements CommonToolbar.CommonToolbarClickListener {
    @BindView(R.id.classical)
    LinearLayout classical;
    @BindView(R.id.intelligent)
    LinearLayout intelligent;
    @BindView(R.id.btn_enter)
    Button btnEnter;
    boolean beforeIsIntel;
    protected LoadingDialog loadingDialog;
    CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected int getLayoutRes() {
        return R.layout.course_system_change_activity;
    }

    @Override
    protected void initView() {
        mToolbar.setTitleText("选择学习模式");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mToolbar.setImageMenuRes(R.drawable.exam_paper_draw_bar_close);
        mToolbar.setOnMenuClickListener(this);
        loadingDialog = new LoadingDialog(this);
        //TODO 判断选择的是否是只能系统
        beforeIsIntel = SharedPrefHelper.getInstance().isIntel();
        setSelect();
        EventBus.getDefault().register(this);
    }

    /**
     * 登录成功刷新
     */
    @Subscribe
    public void onEventLoginSuccess(LoginSuccessEvent event) {
        enterSystem();
    }


    @OnClick({R.id.classical, R.id.intelligent, R.id.btn_enter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.classical:
                beforeIsIntel = false;
                setSelect();
                break;
            case R.id.intelligent:
                beforeIsIntel = true;
                setSelect();
                break;
            case R.id.btn_enter:
                if (beforeIsIntel&&!SharedPrefHelper.getInstance().isLogin()) {
                    gotoActivity(LoginActivity.class);
                    return;
                }
               enterSystem();
                break;
        }
    }

    private void enterSystem() {
        if (beforeIsIntel != SharedPrefHelper.getInstance().isIntel()) {
            if (!NetWorkUtils.isNetworkAvailable(this)){
                ToastBarUtils.showToast(this,getString(R.string.app_nonetwork_message));
                return;
            }
            if (beforeIsIntel && TextUtils.isEmpty(SharedPrefHelper.getInstance().getIntelExamId())) {
                Disposable subscribe = ApiService.getIntelMainTypeInfo(ParamsUtils.getIntelTypeData())
                        .compose(RxUtils.<MainTypeBean>retrofit())
                        .compose(RxUtils.<MainTypeBean>ioMain())
                        .doOnSubscribe(new Consumer<Subscription>() {
                            @Override
                            public void accept(@NonNull Subscription subscription) throws Exception {
                                loadingDialog.show();
                            }
                        }).doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                loadingDialog.dismiss();
                            }
                        })
                        .subscribe(new Consumer<MainTypeBean>() {
                            @Override
                            public void accept(@NonNull MainTypeBean mainTypeBean) throws Exception {
                                List<MainTypeDetailBean> list = mainTypeBean.getList();
                                if (list != null && !list.isEmpty()) {
                                    MainTypeDetailBean mainTypeDetailBean = list.get(0);
                                    SharedPrefHelper.getInstance().setIntelExamId(mainTypeDetailBean.getExamId());
                                    SharedPrefHelper.getInstance().setIntelExamName(mainTypeDetailBean.getExamName());
                                    SharedPrefHelper.getInstance().setIntel(beforeIsIntel);
                                    EventBus.getDefault().post(new SystemChangeEvent());
                                    finish();
                                }else {
                                    ToastBarUtils.showToast(SystemChangeActivity.this, "您还没有购买相应的商品");
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                if (throwable instanceof ApiException) {
                                    if (((ApiException) throwable).getCode() == -8) {
                                        ToastBarUtils.showToast(SystemChangeActivity.this, "您还没有购买相应的商品");
                                    } else {
                                        ToastBarUtils.showToast(SystemChangeActivity.this, throwable.getMessage());
                                    }
                                } else {
                                    if (throwable instanceof UnknownHostException){
                                        ToastBarUtils.showToast(SystemChangeActivity.this,getString(R.string.app_nonetwork_message));
                                        return;
                                    }
                                    ToastBarUtils.showToast(SystemChangeActivity.this, "系统异常");
                                }
                            }
                        });
                mDisposable.add(subscribe);

            } else {
                SharedPrefHelper.getInstance().setIntel(beforeIsIntel);
                EventBus.getDefault().post(new SystemChangeEvent());
                finish();
            }
        } else {
            EventBus.getDefault().post(new SystemChangeEvent());
            finish();
        }
    }

    private void setSelect() {
        if (beforeIsIntel) {
            intelligent.setBackgroundResource(R.drawable.orange_bound_btn_selector);
            classical.setBackgroundResource(R.drawable.gray_bound_btn_selecter);
        } else {
            classical.setBackgroundResource(R.drawable.orange_bound_btn_selector);
            intelligent.setBackgroundResource(R.drawable.gray_bound_btn_selecter);
        }
    }

    @Override
    protected void onDestroy() {
        mDisposable.dispose();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_top);
    }

    @Override
    public void onClick(View view) {
        finish();
    }


}
