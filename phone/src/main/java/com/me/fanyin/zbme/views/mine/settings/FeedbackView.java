package com.me.fanyin.zbme.views.mine.settings;

import com.me.data.mvp.MvpView;

/**
 * Created by jjr on 2017/5/19.
 */

interface FeedbackView extends MvpView {

    void submitSuccessful();
    String getFeedbackTitle();
    String getFeedbackContent();

}
