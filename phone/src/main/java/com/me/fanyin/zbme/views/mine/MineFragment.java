package com.me.fanyin.zbme.views.mine;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.me.core.utils.DensityUtils;
import com.me.core.utils.ToastBarUtils;
import com.me.data.common.Statistics;
import com.me.data.event.LoginSuccessEvent;
import com.me.data.event.LogoutSuccessEvent;
import com.me.data.local.SharedPrefHelper;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpFragment;
import com.me.fanyin.zbme.views.mine.settings.SettingsActivity;
import com.me.fanyin.zbme.views.user.LoginActivity;
import com.me.fanyin.zbme.widget.GlideCircleTransform;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends BaseMvpFragment<MineView, MinePersenter> implements MineView {

    @BindView(R.id.mine_ctl)
    CollapsingToolbarLayout mine_ctl;
    @BindView(R.id.mine_abl)
    AppBarLayout mine_abl;
    @BindView(R.id.mine_head_ll)
    LinearLayout mine_head_ll;
    @BindView(R.id.mine_avatar_iv)
    ImageView mine_avatar_iv;
    @BindView(R.id.mine_username_tv)
    TextView mine_username_tv;
    @BindView(R.id.mine_logo_iv)
    ImageView mine_logo_iv;
    @BindView(R.id.mine_title_tv)
    TextView mine_title_tv;
    @BindView(R.id.mine_lv_tv)
    TextView mine_lv_tv;
    @BindView(R.id.mine_more_iv)
    ImageView mine_more_iv;
    @BindView(R.id.mine_cache_tv)
    TextView mine_cache_tv;
    @BindView(R.id.mine_answer_tv)
    TextView mine_answer_tv;
    @BindView(R.id.mine_errors_tv)
    TextView mine_errors_tv;
    @BindView(R.id.mine_notes_tv)
    TextView mine_notes_tv;
    @BindView(R.id.mine_collect_tv)
    TextView mine_collect_tv;
    @BindView(R.id.mine_message_tv)
    TextView mine_message_tv;
    @BindView(R.id.mine_learning_record_tv)
    TextView mine_learning_record_tv;
    @BindView(R.id.mine_curriculum_tv)
    TextView mine_curriculum_tv;
    @BindView(R.id.mine_books_activate_tv)
    TextView mine_books_activate_tv;
    @BindView(R.id.mine_order_tv)
    TextView mine_order_tv;
    @BindView(R.id.mine_invoice_tv)
    TextView mine_invoice_tv;
    @BindView(R.id.mine_account_tv)
    TextView mine_account_tv;
    @BindView(R.id.top_bg_iv)
    ImageView top_bg_iv;
    private ImageView mine_menu_kefu_iv;
    private ImageView mine_menu_settings_iv;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        resetUI();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(LoginSuccessEvent event) {
        resetUI();
    }

    @Subscribe
    public void onEventMainThread(LogoutSuccessEvent event) {
        resetUI();
    }

    @Override
    public void initView() {
        initToolbar();
        resetToolbar();
        mine_avatar_iv.setOnClickListener(this);
        mine_username_tv.setOnClickListener(this);
        mine_logo_iv.setOnClickListener(this);
        mine_title_tv.setOnClickListener(this);
        mine_more_iv.setOnClickListener(this);
        mine_cache_tv.setOnClickListener(this);
        mine_answer_tv.setOnClickListener(this);
        mine_errors_tv.setOnClickListener(this);
        mine_notes_tv.setOnClickListener(this);
        mine_collect_tv.setOnClickListener(this);
        mine_message_tv.setOnClickListener(this);
        mine_learning_record_tv.setOnClickListener(this);
        mine_curriculum_tv.setOnClickListener(this);
        mine_books_activate_tv.setOnClickListener(this);
        mine_order_tv.setOnClickListener(this);
        mine_invoice_tv.setOnClickListener(this);
        mine_account_tv.setOnClickListener(this);
    }

    private void initToolbar() {
        mToolbar.inflateMenu(R.menu.mine_menu);
        mine_menu_kefu_iv = (ImageView) mToolbar.getMenu().findItem(R.id.menu_mine_kefu).getActionView().findViewById(R.id.mine_menu_kefu_iv);
        mine_menu_kefu_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(mActivity, Statistics.PROFILE_ONLINECUSTOMERSERVICE);
            }
        });
        mine_menu_settings_iv = (ImageView) mToolbar.getMenu().findItem(R.id.menu_mine_settings).getActionView().findViewById(R.id.mine_menu_settings_iv);
        mine_menu_settings_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(SettingsActivity.class);
            }
        });
    }


    private void resetToolbar() {
        mine_abl.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -DensityUtils.dip2px(context(), 93)) { //appbarlayout height 150dp toobar height 50dp
                    mine_logo_iv.setVisibility(View.VISIBLE);
                    mine_title_tv.setVisibility(View.VISIBLE);
                    mine_menu_kefu_iv.setImageResource(R.mipmap.btn_nav_kefu);
                    mine_menu_settings_iv.setImageResource(R.mipmap.btn_nav_setting);
                } else {
                    mine_logo_iv.setVisibility(View.GONE);
                    mine_title_tv.setVisibility(View.GONE);
                    mine_menu_kefu_iv.setImageResource(R.mipmap.btn_nav_kefu_white);
                    mine_menu_settings_iv.setImageResource(R.mipmap.btn_nav_setting_white);
                }
            }
        });
    }

    private void resetUI() {
        mine_ctl.setTitle("");
        Glide.with(this).load(SharedPrefHelper.getInstance().getUserAvatarImageUrl()).asBitmap().placeholder(R.mipmap.ico_avatar_weidenglu).error(SharedPrefHelper.getInstance().isLogin() ? R.mipmap.ico_avatar_denglu : R.mipmap.ico_avatar_weidenglu).transform(new GlideCircleTransform(mActivity)).into(mine_avatar_iv);
        mine_username_tv.setText(SharedPrefHelper.getInstance().isLogin() ? SharedPrefHelper.getInstance().getLoginUsername() : "登录/注册");
        mine_lv_tv.setText(SharedPrefHelper.getInstance().isLogin() ? "Lv " + SharedPrefHelper.getInstance().getLevel() : "");
        mine_lv_tv.setVisibility(SharedPrefHelper.getInstance().isLogin() ? View.VISIBLE : View.GONE);
        mine_title_tv.setText(SharedPrefHelper.getInstance().isLogin() ? SharedPrefHelper.getInstance().getLoginUsername() : "登录/注册");
        Glide.with(this).load(SharedPrefHelper.getInstance().getUserAvatarImageUrl()).asBitmap().placeholder(R.mipmap.ico_avatar_weidenglu).error(SharedPrefHelper.getInstance().isLogin() ? R.mipmap.ico_avatar_denglu : R.mipmap.ico_avatar_weidenglu).transform(new GlideCircleTransform(mActivity)).into(mine_logo_iv);
    }

    @Override
    public void initData() {
        mPresenter.getData();
    }

    @Override
    public void showError(String message) {
        ToastBarUtils.showToast(mActivity, message);
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.mine_fragment;
    }

    @Override
    public void onClick(View v) {
    }

    public void gotoActivityAfterCheckLogin(Class<?> clz) {
        gotoActivityAfterCheckLogin(clz, true);
    }

    public void gotoActivityAfterCheckLogin(Class<?> clz, boolean afterLoginToActiity) {
        if (SharedPrefHelper.getInstance().isLogin()) {
            gotoActivity(clz);
        } else if (afterLoginToActiity) {
            gotoActivity(LoginActivity.class, clz.getName());
        } else {
            gotoActivity(LoginActivity.class);
        }
    }
}
