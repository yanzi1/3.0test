package com.me.fanyin.zbme.views.download;

import com.me.data.model.play.CourseDetail;
import com.me.data.mvp.MvpView;

/**
 * Created by mayunfei on 17-6-3.
 */

public interface DownloadMoreView extends MvpView {
    /**
     * 课程详情
     */
    void setData(CourseDetail courseDetail);

    void setdownloadCount(int size);
}
