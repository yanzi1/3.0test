package com.me.fanyin.zbme.views.download;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.me.core.utils.ToastBarUtils;
import com.me.data.local.SharedPrefHelper;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.views.download.adapter.MyDownloadAdapter;
import com.me.fanyin.zbme.widget.CommonToolbar;
import com.me.rxdownload.utils.StorageUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 我的下载
 */
public class MyDownloadActivity extends BaseMvpActivity<MyDownloadView, MyDownloadPresenter> implements MyDownloadView, MyDownloadAdapter.OnSelectListener {

    @BindView(R.id.listView)
    ExpandableListView expandableListView;
    @BindView(R.id.progressBar_sdcard_size)
    ProgressBar progressBarSdcardSize;
    @BindView(R.id.tv_sdcard_size)
    TextView tvSdcardSize;
    MyDownloadAdapter myDownloadAdapter;
    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.layout_edit_layout)
    LinearLayout layoutEditLayout;
    @BindView(R.id.layout_sdcard)
    FrameLayout layoutSdcard;
    List<MyDownloadAdapter.GroupItem> data;

    @BindView(R.id.empty)
    View empty;

    private boolean isEdit = false;
    private MyDownloadHeaderView myDownloadHeaderView;

    @Override
    protected void initView() {
        data = new ArrayList<>();
        myDownloadAdapter = new MyDownloadAdapter(data, mPresenter);

        myDownloadHeaderView = new MyDownloadHeaderView(context());
        myDownloadHeaderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(DownloadManagerActivity.class);
            }
        });
        //4.1版本必须先添加Header再设置adapter
        expandableListView.addHeaderView(myDownloadHeaderView);
        expandableListView.setAdapter(myDownloadAdapter);

        mToolbar.setTitleText("我的下载");
        tvSelect.setOnClickListener(this);
        getSdcardSize();
        setMenu();
    }

    /**
     * 显示sd卡存储
     */
    @Override
    public void getSdcardSize() {
        String path = SharedPrefHelper.getInstance().getDownloadRootPath();
        long totalSize = StorageUtils.getTotalSize(path);
        long availableSize = StorageUtils.getAvailableSize(path);
        tvSdcardSize.setText("当前可用: " + StorageUtils.fmtSpace(availableSize) + " / " + StorageUtils.fmtSpace(totalSize));
        progressBarSdcardSize.setMax(100);
        progressBarSdcardSize.setProgress((int) (100 * ((totalSize - availableSize) * 1.0 / totalSize)));
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


    @Override
    protected void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getData();
        mPresenter.getDownloading();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.download_my_activity;
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_select:
                myDownloadAdapter.selectAll();
                break;
        }
    }

    @Override
    public void showError(String message) {

    }


    @Override
    public void showDownloadedData(List<MyDownloadAdapter.GroupItem> multiItemEntities) {
        data.clear();
        data.addAll(multiItemEntities);
        myDownloadAdapter.notifyDataSetChanged();
        myDownloadAdapter.setOnSelectListener(this);
        int size = data.size();
        for (int i = 0; i < size; i++) {
            if (!expandableListView.isGroupExpanded(i)) {
                expandableListView.expandGroup(i);
            }
        }
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
    public void showEdit() {
        myDownloadAdapter.setEdit(true);
        //默认展开
        int size = data.size();
        for (int i = 0; i < size; i++) {
            if (!expandableListView.isGroupExpanded(i)) {
                expandableListView.expandGroup(i);
            }
        }
        layoutEditLayout.setVisibility(View.VISIBLE);
        layoutSdcard.setVisibility(View.GONE);
    }

    @Override
    public void showNormal() {
        myDownloadAdapter.setEdit(false);
        myDownloadAdapter.clearAll();
        layoutSdcard.setVisibility(View.VISIBLE);
        layoutEditLayout.setVisibility(View.GONE);
    }

    @Override
    public void showHeader() {
        if (expandableListView.getHeaderViewsCount() == 0) {
            expandableListView.addHeaderView(myDownloadHeaderView);
        }
    }

    @Override
    public void setHeaderCount(String count) {
        myDownloadHeaderView.setDownloadCount(count);
    }

    @Override
    public void hideHeader() {
        if (expandableListView.getHeaderViewsCount() > 0) {
            expandableListView.removeHeaderView(myDownloadHeaderView);
        }

    }

    @Override
    public void setHeaderDownloadingTitle(String title) {
        myDownloadHeaderView.setHeaderDownloadingTitle(title);
    }

    @Override
    public void setHeaderProgressBar(long totalSize, long completedSize) {
        myDownloadHeaderView.setHeaderProgressBar(totalSize, completedSize);
    }

    @Override
    public void onSelect(int count) {
        if (count == 0) {
            tvDelete.setText("删除");
            tvDelete.setTextColor(ContextCompat.getColor(context(), R.color.delete_un_click));
            tvDelete.setOnClickListener(null);
        } else {
            tvDelete.setText("删除(" + count + ")");
            tvDelete.setTextColor(ContextCompat.getColor(context(), R.color.color_accent));
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除所选
                    mPresenter.deleteSelect();
                }
            });
        }
        if (myDownloadAdapter.isSellectAll()){
            tvSelect.setText("取消全选");
        }else {
            tvSelect.setText("全选");
        }
    }

    @Override
    public void showEmpty() {
        empty.setVisibility(View.VISIBLE);
        expandableListView.setVisibility(View.GONE);
        mToolbar.setMenuLayoutVisibility(View.GONE);
    }

    @Override
    public void showData() {
        empty.setVisibility(View.GONE);
        mToolbar.setMenuLayoutVisibility(View.VISIBLE);
        expandableListView.setVisibility(View.VISIBLE);
    }

}
