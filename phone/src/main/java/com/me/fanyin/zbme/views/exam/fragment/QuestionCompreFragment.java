package com.me.fanyin.zbme.views.exam.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.me.data.common.Constants;
import com.me.data.model.exam.Question;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.exam.activity.base.BaseFragment;
import com.me.fanyin.zbme.views.exam.adapter.OptionAdapter;
import com.me.fanyin.zbme.views.exam.adapter.RelevantPointAdapter;
import com.me.fanyin.zbme.views.exam.dict.ExamTypeEnum;
import com.me.fanyin.zbme.views.exam.event.ShowComprehensiveAnalyzeEvent;
import com.me.fanyin.zbme.views.exam.view.NoScrollListview;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wyc on 2016/5/5.
 */
public class QuestionCompreFragment extends BaseFragment implements QuestionCompreFragmentView{
    @BindView(R.id.sv_examination)
    ScrollView sv_examination;
    @BindView(R.id.lv_options)
    NoScrollListview lv_options;
    @BindView(R.id.exam_pager_quiz_analyze_layout)
    LinearLayout exam_pager_quiz_analyze_layout;
    @BindView(R.id.exam_question_local_answer)
    TextView exam_question_local_answer;
    @BindView(R.id.exam_question_real_answer)
    TextView exam_question_real_answer;
    @BindView(R.id.exam_question_quiz_analyze)
    HtmlTextView exam_question_quiz_analyze;
    @BindView(R.id.lv_exam_question_relate_knowledge)
    NoScrollListview lv_exam_question_relate_knowledge;
    @BindView(R.id.ll_relevant_point)
    LinearLayout ll_relevant_point;
    @BindView(R.id.tv_questionType)
    TextView tv_questionType;
    @BindView(R.id.wv_question_name)
    WebView wv_question_name;
    @BindView(R.id.tv_question_name)
    HtmlTextView tv_question_name;
    @BindView(R.id.ll_relevant_answer)
    LinearLayout ll_relevant_answer;
    @BindView(R.id.rl_answer_htwv)
    RelativeLayout rl_answer_htwv;
    @BindView(R.id.ll_answer_normal)
    LinearLayout ll_answer_normal;
    @BindView(R.id.tv_question_answer)
    HtmlTextView tv_question_answer;
    @BindView(R.id.wv_question_answer_wv)
    WebView wv_question_answer_wv;
    @BindView(R.id.ll_all_analysis)
    LinearLayout ll_all_analysis;
    @BindView(R.id.exam_question_my_ll)
    LinearLayout exam_question_my_ll;
    @BindView(R.id.ll_point)
    LinearLayout ll_point;
    @BindView(R.id.ll_solution_thinking)
    LinearLayout ll_solution_thinking;
    @BindView(R.id.solution_thinking_wv)
    WebView solution_thinking_wv;
    @BindView(R.id.exam_question_quiz_analyze_wv)
    WebView exam_question_quiz_analyze_wv;

    private  final String encoding = "utf-8";
    private  final String mimeType = "text/html";
    private QuestionCompreFragmentPersenter questionCompreFragmentPersenter;
    private RelevantPointAdapter relevantPointAdapter;
    private OptionAdapter adapter;
    @OnClick(R.id.ll_relevant_answer) void RelevantAnswer(){
        questionCompreFragmentPersenter.onClickRelevantQuestion();
        MobclickAgent.onEvent(getActivity(), Constants.EXAM_TO_RECOMMQUESTION);
    }

