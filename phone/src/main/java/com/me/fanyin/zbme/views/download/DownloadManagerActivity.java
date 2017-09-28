package com.me.fanyin.zbme.views.download;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.me.core.utils.NetWorkUtils;
import com.me.core.utils.ToastBarUtils;
import com.me.data.local.SharedPrefHelper;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.views.download.adapter.DownloadManagerAdapter;
import com.me.fanyin.zbme.views.download.adapter.MyDownloadAdapter;
import com.me.fanyin.zbme.widget.CommonToolbar;
import com.me.fanyin.zbme.widget.DialogManager;
import com.me.rxdownload.entity.DownloadBundle;
import com.me.rxdownload.utils.StorageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 下载管理
 */
public class DownloadManagerActivity extends BaseMvpActivity<DownloadManagerView, DownloadManagerPresenter> implements DownloadManagerView, MyDownloadAdapter.OnSelectListener {

    @BindView(R.id.progressBar_sdcard_size)
    ProgressBar progressBarSdcardSize;
    @BindView(R.id.tv_sdcard_size)
    TextView tvSdcardSize;
    @BindView(R.id.layout_sdcard)
    FrameLayout layoutSdcard;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.layout_edit_layout)
    LinearLayout layoutEditLayout;

    private List<DownloadBundle> data;
    private DownloadManagerAdapter adapter;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private TextView tv_control;

    private boolean isEdit = false;

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        mToolbar.setTitleText("正在下载");
        data = new ArrayList<>();
        mPresenter.getData();
        adapter = new DownloadManagerAdapter(data, this);
        adapter.setOnSelectListener(this);
        recycler.setLayoutManager(new LinearLayoutManager(context()));
        adapter.bindToRecyclerView(recycler);
        recycler.setAdapter(adapter);
        adapter.setEmptyView(R.layout.app_view_empty);
        View header = LayoutInflater.from(this).inflate(R.layout.download_manager_control, null, false);
        tv_control = (TextView) header.findViewById(R.id.tv_control);
        adapter.addHeaderView(header);

        tvSelect.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        getSdcardSize();
        setMenu();

    }


    @Override
    protected int getLayoutRes() {
        return R.layout.download_manager_activity;
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_select:
                adapter.selectAll();
                break;
            case R.id.tv_delete:
                break;
        }
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onDestroy() {
        adapter.disposable();
        super.onDestroy();
    }

    @Override
    public void showData(List<DownloadBundle> downloadBundleList) {
        data.clear();
        data.addAll(downloadBundleList);
        adapter.notifyDataSetChanged();

        if (data.size() == 0) {
            isEdit = false;
            setMenu();
            mToolbar.settextMenuTextColor(R.color.text_color_primary_light_more);
            mToolbar.setOnMenuClickListener(null);
        } else {
            setMenu();
        }

    }

    @Override
    public void showStartAll() {
        tv_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNetOnClick(context(), new Runnable() {
                    @Override
                    public void run() {
                        mPresenter.startAll();
                    }
                });

            }
        });
        final Drawable start = ContextCompat.getDrawable(context(), R.mipmap.btn_download_allstart);
        start.setBounds(0, 0, start.getMinimumWidth(), start.getMinimumHeight());
        tv_control.setCompoundDrawables(start, null, null, null);
        tv_control.setText("全部开始");
    }

    @Override
    public void showPauseAll() {
        final Drawable pause = ContextCompat.getDrawable(context(), R.mipmap.btn_download_allpause);
        pause.setBounds(0, 0, pause.getMinimumWidth(), pause.getMinimumHeight());
        tv_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.pauseAll();
            }
        });
        tv_control.setCompoundDrawables(pause, null, null, null);
        tv_control.setText("全部暂停");
    }

    private void checkNetOnClick(Context context, final Runnable runnable) {
        String type = NetWorkUtils.getNetworkTypeName(context);
        if (type.equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {//
            DialogManager.showOneButtonDialog(context, null, context.getResources().getString(R.string.dialog_message_vedio), 0, null);
        } else if (type.equals(NetWorkUtils.NETWORK_TYPE_WIFI)) {
            runnable.run();
        } else {

            if (SharedPrefHelper.getInstance().canUseIn4G()){
                DialogManager.showConfirmWithCancelDialog(context(), new DialogManager.ConfirmWithCancelDialogListener() {
                    @Override
                    public void confirm() {
                        runnable.run();
                    }

                    @Override
                    public void cancel() {

                    }
                }, context().getResources().getString(R.string.dialog_allow_download), 0, null, null);
            }else {
                DialogManager.showConfirmWithCancelDialog(context(), new DialogManager.ConfirmWithCancelDialogListener() {
                    @Override
                    public void confirm() {
                        SharedPrefHelper.getInstance().set4GCanUse(true);
                        DialogManager.showConfirmWithCancelDialog(context(), new DialogManager.ConfirmWithCancelDialogListener() {
                            @Override
                            public void confirm() {
                                runnable.run();
                            }

                            @Override
                            public void cancel() {

                            }
                        }, context().getResources().getString(R.string.dialog_allow_download), 0, null, null);
                    }

                    @Override
                    public void cancel() {

                    }
                }, context().getResources().getString(R.string.dialog_warnning_download), 0, null, null);
            }
        }
    }

    @Override
    public void getSdcardSize() {
        String path = SharedPrefHelper.getInstance().getDownloadRootPath();
        long totalSize = StorageUtils.getTotalSize(path);
        long availableSize = StorageUtils.getAvailableSize(path);
        tvSdcardSize.setText("当前可用: " + StorageUtils.fmtSpace(availableSize) + " / " + StorageUtils.fmtSpace(totalSize));
        progressBarSdcardSize.setMax(100);
        progressBarSdcardSize.setProgress((int) (100 * ((totalSize - availableSize) * 1.0 / totalSize)));
    }

    private void showEdit() {
        adapter.setEdit(true);
        layoutEditLayout.setVisibility(View.VISIBLE);
        layoutSdcard.setVisibility(View.GONE);
    }

    private void showNormal() {
        adapter.setEdit(false);
        adapter.clearAll();
        layoutSdcard.setVisibility(View.VISIBLE);
        layoutEditLayout.setVisibility(View.GONE);
    }

    @Override
    public void onSelect(int count) {
        if (count == 0) {
            tvDelete.setTextColor(ContextCompat.getColor(context(), R.color.delete_un_click));
            tvDelete.setText("删除");
            tvDelete.setOnClickListener(null);
        } else {
            tvDelete.setText("删除(" + count + ")");
            tvDelete.setTextColor(ContextCompat.getColor(context(), R.color.color_accent));
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除所选
                    mPresenter.deleteSelect(adapter.getSelect());
                }
            });
        }
        if (adapter.isSellectAll()) {
            tvSelect.setText("取消全选");
        } else {
            tvSelect.setText("全选");
        }
    }

    @Override
    public void setMenu() {
        if (isEdit) {
            mToolbar.setTextMenuText("取消");
            mToolbar.settextMenuTextColor(R.color.color_primary);
            showEdit();

        } else {
            mToolbar.settextMenuTextColor(R.color.text_color_primary);
            mToolbar.setTextMenuText("编辑");
            showNormal();
        }

        mToolbar.setOnMenuClickListener(new CommonToolbar.CommonToolbarClickListener() {
            @Override
            public void onClick(View view) {
                isEdit = !isEdit;
                setMenu();
            }
        });
    }

    @Override
    public void deleteSuccess() {
        ToastBarUtils.showToast(context(), "删除成功");
    }

    @Override
    public void deleteFail() {
        ToastBarUtils.showToast(context(), "删除失败");
    }


}
