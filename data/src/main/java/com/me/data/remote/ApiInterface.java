package com.me.data.remote;

import com.me.data.model.BaseRes;
import com.me.data.model.WelcomeRes;
import com.me.data.model.course.CourseStatiscalDetailBean;
import com.me.data.model.course.KnowledgePointsStatiscialBean;
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
import com.me.data.model.mine.Area;
import com.me.data.model.mine.BookCardActivateListBean;
import com.me.data.model.mine.City;
import com.me.data.model.mine.CollectAnswer;
import com.me.data.model.mine.CollectCourse;
import com.me.data.model.mine.CollectExercises;
import com.me.data.model.mine.ErrorsCountAndTypes;
import com.me.data.model.mine.ExamMenu;
import com.me.data.model.mine.InvoiceBean;
import com.me.data.model.mine.InvoiceBookAndCourseMoneyBean;
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
import com.me.data.model.mine.Provice;
import com.me.data.model.mine.TmallOrderActivateListBean;
import com.me.data.model.mine.VersionInfo;
import com.me.data.model.play.ClassHomeBean;
import com.me.data.model.play.CourseDetail;
import com.me.data.model.play.CourseListData;
import com.me.data.model.play.NoteDetail;
import com.me.data.model.play.StudyPlanBean;
import com.me.data.model.studyrecord.DayRecord;
import com.me.data.model.studyrecord.EnableDate;
import com.me.data.model.studyrecord.WeekRecordObj;
import com.me.data.model.user.AccountSecurLevelBean;
import com.me.data.model.user.GetBackPwUserInfoBean;
import com.me.data.model.user.MyAccountDetailBean;
import com.me.data.model.user.UserBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by xunice on 09/03/2017.
 */

public interface ApiInterface {

    String BASE_URL = "http://cloudclass.api.test.com/";//测试
//    String BASE_URL = "http://cloudclass.api.dev3.com/";//开发
//    String BASE_URL = "http://cloudclass.api.dongao.com/";//正式

    String BASE_IMAGE_URL = "http://webupload.admin.dongao.com/ec/shop";

    String CHECK_UPDATE_URL = BASE_URL+ "appApi/V4/getVersion?";//版本更新

    String LOGIN = "userApi/V4/login";//登录
    String LOGOUT = "userApi/V4/loginOut";//登出
    String THIRD_PARTY_LOGIN = "userApi/V4/outerlogin";//第三方登录
    String REQUEST_VERIFICATION_CODE = "userApi/V4/sendCode";//注册请求验证码
    String VERIFI_THE_CODE = "userApi/V4/checkCode";//注册请求验证码
    String REGISTER = "userApi/V4/register";//注册请求验证码
    String GET_BACK_PW_BY_EMAIL = "userApi/V4/findByEmailEqual";//注册请求验证码
    String GET_BACK_PW_BY_PHONE = "userApi/V4/findByMobilePhoneEqual";//注册请求验证码
    String GET_BACK_PW = "userApi/V4/resetPwd";//注册请求验证码
    String SECUR_LEVEL = "userApi/V4/getSecur";//请求安全级别
    String MODIFY_EMAIL = "userApi/V4/modEmail";//修改邮箱
    String MODIFY_PHONE = "userApi/V4/modPhone";//修改手机号
    String MODIFY_PSW = "userApi/V4/modPassword";//修改手机号
    String MY_ACCOUNT_DETAIL = "orderApi/V4/cashAccountLog";//我的账户详情
    String MY_ACCOUNT = "orderApi/V4/cashAccount";//我的账户详情
    String OPEN_CLASS_LIST = "orderApi/V4/openLessonList";//我的开通课程列表
    String OPEN_COURSE = "orderApi/V4/openLessonServices";//开通课程
    String MAIN_TYPE_DETAIL = "homeApi/V4/getExamList";
    String MAIN_TYPE_FRAGMENT_DETAIL = "homeApi/V4/getHomePage";
    String MAIN_SUBPAGE_GET_TAB_LIST = "homeApi/V4/getTabList";//首页列表页标签
    String MAIN_AD = "appApi/V4/getAd";//首页列表页标签

    String PLAY_URL = "courseApi/V4/playInfo";//获取播放地址接口
    String CHANGE_STATUS = "studyApi/V4/iDoIt";
    String SMART_PLAY_URL = "studyApi/V4/playInfo";//获取播放地址接口
    String SMART_LECTURE_URL = "intelligentExamApi/V4/courseHandout";//获取JIANGYI地址接口
    String POST_STUDYLOG = "courseApi/V4/user/saveListenRecord";//上传听课记录
    String POST_STUDYLOG_SMART = "studyApi/V4/saveListenRecord";//上传听课记录
    String DOWNLOAD_URL = "courseApi/V4/downPlay";//获取下载
    String PLAY_DETAIL_LIST = "courseApi/V4/lectureInfos";//课程详情目录
    String COURSEWARE_COLLECT = "courseApi/V4/collect/add";//视频收藏
    String DEL_COURSEWARE_COLLECT = "courseApi/V4/collect/del";//取消视频收藏
    String IS_COURSEWARE_COLLECT = "courseApi/V4/collect/exists";//取消视频收藏

    String IS_COMMENTED = "courseApi/V4/courseEvaluate/findMemberEvaluate";//是否评价

    String CLASS_HOME = "homeApi/V4/getSchoolPage";//课堂首页
    String PLAY_CONTINUE = "courseApi/V4/lastListen";//继续播放 sujectId
    String CLASS_PLAY_CONTINUE = "courseApi/V4/user/examLastListen";//继续播放 examid
    String POST_COMMENT = "courseApi/V4/courseEvaluate/save";//提交评价
    String COURSE_SUBJECTLIST = "courseApi/V4/member/examComplateSubjectList";//科目列表
    String COURSE_SUBJECTLIST_SMART = "intelligentExamApi/V4/examComplateSubjectList";//科目列表
    String COURSE_LIST = "courseApi/V4/courseInfos";//课程列表

