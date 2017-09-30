package com.me.fanyin.zbme.views.exam.remote;


import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by xunice on 16/3/28.
 */
public interface ApiService {

//    public static final String POST_HOST = "http://192.168.0.174:8080/app/api/v1/";
//    public static final String POST_HOST = "http://data.dongao.com/app/api/v1/";
    public static final String POST_HOST = "http://kq.api.dongao.com/app/api/v3/";
    public static final String API_HOST_VERSION = "http://kq.api.dongao.com/app/api/v3/";
//    public static final String POST_HOST = "http://kq.api.test.com:8081/app/api/v3/";
//    public static final String API_HOST_VERSION = "http://kq.api.test.com:8081/app/api/v3/";
    public static final String API_HOST_QRCODE = "http://qrcode.api.dongao.com/qr/api/decodeUrl";
    //登录
    public final static String LOGIN = "user/appLogin";

    //忘记密码
    public final static String FORGET_PSW = "user/updateUserPassWord";
    //忘记密码发送验证码
    public final static String FORGET_PSW_GET_CODE = "user/sendPhoneCodeUpdatePassWord";

    //我的
    public final static String MY_USER_INFO = "user/userBaseInfo";

    public final static String SUBJECT_LIST = "course/subjectInfoByUserId";

    public final static String COURSE_LIST = "getUserSubjects";

    public final static String COURSE_DETAIL_LIST = "getUserCourses.html";

    public final static String COURSE_CENTER_LIST = "shopping/exam/getExamList";

    //注册
    public final static String REGISTER = "user/registerAppUser";

    public final static String COURSE_SELECT_LIST = "shopping/goods/getGoodsList";

    public final static String GOODS_SELECT_DISCOUNT = "shopping/getCartInfo";
    
    public final static String COMMODITY_ORDER_INFO = "shopping/goods/getGoodsList";//获取商品订单支付信息


    // 获取验证码
    public final static String MOBILEVALID = "user/sendPhoneCode";

    //获取订单列表
    public final static String MY_ORDER_LIST = "shopping/order/getUserOrderInfo";

    //获取订单详情
    public final static String MY_ORDER_DETAIL = "shopping/order/getOrderDetail";
    /* wyc  */
    public final static String STUDY_BAR_FRAGMENT ="mobile/studyBarList";
    public final static String STUDY_BAR_FRAGMENT_PRIVATE ="mobile/noticeListByClassId";
    public final static String STUDY_BAR_FRAGMENT_QUESTIONSOLUTION ="qas/list";
    public final static String EXAM_ORIGINAL_QUESTION = "examination/getExamquestionNewById";
    public final static String STUDY_BAR_MY_MESSAGE ="mobile/getBroadcasts";

//    public final static String EXAM_PAPER = "examinations/examination_paper";
//    public final static String EXAM_PAPER_LIST = "examinations/examination_paper_list";
//    public final static String UPLOAD_EXAM_PAPER = "examinations/examination_submit";
    public final static String EXAM_PAPER_LIST = "examination/examinationPaperList";
    public final static String EXAM_PAPER = "examination/getNewExaminationPaperById";
    public final static String UPLOAD_EXAM_PAPER = "examination/saveExaminationPaper";


    public final static String EXAM_PRACTICE_LIST = "http://jxjyapi.dongao.com/v1/courseware/listCourseware?app=com.dongao.app.dongaoacc&appName=da-jxjy-app&currentYear=2016&deviceType=1&mobileAccessToken=8e27051ca5c2478cb3a6350106716147&uniqueId=0A00270000000000&userID=cc3d8e9809864e94952ca2f23aa9f62f&version=1.0.0&sign=7ff7b2c46088f3ca68dd738d040895dd";
    //获取域名
    public final static String DOMAIN_NAME_LIST = "mobile/getDomainList";
    //精华答疑
    public static final String RECOMMEND_QUESTION = "mobile/essenceQas";

    //我的答疑http://examapi.dongao.com/V1_1/qa/myqa.html
    public static final String MY_QUESTION_LIST = "mobile/myQas";

    //是否能提问
    public static final String IS_CAN_ASK = "mobile/isAskForExaminationQuestion";

    //追问我的答疑
    public static  final String CONTINUE_ASK_QUESTION = "mobile/queryAgainQas";

    //新增我的答疑(教材)
    public static final String ADD_QUESTION = "mobile/saveBookAsk";

    //新增我的答疑（非教材）
    public static final String ADD_QUESTION_NORMAL = "mobile/saveExamquestionAsk";

    //修改我的答疑
    public static final String MODIFY_QUESTION = "mobile/modifyMyQas";

