package com.me.fanyin.zbme.views.exam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.me.core.utils.DensityUtils;
import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.exam.Option;
import com.me.data.model.exam.Question;
import com.me.data.model.exam.RelevantPoint;
import com.me.fanyin.zbme.views.exam.ExamPersenter;
import com.me.fanyin.zbme.views.exam.dict.ExamTypeEnum;
import com.me.fanyin.zbme.views.exam.event.Comprehensive;
import com.me.fanyin.zbme.views.exam.event.ComprehensiveUpdatePage;
import com.me.fanyin.zbme.views.exam.event.DeleteCompEvent;
import com.me.fanyin.zbme.views.exam.event.ExamIndexEvent;
import com.me.fanyin.zbme.views.exam.event.ShowAnalyzeEvent;
import com.me.fanyin.zbme.views.exam.mvp.BasePersenter;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by dell on 2016/5/4.
 */
public class QuestionFragmentPersenter extends BasePersenter<QuestionFragmentView> {
	public Map<Integer, Integer> heightList = new HashMap<>();
	public int index;
	public Question question;
	private int buttonHeight;
	private int exam_tag;
	private Intent scardIntent;
	private int childPossition;//题冒题的子位置
	private boolean isShowWebView = false;
	public String[] optionJudge = {"对", "错"};
	public int[] optionAnswer = {1, 2};
	public String[] optionChoice = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
			"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	private int lvHeight = 0;
	public static ArrayList<Question> questions = new ArrayList<>();
	private StringBuffer sb;
	private List<Question> comprehensiveList;
	public static int currentIndex = 0;
	private DisplayMetrics dm;
	private int screenWidth;
	private int screenHeight;
	private int compreHeight;
	private RelativeLayout.LayoutParams lp;
	private int defaultHeight;
	private RelativeLayout.LayoutParams PP;


	@Override
	public void getData() {
		buttonHeight = DensityUtils.dip2px(getMvpView().context(), 20);
		index = getMvpView().getArgumentData().getInt(Constants.ARG_POSITION);
		question = ExamPersenter.questionlist.get(index);
		initData();
	}

