package com.me.data.remote;

import com.me.data.model.BaseRes;
import com.me.data.model.WelcomeRes;
import com.me.data.model.course.CourseStatiscalDetailBean;
import com.me.data.model.course.KnowledgePointsStatiscialBean;
import com.me.data.model.exam.ExamRank;
import com.me.data.model.exam.newmodle.ContinueRecordExam;
import com.me.data.model.exam.newmodle.ErrorStatistic;
import com.me.data.model.exam.newmodle.ExamFeedbackType;
import com.me.data.model.exam.newmodle.ExamMain;
import com.me.data.model.exam.newmodle.ExamPaperReportAll;
import com.me.data.model.exam.newmodle.ExamPreviousPaper;
import com.me.data.model.exam.newmodle.KnowledgeVoVideo;
import com.me.data.model.exam.newmodle.MyWrongExamPaper;
import com.me.data.model.exam.newmodle.StartOrContinueExam;
import com.me.data.model.intel.IntelMainBean;
import com.me.data.model.intel.review.ChoiceTypesBean;
import com.me.data.model.main.BooksErrataListBean;
import com.me.data.model.main.BooksErrataMenuListBean;
import com.me.data.model.main.CaptureResultBean;
import com.me.data.model.main.MainADBean;
import com.me.data.model.main.MainFragmentBean;
import com.me.data.model.main.MainTypeBean;
import com.me.data.model.main.RecommendedGoodListBean;
import com.me.data.model.main.StudentsEvaluateInfo;
import com.me.data.model.main.SubpageListBean;
import com.me.data.model.main.TabListBean;
import com.me.data.model.mall.Category;
import com.me.data.model.mall.CouDanBean;
import com.me.data.model.mall.GoodsDetailBean;
import com.me.data.model.mall.GoodsListItemBean;
import com.me.data.model.mall.GoodsRecomondBean;
import com.me.data.model.mall.JobNumBean;
import com.me.data.model.mall.LogisticsBean;
import com.me.data.model.mall.MyOrderResponseBean;
import com.me.data.model.mall.OrderDetailResponseBean;
import com.me.data.model.mall.OrderPriceBean;
import com.me.data.model.mall.OrderSaveBean;
import com.me.data.model.mall.OrderUserAccountBean;
import com.me.data.model.mall.OrderVOBean;
import com.me.data.model.mall.PayInfoBean;
import com.me.data.model.mall.ShopCartBean;
import com.me.data.model.mine.ActivateServicesListBean;
import com.me.data.model.mine.AddressBean;
import com.me.data.model.mine.BookCardActivateListBean;
import com.me.data.model.mine.CollectAnswer;
import com.me.data.model.mine.CollectCourse;
import com.me.data.model.mine.CollectExercises;
import com.me.data.model.mine.ErrorsCountAndTypes;
import com.me.data.model.mine.ExamMenu;
import com.me.data.model.mine.InvoiceBean;
import com.me.data.model.mine.InvoiceBookAndCourseMoneyBean;
import com.me.data.model.mine.InvoiceRequestBean;
import com.me.data.model.mine.InvoiceTitleBean;
import com.me.data.model.mine.LearningRecordCourseListBean;
import com.me.data.model.mine.LearningRecordExercisesListBean;
import com.me.data.model.mine.MessageListBean;
import com.me.data.model.mine.MyAccountBean;
import com.me.data.model.mine.MyCouponResponseBean;
import com.me.data.model.mine.MyInvoiceResponseBean;
import com.me.data.model.mine.NotesCourseList;
import com.me.data.model.mine.NotesExercisesList;
import com.me.data.model.mine.OpenClassListBean;
import com.me.data.model.mine.PersonalInformationBean;
import com.me.data.model.mine.TmallOrderActivateListBean;
import com.me.data.model.mine.VersionInfo;
import com.me.data.model.play.ClassHomeBean;
import com.me.data.model.play.CourseDetail;
import com.me.data.model.play.CourseListData;
import com.me.data.model.play.NoteDetail;
import com.me.data.model.play.StudyPlanBean;
import com.me.data.model.query.QueryAddBookBean;
import com.me.data.model.query.QueryAddKnowledgeDataBean;
import com.me.data.model.query.QueryDetailItemBean;
import com.me.data.model.query.QueryJudgeDataBean;
import com.me.data.model.query.QueryMainBean;
import com.me.data.model.query.QueryRecommendBean;
import com.me.data.model.studyrecord.DayRecord;
import com.me.data.model.studyrecord.EnableDate;
import com.me.data.model.studyrecord.WeekRecordObj;
import com.me.data.model.user.AccountSecurLevelBean;
import com.me.data.model.user.GetBackPwUserInfoBean;
import com.me.data.model.user.MyAccountDetailBean;
import com.me.data.model.user.UserBean;
import com.me.data.remote.utils.ParamsUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public class ApiService {
    /**
     * 线程处理和部分业务处理
     */
    static <T> Flowable<BaseRes<T>> getData(Flowable<BaseRes<T>> flowable) {
        return flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<BaseRes<T>, Flowable<BaseRes<T>>>() {
                    @Override
                    public Flowable<BaseRes<T>> apply(@NonNull BaseRes<T> tBaseRes) throws Exception {

                        return Flowable
                                .just(tBaseRes)
                                .subscribeOn(AndroidSchedulers.mainThread());
                    }
                });
    }


    static <T> Flowable<T> getDatas(Flowable<T> flowable) {
        return flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<T, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(@NonNull T tRes) throws Exception {

                        return Flowable
                                .just(tRes)
                                .subscribeOn(AndroidSchedulers.mainThread());
                    }
                });
    }

    static Flowable<String> getString(Flowable<String> flowable) {
        return flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 登录请求
     */
    public static Flowable<BaseRes<UserBean>> login(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().login(map));
    }

    /**
     * 登出
     */
    public static Flowable<BaseRes<String>> logout() {
        return getData(ApiClient.getApiInterface().logout(ParamsUtils.logout()));
    }

    /**
     * 第三方登录
     */
    public static Flowable<BaseRes<UserBean>> thirdPartyLogin(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().thirdPartyLogin(map));
    }

    /**
     * 注册请求验证码
     */
    public static Flowable<BaseRes<String>> requestVerificationCode(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().requestVerificationCode(map));
    }

    /**
     * 校验验证码
     */
    public static Flowable<BaseRes<String>> verifyTheCode(HashMap<String, String> map) {
        return getData(ApiClient.getApiInterface().verifyTheCode(map));
    }

    /**
     * 注册
     */
    public static Flowable<BaseRes<UserBean>> register(HashMap<String, String> map) {
        return getData(ApiClient.getApiInterface().register(map));
    }

    /**
     * 根据email获取对应用户的名称
     */
    public static Flowable<BaseRes<GetBackPwUserInfoBean>> reqeuestAccountInfoByEmail(HashMap<String, String> map) {
        return getData(ApiClient.getApiInterface().reqeuestAccountInfoByEmail(map));
    }

    /**
     * 根据email获取对应用户的名称
     */
    public static Flowable<BaseRes<GetBackPwUserInfoBean>> reqeuestAccountInfoByPhone(HashMap<String, String> map) {
        return getData(ApiClient.getApiInterface().reqeuestAccountInfoByPhone(map));
    }

    /**
     * 找回密码
     */
    public static Flowable<BaseRes<String>> getBackPw(HashMap<String, String> map) {
        return getData(ApiClient.getApiInterface().getBackPw(map));
    }

    /*********************************** 做题模块 ↓↓ ***********************/
    /**
     * 开始做题或重新做题
     * @param map
     * @return
     */
    public static Flowable<BaseRes<StartOrContinueExam>> getExamPaper(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getExamPaper(map));
    }
    /**
     * 从做题记录继续做题
     * @param map
     * @return
     */
    public static Flowable<BaseRes<ContinueRecordExam>> getExamPaperContinueFromRecord(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getExamPaperFromRecord(map));
    }
    /**
     * 智能系统做题、做真题
     * @param map
     * @return
     */
    public static Flowable<BaseRes<ContinueRecordExam>> intelExamStart(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().intelExamStart(map));
    }
    /**
     * 我的错题列表
     * @param map
     * @return
     */
    public static Flowable<BaseRes<MyWrongExamPaper>> getMyWrongExamPaper(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getMyWrongExamPaper(map));
    }

    /**
     * 智能系统做复习包内的习题
     * @param map
     * @return
     */
    public static Flowable<BaseRes<MyWrongExamPaper>> intelReviewStart(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().intelReviewExam(map));
    }

    /**
     * 智能系统变更复习包中的习题状态
     * @param map
     * @return
     */
    public static Flowable<BaseRes<String>> intelReviewChangeStauts(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().intelReviewExamChangeStatus(map));
    }

    /**
     * 添加收藏
     * @param map
     * @return
     */
    public static Flowable<BaseRes<String>> deleteWrongQues(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().deleteWrongQues(map));
    }
    /**
     * 获取知识点视频播放的各种id
     * @param map
     * @return
     */
    public static Flowable<BaseRes<KnowledgeVoVideo>> getKnowledgeVideo(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getExamKnowledgeVideo(map));
    }
    /**
     * 该题是否有笔记
     * @param map
     * @return
     */
    public static Flowable<BaseRes<NoteDetail>> getQuesIsHaveNotes(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getQuesIsHaveNotes(map));
    }
    /**
     * 推荐做题
     * @param map
     * @return
     */
    public static Flowable<BaseRes<StartOrContinueExam>> getRecommExamPaper(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getRecommExamPaper(map));
    }
    /**
     * 查看全部解析
     * @param map
     * @return
     */
    public static Flowable<BaseRes<StartOrContinueExam>> getExamPaperAnys(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getExamPaperAnys(map));
    }
    /**
     * 试题详情
     * @param map
     * @return
     */
    public static Flowable<BaseRes<StartOrContinueExam>> getExamPaperDetail(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getExamPaperDetail(map));
    }
    /**
     * 答题报告
     * @param map
     * @return
     */
    public static Flowable<BaseRes<ExamPaperReportAll>> getExamPaperReport(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getExamPaperReort(map));
    }
    /**
     * 智能系统答题报告
     * @param map
     * @return
     */
    public static Flowable<BaseRes<ExamPaperReportAll>> getExamPaperReportIntel(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getExamPaperReortIntel(map));
    }
    /**
     * 添加收藏
     * @param map
     * @return
     */
    public static Flowable<BaseRes<String>> examPaperAddFavor(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().examPaperAddFvor(map));
    }
    /**
     * 删除收藏
     * @param map
     * @return
     */
    public static Flowable<BaseRes<String>> examPaperDeleteFavor(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().examPaperDeleteFvor(map));
    }
    /**
     * 题库首页
     * @param map
     * @return
     */
    public static Flowable<BaseRes<ExamMain>> getExamMain(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getExamMain(map));
    }
    /**
     * 历年真题列表
     * @param map
     * @return
     */
    public static Flowable<BaseRes<ExamPreviousPaper>> getExamPreviousPaper(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getExamPreviousPaperList(map));
    }
    /**
     * 试题反馈试题类型
     * @return
     */
    public static Flowable<BaseRes<List<ExamFeedbackType>>> getExamFeedBackType(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getExamFeedbackType(map));
    }

    /**
     * 提交试题反馈
     * @param map
     * @return
     */
    public static Flowable<BaseRes<String>> postExamfeedback(@FieldMap Map<String, String> map){
        return getData(ApiClient.getApiInterface().postExamFeedback(map));
    }

    /**
     * 提交试卷
     * @param map
     * @return
     */
    public static Flowable<BaseRes<String>> postExamPaper(@FieldMap Map<String, String> map){
        return getData(ApiClient.getApiInterface().postExamPaper(map));
    }

    /**
     * 智能系统提交试卷
     * @param map
     * @return
     */
    public static Flowable<BaseRes<String>> postExamPaperIntel(@FieldMap Map<String, String> map){
        return getData(ApiClient.getApiInterface().postExamPaperIntel(map));
    }

    /**
     * 保存试卷
     * @param map
     * @return
     */
    public static Flowable<BaseRes<String>> saveExamPaper(@FieldMap Map<String, String> map){
        return getData(ApiClient.getApiInterface().saveExamPaper(map));
    }

    /**
     * 智能系统保存试卷
     * @param map
     * @return
     */
    public static Flowable<BaseRes<String>> saveExamPaperIntel(@FieldMap Map<String, String> map){
        return getData(ApiClient.getApiInterface().saveExamPaperIntel(map));
    }

    /**
     * 提交试卷
     * @param map
     * @return
     */
    public static Flowable<BaseRes<ErrorStatistic>> mineErrorQuestionStatistic(@FieldMap Map<String, String> map){
        return getData(ApiClient.getApiInterface().mineErrorQuestionStatistic(map));
    }

    /**
     * 提交试卷
     * @param map
     * @return
     */
    public static Flowable<BaseRes<ExamRank>> getExamRank(@QueryMap Map<String, String> map){
        return getData(ApiClient.getApiInterface().getExamRank(map));
    }

    /*********************************** 做题模块 ↑↑ ***********************/

    /**
     * 获取提问中图书的列表
     *
     * @param map
     * @return
     */
    public static Flowable<BaseRes<List<QueryAddBookBean>>> getQueryBook(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getQueryBook(map));
    }
    /**
     * 获取答疑首页的fragment0数据
     *
     * @return
     */
    public static Flowable<BaseRes<QueryMainBean>> getQueryMainFragment(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getQueryMainFragment(map));
    }
    /**
     * 获取答疑首页的fragment1数据
     *
     * @return
     */
    public static Flowable<BaseRes<QueryMainBean>> getQueryMainFragment1(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getQueryMainFragment1(map));
    }
    /**
     * 获取答疑首页的fragment2数据
     *
     * @return
     */
    public static Flowable<BaseRes<QueryMainBean>> getQueryMainFragment2(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getQueryMainFragment2(map));
    }
    /**
     * 获取自动匹配词
     *
     * @return
     */
    public static Flowable<BaseRes<List<String>>> getQueryWord(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getQueryWord(map));
    }

    /**
     * 获取提问中知识点的列表
     *
     * @param map
     * @return
     */
    public static Flowable<BaseRes<QueryAddKnowledgeDataBean>> getQueryKnowledge(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getQueryKnowledge(map));
    }

    /**
     * 获取提问中知识点对应的答疑列表
     *
     * @param map
     * @return
     */
    public static Flowable<BaseRes<QueryRecommendBean>> getQueryKnowledgeQueryList(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getQueryKnowledgeQuery(map));
    }
    /**
     * 获取讲义的推荐答疑
     * @param map
     * @return
     */
    public static Flowable<BaseRes<QueryRecommendBean>> getQueryJiangyiQueryList(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getQueryJiangyiQuery(map));
    }
    /**
     * 获取智能系统的推荐答疑
     * @param map
     * @return
     */
    public static Flowable<BaseRes<QueryRecommendBean>> getQueryIntelQueryList(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getIntelQuery(map));
    }
    /**
     * 获取智能系统试题的推荐答疑
     * @param map
     * @return
     */
    public static Flowable<BaseRes<QueryRecommendBean>> getIntelQuestionQuery(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getIntelQuestionQuery(map));
    }

    /**
     * 试题提问
     *
     * @param map
     * @return
     */
    public static Flowable<BaseRes<String>> submitQueryQuestion(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().submitQueryQuestion(map));
    }
    /**
     * 答疑收藏
     *
     * @param map
     * @return
     */
    public static Flowable<BaseRes<String>> collectQueryQuestion(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().collectQueryQuestion(map));
    }
    /**
     * 答疑取消收藏
     *
     * @param map
     * @return
     */
    public static Flowable<BaseRes<String>> collectCancelQueryQuestion(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().collectCancelQueryQuestion(map));
    }
    /**
     * 答疑点赞
     *
     * @param map
     * @return
     */
    public static Flowable<BaseRes<String>> favoriteCancelQueryQuestion(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().favoriteCancelQueryQuestion(map));
    }

    /**
     * 图书提问
     *
     * @param map
     * @return
     */
    public static Flowable<BaseRes<String>> submitQueryBookQuestion(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().submitQueryBookQuestion(map));
    }
    /**
     * 修改提问
     *
     * @param map
     * @return
     */
    public static Flowable<BaseRes<String>> updateQueryBookQuestion(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().updateQueryBookQuestion(map));
    }
    /**
     * 删除提问
     *
     * @param map
     * @return
     */
    public static Flowable<BaseRes<String>> deleteQueryBookQuestion(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().deleteQueryBookQuestion(map));
    }
    /**
     * 增加追问
     *
     * @param map
     * @return
     */
    public static Flowable<BaseRes<String>> addOthreQueryBookQuestion(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().addOthreQueryBookQuestion(map));
    }
    /**
     * 答疑详情
     *
     * @param map
     * @return
     */
    public static Flowable<BaseRes<QueryDetailItemBean>> getQueryBookQuestionDetail(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getQueryBookQuestionDetail(map));
    }
    /**
     * 智能系统答疑详情
     *
     * @param map
     * @return
     */
    public static Flowable<BaseRes<QueryDetailItemBean>> getIntelQueryDetail(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getIntelQueryDetail(map));
    }
    /**
     * 答疑评价列表
     *
     * @param map
     * @return
     */
    public static Flowable<BaseRes<List<QueryJudgeDataBean>>> getQueryJudgeData(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getQueryJudgeData(map));
    }
    /**
     * 答疑评价提问
     * @param map
     * @return
     */
    public static Flowable<BaseRes<String>> submitQueryJudge(@FieldMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().submitQueryJudge(map));
    }

    /**
     * 首页
     */
    public static Flowable<BaseRes<MainTypeBean>> getMainTypeInfo(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getMainTypeInfo(map));
    }
    /**
     * 首页广告
     */
    public static Flowable<BaseRes<MainADBean>> getMainAD(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getMainAD(map));
    }

    /**
     * 二级页面栏目列表
     */
    public static Flowable<BaseRes<MainFragmentBean>> getMainTypeFragment(@QueryMap Map<String, String> map) {
        return getData(ApiClient.getApiInterface().getMainTypeFragment(map));
    }


    /**
     * 二级页面栏目列表
     */
    public static Flowable<BaseRes<TabListBean>> getTabList(String examId, String forumId) {
        return getData(ApiClient.getApiInterface().getTabList(ParamsUtils.getTabList(examId, forumId)));
    }

    /**
     * 科目列表
     */
    public static Flowable<BaseRes<String>> subjectList(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().subjectList(map));
    }

    /**
     * 科目列表
     */
    public static Flowable<BaseRes<String>> subjectSmartList(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().subjectSmartList(map));
    }

    /**
     * 课程目录列表
     */
    public static Flowable<BaseRes<CourseListData>> courseList(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().courseList(map));
    }

