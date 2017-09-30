package com.me.fanyin.zbme.views.exam.activity.myfault;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.exam.activity.base.BaseFragment;
import com.me.fanyin.zbme.views.exam.activity.myfault.adapter.MyFaultFragmentAdapter;
import com.me.fanyin.zbme.widget.EmptyViewLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wyc on 2016/5/18.
 */
public class MyFaultFragment extends BaseFragment implements MyFaultFragmentView{
    private static final String ARG_POSITION = "position";
    private MyFaultFragmentPercenter myFaultFragmentPercenter;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipe_container;
    @BindView(R.id.myfault_fault_list)
    ListView myfault_fault_list;
    private EmptyViewLayout mEmptyLayout;
    private MyFaultFragmentAdapter mAdapter;

    public static MyFaultFragment newInstance(int index) {
        MyFaultFragment f = new MyFaultFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, index);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.exam_myfault_fragment,
                container, false);
        ButterKnife.bind(this, rootView);
        myFaultFragmentPercenter=new MyFaultFragmentPercenter();
        myFaultFragmentPercenter.attachView(this);
        ViewGroup parent = (ViewGroup)rootView.getParent();
        if(parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    @Override
    public void initView() {
        mEmptyLayout = new EmptyViewLayout(getActivity(), swipe_container);
        mEmptyLayout.setErrorButtonClickListener(mErrorClickListener);
        mEmptyLayout.setEmptyButtonClickListener(emptyButtonClickListener);
        myfault_fault_list.setFocusable(false);
        swipe_container.setColorSchemeColors(getResources().getColor(R.color.color_primary));
        //mSwipeRefreshLayout.setRefreshing(true);
        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_container.setRefreshing(true);
                myFaultFragmentPercenter.getData();
            }
        });
        setListener();
        myFaultFragmentPercenter.isPrepared=true;
        myfault_fault_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myFaultFragmentPercenter.setOnItemClick(parent,view,position,id);
            }
        });
    }

    /**
     * 错误监听
     */
    private View.OnClickListener mErrorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    /**
     * 无数据监听
     */
    private View.OnClickListener emptyButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            swipe_container.setRefreshing(true);
            myFaultFragmentPercenter.getData();
        }
    };

    private void setListener() {

    }

    @Override
    public void initData() {
        if (! myFaultFragmentPercenter.isPrepared || ! myFaultFragmentPercenter.isVisible) {
            return;
        }

        if( myFaultFragmentPercenter.mHasLoadedOnce){
            return;
        }
        mEmptyLayout.showLoading();
        myFaultFragmentPercenter.getData();
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override

    public void showError(String message) {

    }

    @Override
    public Context context() {
        return getActivity();
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void initAdapter() {
        mAdapter = new MyFaultFragmentAdapter(getActivity()); //创建adapter
        mAdapter.setList(myFaultFragmentPercenter.faultList);
        myfault_fault_list.setAdapter(mAdapter);
    }

    @Override
    public EmptyViewLayout getEmptyView() {
        return mEmptyLayout;
    }

    @Override
    public void setIsRefresh(boolean isRefresh) {
        swipe_container.setRefreshing(isRefresh);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (! myFaultFragmentPercenter.isPrepared || ! myFaultFragmentPercenter.isVisible) {
//            return;
//        }
//
//        if( myFaultFragmentPercenter.mHasLoadedOnce){
//            return;
//        }
        mEmptyLayout.showLoading();
        myFaultFragmentPercenter.getData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (myFaultFragmentPercenter!=null){
            myFaultFragmentPercenter.setUserHint(getUserVisibleHint());
        }
    }


}