    public static QuestionCompreFragment newInstance(Question question) {
        QuestionCompreFragment f = new QuestionCompreFragment();
        Bundle b = new Bundle();
        b.putSerializable(Constants.ARG_POSITION, question);
        f.setArguments(b);
        return f;
    }
    
    
   
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        View rootView1 = inflater.inflate(R.layout.exam_test_pager_item_yuan_comprehensive,
                container, false);
        ButterKnife.bind(this, rootView1);
        questionCompreFragmentPersenter=new QuestionCompreFragmentPersenter();
        questionCompreFragmentPersenter.attachView(this);
        ViewGroup p = (ViewGroup) rootView1.getParent();
        if (p != null) {
            p.removeAllViewsInLayout();
        }
        return rootView1;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
    }

    @Override
    public void initView() {
        solution_thinking_wv.getSettings().setAppCacheEnabled(false);
        solution_thinking_wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        solution_thinking_wv.getSettings().setDefaultTextEncodingName("UTF-8");
        sv_examination.smoothScrollTo(0, 20);
        lv_options.setFocusable(false);
        lv_exam_question_relate_knowledge.setEnabled(false);
        wv_question_name.setFocusable(false);
        wv_question_name.getSettings().setAppCacheEnabled(false);
        wv_question_name.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wv_question_name.getSettings().setDefaultTextEncodingName("UTF-8");
        wv_question_answer_wv.getSettings().setAppCacheEnabled(false);
        wv_question_answer_wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        wv_question_answer_wv.getSettings().setDefaultTextEncodingName("UTF-8");
        exam_question_quiz_analyze_wv.getSettings().setAppCacheEnabled(false);
        exam_question_quiz_analyze_wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        exam_question_quiz_analyze_wv.getSettings().setDefaultTextEncodingName("UTF-8");
        
    }

    @Override
    public void initData() {
        questionCompreFragmentPersenter.getData();
        relevantPointAdapter=new RelevantPointAdapter(getActivity(),questionCompreFragmentPersenter.question.getPointList());
        lv_exam_question_relate_knowledge.setAdapter(relevantPointAdapter);
        adapter = new OptionAdapter(getActivity(), questionCompreFragmentPersenter.question.getOptionList(), lv_options, questionCompreFragmentPersenter.index, questionCompreFragmentPersenter.question,
                new OptionAdapter.heightListener() {
                    @Override
                    public void onheightChange(int height, int position) {
                        questionCompreFragmentPersenter.getHeightChange(height,position);
                    }
                });
        lv_options.setAdapter(adapter);
        if ( questionCompreFragmentPersenter.question.getChoiceType()== ExamTypeEnum.EXAM_TYPE_DANXUAN.getId()){//单选
            lv_options.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }else{ 
            lv_options.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        }
        lv_options.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                questionCompreFragmentPersenter.setListViewItemClick(position);
            }
        });
        questionCompreFragmentPersenter.setListViewHeightBasedOnChildren(lv_options);
    }
    @Override
    public void onClick(View v) {

    }

    @Override
    public Bundle getArgumentData() {
        return getArguments();
    }

    @Override
    public Intent getTheIntent() {
        return getActivity().getIntent();
    }

    @Override
    public void setNSListviewIsWebview(boolean isWebview) {
        lv_options.setIsWebView(isWebview);
    }

    @Override
    public void setLocalAnswer(String localAnswer) {
        if (localAnswer.isEmpty()){
            exam_question_my_ll.setVisibility(View.GONE);
        }else{
            exam_question_my_ll.setVisibility(View.VISIBLE);
            exam_question_local_answer.setText(localAnswer);
        }
    }

    @Override
    public void setRealAnswer(boolean choiceTypeIsNormal,boolean isTextView,String realAnswer) {
        if (choiceTypeIsNormal){
            exam_question_real_answer.setText(realAnswer);
            rl_answer_htwv.setVisibility(View.GONE);
            ll_answer_normal.setVisibility(View.VISIBLE);
            ll_point.setVisibility(View.GONE);
        }else{
            ll_point.setVisibility(View.VISIBLE);
            rl_answer_htwv.setVisibility(View.VISIBLE);
            ll_answer_normal.setVisibility(View.GONE);
            if (isTextView){
                tv_question_answer.setHtml("<font color='#808080' style='font-size:18px;'>" + (realAnswer) + "</font>"  );
                tv_question_answer.setVisibility(View.VISIBLE);
                wv_question_answer_wv.setVisibility(View.GONE);
            }else{
                wv_question_answer_wv.loadDataWithBaseURL("", "<font color='#808080' style='font-size:18px;'>" + (realAnswer) + "</font>", mimeType, encoding, "");
                wv_question_answer_wv.setVisibility(View.VISIBLE);
                tv_question_answer.setVisibility(View.GONE);
            }
        }
        
    }

    @Override
    public void setAnalyzeIsWebview(boolean isWebview,String analyze) { 
        if (isWebview){
            exam_question_quiz_analyze_wv.loadDataWithBaseURL("", "<font color='#808080' style='font-size:18px;'>" + (analyze) + "</font>", mimeType, encoding, "");
            exam_question_quiz_analyze_wv.setVisibility(View.VISIBLE);
            exam_question_quiz_analyze.setVisibility(View.GONE);
        }else{
            exam_question_quiz_analyze.setHtml("<font color='#808080' style='font-size:18px;'>" + (analyze) + "</font>" );
            exam_question_quiz_analyze.setVisibility(View.VISIBLE);
            exam_question_quiz_analyze_wv.setVisibility(View.GONE);
        }
    }

    @Override
    public void showRlevantPoint(boolean show) {
        if (show){
            ll_relevant_point.setVisibility(View.VISIBLE);
        }else{
            ll_relevant_point.setVisibility(View.GONE);
        }
    }

    @Override
    public void showAnalyze(boolean showAnalyze) {
        if (showAnalyze){
            ll_all_analysis.setVisibility(View.VISIBLE);
        }else{
            ll_all_analysis.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNormalQuestionOrNot(boolean isNormal) {
        if (isNormal){
            sv_examination.setVisibility(View.VISIBLE);
        }else{
            sv_examination.setVisibility(View.GONE);
        }
    }


    @Override
    public ListView getOptionListView() {
        return lv_options;
    }

    @Override
    public void setOptionListViewIsEnable(boolean isEnable) {
        lv_options.setEnabled(isEnable);
    }

    @Override
    public void setNotNormalTipShow(boolean isShow) {
        if (isShow){
            ll_point.setVisibility(View.VISIBLE);
        }else{
            ll_point.setVisibility(View.GONE);
        }
    }

    @Override
    public void setShowAllAnalyze(boolean isShow) {
        if (isShow)
            exam_pager_quiz_analyze_layout.setVisibility(View.VISIBLE);
        else
            exam_pager_quiz_analyze_layout.setVisibility(View.GONE);
    }

    @Override
    public void setRelavantAnswers(boolean isShow) {
        if (isShow){
            ll_relevant_answer.setVisibility(View.VISIBLE);
        }else {
            ll_relevant_answer.setVisibility(View.GONE);
        }
    }

    @Override
    public void setQuestionTypeName( String name) {
        tv_questionType.setText(name);
    }

    @Override
    public void setQuestionTitleName(boolean isWebview,String name) {
        if (!isWebview){
            tv_question_name.setHtml("<font color='#808080' style='font-size:18px;'>" + (name) + "</font>"  );
            tv_question_name.setVisibility(View.VISIBLE);
            wv_question_name.setVisibility(View.GONE);
        }else{
            wv_question_name.loadDataWithBaseURL("", "<font color='#808080' style='font-size:18px;'>" + (name) + "</font>", mimeType, encoding, "");
            wv_question_name.setVisibility(View.VISIBLE);
            tv_question_name.setVisibility(View.GONE);
        }
    }

    @Override
    public void showSolutions(boolean isShow,String solutions) {
        if (!isShow){
            ll_solution_thinking.setVisibility(View.GONE);
        }else{
            solution_thinking_wv.loadDataWithBaseURL("", "<font color='#808080' style='font-size:18px;'>" + (solutions) + "</font>", mimeType, encoding, "");
            ll_solution_thinking.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void refreshOptionAdapter() {
        adapter.notifyDataSetChanged();
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
    @Subscribe
    public void onEventMainThread(ShowComprehensiveAnalyzeEvent event) {
        questionCompreFragmentPersenter.onEventMainThreadShowAnalyzeEvent(event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
