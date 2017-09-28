package com.me.fanyin.zbme.views.download;

import com.me.data.mvp.MvpView;
import com.me.rxdownload.entity.DownloadBundle;

import java.util.List;


public interface DownloadManagerView extends MvpView {
    void showData(List<DownloadBundle> downloadBundleList);

    void showStartAll();
    void showPauseAll();

    void getSdcardSize();
    void onSelect(int count);
    void setMenu();
    void deleteSuccess();
    void deleteFail();
}