    //获取科目下的可选章节列表（新增答疑页面选择的数据源）
    public static final String QUESTION_SUBJECT_SECTION_LIST = "mobile/getSectionsBySubjectId";

    //提交图片
    public static final String POST_IMG = "mobile/uploadImage";//http://172.16.208.144/app/api/v1/
//    public final static String COMMODITY_ORDER_INFO_WX = "mobile/prepareWXPayParam";//获取商品订单支付信息
    public final static String COMMODITY_ORDER_INFO_WX = "https://pay.api.dongao.com/payment/appPay";//获取商品订单支付信息

    public final static String PAY_SUCCESS_OPEN = "mobile/openServerByOrderId";//获取商品订单支付信息

    public final static String COMMODITY_ORDER_INFO_ZFB = "mobile/prepareAlipayParam";//获取商品订单支付信息

    //图书激活
    public static final String BOOK_ACTIVE_CARD = "questionAnwser/activityByBookCode";

    //天猫激活
    public static final String BOOK_ACTIVE_TMALL = "http://apps.api.dongao.com/app/tmall/verify.html";

    //图书激活
    public static final String BOOK_ACTIVE_CARD_LIST = "questionAnwser/getCouponBookByUserId";

    //验证deviceToken
    public static  final String CHECK_DEVICETOKEN = "checkAccessToken.html";


    //首页数据
    //public static final String HOME_LIST = "getHomeList";

    //每日一练科目定制科目列表
    public static final String DAY_TEST_SUBJECT_LSIT = "examinations/custom_subject_list";

    //每日一练题目拉取
    public static  final String DAY_TEST_QUESTION = "examinations/daily_exercise";

    //上传友盟的devicetoken
    public static  final String COMMIT_DEVICETOKEN = "user/loginOut";

    //获取试题列表
    public static  final String GET_PAPER_LIST = "course/examinationTypeList";

    //获取课程列表
    public static  final String GET_COURSEPLAY_LIST = "course/coursesBySubjectId";

    //获取扫一扫类型
    public static  final String GET_TYPE = "mobile/getQRCodeMetaInfo";

    //获取扫一扫类型1的数据
    public static  final String GETDATA_TYPE1= "mobile/getCourseByQrCodeId";

    //获取扫一扫类型2的数据
    public static  final String GETDATA_TYPE2 = "mobile/getQRCodeMetaInfo";

    //意见反馈
    public static  final String FEED_BACK = "mobile/feedBackForUser";

    //上传地理位置
    public static  final String UPLOAD_POSITION = "position/saveUserPosition";

    //课程详情
    public static final String PLAY_DETAIL = "course/getCourseWaresByCourseId";

    public static final String VERSION = "mobile/versionUpgrad";

    //检查用户绑定
    public static  final String CHECK_MACHINE = "mobile/checkMachineSign";

    //上传听课记录
    public static  final String UPLOAD_VIDEO = "courseware/uploadCourseWareLessionLog";

    //上传运维记录
    public static  final String UPLOAD_OPERA = "examinations/sync_records";

    //首页
    public static  final String HOME_LIST = "pushmsg/getUserPushmsgList";

    //首页
    public static  final String HOME_LIST_BY_ID = "pushmsg/getUserPushmsgListById";
    public static  final String HOME_LIST_BY_ID_NEW = "pushmsg/getPushmsgList";

    //选课
  // public static  final String SELECT_COURSE_URL = "http://p.m.test.com/appInfo/toApp.html";
  // public static  final String ORDER_URL = "http://p.m.test.com/appInfo/myorder.html?qtype=all";
//    public static  final String ORDER_URL = "http://p.m.test.com/appInfo/to_order.html";
//    public static  final String ORDER_URL = "http://172.16.208.156/appInfo/myorder.html?qtype=all";
    //192.168.0.220:8081 p.m.dongao.com 172.16.200.16 http://192.168.0.220:8081/appInfo/toApp.html
    //http://192.168.0.220:8081/uc/myorder.html
    public static  final String SELECT_COURSE_URL = "http://mweb.api.dongao.com/appInfo/toApp.html";//http://p.m.test.com/appInfo/toApp.html
  // public static  final String SELECT_COURSE_URL = "http://172.16.208.80/appInfo/toApp.html";
  // public static  final String ORDER_URL = "http://172.16.208.80/appInfo/myorder.html?qtype=all";
//    public static  final String SELECT_COURSE_URL = "http://p.m.test.com/zsfa/to_swx.html";
//    public static  final String SELECT_COURSE_URL = "http://172.16.208.156/appInfo/toApp.html";
//     public static  final String ORDER_URL = "http://192.168.0.220:8081/uc/myorder.html";
  public static  final String ORDER_URL = "http://mweb.api.dongao.com/uc/myorder.html";