	private void initData() {//初始化参数
		if (question.getPointList() == null || question.getPointList().size() == 0) {
			question.setPointList(new ArrayList<RelevantPoint>());
		}
		if (question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_PANDUAN.getId()) {
			if (question.getOptionList().size() <= 1) {
				List<Option> opList = new ArrayList<>();
				for (int i = 0; i < 2; i++) {
					Option option = new Option();
					option.setOptionContent(optionJudge[i]);
					option.setName(optionAnswer[i] + "");
					opList.add(option);
				}
				question.setOptionList(opList);
			}
		}
		//判断当前的题是哪一种类型
		exam_tag = SharedPrefHelper.getInstance().getExamTag();
		judgeHasSolutions();
		if (exam_tag == Constants.EXAM_TAG_REPORT) {
			scardIntent = getMvpView().getTheIntent();
			if (question.getQuestionId().equals(scardIntent.getStringExtra("compreQuestionId"))) {
				childPossition = scardIntent.getIntExtra("childPosition", 0);
			}
		} else if (exam_tag == Constants.EXAM_TAG_ABILITY) {
			if (null != ExamPersenter.answerLog && !ExamPersenter.answerLog.isFinished()) {
				childPossition = ExamPersenter.answerLog.getChildIndex();
			}
		} else if (exam_tag == Constants.EXAM_TAG_COLLECTION || exam_tag == Constants.EXAM_TAG_FALT) {

		} else {
			scardIntent = getMvpView().getTheIntent();
			int state = scardIntent.getIntExtra("EXAM_KNOWLEDGE", 0);
			if (state != Constants.EXAM_KNOWLEDGE_RESTART && null != ExamPersenter.answerLog) {
				childPossition = ExamPersenter.answerLog.getChildIndex();
			}
		}
		if (exam_tag == Constants.EXAM_TAG_COLLECTION || exam_tag == Constants.EXAM_TAG_FALT) {
//TODO           
		} else {
			if (index == (ExamPersenter.questionlist.size() - 1)) {
//TODO               
			}
		}
		if (question.getOptionList() != null && question.getOptionList().size() != 0) {
			for (int i = 0; i < question.getOptionList().size(); i++) {
				if (question.getOptionList().get(i).getShowWebView() != null && question.getOptionList().get(i).getShowWebView().equals("1")) {
					isShowWebView = true;
				}
			}
		}
		getMvpView().setNSListviewIsWebview(isShowWebView);
		//判断当前是否是判断题
		if (question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_PANDUAN.getId()) {
			getMvpView().setRealAnswer(true, true, optionJudge[Integer.valueOf(question.getRealAnswer().toString().trim()) - 1] + "");
			if (question.getUserAnswer() != null && !"".equals(question.getUserAnswer())) {
				getMvpView().setLocalAnswer(optionJudge[Integer.valueOf(question.getUserAnswer().toString().trim()) - 1] + "");
			} else {
				getMvpView().setLocalAnswer("");
			}
		} else {
			//判断是否是单选多选不定项或判断
			boolean isNormalChoiceType = (question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_PANDUAN.getId() || question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_DANXUAN.getId()
					|| question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_DUOXUAN.getId() || question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_BUDINGXIANG.getId());
			boolean isShowHtmlTextview = !(question.getRealAnswer().contains("</td>") || question.getRealAnswer().contains("<img"));
			getMvpView().setRealAnswer(isNormalChoiceType, isShowHtmlTextview, question.getRealAnswer());
			if (question.getUserAnswer() != null) {
				getMvpView().setLocalAnswer(question.getUserAnswer() + "");
			} else {
				getMvpView().setLocalAnswer("");
			}
		}
		boolean hasAnalyze = !(question.getQuizAnalyze() == null || "".equals(question.getQuizAnalyze()));
		getMvpView().showAnalyze(hasAnalyze);
		if (hasAnalyze) {//如果有试题详解再显示
			boolean analyzeIsWebview = question.getQuizAnalyze().contains("</td>") || question.getQuizAnalyze().contains("<img");
			getMvpView().setAnalyzeIsWebview(analyzeIsWebview, question.getQuizAnalyze());
		}
		boolean showRelevant = !(question.getPointList() == null || question.getPointList().size() == 0);
		getMvpView().showRlevantPoint(showRelevant);
		judgeTypeForQuestion();
	}

	private void judgeHasSolutions() {
		if (question.getSolutions() == null || question.getSolutions().isEmpty()) {
			getMvpView().showSolutions(false, "");
		} else {
			getMvpView().showSolutions(true, question.getSolutions());
		}
	}

	private void judgeTypeForQuestion() {
		boolean questionTypeIsNormal = (question.getQuestionList() == null || question.getQuestionList().size() == 0);
		getMvpView().showNormalQuestionOrNot(questionTypeIsNormal);
		boolean titleIsWebview = (question.getTitle().contains("</td>") || question.getTitle().contains("<img"));
		if (questionTypeIsNormal) {//非题冒题
			hideAnalyze();
			getMvpView().setQuestionTitleName(titleIsWebview, question.getTitle());
			getMvpView().setQuestionTypeName(titleIsWebview, ExamTypeEnum.getValue(question.getChoiceType()));
		} else {//题冒题
			comprehensiveList = question.getQuestionList();
			getMvpView().setCompreQuestionTitleName(titleIsWebview, question.getTitle());
			getMvpView().setCompreQuestionTypeName(titleIsWebview, ExamTypeEnum.getValue(question.getQuestionList().get(0).getChoiceType()));
			getMvpView().initChildFragmentAdapter();
			if (childPossition != 0) {
				getMvpView().setChildVPPosition(childPossition);
			}
			initCompreHeight();
		}
	}

