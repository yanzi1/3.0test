package com.me.fanyin.zbme.views.main;

import com.me.data.model.main.MainADBean;
import com.me.data.mvp.MvpView;

/**
 * Created by xunice on 13/03/2017.
 */

public interface MainView extends MvpView {
	void initAdapter();
	void showViewStatus(int status);
	void showADDialog(MainADBean mainADBean);
}