    public static  final String VERSION_URL = API_HOST_VERSION + VERSION;

    //获取播放\下载地址
    public static final String GET_COURSE_PLAYINFO = "course/getPlayInfo";


    /**
     * 答疑部分接口
     */
    //获取图书列表
    public static final String QUESTION_BOOK_LIST = "questionAnwser/getBookBySId";
    //获取知识点列表
    public static final String QUESTION_KNOWLEADEPOINT_LIST = "questionAnwser/getKnowledgeAndQuestion";
    //推荐答疑
    public static final String QUESTION_RECOMM_LIST = "questionAnwser/getRecommendQas";
    //我的答疑
    public static final String QUESTION_MY_LIST = "questionAnwser/getMyQas";
    //答疑详情
    public static final String QUESTION_DETAIL = "questionAnwser/getQasById";
    //追问
    public static final String QUESTION_ZHUI = "questionAnwser/queryAgainQas";
    //修改
    public static final String QUESIOTN_MODIFY = "questionAnwser/modifyQasById";
    //新增答疑
    public static final String QUESTION_ADD_BOOK = "questionAnwser/saveBookAsk";
    //    public static final String QUESTION_ADD_BOOK = "http://172.16.198.213:8080/app/api/v3/questionAnwser/saveBookAsk";
    //删除答疑无追问
    public static final String QUESTIN_DELETE_NONE = "questionAnwser/delQuestionAnswer";
    //删除答疑有追问
    public static final String QUESTIN_DELETE = "questionAnwser/delQaInfo";
    //删除答疑新增接口 3月-6号
    public static final String QUESTIN_DELETE_NEW = "questionAnwser/delQaInfoNew";
    //试题答疑
    public static final String EXAM_RECOMM_QUESTION = "questionAnwser/getRecommendQas";
    //试题提问
    public  static final String QUESTION_ADD_QUESTION = "questionAnwser/saveExamquestionAsk";

    //获取课堂答疑数字
    public static  final String COURSE_ANSWER_NUM = "questionAnwser/getQasCount";

    //获取播放地址
    public static  final String GET_PLAYURL = "course/getPlayInfo";

    @Headers("Cache-Control: public, max-age=3600") //设置缓存
    @GET(COURSE_LIST)
	Call<String> courseList(@QueryMap Map<String, String> options);

    @GET(PLAY_DETAIL)
	Call<String> playDetail(@QueryMap Map<String, String> options);