	private void initCompreHeight() {
		dm = getMvpView().context().getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
//        if (screenHeight<1280){
		compreHeight = screenHeight / 2;
//        }
		if (question.getCompreHeight() > 0) {
			compreHeight = question.getCompreHeight();
		}
		lp = new RelativeLayout.LayoutParams(
				screenWidth, RelativeLayout.LayoutParams.FILL_PARENT);
		lp.topMargin = compreHeight;
		getMvpView().setCustomRelativeLayoutHeight(lp);
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				defaultHeight = compreHeight + buttonHeight;
				PP = new RelativeLayout.LayoutParams(
						screenWidth, defaultHeight);
				getMvpView().setCompreHeight(PP);
			}
		});
	}

	public void hideAnalyze() {
		if (exam_tag == Constants.EXAM_TAG_REPORT) {
			getMvpView().setOptionListViewIsEnable(false);
			getMvpView().setShowAllAnalyze(true);
			getMvpView().setNotNormalTipShow(false);

		} else if (exam_tag == Constants.EXAM_TAG_COLLECTION || exam_tag == Constants.EXAM_TAG_FALT) {
			getMvpView().setOptionListViewIsEnable(true);
			if (questions.contains(question)) {
				getMvpView().setShowAllAnalyze(true);
				getMvpView().setNotNormalTipShow(false);
			} else {
				getMvpView().setShowAllAnalyze(false);
				//判断当前的题是否是综合题还是普通题
				if (question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_PANDUAN.getId() || question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_DANXUAN.getId()
						|| question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_DUOXUAN.getId() || question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_BUDINGXIANG.getId()) {
					getMvpView().setNotNormalTipShow(false);
				} else {
					getMvpView().setNotNormalTipShow(true);
				}
			}
		} else if (exam_tag == Constants.EXAM_ORIGINAL_QUESTION) {//获取原题
			getMvpView().setOptionListViewIsEnable(false);
			getMvpView().setShowAllAnalyze(true);
			getMvpView().setNotNormalTipShow(false);
			getMvpView().setRelavantAnswers(false);
		} else {
			getMvpView().setOptionListViewIsEnable(true);
		}
	}

	@Override
	public void setData(String obj) {

	}

	@Override
	public void attachView(QuestionFragmentView mvpView) {
		super.attachView(mvpView);
	}

	public void getHeightChange(int height, int position) {
		if (heightList.size() <= 4) {
			if (heightList.size() == 0 && question.getLvHeight() != 0) {
				return;
			}
			if (heightList.get(position) != null && height == heightList.get(position)) {
				return;
			}
			heightList.put(position, height);
			if (heightList.size() == 4) {
				setListViewHeightBasedOnChildren(getMvpView().getOptionListView());
			}
		}
	}

	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		int h = 0;
		for (Integer i : heightList.keySet()) {
			h += heightList.get(i);
		}
		if (h > totalHeight) {
			totalHeight = h;
		}
		totalHeight = totalHeight + 140;
		lvHeight = totalHeight;
		if (question.getLvHeight() != 0) {
			if (lvHeight == question.getLvHeight()) {
			} else if (lvHeight < question.getLvHeight()) {
				lvHeight = question.getLvHeight();
				ViewGroup.LayoutParams params = listView.getLayoutParams();
				params.height = question.getLvHeight() + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + 50;
				listView.setLayoutParams(params);
			} else {
				question.setLvHeight(lvHeight);
				ViewGroup.LayoutParams params = listView.getLayoutParams();
				params.height = question.getLvHeight() + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + 50;
				listView.setLayoutParams(params);
			}
		} else {
			if (heightList.size() != 4) {
				return;
			}
			question.setLvHeight(totalHeight);
			ViewGroup.LayoutParams params = listView.getLayoutParams();
			params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + 50;

			listView.setLayoutParams(params);
		}
	}

	public void setListViewItemClick(int position) {
		if (question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_DANXUAN.getId()) {//单选
			long[] ids = getMvpView().getOptionListView().getCheckedItemIds();
			sb = new StringBuffer();
			for (int i = 0; i < ids.length; i++) {
				long id1 = ids[i];
				sb.append(optionChoice[(int) id1]).append("");
			}
			question.setUserAnswer(sb.toString() + "");
			getMvpView().refreshOptionAdapter();
			EventBus.getDefault().post(new ExamIndexEvent(position, 2));
		} else if (question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_DUOXUAN.getId() || question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_BUDINGXIANG.getId()) {//多选
			sb = new StringBuffer();
			long[] ids = getMvpView().getOptionListView().getCheckedItemIds();
			for (int i = 0; i < ids.length; i++) {
				long id1 = ids[i];
				sb.append(optionChoice[(int) id1]).append(",");
			}
			if (sb.toString().equals("") || sb.toString().length() == 1 || sb.toString().length() == 0) {
				question.setUserAnswer("");
			} else {
				question.setUserAnswer(sb.toString().substring(0, sb.toString().length() - 1) + "");
			}
			getMvpView().refreshOptionAdapter();
		} else if (question.getChoiceType() == ExamTypeEnum.EXAM_TYPE_PANDUAN.getId()) {//判断
			sb = new StringBuffer();
			long[] ids = getMvpView().getOptionListView().getCheckedItemIds();
			for (int i = 0; i < ids.length; i++) {
				long id1 = ids[i];
				sb.append(optionAnswer[(int) id1]).append("");
			}
			question.setUserAnswer(sb.toString() + "");
			getMvpView().refreshOptionAdapter();
			EventBus.getDefault().post(new ExamIndexEvent(position, 2));
		}
	}

	public void setChildPageChangeListener(int childPossition) {
		currentIndex = childPossition;
		Bundle bundle = new Bundle();
		ComprehensiveUpdatePage comprehensiveUpdatePage = new ComprehensiveUpdatePage(question.getQuestionList().get(childPossition), childPossition, question.getQuestionId());
		bundle.putSerializable("ComprehensiveUpdatePage", (Serializable) comprehensiveUpdatePage);
		sendBroadcast(bundle);
	}

	public void sendBroadcast(Bundle bundle) {
		Intent intent = new Intent("com.dongao.positon.change");
		intent.putExtras(bundle);
		getMvpView().context().sendBroadcast(intent); // 发送一个广播, 广播的动作为: com.fengxunwangluo.help
	}

	public void setCustomRelativeLayoutClick(int left, int top, int right, int bottom) {
		PP = new RelativeLayout.LayoutParams(
				screenWidth, (top + buttonHeight));
		getMvpView().setCompreHeight(PP);
		lp = new RelativeLayout.LayoutParams(
				screenWidth, RelativeLayout.LayoutParams.FILL_PARENT);
		lp.topMargin = top;
		getMvpView().setCustomRelativeLayoutHeight(lp);
		question.setCompreHeight(top);
	}

	public void onEventMainThreadShowAnalyzeEvent(ShowAnalyzeEvent event) {
		hideAnalyze();
	}

	public void onEventMainThreadDeleteCompEvent(DeleteCompEvent event) {
		if (question.getQuestionList() != null && question.getQuestionList().size() != 0) {
			getMvpView().setChildVPData();
		}
	}

	public void onEventMainThreadComprehensive(Comprehensive event) {
		if (question.getQuestionId().equals(event.questionId)) {
			getMvpView().setChildVPPosition(event.index);
		}
	}

	public void onClickRelevantQuestion() {
//		Intent intent1 = new Intent(getMvpView().context(), ExamRecommQuestionNewActivity.class);
//		intent1.putExtra("examinationQuestionId", question.getQuestionId());
//		Bundle intent = new Bundle();
//		String examinationId = getMvpView().getTheIntent().getStringExtra("examinationId");
//		if (examinationId == null || examinationId.isEmpty()) {
//			examinationId = SharedPrefHelper.getInstance().getExaminationId();
//		}
//		intent.putString("examinationId", examinationId);
//		intent.putString("paperName", SharedPrefHelper.getInstance().getExaminationTitle());
//		intent.putString("largeSegmentName", ExamTypeEnum.getValue(question.getChoiceType()));
//		intent.putString("subsectionName", CommenUtils.getPositionAtAll(question, ExamPersenter.questionlist) + "");
//		intent1.putExtras(intent);
//		getMvpView().context().startActivity(intent1);
	}
}