    String POST_IMAGE_NOTE = "notesApi/V4/uploadNoteImg";//上传图片
    String POST_NOTE = "notesApi/V4/addNote";//提交笔记 试题
    String UPDATE_NOTE = "notesApi/V4/updateNote";//笔记更新 试题
    String GET_NOTE_DETAIL = "notesApi/V4/getNote";//笔记详情 试题
    String DEL_NOTE = "notesApi/V4/delNote";//笔记删除 试题

    String POST_NOTE_CLASS = "courseApi/V4/saveNote";//提交笔记 课堂
    String UPDATE_NOTE_CLASS = "courseApi/V4/editNote";//笔记更新 课堂
    String GET_NOTE_DETAIL_CLASS = "courseApi/V4/viewNote";//笔记详情 课堂
    String DEL_NOTE_CLASS = "courseApi/V4/delNote";//笔记删除 课堂

    String STUDY_PLAN = "intelligentExamApi/V4/studyPlan";//学习计划

    String GOODS_DETAIL = "shopApi/V4/goodsInfoById";
    String VENDER_GOODS_RECOMMEND = "shopApi/V4/getRecommend";
    String GOODS_RECOMMEND = "shopApi/V4/getGoodsRecommend";
    String SHOP_CART_ADD_GOODS = "cartApi/V4/gate";
    String SHOP_CART_ADD_BATCHS = "cartApi/V4/batchGate";
    String SHOP_CART_DETAIL = "cartApi/V4/cartInfo";
    String SHOP_CART_ALL_SELECT = "cartApi/V4/selectAllItem";
    String SHOP_CART_ALL_SELECT_CANCEL = "cartApi/V4/cancelAllItem";
    String SHOP_CART_SELECT_VENDER = "cartApi/V4/selectVenderItem";
    String SHOP_CART_SELECT_VENDER_CANCEL = "cartApi/V4/cancelVenderItem";
    String SHOP_CART_SELECT_PRODUCT = "cartApi/V4/selectItem";
    String SHOP_CART_SELECT_PRODUCT_CANCEL = "cartApi/V4/cancelItem";
    String SHOP_CART_SELECT_PROMO = "cartApi/V4/changePromo";
    String SHOP_CART_SELECT_NUM = "cartApi/V4/changeNum";
    String SHOP_CART_SELECT_GIFT = "cartApi/V4/addGiftsOfPromo";
    String SHOP_CART_CLEAR_GIFT = "cartApi/V4/clearGiftsOfPromo";
    String SHOP_CART_SELECT_DELETE = "cartApi/V4/removeGoodsFromCart";
    String SHOP_CART_SELECT_All_DELETE = "cartApi/V4/batchDelGoodsFromCart";
    String SHOP_CART_SAVE_ORDER = "orderApi/V4/saveOrder";
    String SHOP_CART_COUNT_MONEY = "orderApi/V4/countMoney";

    String SHOP_CART_NUM = "cartApi/V4/cartGoodsTotal";

    String SHOP_CART_USER_ACCOUNT = "orderApi/V4/userAccount";
    String ORDER_PAY_STATUS = "orderApi/V4/findOrderDtoByOrderNo";

    String COMMODITY_ORDER_INFO = "payApi/V4/appPay";
    //String COMMODITY_ORDER_INFO = "http://pay.api.dongao.com/payment/appPay";//获取商品订单支付信息
    String PAY_SUCCESS_OPEN = "mobile/openServerByOrderId";


    String QUERYADD_MAIN_FRAGMENT = "answerApi/V4/myAnswerList";
    String QUERYADD_MAIN_FRAGMENT1 = "answerApi/V4/findEssenceQuestion";
    String QUERYADD_MAIN_FRAGMENT2 = "answerApi/V4/answerListBySujectId";
    String QUERYADD_MAIN_QUERY_WORD = "answerApi/V4/knowledgeSearch";
    String QUERYADD_BOOK_LIST = "answerApi/V4/getBookList";
    String QUERYADD_BOOK_KNOWLEDGE_LIST = "answerApi/V4/findKnowledgeList";
    String QUERYADD_BOOK_KNOWLEDGE_QUERY_LIST = "answerApi/V4/recommendQaByType";
    String QUERYADD_BOOK_JIANGYI_QUERY_LIST = "answerApi/V4/recommendQaByLectureId";
    String QUERYADD_BOOK_QUESTION_ADD = "questionAnwser/saveExamquestionAsk";
    String QUERYADD_BOOK_BOOK_ADD = "answerApi/V4/saveQuestionAnswer";
    String QUERYADD_BOOK_BOOK_ADD_UPDATE = "answerApi/V4/updateQuestionAnswer";
    String QUERYADD_BOOK_BOOK_ADD_DELETE = "answerApi/V4/delQuestionByQaId";
    String QUERYADD_BOOK_BOOK_ADD_OTHER = "answerApi/V4/queryAgainQuestion";
    String QUERYADD_QUESTION_COLLECT = "answerApi/V4/addCollectionQa";
    String QUERYADD_QUESTION_COLLECT_CANCEL = "answerApi/V4/delCollectionQa";
    String QUERYADD_QUESTION_FAVORIT = "answerApi/V4/doLike";
    String QUERYADD_QUESTION_DETAIL = "answerApi/V4/qaDetail";
    String QUERYADD_JUDGE_DATA = "answerApi/V4/lableSort";
    String QUERYADD_JUDGE_SUBMIT = "answerApi/V4/addLable";


    String INTEL_HOME = "intelligentExamApi/V4/examHome";
    String INTEL_MAIN_TYPE_DETAIL = "intelligentExamApi/V4/getExamListByUserId";
    String INTEL_QUERY_RECOMMEND = "studyApi/V4/relevantQaPageByNodeId";
    String INTEL_QUERY_QUESTION_RECOMMEND = "studyApi/V4/recommendQaByType";
    String INTEL_QUERY_DETAIL = "studyApi/V4/qaDetail";


