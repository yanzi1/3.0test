package com.me.fanyin.zbme.views.main.fragemnt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.data.common.Constants;
import com.me.data.model.main.RecommendedGoodListBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpFragment;
import com.me.fanyin.zbme.views.main.fragemnt.adapter.RecommendedAdapter;

import butterknife.BindView;

/**
 * Created by jjr on 2017/5/11.
 *
 * 推荐课程、图书的fragment
 */

public class RecommendedFragment extends BaseMvpFragment<RecommendedFragmentView, RecommendedFragmentPresenter> implements RecommendedFragmentView {

    @BindView(R.id.recommended_goods_rcv)
    RecyclerView recommended_goods_rcv;
    private String goodsType;
    private String examId;
    private RecommendedAdapter mAdapter;

    public static RecommendedFragment newInstance(String examId, String goodsType) {
        RecommendedFragment fragment = new RecommendedFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXAM_ID, examId);
        bundle.putString(Constants.GOODS_TYPE, goodsType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        examId = arguments.getString(Constants.EXAM_ID);
        goodsType = arguments.getString(Constants.GOODS_TYPE);
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void initView() {
        recommended_goods_rcv.setLayoutManager(new LinearLayoutManager(mActivity));
        recommended_goods_rcv.setNestedScrollingEnabled(false);
        recommended_goods_rcv.setHasFixedSize(true);
        mAdapter = mPresenter.initAdapter(goodsType, recommended_goods_rcv);
    }

    @Override
    public void initData() {
        mPresenter.getData();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.main_recommended_fragment;
    }

    @Override
    public void resetView(RecommendedGoodListBean recommendedGoodListBean) {
        if (recommendedGoodListBean != null && recommendedGoodListBean.getList().size() > 0) {
            mAdapter.setNewData(recommendedGoodListBean.getList());
            LinearLayout common_recyclerview_head_more = (LinearLayout) LayoutInflater.from(mActivity).inflate(R.layout.common_recyclerview_head_more, recommended_goods_rcv, false);
            TextView recyclerview_head_title_tv = (TextView) common_recyclerview_head_more.findViewById(R.id.recyclerview_head_title_tv);
            switch (goodsType) {
                case "2":
                    recyclerview_head_title_tv.setText("推荐课程");
                    break;
                case "3":
                    recyclerview_head_title_tv.setText("推荐图书");
                    break;
            }
            mAdapter.addHeaderView(common_recyclerview_head_more);

            recommendedIsEmpty(false);
        } else {
            recommendedIsEmpty(true);
        }
    }

    @Override
    public String getExamId() {
        return examId;
    }

    @Override
    public String getGoodsType() {
        if(goodsType.equals("1"))
            return "2";
        return goodsType;
    }

    public void recommendedIsEmpty(boolean isEmpty) {
        if (mRecommendedIsEmptyListener != null) {
            mRecommendedIsEmptyListener.recommendedIsEmpty(isEmpty);
        }
    }

    private RecommendedIsEmptyListener mRecommendedIsEmptyListener;

    public void setRecommendedIsEmptyListener(RecommendedIsEmptyListener listener) {
        mRecommendedIsEmptyListener = listener;
    }

    public interface RecommendedIsEmptyListener {
        void recommendedIsEmpty(boolean isEmpty);
    }
}
