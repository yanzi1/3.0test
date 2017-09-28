package com.me.fanyin.zbme.views.course.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.me.data.model.course.CourseStatiscalBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseFragment;
import com.me.fanyin.zbme.views.course.adapter.CourseStatiscalAdapter;
import com.me.fanyin.zbme.widget.BingImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticalFragment extends BaseFragment implements CourseStatiscalAdapter.OnItemClickListener {
    public static final int TYPE_LECTURES=1;
    public static final int TYPE_QUESTION=2;
    public static final int WEEK=0;
    public static final int MONTH=1;

    @BindView(R.id.statistical_layout)
    View statistical_layout;
    @BindView(R.id.empty_rl)
    View empty_rl;
    @BindView(R.id.img_empty)
    ImageView imgEmpty;
    @BindView(R.id.app_message_tv)
    TextView appMessageTv;

    @BindView(R.id.type_empty_layout)
    View type_empty_layout;
    @BindView(R.id.type_data_layout)
    View type_data_layout;

    @BindView(R.id.lectures_or_question_rg)
    RadioGroup lectures_or_question_rg;
    @BindView(R.id.lectures_time_rb)
    RadioButton lectures_time_rb;
    @BindView(R.id.question_count_rb)
    RadioButton question_count_rb;
    @BindView(R.id.description_tv)
    TextView description_tv;
    @BindView(R.id.total_tv)
    TextView total_tv;
    @BindView(R.id.unit_tv)
    TextView unit_tv;
    @BindView(R.id.switch_layout)
    View switch_layout;
    @BindView(R.id.switch_tv)
    TextView switch_tv;
    @BindView(R.id.statistical_view)
    BingImage statistical_view;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private int type=TYPE_LECTURES;//1：听课 2：做题
    private int weekOrMonth=WEEK;//0：week 1：month
    private int[] noDataColors;
    private int[] haveDataColors;
    private List<CourseStatiscalBean> statiscalBeanList;
    private CourseStatiscalBean courseStatiscalBean;
    private CourseStatiscalBean.DateBean dateBean;
    private List<CourseStatiscalAdapter.CourseStatiscalAdapterBean> list;
    private CourseStatiscalAdapter adapter;


    public static StatisticalFragment newInstance(Bundle bundle){
        StatisticalFragment statisticalFragment=new StatisticalFragment();
        statisticalFragment.setArguments(bundle);
        return statisticalFragment;
    }

    @Override
    public void initView() {
        Bundle b = getArguments();
        statiscalBeanList= (ArrayList<CourseStatiscalBean>) b.getSerializable("data");
        if (b.getBoolean("isDataError",false))
            showDataError();
        else{
            if (b.getBoolean("isNetError",false))
                showNetError();
        }

        haveDataColors = getResources().getIntArray(R.array.bing_have_data_colors);
        noDataColors = getResources().getIntArray(R.array.bing_no_data_colors);

        lectures_or_question_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.lectures_time_rb:
                        question_count_rb.setBackgroundResource(R.drawable.right_circle_white_selector);
                        lectures_time_rb.setBackgroundResource(R.drawable.left_circle_primary_bg);
                        question_count_rb.setTextColor(ContextCompat.getColor(getActivity(),R.color.color_primary));
                        lectures_time_rb.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
                        type=TYPE_LECTURES;
                        break;
                    case R.id.question_count_rb:
                        question_count_rb.setBackgroundResource(R.drawable.right_circle_primary_bg);
                        lectures_time_rb.setBackgroundResource(R.drawable.left_circle_white_selector);
                        question_count_rb.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
                        lectures_time_rb.setTextColor(ContextCompat.getColor(getActivity(),R.color.color_primary));
                        type=TYPE_QUESTION;
                        break;
                }
                initData();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);

        list=new ArrayList<>();
        adapter = new CourseStatiscalAdapter(getActivity(),list,this);
        recyclerView.setAdapter(adapter);

        lectures_time_rb.setChecked(true);

    }

    @Override
    public void initData() {
        if (statiscalBeanList != null && statiscalBeanList.size()>0){

            String switchText = "";
            String totalDescriptionText="";
            String unitText="";
            if (type == TYPE_LECTURES){
                getCourseStatiscalBean();
                if (weekOrMonth == WEEK){
                    dateBean=courseStatiscalBean.getWeek();
                    switchText="切换月时长";
                }else{
                    dateBean=courseStatiscalBean.getMonth();
                    switchText="切换周时长";
                }
                totalDescriptionText="总时长:";
                unitText="Min";
            }else if (type == TYPE_QUESTION){
                getCourseStatiscalBean();
                if (weekOrMonth == WEEK){
                    dateBean=courseStatiscalBean.getWeek();
                    switchText="切换月题量";
                }else{
                    dateBean=courseStatiscalBean.getMonth();
                    switchText="切换周题量";
                }
                unitText="题";
                totalDescriptionText="总题量:";
            }
            switch_tv.setText(switchText);
            description_tv.setText(totalDescriptionText);
            unit_tv.setText(unitText);
            total_tv.setText("");
            if (dateBean==null || dateBean.getList()==null || dateBean.getList().size()<=0){
                type_data_layout.setVisibility(View.GONE);
                type_empty_layout.setVisibility(View.VISIBLE);
                return;
            }else{
                type_data_layout.setVisibility(View.VISIBLE);
                type_empty_layout.setVisibility(View.GONE);
            }

            total_tv.setText(dateBean.getTotal());
            setBingData();
            setList();
            statistical_layout.setVisibility(View.VISIBLE);
            empty_rl.setVisibility(View.GONE);
        }else{
            showDataEmpty();
        }
    }

    private void setList() {
        list.clear();
        List<CourseStatiscalBean.DateBean.StatiscalItemBean> itemBeanList = dateBean.getList();
        int hasDataIndex=0;
        int noDataIndex=0;
        for (int i = 0; i < itemBeanList.size(); i++) {
            CourseStatiscalBean.DateBean.StatiscalItemBean statiscalItemBean = itemBeanList.get(i);
            list.add(new CourseStatiscalAdapter.CourseStatiscalAdapterBean(
                    statiscalItemBean.getName()
                    ,statiscalItemBean.getData()
                    ,i<5 ? statiscalItemBean.getPrecent() > 0 ? haveDataColors[i] : noDataColors[i]
                    :statiscalItemBean.getPrecent()>0?haveDataColors[haveDataColors.length-1]
                            :noDataColors[noDataColors.length-1]));
        }
        adapter.setType(type);
        adapter.notifyDataSetChanged();
    }

    private void setBingData() {
        int hasDataIndex=0;
        int noDataIndex=0;
        float totalPrecent=0;
        List<BingImage.BingBean> bingBeanList=new ArrayList<>();
        List<CourseStatiscalBean.DateBean.StatiscalItemBean> itemBeanList = dateBean.getList();
        BingImage.BingBean other=null;

        int noCount=0;
        for (int i = 0; i < itemBeanList.size(); i++) {
            CourseStatiscalBean.DateBean.StatiscalItemBean item = itemBeanList.get(i);
            if (i<5){
                if (item.getPrecent()>0){
                    BingImage.BingBean bingBean=new BingImage.BingBean(
                            haveDataColors[i]
                            ,item.getPrecent()<0.12f?0.12f:item.getPrecent()
                            ,""
                            ,item.getName());
                    totalPrecent+=bingBean.getPercent();
                    bingBeanList.add(bingBean);
                }

            }else{
                if (itemBeanList.size()==6){
                    if (item.getPrecent()>0){
                        BingImage.BingBean bingBean=new BingImage.BingBean(
                                haveDataColors[haveDataColors.length-1]
                                ,item.getPrecent()<0.12f?0.12f:item.getPrecent()
                                ,""
                                ,item.getName());
                        totalPrecent+=bingBean.getPercent();
                        bingBeanList.add(bingBean);
                    }
                }else{
                    if (item.getPrecent()>0){
                        if (other==null){
                            other=new BingImage.BingBean();
                            other.setDescription("其他");
                            other.setName("");
                            other.setColor(haveDataColors[haveDataColors.length-1]);
                        }
                        other.setPercent(other.getPercent()+item.getPrecent());
                    }
                }
            }
            if (item.getPrecent()<=0){
                noCount++;
            }
        }

        if (noCount==itemBeanList.size()){
            for (int i = 0; i < itemBeanList.size(); i++) {
                CourseStatiscalBean.DateBean.StatiscalItemBean item = itemBeanList.get(i);
                if (i<5){
                    BingImage.BingBean bingBean=new BingImage.BingBean(
                            noDataColors[i]
                            ,item.getPrecent()<0.12f?0.12f:item.getPrecent()
                            ,""
                            ,item.getName());
                    totalPrecent+=bingBean.getPercent();
                    bingBeanList.add(bingBean);
                }else{
                    if (itemBeanList.size()==6){
                        BingImage.BingBean bingBean=new BingImage.BingBean(
                                noDataColors[noDataColors.length-1]
                                ,item.getPrecent()<0.12f?0.12f:item.getPrecent()
                                ,""
                                ,item.getName());
                        totalPrecent+=bingBean.getPercent();
                        bingBeanList.add(bingBean);
                    }else{
                        if (other==null){
                            other=new BingImage.BingBean();
                            other.setDescription("其他");
                            other.setName("");
                            other.setColor(noDataColors[noDataColors.length-1]);
                        }
                        other.setPercent(other.getPercent()+item.getPrecent());

                    }
                }
            }
        }

        if (other!=null && other.getPercent() > 0 ){
            other.setPercent(other.getPercent()<0.12f?0.12f:other.getPercent());
            totalPrecent+=other.getPercent();
            bingBeanList.add(other);
        }

        float part=1f/totalPrecent;
        for (int i = 0; i < bingBeanList.size() ; i++) {
            BingImage.BingBean bingBean = bingBeanList.get(i);
            bingBean.setPercent(bingBean.getPercent()*part);
        }
        statistical_view.setData(bingBeanList);
    }

    private void getCourseStatiscalBean() {
        for (int i = 0; i < statiscalBeanList.size(); i++) {
            CourseStatiscalBean courseStatiscalBean = statiscalBeanList.get(i);
            if (courseStatiscalBean.getType() == type ){
                this.courseStatiscalBean=courseStatiscalBean;
                break;
            }
        }
    }

    public void setData(List<CourseStatiscalBean> data){
        statiscalBeanList=data;
        initData();
    }


    public void showDataError() {
        statistical_layout.setVisibility(View.GONE);
        empty_rl.setVisibility(View.VISIBLE);

        imgEmpty.setImageResource(R.mipmap.img_special_dataerror);
        appMessageTv.setText(getResources().getString(R.string.app_error_message));
    }

    public void showDataEmpty() {
        statistical_layout.setVisibility(View.GONE);
        empty_rl.setVisibility(View.VISIBLE);

        imgEmpty.setImageResource(R.mipmap.img_special_nothing);
        appMessageTv.setText(getResources().getString(R.string.app_empty_message));
    }

    public void showNetError() {
        statistical_layout.setVisibility(View.GONE);
        empty_rl.setVisibility(View.VISIBLE);

        imgEmpty.setImageResource(R.mipmap.img_special_nonetwork);
        appMessageTv.setText(getResources().getString(R.string.app_nonetwork_message));
    }

    @Override
    public void itemClick(View view, int position) {
    }

    @OnClick({R.id.switch_layout})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.switch_layout:
                weekOrMonth = weekOrMonth == WEEK ? MONTH : WEEK;
                initData();
                break;
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_statistical;
    }

    private class MeasureHeightLayoutManager extends LinearLayoutManager{

        MeasureHeightLayoutManager(Context context) {
            super(context);
        }

        @Override
        public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
            if (state.getItemCount() >0 ){
                View view = recycler.getViewForPosition(0);
                int measuredWidth =0;
                int measuredHeight=0;
                int height=0;
                if(view != null){
                    measureChild(view, widthSpec, heightSpec);
                    measuredWidth = View.MeasureSpec.getSize(widthSpec);
                    measuredHeight = view.getMeasuredHeight();
                }

                for (int i = 0; i < getItemCount(); i++) {
                    height+=measuredHeight;
                }
                setMeasuredDimension(measuredWidth, height);
            }else{
                super.onMeasure(recycler,state,widthSpec,heightSpec);
            }

        }
    }
}