    String ORDER_LIST = "orderApi/V4/orderList"; //查询订单分页列表

    String MALL_GETSHOPGOODSLIST = "shopApi/V4/getShopGoodsList"; //商城列表页面
    String MALL_CART_COUDAN = "shopApi/V4/activityGoodsList"; //凑单页面
    String MALL_ORDER_DETAIL_BY_ID = "orderApi/V4/getDetail"; // 前台查询订单明细
    String MALL_ORDER_LOGISTICS_BY_ID = "orderApi/V4/getOrderLogistics"; //查看物流
    String MALL_ORDER_FIND_USER_BY_JOBNUM = "orderApi/V4/findUserByJobNum"; //根据客服工号[邀请码]查询客服信息
    String MALL_ORDER_CANCELORDER = "orderApi/V4/cancelOrder"; //根据客服工号[邀请码]查询客服信息


    String MINE_GOODS_GET_CATEGORY = "shopApi/V4/getCategory"; // 商城分类
    String MINE_ADDRESS_CONSIGNEELIST = "consigneeApi/V4/consigneeList"; // 获取用户地址列表
    String MINE_ADDRESS_SAVEANDUPDATE = "consigneeApi/V4/saveAndUpdate"; // 保存更新地址
    String MINE_ADDRESS_UPDATE_DEFAULT = "consigneeApi/V4/updateDefault"; // 更新默认地址
    String MINE_ADDRESS_DELETE = "consigneeApi/V4/delete"; // 删除地址
    String MINE_INVOICE = "invoiceApi/V4/getInvoicePage"; // 我的发票
    String MINE_INVOICE_TITLE = "invoiceApi/V4/getUserInvoiceTitle"; // 我的发票title
    String MINE_INVOICE_DETAIL_BY_ORDER_ID = "invoiceApi/V4/orderInvoiceDetail"; // 根据订单获取发票
    String MINE_INVOICE_GET_BOOK_AND_COURSE_MONEY = "invoiceApi/V4/getBookAndCourseMoney"; // 根据订单获取发票详情
    String MINE_INVOICE_ADD_DIANZI = "invoiceApi/V4/addDZInvoice"; // 新增电子发票
    String MINE_INVOICE_ADD_PUTONG = "invoiceApi/V4/addInvoice"; // 新增纸质发票
    String MINE_INVOICE_ADD_TITLE = "invoiceApi/V4/saveOrUpdateTitle"; // 新增纸质发票

    String MINE_GETUSERCOUPON = "couponApi/V4/getUserCouponByStatus"; // 用户的优惠券
    String MINE_ACTIVITY_COUPON = "couponApi/V4/activityCoupon"; // 激活优惠券


    String BOOK_ACTIVE_BOOK_CARD = "couponApi/V4/couponBookActive";//图书卡激活
    String BOOK_ACTIVE_BOOK_CARD_LIST = "couponApi/V4/couponBookList";//图书卡激活明细列表
    String BOOK_ACTIVE_BOOK_CARD_SERVICES_INFO = "couponApi/V4/couponServicesInfo";//图书卡激活服务内容查询
    String BOOK_ACTIVE_TMALL_ORDER = "couponApi/V4/activityTmallOrder";//天猫订单激活
    String BOOK_ACTIVE_TMALL_ORDER_LIST = "couponApi/V4/activityTmallOrderList";//天猫订单激活明细列表

    String PERSONAL_INFORMATION = "userApi/V4/info";//个人信息

    String EXAM_GET_PAPER = "examinationApi/V4/getPaperView";//开始做题或重新做题
    String EXAM_GET_MY_WRONG_PAPER = "examinationApi/V4/getMyErrorList";//开始做题或重新做题
    String EXAM_DELET_WRONG_QUESTION = "examinationApi/V4/delErrorQues";//删除错题
    String EXAM_GET_QUES_IS_HAVE_NOTES = "notesApi/V4/getNoteByQuestions";//查询是否有笔记
    String EXAM_GET_PAPER_CONTINUE_FROM_RECORD = "examinationApi/V4/continueDoExam";//从做题记录继续做题
    String EXAM_GET_RECOMM_PAPER = "examinationApi/V4/recommendQues";//推荐做题
    String EXAM_GET_ANYS = "examinationApi/V4/getAnalyze";//查看全部解析
    String EXAM_GET_DETAIL = "examinationApi/V4/getQuestionDetail";//试题详情
    String EXAM_GET_REPORT = "examinationApi/V4/getStudentView";//答题报告
    String EXAM_ADD_FAVOR = "examinationApi/V4/addCollectQues";//添加收藏
    String EXAM_DELETE_FAVOR = "examinationApi/V4/delCollected";//删除收藏
    String EXAM_KNOWLEDGE_VIDEO = "courseApi/V4/kpLecture";//相关知识点视频播放的id
    String EXAM_MAIN = "examinationApi/V4/getPaperTypeList";//题库首页
    String EXAM_PREVIOUS_PAPER = "examinationApi/V4/getPaperList";//历年真题列表
    String EXAM_FEEDBACK_TYPEE = "examinationApi/V4/getTypeList";//试题返回试题类型
    String EXAM_FEEDBACK_POST = "examinationApi/V4/saveQuesFeedBack";//提交试题反馈
    String EXAM_POST_EXAM_PAPER = "examinationApi/V4/submitPaper";//提交试卷
    String EXAM_SAVE_EXAM_PAPER = "examinationApi/V4/saveRecord";//保存试卷
    String MINE_ERROR_QUESTION_STATISTIC = "examinationApi/V4/getErrorCount";//错题统计
    String EXAM_INTEL_START_EXAM = "studyApi/V4/doTitle";//智能系统 做题、做真题
    String EXAM_INTEL_RANK = "studyApi/V4/examRank";//答题报告排名
    String EXAM_INTEL_REVIEW_EXAM = "intelligentExamApi/V4/reviewQuestion";//做复习包的习题
    String EXMA_INTEL_REVIEW_CHANGE_STATUS = "intelligentExamApi/V4/changeQuestionStatus";
    String EXAM_POST_EXAM_PAPER_INTEL = "studyApi/V4/savePaper";// 智能系统 提交试卷
    String EXAM_SAVE_EXAM_PAPER_INTEL = "studyApi/V4/saveRecord";//智能系统 保存试卷
    String EXAM_GET_REPORT_INTEL = "studyApi/V4/recordView";//智能系统 答题报告

