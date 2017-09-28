package com.me.fanyin.zbme.views.download;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.CourseWare;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseActivity;
import com.me.fanyin.zbme.views.course.play.widget.SelectDialog;
import com.me.fanyin.zbme.widget.dropdown.DropDownMenuTitle;

import butterknife.BindView;

/**
 * Created by mayunfei on 17-6-3.
 */

public class DownloadMoreActivity extends BaseActivity implements DialogInterface.OnDismissListener, SelectDialog.SelectDialogListener {

    DownloadMoreFragment downloadMoreFragment;
    @BindView(R.id.download_more_title)
    DropDownMenuTitle download_more_title;
    @BindView(R.id.download_more_title_ll)
    LinearLayout download_more_title_ll;

    public static void startActivity(Context context, CourseWare courseWare) {
        Intent intent = new Intent(context, DownloadMoreActivity.class);
        intent.putExtra("data", courseWare);
        context.startActivity(intent);
    }

    @BindView(R.id.fragment_layout)
    FrameLayout fragmentLayout;

    @Override
    protected int getLayoutRes() {
        return R.layout.download_more_activity;
    }

    @Override
    protected void initView() {
        mToolbar.setTitleText("缓存更多");
        download_more_title.setText(TextUtils.isEmpty(SharedPrefHelper.getInstance().getPlayQuaity())?"流畅":SharedPrefHelper.getInstance().getPlayQuaity());
        download_more_title_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (downloadMoreFragment != null) {
                    downloadMoreFragment.showPlayQuaityDialog(DownloadMoreActivity.this, DownloadMoreActivity.this,TextUtils.isEmpty(SharedPrefHelper.getInstance().getPlayQuaity())?"流畅":SharedPrefHelper.getInstance().getPlayQuaity());
                    download_more_title.openMenu();
                }
            }
        });
    }

    @Override
    protected void initData() {
        CourseWare courseWare = (CourseWare) getIntent().getSerializableExtra("data");
        if (courseWare != null) {
            if (downloadMoreFragment == null) {
                downloadMoreFragment = new DownloadMoreFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", courseWare);
                downloadMoreFragment.setArguments(bundle);
            }
            addFragment(R.id.fragment_layout, downloadMoreFragment);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        download_more_title.closeMenu();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        download_more_title.closeMenu();
        switch (position) {
            case 0:
                SharedPrefHelper.getInstance().setPlayQuaity("流畅");
                download_more_title.setText("流畅");
                break;
            case 1:
                SharedPrefHelper.getInstance().setPlayQuaity("标清");
                download_more_title.setText("标清");
                break;
            case 2:
                SharedPrefHelper.getInstance().setPlayQuaity("高清");
                download_more_title.setText("高清");
                break;
            default:
                break;
        }
    }
}
