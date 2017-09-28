package com.me.fanyin.zbme.views.download;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.me.fanyin.zbme.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mayunfei on 17-4-18.
 */

public class MyDownloadHeaderView extends FrameLayout {
    @BindView(R.id.tv_download_count)
    TextView tvDownloadCount;
    @BindView(R.id.tv_class)
    TextView tvClass;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public MyDownloadHeaderView(@NonNull Context context) {
        this(context, null);
    }

    public MyDownloadHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDownloadHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.download_my_header, this, true);
        ButterKnife.bind(this, this);
    }

    public void setDownloadCount(String downloadCount) {
        tvDownloadCount.setText(downloadCount);
    }

    public void setHeaderDownloadingTitle(String title) {
        tvClass.setText(title);
    }

    public void setHeaderProgressBar(long totalSize, long completedSize) {
        progressBar.setMax(Long.valueOf(totalSize).intValue());
        progressBar.setProgress(Long.valueOf(completedSize).intValue());
    }
}