    String EXAM_SUBJECT_SUB_LIST = "userBaseApi/V4/getComplateExamSubsSubList";//学员考种科目列表
    String RECOMMENDED_GOODS = "homeApi/V4/getGoodListByExam";//推荐课程、图书列表
    String STUDENTS_EVALUATE_INFOS = "homeApi/V4/evaluateInfos";//首页免费试听中播放视频时学员评价

    String COUNT_AND_CHOICE_TYPES = "examinationApi/V4/getCountAndChoiceTypes";//科目下错题数量以及已错题型列表

    /**
     * 我的收藏
     */
    String COLLECT_ANSWER_LIST = "answerApi/V4/myCollectQuestion";//学员考季下收藏<答疑>列表
    String COLLECT_COURSE_LIST = "courseApi/V4/collect/list";//学员考季下收藏<课程>列表
    String COLLECT_EXERCISES_LIST = "examinationApi/V4/getCollectQuesList";//学员考季下收藏<题>列表

    String NOTES_EXERCISES_LIST = "notesApi/V4/getNotes";//我的笔记列表<试题>
    String NOTES_COURSE_LIST = "courseApi/V4/noteList";//我的笔记列表<课程>

    String LEARNING_RECORD_COURSE_LIST = "courseApi/V4/user/subListenRecordList";//学习记录<课程>
    String LEARNING_RECORD_EXERCISES_LIST = "examinationApi/V4/getRecordList";//学习记录<试题>

    String MESSAGE_LIST = "homeApi/V4/myMessageList";//我的消息列表

    String CAPTURE_QR_CODE = "http://qrcode.api.dongao.com/qr/newapi/decodeUrl";//扫码看课、做题
    String WELCOME_RES = "appApi/V4/getAppStartPage";//欢迎页

    String BOOKS_ERRATA_MENU_LIST = "homeApi/V4/mixtureList";//图书勘误筛选条件
    String BOOKS_ERRATA_LIST = "homeApi/V4/bookCorrect";//图书勘误列表

    String SUBMIT_FEEDBACK = "appApi/V4/addOpinionData";//提交意见反馈
    String VERSION_DESC = "appApi/V4/getVersionDesc";//获取版本信息

    String LECTURES_STATISCAL="userStatisticsApi/V4/getCourseStatisticsBySubjectId";//听课时长统计
    String QUESTION_STATISCAL="userStatisticsApi/V4/getQusStatisticsBySubjectId";//做题数量统计
    String KP_STATISCAL="studyApi/V4/noteStatistics";//知识点统计
    String STUDY_STATISCAL="studyApi/V4/getStatisticsBySubjectId";//学习统计

    String REVIEW_HANDOUT = "intelligentExamApi/V4/handout";//复习讲义
    String REVIEW_QUESTION_TYPE = "intelligentExamApi/V4/reviewQuestionType";//复习题题型
    String HAVE_REVIEWED = "intelligentExamApi/V4/changeQuestionStatus";//已复习

    String STUDY_RECORD_ENABLE_DATE = "intelligentExamApi/V4/calendar";//学习记录有效时间
    String STUDY_RECORD_DAY_RECORD = "intelligentExamApi/V4/records";//学习记录 每日记录
    String STUDY_RECORD_WEEK_RECORD = "intelligentExamApi/V4/recordsWeekReport";//周学习记录

