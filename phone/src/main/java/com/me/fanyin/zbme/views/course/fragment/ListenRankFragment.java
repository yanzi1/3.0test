package com.me.fanyin.zbme.views.course.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.adapter.RecordAdapter;
import com.me.fanyin.zbme.views.course.play.widget.ListViewForScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 我的课程中的 课程介绍
 */
public class ListenRankFragment extends Fragment {

    @BindView(R.id.list_record)
    ListViewForScrollView listRecord;
    private View view;
    Unbinder unbinder;
    private RecordAdapter adapter;

    public ListenRankFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.listen_record_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        initData();
        return view;
    }

    private void initView() {
        adapter=new RecordAdapter(getActivity());
        listRecord.setAdapter(adapter);
    }

    private void initData() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