    @GET(COURSE_DETAIL_LIST)
	Call<String> courseDetailList(@QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST(LOGIN)
	Call<String> login(@FieldMap Map<String, String> options);

    @GET(COURSE_CENTER_LIST)
	Call<String> getExamList(@QueryMap Map<String, String> options);

    @GET(COURSE_SELECT_LIST)
	Call<String> getGoodsList(@QueryMap Map<String, String> options);

    @GET(MY_USER_INFO)
	Call<String> myUserInfo(@QueryMap Map<String, String> options);

    @Headers("Cache-Control: public, max-age=3600") //设置缓存
    @GET(GOODS_SELECT_DISCOUNT)
	Call<String> getGoodsDiscount(@QueryMap Map<String, String> options);


    @FormUrlEncoded
    @POST(COMMODITY_ORDER_INFO)
	Call<String> getCommodityOrderInfo(@FieldMap Map<String, String> options);

    //微信支付获取订单信息
    @GET(COMMODITY_ORDER_INFO_WX)
	Call<String> getCommodityOrderInfoByWX(@QueryMap Map<String, String> options);

    //支付成功，开课
    @GET(PAY_SUCCESS_OPEN)
	Call<String> paySuccessOpen(@QueryMap Map<String, String> options);

    //支付宝支付获取订单信息
    @GET(COMMODITY_ORDER_INFO_ZFB)
	Call<String> getCommodityOrderInfoByZFB(@QueryMap Map<String, String> options);

    @GET(REGISTER)
	Call<String> register(@QueryMap Map<String, String> options);

    @GET(MOBILEVALID)
	Call<String> mobilevalid(@QueryMap Map<String, String> options);

    @Headers("Cache-Control: public, max-age=3600") //设置缓存
    @GET(MY_ORDER_LIST)
	Call<String> orderList(@QueryMap Map<String, String> options);

    @Headers("Cache-Control: public, max-age=3600") //设置缓存
    @GET(MY_ORDER_DETAIL)
	Call<String> orderDetail(@QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST(RECOMMEND_QUESTION)
	Call<String> recommQuestion(@FieldMap Map<String, String> options);

    @FormUrlEncoded
    @POST(CONTINUE_ASK_QUESTION)
	Call<String> continueAskQuestion(@FieldMap Map<String, String> options);

    @FormUrlEncoded
    @POST(QUESTION_SUBJECT_SECTION_LIST)
	Call<String> questionSubjectSection(@FieldMap Map<String, String> options);

    @GET(IS_CAN_ASK)
	Call<String> isCanAsk(@QueryMap Map<String, String> options);

    @GET(FORGET_PSW)
	Call<String> forgetPsw(@QueryMap Map<String, String> options);

    @GET(FORGET_PSW_GET_CODE)
	Call<String> forgetPswGetCode(@QueryMap Map<String, String> options);

    @GET(MY_QUESTION_LIST)
	Call<String> myQuestion(@QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST(ADD_QUESTION)
	Call<String> addQuestion(@FieldMap Map<String, String> options);

    @FormUrlEncoded
    @POST(MODIFY_QUESTION)
	Call<String> modifyQuestion(@FieldMap Map<String, String> options);

    @FormUrlEncoded
    @POST(ADD_QUESTION_NORMAL)
	Call<String> addQuestionNormal(@FieldMap Map<String, String> options);

    @FormUrlEncoded
    @POST(POST_IMG)
	Call<String> postImg(@FieldMap Map<String, Object> options);

    @Multipart
    @POST(POST_IMG)
	Call<String> postImg(@Part("file") RequestBody photo);

    /**
     * wyc 获取试题
     */
    @GET(EXAM_PAPER)
	Call<String> getExamPaper(@QueryMap Map<String, String> options);

    /**
     * wyc 获取原题
     */
    @GET(EXAM_ORIGINAL_QUESTION)
	Call<String> getOriginalQuestion(@QueryMap Map<String, String> options);

    /**
     * wyc 获取试卷列表
     */
    @GET(EXAM_PAPER_LIST)
	Call<String> getExamPaperList(@QueryMap Map<String, String> options);

    /**
     * wyc 获取域名集合
     */
    @GET(DOMAIN_NAME_LIST)
	Call<String> getDomainNameList(@QueryMap Map<String, String> options);

    /**
     * 上传做完的试卷
     * @param options
     * @return
     */
    @POST(UPLOAD_EXAM_PAPER)
	Call<String> postUploadExamPaper(@QueryMap Map<String, String> options);
    
    @GET(EXAM_PRACTICE_LIST)
	Call<String> getExamPracticeList(@QueryMap Map<String, String> options);

    @GET(VERSION)
	Call<String> version(@QueryMap Map<String, String> options);

    @GET(EXAM_PRACTICE_LIST)
	Call<String> getExamListData(@QueryMap Map<String, String> options);

    @GET(SUBJECT_LIST)
	Call<String> subjectList(@QueryMap Map<String, String> options);

    @GET(BOOK_ACTIVE_CARD)
	Call<String> bookActivate(@QueryMap Map<String, String> options);

    @GET(BOOK_ACTIVE_TMALL)
	Call<String> tmallActivate(@QueryMap Map<String, String> options);

    @GET(BOOK_ACTIVE_CARD_LIST)
	Call<String> bookActivateList(@QueryMap Map<String, String> options);


    @FormUrlEncoded
    @POST(CHECK_DEVICETOKEN)
	Call<String> checkToken(@FieldMap Map<String, String> options);

   // @GET("http://172.16.208.200/gaze/api/getUserPushmsgList")
//    "http://192.168.0.176:8080/app/api/v1/pushmsg/getUserPushmsgListById"
    @GET(POST_HOST+HOME_LIST_BY_ID)
	Call<String> homeList(@QueryMap Map<String, String> options);

    @GET(POST_HOST+HOME_LIST_BY_ID_NEW)//最新首页下拉逻辑
	Call<String> homeListNew(@QueryMap Map<String, String> options);

    @GET(STUDY_BAR_FRAGMENT)//获取学习中心的数据
	Call<String> getStudyBarFragment(@QueryMap Map<String, String> options);

    @GET(STUDY_BAR_FRAGMENT_PRIVATE)//获取私教班数据
	Call<String> getPrivateClass(@QueryMap Map<String, String> options);

    @GET(STUDY_BAR_MY_MESSAGE)//获取新的消息列表
	Call<String> getMyMessage(@QueryMap Map<String, String> options);

    @GET(STUDY_BAR_FRAGMENT_QUESTIONSOLUTION)//获取答疑消息列表
	Call<String> getQuestionSolution(@QueryMap Map<String, String> options);


    @GET(DAY_TEST_SUBJECT_LSIT)
	Call<String> daytestsubjectlist(@QueryMap Map<String, String> options);

    @GET(CHECK_MACHINE)
	Call<String> checkMachine(@QueryMap Map<String, String> options);

    @GET(GET_PAPER_LIST)
	Call<String> getPaperList(@QueryMap Map<String, String> options);

    @GET(GET_COURSEPLAY_LIST)
	Call<String> getCoursePlayList(@QueryMap Map<String, String> options);

    @GET(GET_COURSE_PLAYINFO)
	Call<String> getCoursePlayInfo(@QueryMap Map<String, String> options);

    @GET(API_HOST_QRCODE)
	Call<String> getype(@QueryMap Map<String, String> options);

    @GET(GETDATA_TYPE1)
	Call<String> getDataType1(@QueryMap Map<String, String> options);

    @GET(GETDATA_TYPE2)
	Call<String> getDataType2(@QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST(FEED_BACK)
	Call<String> feedBack(@FieldMap Map<String, String> options);

    @GET(POST_HOST+UPLOAD_POSITION)
	Call<String> upLoadPositon(@QueryMap Map<String, String> options);

    @GET(DAY_TEST_QUESTION)
	Call<String> daytestQuestion(@QueryMap Map<String, String> options);

    @POST(COMMIT_DEVICETOKEN)
	Call<String> CommitDevicetoken(@QueryMap Map<String, String> options);

    //http://120.55.160.19/yd_errlog_multi.php  UPLOAD_OPERA
    @FormUrlEncoded
    @POST("http://120.55.160.19/yd_errlog_multi.php")
	Call<String> postOperates(@FieldMap Map<String, String> options);

    @FormUrlEncoded
    @POST(POST_HOST+UPLOAD_VIDEO)
	Call<String> upLoadVideos(@FieldMap Map<String, String> options);

    @GET
	Call<String> CheckTimes(@Url String url);

    @GET(COURSE_ANSWER_NUM)
	Call<String> courseAnswerNum(@QueryMap Map<String, String> options);

    /**
     * @param imgs
     * @return
     */
    @Multipart
    @POST(POST_IMG)
	Call<String> uploadImage(@PartMap Map<String, RequestBody> imgs);

    @GET
	Call<String> analysisDNS(@Url String url);

    @GET(GET_PLAYURL)
	Call<String> getPlayUrl(@QueryMap Map<String, String> options);

    @GET
	Call<String> downloadM3U8(@Url String url);

    @GET
	Call<String> getQrcodeNew(@Url String url);


    /**
     * 答疑
     */
    @GET(QUESTION_BOOK_LIST)
	Call<String> questionBookList(@QueryMap Map<String, String> options);
    @FormUrlEncoded
    @POST(QUESTION_KNOWLEADEPOINT_LIST)
	Call<String> questionKnowLedgePointList(@FieldMap Map<String, String> options);
    @GET(QUESTION_RECOMM_LIST)
	Call<String> questionRecommList(@QueryMap Map<String, String> options);
    @FormUrlEncoded
    @POST(QUESTION_MY_LIST)
	Call<String> questionMyList(@FieldMap Map<String, String> options);
    @GET(QUESTION_DETAIL)
	Call<String> questionDetail(@QueryMap Map<String, String> options);
    @FormUrlEncoded
    @POST(QUESTION_ZHUI)
	Call<String> questionZhui(@FieldMap Map<String, String> options);
    @FormUrlEncoded
    @POST(QUESIOTN_MODIFY)
	Call<String> questionModify(@FieldMap Map<String, String> options);
    @FormUrlEncoded
    @POST(QUESTION_ADD_BOOK)
	Call<String> questionAdd(@FieldMap Map<String, String> options);
    @GET(QUESTIN_DELETE_NONE)
	Call<String> questionDeleteNone(@QueryMap Map<String, String> options);
    @GET(QUESTIN_DELETE)
	Call<String> questionDelete(@QueryMap Map<String, String> options);
    @GET(QUESTIN_DELETE_NEW)
	Call<String> questionDeleteNew(@QueryMap Map<String, String> options);
    @GET(EXAM_RECOMM_QUESTION)
	Call<String> examRecommQuestion(@QueryMap Map<String, String> options);
    @FormUrlEncoded
    @POST(QUESTION_ADD_QUESTION)
	Call<String> examQuestionAdd(@FieldMap Map<String, String> options);
}