    @FormUrlEncoded
    @POST(LOGIN)
    Flowable<BaseRes<UserBean>> login(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(LOGOUT)
    Flowable<BaseRes<String>> logout(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST(THIRD_PARTY_LOGIN)
    Flowable<BaseRes<UserBean>> thirdPartyLogin(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(REQUEST_VERIFICATION_CODE)
    Flowable<BaseRes<String>> requestVerificationCode(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(VERIFI_THE_CODE)
    Flowable<BaseRes<String>> verifyTheCode(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST(REGISTER)
    Flowable<BaseRes<UserBean>> register(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST(GET_BACK_PW_BY_EMAIL)
    Flowable<BaseRes<GetBackPwUserInfoBean>> reqeuestAccountInfoByEmail(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST(GET_BACK_PW_BY_PHONE)
    Flowable<BaseRes<GetBackPwUserInfoBean>> reqeuestAccountInfoByPhone(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST(GET_BACK_PW)
    Flowable<BaseRes<String>> getBackPw(@FieldMap HashMap<String, String> map);

    @GET(LOGIN)
    Flowable<BaseRes<UserBean>> login(@Query("username") String username, @Query("password") String password);

    @GET(MAIN_TYPE_DETAIL)
    Flowable<BaseRes<MainTypeBean>> getMainTypeInfo(@QueryMap Map<String, String> map);
   
    @GET(INTEL_MAIN_TYPE_DETAIL)
    Flowable<BaseRes<MainTypeBean>> getIntelMainTypeInfo(@QueryMap Map<String, String> map);

    @GET(MAIN_AD)
    Flowable<BaseRes<MainADBean>> getMainAD(@QueryMap Map<String, String> map);

    @GET(MAIN_TYPE_FRAGMENT_DETAIL)
    Flowable<BaseRes<MainFragmentBean>> getMainTypeFragment(@QueryMap Map<String, String> map);

    @GET(MAIN_SUBPAGE_GET_TAB_LIST)
    Flowable<BaseRes<TabListBean>> getTabList(@QueryMap Map<String, String> map);

    @GET(PLAY_DETAIL_LIST)
    Flowable<BaseRes<CourseDetail>> playDetail(@QueryMap Map<String, String> map);

    @GET(PLAY_CONTINUE)
    Flowable<BaseRes<String>> playContinue(@QueryMap Map<String, String> map);

    @GET(CLASS_PLAY_CONTINUE)
    Flowable<BaseRes<String>> classPlayContinue(@QueryMap Map<String, String> map);

    @GET(CLASS_HOME)
    Flowable<BaseRes<ClassHomeBean>> classHome(@QueryMap Map<String, String> map);

    //http://cloudclass.api.test.com/intelligentExamApi/V4/studyPlan
    //STUDY_PLAN
    @GET("http://cloudclass.api.test.com/intelligentExamApi/V4/studyPlan")
    Flowable<BaseRes<StudyPlanBean>> studyPlan(@QueryMap Map<String, String> map);

    @GET(COURSEWARE_COLLECT)
    Flowable<BaseRes<String>> collectCw(@QueryMap Map<String, String> map);

    @GET(IS_COMMENTED)
    Flowable<ResponseBody> isCommented(@QueryMap Map<String, String> map);

    @GET(DEL_COURSEWARE_COLLECT)
    Flowable<BaseRes<String>> delCollectCw(@QueryMap Map<String, String> map);

    @GET(IS_COURSEWARE_COLLECT)
    Flowable<BaseRes<String>> isCollectCw(@QueryMap Map<String, String> map);

    @GET(COURSE_LIST)
    Flowable<BaseRes<CourseListData>> courseList(@QueryMap Map<String, String> map);

    @Multipart
    @POST(POST_IMAGE_NOTE)
    Call<String> postImg(@Part MultipartBody.Part file,@QueryMap Map<String, String> map);

    @GET(GET_NOTE_DETAIL)
    Flowable<BaseRes<String>> getNoteDetail(@QueryMap Map<String, String> map);

    @GET(GET_NOTE_DETAIL_CLASS)
    Flowable<BaseRes<String>> getNoteClassDetail(@QueryMap Map<String, String> map);

    @GET(DEL_NOTE_CLASS)
    Flowable<BaseRes<String>> delteNotesClass(@QueryMap Map<String, String> map);

    @GET(DEL_NOTE)
    Flowable<BaseRes<String>> delteNotes(@QueryMap Map<String, String> map);

    @GET(POST_NOTE)
    Flowable<BaseRes<String>> postNotes(@QueryMap Map<String, String> map);

    @GET(POST_NOTE_CLASS)
    Flowable<BaseRes<String>> postNotesClass(@QueryMap Map<String, String> map);

    @GET(UPDATE_NOTE_CLASS)
    Flowable<BaseRes<String>> updaNoteClass(@QueryMap Map<String, String> map);

    @GET(UPDATE_NOTE)
    Flowable<BaseRes<String>> updaNote(@QueryMap Map<String, String> map);

    @GET(POST_COMMENT)
    Flowable<ResponseBody> postComment(@QueryMap Map<String, String> map);

    @GET(COURSE_SUBJECTLIST)
    Flowable<BaseRes<String>> subjectList(@QueryMap Map<String, String> map);

    @GET(COURSE_SUBJECTLIST_SMART)
    Flowable<BaseRes<String>> subjectSmartList(@QueryMap Map<String, String> map);

    @GET(PLAY_URL)
    Flowable<BaseRes<String>> getPlayUrl(@QueryMap Map<String, String> map);

    @GET(CHANGE_STATUS)
    Flowable<BaseRes<String>> changeStatus(@QueryMap Map<String, String> map);

    @GET(SMART_PLAY_URL)
    Flowable<BaseRes<String>> getSmartPlayUrl(@QueryMap Map<String, String> map);

    @GET(SMART_LECTURE_URL)
    Flowable<BaseRes<String>> getSmartLectureUrl(@QueryMap Map<String, String> map);

    @FormUrlEncoded
    @POST(POST_STUDYLOG)
    Flowable<BaseRes<String>> upLoadVideos(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(POST_STUDYLOG_SMART)
    Flowable<BaseRes<String>> upLoadVideosSmart(@FieldMap Map<String, String> map);

    @GET(DOWNLOAD_URL)
    Flowable<BaseRes<String>> getDownloadUrl(@QueryMap Map<String, String> map);

    @GET
    Flowable<ResponseBody> getM3U8(@Url String url);

    @FormUrlEncoded
    @POST("http://120.55.160.19/yd_errlog_multi.php")
    Call<String> postOperates(@FieldMap Map<String, String> options);

//    @GET(EXAM_PAPER)
//    Flowable<BaseRes<String>> getExamPaper(@QueryMap Map<String, String> map);

    /*********************************** 做题模块 ↓↓ ***********************/

    @GET(EXAM_ADD_FAVOR)
    Flowable<BaseRes<String>> examPaperAddFvor(@QueryMap Map<String, String> map);

    @GET(EXAM_DELETE_FAVOR)
    Flowable<BaseRes<String>> examPaperDeleteFvor(@QueryMap Map<String, String> map);

    @GET(EXAM_DELET_WRONG_QUESTION)
    Flowable<BaseRes<String>> deleteWrongQues(@QueryMap Map<String, String> map);

    @GET(EXAM_GET_QUES_IS_HAVE_NOTES)
    Flowable<BaseRes<NoteDetail>> getQuesIsHaveNotes(@QueryMap Map<String, String> map);

    @POST(EXAM_FEEDBACK_POST)
    @FormUrlEncoded
    Flowable<BaseRes<String>> postExamFeedback(@FieldMap Map<String, String> map);

//    @GET(EXAM_POST_EXAM_PAPER)
    @FormUrlEncoded
    @POST(EXAM_POST_EXAM_PAPER)
    Flowable<BaseRes<String>> postExamPaper(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(EXAM_POST_EXAM_PAPER_INTEL)
    Flowable<BaseRes<String>> postExamPaperIntel(@FieldMap Map<String, String> map);

//    @GET(EXAM_SAVE_EXAM_PAPER)
    @FormUrlEncoded
    @POST(EXAM_SAVE_EXAM_PAPER)
    Flowable<BaseRes<String>> saveExamPaper(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(EXAM_SAVE_EXAM_PAPER_INTEL)
    Flowable<BaseRes<String>> saveExamPaperIntel(@FieldMap Map<String, String> map);

    /*********************************** 做题模块 ↑↑ ***********************/
    @GET(GOODS_DETAIL)
    Flowable<BaseRes<GoodsDetailBean>> getGoodsDetail(@QueryMap Map<String, String> map);

    @GET(GOODS_RECOMMEND)
    Flowable<BaseRes<List<GoodsRecomondBean>>> getGoodsRecommend(@QueryMap Map<String, String> map);

    @GET(VENDER_GOODS_RECOMMEND)
    Flowable<BaseRes<List<GoodsRecomondBean>>> getRecommend(@QueryMap Map<String, String> map);


    @POST(SHOP_CART_ADD_GOODS)
    @FormUrlEncoded
    Flowable<BaseRes<String>> getShopCartAddGoods(@FieldMap Map<String, String> map);


    @POST(SHOP_CART_ADD_BATCHS)
    @FormUrlEncoded
    Flowable<BaseRes<String>> getShopCartBatchs(@FieldMap Map<String, String> map);

    @GET(SHOP_CART_DETAIL)
    Flowable<BaseRes<ShopCartBean>> getShopCartDetail(@QueryMap Map<String, String> map);

    @POST(SHOP_CART_ALL_SELECT)
    @FormUrlEncoded
    Flowable<BaseRes<ShopCartBean>> getShopCartAllSelect(@FieldMap Map<String, String> map);

    @POST(SHOP_CART_ALL_SELECT_CANCEL)
    @FormUrlEncoded
    Flowable<BaseRes<ShopCartBean>> getShopCartAllSelectCancel(@FieldMap Map<String, String> map);

    @POST(SHOP_CART_SELECT_VENDER)
    @FormUrlEncoded
    Flowable<BaseRes<ShopCartBean>> getShopCartSelectVender(@FieldMap Map<String, String> map);

    @POST(SHOP_CART_SELECT_VENDER_CANCEL)
    @FormUrlEncoded
    Flowable<BaseRes<ShopCartBean>> getShopCartSelectVenderCancel(@FieldMap Map<String, String> map);

    @POST(SHOP_CART_SELECT_PROMO)
    @FormUrlEncoded
    Flowable<BaseRes<ShopCartBean>> getShopCartSelectPromo(@FieldMap Map<String, String> map);

    @POST(SHOP_CART_SELECT_NUM)
    @FormUrlEncoded
    Flowable<BaseRes<ShopCartBean>> getShopCartSelectNum(@FieldMap Map<String, String> map);

    @POST(SHOP_CART_SELECT_PRODUCT)
    @FormUrlEncoded
    Flowable<BaseRes<ShopCartBean>> getShopCartSelectProduct(@FieldMap Map<String, String> map);

    @POST(SHOP_CART_SELECT_PRODUCT_CANCEL)
    @FormUrlEncoded
    Flowable<BaseRes<ShopCartBean>> getShopCartSelectProductCancel(@FieldMap Map<String, String> map);

    @POST(SHOP_CART_SELECT_DELETE)
    @FormUrlEncoded
    Flowable<BaseRes<ShopCartBean>> getShopCartSelectDelete(@FieldMap Map<String, String> map);

    @POST(SHOP_CART_SELECT_All_DELETE)
    @FormUrlEncoded
    Flowable<BaseRes<ShopCartBean>> getShopCartSelectAllDelete(@FieldMap Map<String, String> map);

    @POST(SHOP_CART_SELECT_GIFT)
        // @FormUrlEncoded
    Flowable<BaseRes<ShopCartBean>> getShopCartSelectGift(@QueryMap Map<String, String> map);

    @POST(SHOP_CART_CLEAR_GIFT)
        // @FormUrlEncoded
    Flowable<BaseRes<ShopCartBean>> getShopCartClearGift(@QueryMap Map<String, String> map);

    @POST(SHOP_CART_SAVE_ORDER)
        // @FormUrlEncoded
    Flowable<BaseRes<OrderSaveBean>> getShopCartSaveOrder(@QueryMap Map<String, String> map);

    @POST(SHOP_CART_COUNT_MONEY)
        // @FormUrlEncoded
    Flowable<BaseRes<OrderPriceBean>> getShopCartCountMoney(@QueryMap Map<String, String> map);


    @GET(SHOP_CART_NUM)
    Flowable<BaseRes<String>> getShopCartNum(@QueryMap Map<String, String> map);


    @POST(SHOP_CART_USER_ACCOUNT)
    Flowable<BaseRes<OrderUserAccountBean>> getShopCartUserAccount(@QueryMap Map<String, String> map);

    @POST(ORDER_PAY_STATUS)
    Flowable<BaseRes<OrderVOBean>> getOrderDetail(@QueryMap Map<String, String> map);


    //支付获取订单信息
    @POST(COMMODITY_ORDER_INFO)
    Flowable<BaseRes<PayInfoBean>> getCommodityOrderInfo(@QueryMap Map<String, String> options);

    //支付成功，开课
    @GET(PAY_SUCCESS_OPEN)
    Flowable<String> paySuccessOpen(@QueryMap Map<String, String> options);

    @GET(MINE_ADDRESS_CONSIGNEELIST)
    Flowable<BaseRes<List<AddressBean>>> getConsigneeList(@QueryMap Map<String, String> map);

    @FormUrlEncoded
    @POST(MINE_ADDRESS_SAVEANDUPDATE)
    Flowable<BaseRes<AddressBean>> getConsigneeSaveAndUpdate(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(MINE_ADDRESS_UPDATE_DEFAULT)
    Flowable<BaseRes<String>> updateConsigneeDefault(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(MINE_ADDRESS_DELETE)
    Flowable<BaseRes<Object>> deleteConsignee(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(MINE_INVOICE)
    Flowable<BaseRes<MyInvoiceResponseBean>> getInvoicePage(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(MINE_INVOICE_TITLE)
    Flowable<BaseRes<List<InvoiceTitleBean>>> getInvoiceTitle(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(MINE_INVOICE_DETAIL_BY_ORDER_ID)
    Flowable<BaseRes<InvoiceBean>> getInvoiceDetailByOrderId(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(MINE_INVOICE_GET_BOOK_AND_COURSE_MONEY)
    Flowable<BaseRes<InvoiceBookAndCourseMoneyBean>> getInvoiceBookAndCourse_Money(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(MINE_INVOICE_ADD_DIANZI)
    Flowable<BaseRes<InvoiceBean>> addDianZiInvoice(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(MINE_INVOICE_ADD_PUTONG)
    Flowable<BaseRes<InvoiceBean>> addPuTongInvoice(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(MINE_INVOICE_ADD_TITLE)
    Flowable<BaseRes<InvoiceTitleBean>> addInVoiceTitle(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(MINE_GETUSERCOUPON)
    Flowable<BaseRes<MyCouponResponseBean>> getUserCoupon(@FieldMap Map<String, String> map);

    @GET(MALL_GETSHOPGOODSLIST)
    Flowable<BaseRes<GoodsListItemBean>> getShopGoodsList(@QueryMap Map<String, String> map);

    @FormUrlEncoded
    @POST(ORDER_LIST)
    Flowable<BaseRes<MyOrderResponseBean>> getOrderList(@FieldMap Map<String, String> map);

    @GET(MINE_GOODS_GET_CATEGORY)
    Flowable<BaseRes<List<Category>>> getCategory(@QueryMap Map<String, String> map);

    @POST(MALL_ORDER_DETAIL_BY_ID)
    Flowable<BaseRes<OrderDetailResponseBean>> getOrderDetailByOrderId(@QueryMap Map<String, String> map);

    @GET(MALL_CART_COUDAN)
    Flowable<BaseRes<CouDanBean>> getCouDanData(@QueryMap Map<String, String> map);

    @POST(BOOK_ACTIVE_BOOK_CARD)
    Flowable<BaseRes<String>> bookCardActivate(@QueryMap Map<String, String> options);

    @POST(BOOK_ACTIVE_BOOK_CARD_LIST)
    Flowable<BaseRes<BookCardActivateListBean>> bookCardActivateList(@QueryMap Map<String, String> options);

    @POST(BOOK_ACTIVE_BOOK_CARD_SERVICES_INFO)
    Flowable<BaseRes<ActivateServicesListBean>> bookCardActivateServicesInfo(@QueryMap Map<String, String> options);

    @POST(BOOK_ACTIVE_TMALL_ORDER)
    Flowable<BaseRes<String>> tmallOrderActivate(@QueryMap Map<String, String> options);

    @POST(BOOK_ACTIVE_TMALL_ORDER_LIST)
    Flowable<BaseRes<TmallOrderActivateListBean>> tmallOrderActivateList(@QueryMap Map<String, String> options);

    @POST(PERSONAL_INFORMATION)
    Flowable<BaseRes<PersonalInformationBean>> getPersonalInfo(@QueryMap Map<String, String> map);


    @POST("http://cloudclass.api.dev3.com/consigneeApi/V4/findProvice")
    Flowable<BaseRes<List<Provice>>> getProvice(@QueryMap Map<String, String> map);


    @POST("http://cloudclass.api.dev3.com/consigneeApi/V4/findCity")
    Flowable<BaseRes<List<City>>> getCity(@QueryMap Map<String, String> map);


    @POST("http://cloudclass.api.dev3.com/consigneeApi/V4/findArea")
    Flowable<BaseRes<List<Area>>> getArea(@QueryMap Map<String, String> map);


    @FormUrlEncoded
    @POST(SECUR_LEVEL)
    Flowable<BaseRes<AccountSecurLevelBean>> requestSecurLevel(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST(MODIFY_EMAIL)
    Flowable<BaseRes<String>> modifyEmail(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST(MODIFY_PHONE)
    Flowable<BaseRes<String>> modifyPhone(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST(MODIFY_PSW)
    Flowable<BaseRes<String>> modifyPsw(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST(MINE_ACTIVITY_COUPON)
    Flowable<BaseRes<String>> activityCoupon(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST(MY_ACCOUNT_DETAIL)
    Flowable<BaseRes<MyAccountDetailBean>> requestAccountDetail(@FieldMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST(MALL_ORDER_LOGISTICS_BY_ID)
    Flowable<BaseRes<List<LogisticsBean>>> getOrderLogistics(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(MALL_ORDER_FIND_USER_BY_JOBNUM)
    Flowable<BaseRes<JobNumBean>> findUserByJobnum(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(MALL_ORDER_CANCELORDER)
    Flowable<BaseRes<String>> orderCancel(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(OPEN_CLASS_LIST)
    Flowable<BaseRes<List<OpenClassListBean>>> requestOpenClassList(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(OPEN_COURSE)
    Flowable<BaseRes<String>> openCourse(@FieldMap HashMap<String, String> map);

    @GET
    Flowable<SubpageListBean> getSubpageData(@Url String url);

    @FormUrlEncoded
    @POST(MY_ACCOUNT)
    Flowable<BaseRes<MyAccountBean>> requestMyAccount(@FieldMap HashMap<String, String> map);

    @GET(RECOMMENDED_GOODS)
    Flowable<BaseRes<RecommendedGoodListBean>> getRecommendedGoodsList(@QueryMap HashMap<String, String> map);

    @GET(STUDENTS_EVALUATE_INFOS)
    Flowable<BaseRes<StudentsEvaluateInfo>> getStudentsEvaluateInfo(@QueryMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST(EXAM_SUBJECT_SUB_LIST)
    Flowable<BaseRes<List<ExamMenu>>> getExamSubjectSubList(@FieldMap HashMap<String, String> map);

    @POST(COUNT_AND_CHOICE_TYPES)
    Flowable<BaseRes<ErrorsCountAndTypes>> getCountAndChoiceTypes(@QueryMap HashMap<String, String> map);

    @POST(COLLECT_ANSWER_LIST)
    Flowable<BaseRes<CollectAnswer>> getCollectAnswerList(@QueryMap HashMap<String, String> map);

    @POST(COLLECT_COURSE_LIST)
    Flowable<BaseRes<List<CollectCourse>>> getCollectCourseList(@QueryMap HashMap<String, String> map);

    @POST(COLLECT_EXERCISES_LIST)
    Flowable<BaseRes<CollectExercises>> getCollectExercisesList(@QueryMap HashMap<String, String> map);

    @POST(NOTES_EXERCISES_LIST)
    Flowable<BaseRes<NotesExercisesList>> getNotesExercisesList(@QueryMap HashMap<String, String> map);

    @POST(NOTES_COURSE_LIST)
    Flowable<BaseRes<NotesCourseList>> getNotesCourseList(@QueryMap HashMap<String, String> map);

    @POST(MESSAGE_LIST)
    Flowable<BaseRes<MessageListBean>> getMessageList(@QueryMap HashMap<String, String> map);

    @GET(CAPTURE_QR_CODE)
    Flowable<BaseRes<CaptureResultBean>> captureQrCode(@QueryMap HashMap<String, String> map);

    @POST(LEARNING_RECORD_COURSE_LIST)
    Flowable<BaseRes<LearningRecordCourseListBean>> getLearningRecordCourseList(@QueryMap HashMap<String, String> map);

    @POST(LEARNING_RECORD_EXERCISES_LIST)
    Flowable<BaseRes<LearningRecordExercisesListBean>> getLearningRecordExercisesList(@QueryMap HashMap<String, String> map);

    @GET(WELCOME_RES)
    Flowable<BaseRes<WelcomeRes>> requestWelcomeData(@QueryMap HashMap<String, String> map);

    @GET
    @Streaming
    Flowable<Response<ResponseBody>> downloadWelcomeImageOrVideo(@Url String url);

    @GET(BOOKS_ERRATA_MENU_LIST)
    Flowable<BaseRes<BooksErrataMenuListBean>> getBooksErrataMenuList(@QueryMap HashMap<String, String> map);

    @GET(BOOKS_ERRATA_LIST)
    Flowable<BaseRes<BooksErrataListBean>> getBooksErrataList(@QueryMap HashMap<String, String> map);

    @POST(SUBMIT_FEEDBACK)
    Flowable<BaseRes<String>> submitFeedback(@QueryMap HashMap<String, String> map);

    @POST(VERSION_DESC)
    Flowable<BaseRes<VersionInfo>> getVersionDesc(@QueryMap HashMap<String, String> map);

    @GET(LECTURES_STATISCAL)
    Flowable<BaseRes<CourseStatiscalDetailBean>> requestLecturesStatiscal(@QueryMap HashMap<String, String> map);

    @GET(QUESTION_STATISCAL)
    Flowable<BaseRes<CourseStatiscalDetailBean>> requestQuestionStatiscal(@QueryMap HashMap<String, String> map);

    @GET(INTEL_HOME)
    Flowable<BaseRes<IntelMainBean>> intelHome(@QueryMap Map<String, String> map);

    @GET(KP_STATISCAL)
    Flowable<BaseRes<KnowledgePointsStatiscialBean>> getKPStatiscialData(@QueryMap HashMap<String, String> map);

    @GET(STUDY_STATISCAL)
    Flowable<BaseRes<CourseStatiscalDetailBean.DateBean>> getStudyStatiscialData(@QueryMap HashMap<String, String> map);

    @POST(REVIEW_HANDOUT)
    Flowable<String> reviewHandout(@QueryMap HashMap<String, String> map);

    @POST(REVIEW_QUESTION_TYPE)
    Flowable<BaseRes<ChoiceTypesBean>> reviewQusetionType(@QueryMap HashMap<String, String> map);

    @FormUrlEncoded
    @POST(HAVE_REVIEWED)
    Flowable<BaseRes<String>> haveReviewed(@FieldMap HashMap<String, String> map);

    @GET(STUDY_RECORD_ENABLE_DATE)
    Flowable<BaseRes<EnableDate>> studyRecordEnableDate(@QueryMap Map<String, String> map);

    @GET(STUDY_RECORD_DAY_RECORD)
    Flowable<BaseRes<DayRecord>> studyRecordDayRecord(@QueryMap Map<String, String> map);

    @GET(STUDY_RECORD_WEEK_RECORD)
    Flowable<BaseRes<WeekRecordObj>> studyRecordWeekRecord(@QueryMap Map<String, String> map);

    @GET(EXMA_INTEL_REVIEW_CHANGE_STATUS)
    Flowable<BaseRes<String>> intelReviewExamChangeStatus(@QueryMap Map<String, String> map);
}
