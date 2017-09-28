package com.me.fanyin.zbme.views.main.fragemnt;

import com.me.data.model.main.RecommendedGoodListBean;
import com.me.data.mvp.MvpView;

/**
 * Created by jjr on 2017/5/11.
 */

public interface RecommendedFragmentView extends MvpView{

    void resetView(RecommendedGoodListBean recommendedGoodListBean);
    String getExamId();
    String getGoodsType();

}
