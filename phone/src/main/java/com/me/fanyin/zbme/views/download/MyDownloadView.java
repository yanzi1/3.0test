package com.me.fanyin.zbme.views.download;

import com.me.data.mvp.MvpView;
import com.me.fanyin.zbme.views.download.adapter.MyDownloadAdapter;

import java.util.List;


public interface MyDownloadView extends MvpView {
    void showDownloadedData(List<MyDownloadAdapter.GroupItem> multiItemEntities);



    void showHeader();

    void setHeaderCount(String size);

    void hideHeader();

    void setHeaderDownloadingTitle(String title);

    void setHeaderProgressBar(long totalSize, long completedSize);


    void showEmpty();
    void showData();


    void getSdcardSize();
    void showEdit();
    void showNormal();
    void onSelect(int count);
    void setMenu();

    void deleteSuccess();

    void deleteFail();
}