//    /**
//     * 上传笔记图片
//     */
//    public static Flowable<ResponseBody> postImage(@Part MultipartBody.Part file) {
//
//        return ApiClient.getApiInterface().postImage(file);
//    }
    /**
     * 笔记详情 试题
     */
    public static Flowable<BaseRes<String>> getNoteDetail(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().getNoteDetail(map));
    }

    /**
     * 笔记详情 课堂
     */
    public static Flowable<BaseRes<String>> getNoteClassDetail(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().getNoteClassDetail(map));
    }

    /**
     * 删除笔记 课堂
     */
    public static Flowable<BaseRes<String>> delteNotesClass(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().delteNotesClass(map));
    }

    /**
     * 删除笔记
     */
    public static Flowable<BaseRes<String>> delteNotes(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().delteNotes(map));
    }

    /**
     * 提交笔记
     */
    public static Flowable<BaseRes<String>> postNotes(@FieldMap Map<String, String> map) {

        return ApiClient.getApiInterface().postNotes(map);
    }

    /**
     * 提交笔记 ketang
     */
    public static Flowable<BaseRes<String>> postNotesClass(@FieldMap Map<String, String> map) {

        return ApiClient.getApiInterface().postNotesClass(map);
    }

    /**
     * 笔记跟新 ketang
     */
    public static Flowable<BaseRes<String>> updaNoteClass(@FieldMap Map<String, String> map) {

        return ApiClient.getApiInterface().updaNoteClass(map);
    }

    /**
     * 笔记跟新
     */
    public static Flowable<BaseRes<String>> updaNote(@FieldMap Map<String, String> map) {

        return ApiClient.getApiInterface().updaNote(map);
    }

    /**
     * 课程详情请求
     */
    public static Flowable<BaseRes<CourseDetail>> playDetail(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().playDetail(map));
    }

    /**
     * 继续播放 sujectId
     */
    public static Flowable<BaseRes<String>> playContinue(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().playContinue(map));
    }

    /**
     * 继续播放 examId
     */
    public static Flowable<BaseRes<String>> classPlayContinue(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().classPlayContinue(map));
    }

    /**
     * 课堂首页
     */
    public static Flowable<BaseRes<ClassHomeBean>> classHome(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().classHome(map));
    }
   
    /**
     * 智能系统首页
     */
    public static Flowable<BaseRes<IntelMainBean>> intelHome(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().intelHome(map));
    }

    /**
<<<<<<< .mine
     * 学习计划
     */
    public static Flowable<BaseRes<StudyPlanBean>> studyPlan(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().studyPlan(map));
    }

    /**
=======
     * 智能系统考种
     */
    public static Flowable<BaseRes<MainTypeBean>> getIntelMainTypeInfo(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getIntelMainTypeInfo(map));
    }

    /**
>>>>>>> .r13912
     * 收藏课程
     */
    public static Flowable<BaseRes<String>> collectCw(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().collectCw(map));
    }

    /**
     * 是否评价
     */
    public static Flowable<ResponseBody> isCommented(@FieldMap Map<String, String> map) {

        return ApiClient.getApiInterface().isCommented(map);
    }

    /**
     * 取消收藏课程
     */
    public static Flowable<BaseRes<String>> delCollectCw(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().delCollectCw(map));
    }

    /**
     * 取消收藏课程
     */
    public static Flowable<BaseRes<String>> isCollect(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().isCollectCw(map));
    }

    /**
     * 播放地址请求
     */
    public static Flowable<ResponseBody> getM3U8(@Url String url) {

        return ApiClient.getApiInterface().getM3U8(url);
    }

    /**
     * 播放地址请求
     */
    public static Flowable<BaseRes<String>> getPlayUrl(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().getPlayUrl(map));
    }

    /**
     * 播放地址请求
     */
    public static Flowable<BaseRes<String>> getSmartPlayUrl(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().getSmartPlayUrl(map));
    }

    /**
     * 智能讲义地址请求
     */
    public static Flowable<BaseRes<String>> getSmartLectureUrl(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().getSmartLectureUrl(map));
    }

    /**
     * 听课记录上传
     */
    public static Flowable<BaseRes<String>> upLoadVideos(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().upLoadVideos(map));
    }

    /**
     * smart听课记录上传
     */
    public static Flowable<BaseRes<String>> upLoadVideosSmart(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().upLoadVideosSmart(map));
    }

    /**
     * 我会了
     */
    public static Flowable<BaseRes<String>> changeStatus(@FieldMap Map<String, String> map) {

        return getData(ApiClient.getApiInterface().changeStatus(map));
    }

    /**
     * 下载
     */
    public static Flowable<BaseRes<String>> getDownloadUrl(String lectureID) {

        return getData(ApiClient.getApiInterface().getDownloadUrl(ParamsUtils.getDownloadPath(lectureID)));
    }

    /**
     * 提交评价
     */
    public static Flowable<ResponseBody> postComment(@FieldMap Map<String, String> map) {

        return ApiClient.getApiInterface().postComment(map);
    }

    /**
     * 商品详情
     */
    public static Flowable<BaseRes<GoodsDetailBean>> getGoodsDetail(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getGoodsDetail(map));
    }

    /**
     * 商品推荐
     */
    public static Flowable<BaseRes<List<GoodsRecomondBean>>> getRecommend(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getRecommend(map));
    }

    /**
     * 商品推荐
     */
    public static Flowable<BaseRes<List<GoodsRecomondBean>>> getGoodsRecommend(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getGoodsRecommend(map));
    }

    /**
     * 添加商品到购物车
     */
    public static Flowable<BaseRes<String>> getShopCartAddGoods(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getShopCartAddGoods(map));
    }

    /**
     * 批量添加商品到购物车
     */
    public static Flowable<BaseRes<String>> getShopCartBatchs(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getShopCartBatchs(map));
    }

    /**
     * 购物车详情
     */
    public static Flowable<BaseRes<ShopCartBean>> getShopCartDetail(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getShopCartDetail(map));
    }

    /**
     * 购物车全选
     */
    public static Flowable<BaseRes<ShopCartBean>> getShopCartAllSelect(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getShopCartAllSelect(map));
    }

    /**
     * 购物车取消全选
     */
    public static Flowable<BaseRes<ShopCartBean>> getShopCartAllSelectCancel(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getShopCartAllSelectCancel(map));
    }

    /**
     * 购物车改变数量
     */
    public static Flowable<BaseRes<ShopCartBean>> getShopCartSelectNum(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getShopCartSelectNum(map));
    }

    /**
     * 购物车选择优惠
     */
    public static Flowable<BaseRes<ShopCartBean>> getShopCartSelectPromo(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getShopCartSelectPromo(map));
    }

    /**
     * 购物车选择商品
     */
    public static Flowable<BaseRes<ShopCartBean>> getShopCartSelectProduct(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getShopCartSelectProduct(map));
    }
    /**
     * 购物车取消选择商品
     */
    public static Flowable<BaseRes<ShopCartBean>> getShopCartSelectProductCancel(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getShopCartSelectProductCancel(map));
    }

    /**
     * 购物车选择店铺
     */
    public static Flowable<BaseRes<ShopCartBean>> getShopCartSelectVender(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getShopCartSelectVender(map));
    }

    /**
     * 购物车取消店铺
     */
    public static Flowable<BaseRes<ShopCartBean>> getShopCartSelectVenderCancel(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getShopCartSelectVenderCancel(map));
    }

    /**
     * 购物车选择赠品
     */
    public static Flowable<BaseRes<ShopCartBean>> getShopCartSelectGift(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getShopCartSelectGift(map));
    }

    /**
     * 购物车清空赠品
     */
    public static Flowable<BaseRes<ShopCartBean>> getShopCartClearGift(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getShopCartClearGift(map));
    }

    /**
     * 购物车删除单个商品
     */
    public static Flowable<BaseRes<ShopCartBean>> getShopCartSelectDelete(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getShopCartSelectDelete(map));
    }

    /**
     * 购物车删除选择的商品
     */
    public static Flowable<BaseRes<ShopCartBean>> getShopCartSelectAllDelete(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getShopCartSelectAllDelete(map));
    }


    /**
     * 购物车提交订单
     */
    public static Flowable<BaseRes<OrderSaveBean>> getShopCartSaveOrder(@QueryMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getShopCartSaveOrder(map));
    }

    /**
     * 购物车计算差价
     */
    public static Flowable<BaseRes<OrderPriceBean>> getShopCartCountMoney(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getShopCartCountMoney(map));
    }

    /**
     * 确认下单获取可用的优惠券等信息
     */
    public static Flowable<BaseRes<OrderUserAccountBean>> getShopCartUserAccount(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getShopCartUserAccount(map));
    }

    public static Flowable<BaseRes<String>> getShopCartNum(Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getShopCartNum(map));
    }

    /**
     * 购物车获取支付参数
     */
    public static Flowable<BaseRes<PayInfoBean>> getCommodityOrderInfo(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getCommodityOrderInfo(map));
    }

    /**
     * 商品支付处理
     */
    public static Flowable<BaseRes<OrderVOBean>> getOrderDetail(@FieldMap Map<String, String> map) {
        //创建请求的类
        return getData(ApiClient.getApiInterface().getOrderDetail(map));
    }



    /**
     * 获得地址列表
     */
    public static Flowable<BaseRes<List<AddressBean>>> getConsigneeList() {
        return getData(ApiClient.getApiInterface().getConsigneeList(ParamsUtils.getConsigneeList()));
    }

    /**
     * 更新修改地址
     */
    public static Flowable<BaseRes<AddressBean>> getConsigneeSaveAndUpdate(AddressBean addressBean) {
        return getData(ApiClient.getApiInterface().getConsigneeSaveAndUpdate(ParamsUtils.getConsigneeSaveAndUpdate(addressBean, false)));
    }

    /**
     * 修改默认地址
     */
    public static Flowable<BaseRes<String>> updateConsigneeDefault(AddressBean addressBean) {
        return getData(ApiClient.getApiInterface().updateConsigneeDefault(ParamsUtils.getConsigneeSaveAndUpdate(addressBean, true)));
    }

    /**
     * 删除地址
     */
    public static Flowable<BaseRes<Object>> deleteConsignee(AddressBean addressBean) {
        return getData(ApiClient.getApiInterface().deleteConsignee(ParamsUtils.deleteConsignee(addressBean.getId())));
    }

    /**
     * 获得商城列表
     */
    public static Flowable<BaseRes<GoodsListItemBean>> getShopGoodsList(int page, int rows,int categoryId,int goodsType) {
        return getData(ApiClient.getApiInterface().getShopGoodsList(ParamsUtils.getShopGoodsList(page, rows,categoryId,goodsType)));
    }

    /**
     * 获得订单列表
     */
    public static Flowable<BaseRes<MyOrderResponseBean>> getOrderList(int page, int rows, int type) {
        return getData(ApiClient.getApiInterface().getOrderList(ParamsUtils.getOrderList(page, rows, type)));
    }

    /**
     * 商城类别
     */
    public static Flowable<BaseRes<List<Category>>> getCategory() {
        return getData(ApiClient.getApiInterface().getCategory(ParamsUtils.commonSignPramas()));
    }

    /**
     * 商城类别
     */
    public static Flowable<BaseRes<OrderDetailResponseBean>> getOrderDetailByOrderId(long orderId) {
        return getData(ApiClient.getApiInterface().getOrderDetailByOrderId(ParamsUtils.getOrderDetailByOrderId(orderId)));
    }

    /**
     * 凑单数据
     */
    public static Flowable<BaseRes<CouDanBean>> getCouDanData(String venderId ,String promoId){
        return getData(ApiClient.getApiInterface().getCouDanData(ParamsUtils.getCoudan(venderId,promoId)));
    }

    /**
     * 随书赠卡图书激活
     */
    public static Flowable<BaseRes<String>> bookCardActivate(String cardNumber){
        return getData(ApiClient.getApiInterface().bookCardActivate(ParamsUtils.bookCardActivate(cardNumber)));
    }

    /**
     * 随书赠卡图书激活明细列表
     */
    public static Flowable<BaseRes<BookCardActivateListBean>> bookCardActivateList(long page){
        return getData(ApiClient.getApiInterface().bookCardActivateList(ParamsUtils.bookCardActivateList(page)));
    }

    /**
     * 随书赠卡图书激活服务内容查询
     */
    public static Flowable<BaseRes<ActivateServicesListBean>> bookCardActivateServicesInfo(long ruleId){
        return getData(ApiClient.getApiInterface().bookCardActivateServicesInfo(ParamsUtils.bookCardActivateServicesInfo(ruleId)));
    }

    /**
     * 天猫预定订单号激活
     */
    public static Flowable<BaseRes<String>> tmallOrderActivate(String orderId){
        return getData(ApiClient.getApiInterface().tmallOrderActivate(ParamsUtils.tmallOrderActivate(orderId)));
    }

    /**
     * 天猫预定订单号激活明细列表
     */
    public static Flowable<BaseRes<TmallOrderActivateListBean>> tmallOrderActivateList(int page){
        return getData(ApiClient.getApiInterface().tmallOrderActivateList(ParamsUtils.tmallOrderActivateList(page)));
    }

    /**
     * 获取用户个人信息
     */
    public static Flowable<BaseRes<PersonalInformationBean>> getPersonalInfo(){
        return getData(ApiClient.getApiInterface().getPersonalInfo(ParamsUtils.getUserInfo()));
    }
    /**
     * 获取用户优惠券
     */
    public static Flowable<BaseRes<MyCouponResponseBean>> getUserCoupon(int status,int page,int rows,String couponType){
        return getData(ApiClient.getApiInterface().getUserCoupon(ParamsUtils.getUserCoupon(status,page,rows,couponType)));
    }

    /**
     * 获取账号安全等级
     */
    public static Flowable<BaseRes<AccountSecurLevelBean>> requestSecurLevel(HashMap<String, String> map) {
        return getData(ApiClient.getApiInterface().requestSecurLevel(map));
    }

    /**
     * 修改邮箱
     */
    public static Flowable<BaseRes<String>> modifyEmail(HashMap<String, String> map) {
        return getData(ApiClient.getApiInterface().modifyEmail(map));
    }

    /**
     * 修改手机号
     */
    public static Flowable<BaseRes<String>> modifyPhone(HashMap<String, String> map) {
        return getData(ApiClient.getApiInterface().modifyPhone(map));
    }

    /**
     * 修改密码
     */
    public static Flowable<BaseRes<String>> modifyPsw(HashMap<String, String> map) {
        return getData(ApiClient.getApiInterface().modifyPsw(map));
    }
    /**
     * 激活优惠券/现金券
     */
    public static Flowable<BaseRes<String>> activityCoupon(String couponNo, boolean isCoupon) {
        return getData(ApiClient.getApiInterface().activityCoupon(ParamsUtils.activityCoupon(couponNo,isCoupon)));
    }

    /**
     * 我的账户详情
     */
    public static Flowable<BaseRes<MyAccountDetailBean>> requestAccountDetail(HashMap<String, String> map) {
        return getData(ApiClient.getApiInterface().requestAccountDetail(map));
    }

    /**
     * 获取我的开通课程列表
     */
    public static Flowable<BaseRes<List<OpenClassListBean>>> requestOpenClassList(Map<String, String> map) {
        return getData(ApiClient.getApiInterface().requestOpenClassList(map));
    }

    /**
     * 开通课程
     */
    public static Flowable<BaseRes<String>> openCourse(HashMap<String, String> map) {
        return getData(ApiClient.getApiInterface().openCourse(map));
    }

    /**
     * 发票页面
     */
    public static Flowable<BaseRes<MyInvoiceResponseBean>> getInvoicePage(int page, int rows) {
        return getData(ApiClient.getApiInterface().getInvoicePage(ParamsUtils.getInvoicePage(page,rows)));
    }

    /**
     * 首页二级页面列表页数据
     */
    public static Flowable<SubpageListBean> getSubpageList(String url) {
        return getDatas(ApiClient.getApiInterface().getSubpageData(url));
    }

    /**
     * 发票title
     */
    public static Flowable<BaseRes<List<InvoiceTitleBean>>> getInvoiceTitle() {
        return getData(ApiClient.getApiInterface().getInvoiceTitle(ParamsUtils.getInvoiceTitle()));
    }

    /**
     * 根据order id 获取发票详情
     */
    public static Flowable<BaseRes<InvoiceBean>> getInvoiceDetailByOrderId(long orderId) {
        return getData(ApiClient.getApiInterface().getInvoiceDetailByOrderId(ParamsUtils.getOrderDetailByOrderId(orderId)));
    }

    /**
     * 根据order id 获取发票明细
     */
    public static Flowable<BaseRes<InvoiceBookAndCourseMoneyBean>> getInvoiceBookAndCourse_Money(long orderId) {
        return getData(ApiClient.getApiInterface().getInvoiceBookAndCourse_Money(ParamsUtils.getOrderDetailByOrderId(orderId)));
    }

    /**
     * 添加电子订单
     */
    public static Flowable<BaseRes<InvoiceBean>> addDianZiInvoice(InvoiceRequestBean invoiceRequestBean) {
        return getData(ApiClient.getApiInterface().addDianZiInvoice(ParamsUtils.addDianZiInvoice(invoiceRequestBean)));
    }

    /**
     * 添加普通
     */
    public static Flowable<BaseRes<InvoiceBean>> addPuTongInvoice(InvoiceRequestBean invoiceRequestBean) {
        return getData(ApiClient.getApiInterface().addPuTongInvoice(ParamsUtils.addPuTongZiInvoice(invoiceRequestBean)));
    }

    /**
     * 添加发票Title
     */
    public static Flowable<BaseRes<InvoiceTitleBean>> addInVoiceTitle(String title) {
        return getData(ApiClient.getApiInterface().addInVoiceTitle(ParamsUtils.addInVoiceTitle(title)));
    }

    /**
     * 获得订单的快递
     */
    public static Flowable<BaseRes<List<LogisticsBean>>> getOrderLogistics(long orderId) {
        return getData(ApiClient.getApiInterface().getOrderLogistics(ParamsUtils.getOrderDetailByOrderId(orderId)));
    }


    /**
     * 根据客服工号[邀请码]查询客服信息
     */
    public static Flowable<BaseRes<JobNumBean>> findUserByJobnum(String jobNum) {
        return getData(ApiClient.getApiInterface().findUserByJobnum(ParamsUtils.findUserByJobnum(jobNum)));
    }

    /**
     * 我的账户
     */
    public static Flowable<BaseRes<MyAccountBean>> requestMyAccount(HashMap<String, String> map) {
        return getData(ApiClient.getApiInterface().requestMyAccount(map));
    }

    /**
     * 取消订单
     */
    public static Flowable<BaseRes<String>> orderCancel(String orderIdAndNo,String ip) {
        return getData(ApiClient.getApiInterface().orderCancel(ParamsUtils.orderCancel(orderIdAndNo,ip)));
    }

    /**
     * 推荐商品(课程、图书)列表
     */
    public static Flowable<BaseRes<RecommendedGoodListBean>> getRecommendedGoodsList(String examId, String shopId, String goodsType) {
        return getData(ApiClient.getApiInterface().getRecommendedGoodsList(ParamsUtils.getRecommendedGoodsList(examId,shopId,goodsType)));
    }

    /**
     * 讲次学员评价
     */
    public static Flowable<BaseRes<StudentsEvaluateInfo>> getStudentsEvaluateInfo(String lectureId, int pageNo, int pageSize) {
        return getData(ApiClient.getApiInterface().getStudentsEvaluateInfo(ParamsUtils.getStudentsEvaluateInfo(lectureId, pageNo, pageSize)));
    }

    /**
     * 获取用户相关考种科目考季列表
     */
    public static Flowable<BaseRes<List<ExamMenu>>> getExamSubjectSubList() {
        return getData(ApiClient.getApiInterface().getExamSubjectSubList(ParamsUtils.getExamSubjectSubList()));
    }

    /**
     * 获取用户相关考种下错题数量和已错题型列表
     */
    public static Flowable<BaseRes<ErrorsCountAndTypes>> getCountAndChoiceTypes(String subjectId) {
        return getData(ApiClient.getApiInterface().getCountAndChoiceTypes(ParamsUtils.getCountAndChoiceTypes(subjectId)));
    }

    /**
     * 获取用户相关考季下已收藏<答疑>列表
     */
    public static Flowable<BaseRes<CollectAnswer>> getCollectAnswerList(String sSubjectId, int pageNo) {
        return getData(ApiClient.getApiInterface().getCollectAnswerList(ParamsUtils.getCollectAnswerList(sSubjectId, pageNo)));
    }

    /**
     * 获取用户相关考季下已收藏<课程>列表
     */
    public static Flowable<BaseRes<List<CollectCourse>>> getCollectCourseList(String sSubjectId, int pageNo) {
        return getData(ApiClient.getApiInterface().getCollectCourseList(ParamsUtils.getCollectCourseList(sSubjectId, pageNo)));
    }

    /**
     * 获取用户相关考季下已收藏<题>列表
     */
    public static Flowable<BaseRes<CollectExercises>> getCollectExercisesList(String sSubjectId, int page) {
        return getData(ApiClient.getApiInterface().getCollectExercisesList(ParamsUtils.getCollectExercisesList(sSubjectId, page)));
    }

    /**
     * 获取用户相关考季下<试题笔记>列表
     */
    public static Flowable<BaseRes<NotesExercisesList>> getNotesExercisesList(String subjectId,String sSubjectId, int pageNo) {
        return getData(ApiClient.getApiInterface().getNotesExercisesList(ParamsUtils.getNotesExercisesList(subjectId,sSubjectId, pageNo)));
    }

    /**
     * 获取用户相关考季下<课程笔记>列表
     */
    public static Flowable<BaseRes<NotesCourseList>> getNotesCourseList(String subjectId,String sSubjectId, int pageNo) {
        return getData(ApiClient.getApiInterface().getNotesCourseList(ParamsUtils.getNotesCourseList(subjectId,sSubjectId, pageNo)));
    }

    /**
     * 获取我的消息列表
     */
    public static Flowable<BaseRes<MessageListBean>> getMessageList(int pageNo) {
        return getData(ApiClient.getApiInterface().getMessageList(ParamsUtils.getMessageList(pageNo)));
    }

    /**
     * 扫码看课、做题结果
     */
    public static Flowable<BaseRes<CaptureResultBean>> captureQrCode(String pUrl) {
        return getData(ApiClient.getApiInterface().captureQrCode(ParamsUtils.captureQrCode(pUrl)));
    }

    /**
     * 学习记录<课程>
     */
    public static Flowable<BaseRes<LearningRecordCourseListBean>> getLearningRecordCourseList(String subjectId, String sSubjectId, int pageNo) {
        return getData(ApiClient.getApiInterface().getLearningRecordCourseList(ParamsUtils.getLearningRecordCourseList(subjectId, sSubjectId, pageNo)));
    }

    /**
     * 学习记录<题库>
     */
    public static Flowable<BaseRes<LearningRecordExercisesListBean>> getLearningRecordExercisesList(String subjectId, String sSubjectId, int page) {
        return getData(ApiClient.getApiInterface().getLearningRecordExercisesList(ParamsUtils.getLearningRecordExercisesList(subjectId, sSubjectId, page)));
    }

    /**
     * 欢迎页
     */
    public static Flowable<BaseRes<WelcomeRes>> requestWelcomeData(HashMap<String, String> map) {
        return getData(ApiClient.getApiInterface().requestWelcomeData(map));
    }

    /**
     * 欢迎页下载广告
     */
    public static Flowable<Response<ResponseBody>> downloadWelcomeImageOrVideo(String url) {

        return ApiClient.getApiInterface().downloadWelcomeImageOrVideo(url);
    }

    /**
     * 图书勘误菜单列表
     */
    public static Flowable<BaseRes<BooksErrataMenuListBean>> getBooksErrataMenuList(String examId) {
        return getData(ApiClient.getApiInterface().getBooksErrataMenuList(ParamsUtils.getBooksErrataMenuList(examId)));
    }

    /**
     * 图书勘误列表
     */
    public static Flowable<BaseRes<BooksErrataListBean>> getBooksErrataList(String bookId, int page) {
        return getData(ApiClient.getApiInterface().getBooksErrataList(ParamsUtils.getBooksErrataList(bookId, page)));
    }

    /**
     * 提交意见反馈
     */
    public static Flowable<BaseRes<String>> submitFeedback(String title, String content) {
        return getData(ApiClient.getApiInterface().submitFeedback(ParamsUtils.submitFeedback(title, content)));
    }

    /**
     * 获取版本信息
     */
    public static Flowable<BaseRes<VersionInfo>> getVersionDesc() {
        return getData(ApiClient.getApiInterface().getVersionDesc(ParamsUtils.getVersionDesc()));
    }

    /**
     * 听课时长或做题数量统计
     * @param map
     * @return
     */
    public static Flowable<BaseRes<CourseStatiscalDetailBean>> requestStatiscalDetail(HashMap<String, String> map,int type) {
        if (type==1)
            return getData(ApiClient.getApiInterface().requestLecturesStatiscal(map));
        else
            return getData(ApiClient.getApiInterface().requestQuestionStatiscal(map));
    }

    /**
     * 知识点统计
     * @param map
     * @return
     */
    public static Flowable<BaseRes<KnowledgePointsStatiscialBean>> getKPStatiscialData(HashMap<String, String> map) {
        return getData(ApiClient.getApiInterface().getKPStatiscialData(map));
    }

    /**
     * 学习统计
     * @param map
     * @return
     */
    public static Flowable<BaseRes<CourseStatiscalDetailBean.DateBean>> getStudyStatiscialData(HashMap<String, String> map) {
        return getData(ApiClient.getApiInterface().getStudyStatiscialData(map));
    }

    /**
     * 复习讲义
     */
    public static Flowable<String> reviewHandout(String reviewId) {
        return getString(ApiClient.getApiInterface().reviewHandout(ParamsUtils.reviewHandout(reviewId)));
    }

    /**
     * 复习题
     */
    public static Flowable<BaseRes<ChoiceTypesBean>> reviewQusetionType(String reviewId) {
        return getData(ApiClient.getApiInterface().reviewQusetionType(ParamsUtils.reviewQusetionType(reviewId)));
    }

    /**
     * 已复习
     */
    public static Flowable<BaseRes<String>> haveReviewed(String reviewId, String questionId) {
        return getData(ApiClient.getApiInterface().haveReviewed(ParamsUtils.haveReviewed(reviewId, questionId)));
    }

    /**
     * 获取学习记录有效时间范围
     */
    public static Flowable<BaseRes<EnableDate>> getStudyRecordEnableDate(String userId,String subjectId) {
        return getData(ApiClient.getApiInterface().studyRecordEnableDate(ParamsUtils.requestStatiscalDetail(userId,subjectId)));
    }


    /**
     * 获取某天的学习记录
     */
    public static Flowable<BaseRes<DayRecord>> getStudyRecordDayRecord(String userId, String subjectId,String date) {
        return getData(ApiClient.getApiInterface().studyRecordDayRecord(ParamsUtils.getStudyRecordDayRecord(userId,subjectId,date)));
    }

    /**
     * 获取周学习记录
     */
    public static Flowable<BaseRes<WeekRecordObj>> getStudyRecordWeekRecord(String userId, String subjectId, String date) {
        return getData(ApiClient.getApiInterface().studyRecordWeekRecord(ParamsUtils.getStudyRecordDayRecord(userId,subjectId,date)));
    }

}

