package com.me.fanyin.zbme.views.exam.activity.exampaperlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.exam.activity.base.BaseFragmentActivity;
import com.me.fanyin.zbme.views.exam.activity.exampaperlist.adapter.ExamPaperListAdapter;
import com.me.fanyin.zbme.views.exam.utils.Constant;
import com.me.fanyin.zbme.views.exam.view.RefreshLayout;
import com.me.fanyin.zbme.widget.EmptyViewLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wyc on 2016/6/21.
 */
public class ExamPaperListActivity extends BaseFragmentActivity implements ExamPaperListView, ExamPaperListAdapter.ReclerViewItemClick {
    @BindView(R.id.top_title_left)
    ImageView top_title_left;
    @BindView(R.id.top_title_right)
    ImageView top_title_right;
    @BindView(R.id.top_title_text)
    TextView top_title_text;
    @BindView(R.id.swipe_container)
    RefreshLayout swipe_container;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @OnClick(R.id.top_title_left) void onBackClick(){
        onBackPressed();
    }
    private ExamPaperListPercenter examPaperListPercenter;
    private EmptyViewLayout mEmptyLayout;
    private ExamPaperListAdapter examPaperListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam_list_activity);
        ButterKnife.bind(this);
        examPaperListPercenter = new ExamPaperListPercenter();
        examPaperListPercenter.attachView(this);
        initView();
        initData();
    }

    @Override
    public void initView() {
        top_title_left.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ExamPaperListActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mEmptyLayout = new EmptyViewLayout(this, swipe_container);
        mEmptyLayout.setErrorButtonClickListener(mErrorClickListener);
        mEmptyLayout.setEmptyButtonClickListener(emptyButtonClickListener);
        swipe_container.setColorSchemeColors(getResources().getColor(R.color.color_primary));
        swipe_container.setChildView(recyclerView);
        setListener();
    }

    private void setListener() {
        swipe_container.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                examPaperListPercenter.getLoadData();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                swipe_container.setEnabled(topRowVerticalPosition >= 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                examPaperListPercenter.currentPage=1;
                examPaperListPercenter.getData();
            }
        });
    }


    /**
     * 错误监听
     */
    private View.OnClickListener mErrorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            examPaperListPercenter.initData();
            examPaperListPercenter.getDBData();
        }
    };
    /**
     * 无数据监听
     */
    private View.OnClickListener emptyButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //showAppMsg("显示kong");
        }
    };

    @Override
    public void initData() {
        mEmptyLayout.showLoading();
        examPaperListPercenter.initData();
//        examPaperListPercenter.getDBData();
    }

    public void showLoading() {
       if(swipe_container != null){
           swipe_container.setRefreshing(true);
        }
    }

    public void hideLoading() {
        if(swipe_container != null){
            swipe_container.setRefreshing(false);
            swipe_container.setLoading(false);
        }
    }

    @Override
    public void showRetry() {
        
    }

    @Override
    public void hideRetry() {

    }

    public void showError(String message) {
        Toast.makeText(ExamPaperListActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public Context context() {
        return ExamPaperListActivity.this;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void initAdapter() {
        if (examPaperListAdapter==null){
            examPaperListAdapter = new ExamPaperListAdapter(this,examPaperListPercenter.KnowledgeList);
            examPaperListAdapter.setOnItemClick(this);
            recyclerView.setAdapter(examPaperListAdapter);
        }else{
            examPaperListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showContentView(int type) {
        if (type== Constant.VIEW_TYPE_0){
            mEmptyLayout.showContentView();
        }else  if (type==Constant.VIEW_TYPE_1){
            mEmptyLayout.showNetErrorView();
        }else  if (type==Constant.VIEW_TYPE_2){
            mEmptyLayout.showEmpty();
        }else{
            mEmptyLayout.showError();
        }
        hideLoading();
    }

    @Override
    public boolean isRefreshNow() {
        if (swipe_container==null){
            return false;
        }
        return swipe_container.isRefreshing();
    }

    @Override
    public RefreshLayout getRefreshLayout() {
        return swipe_container;
    }

    @Override
    public Intent getTheIntent() {
        return getIntent();
    }

    @Override
    public void showTopTextTitle(String title) {
        top_title_text.setVisibility(View.VISIBLE);
        top_title_text.setText(title);
    }

    @Override
    public void setNoDataMoreShow(boolean isShow) {
        if(examPaperListAdapter!=null){
            examPaperListAdapter.setNoDataShow(true);
            examPaperListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClickListenerPosition(int position) {
        examPaperListPercenter.setOnItemClick(position);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        examPaperListPercenter.currentPage=1;
        examPaperListPercenter.getDBData();
    }
}
