package com.me.fanyin.zbme.views.exam.remote;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.me.core.utils.AppUtils;
import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.Opera;
import com.me.fanyin.zbme.views.course.play.utils.StringUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xunice on 16/5/5.
 * 组拼所有的参数
 */
public class ParamsUtils {

    private static ParamsUtils paramsUtils;
    private static Context context;

    public static synchronized ParamsUtils getInstance(Context context) {
        if (null == paramsUtils) {
            paramsUtils = new ParamsUtils();
            ParamsUtils.context = context;
        }
        return paramsUtils;
    }

    public static HashMap<String,String > commonURL(boolean isNeedToken){
        String appName = "da-kq-app"; //app名字，应该不会变得
        String deviceType = "1"; //标示为android phone
        String app = AppUtils.getPackageName(context);
        String uniqueId = AppUtils.getDeviceUUID(context);
        String version = AppUtils.getSystemVersion();
        //添加mobileAccessToken
        String mobileAccessToken = SharedPrefHelper.getInstance().getAccesstoken();
        String timeStamp = System.currentTimeMillis()+"";
        // String random = System.currentTimeMillis() + new Random().nextInt(100)+"";

        if (app == null) {
            app = "com.dongao.mainclient.phone";
        }
        if (uniqueId == null || uniqueId.equals("")) {
            uniqueId = "dongao_123456789";
        }
        if (version == null) {
            version = "1.0.0";
        }
        //TODO 测试需要
//        if(mobileAccessToken == null){
//            mobileAccessToken = "mobiletoken";
//        }
        HashMap<String, String> params = new HashMap<>();
        params.put("deviceType", deviceType);
        params.put("app", app);
        params.put("appName", appName);
        params.put("version", version);
        params.put("uniqueId", uniqueId);
        params.put("timeStamp", timeStamp);
        //  params.put("random", CryptoUtil.md5HexDigest(random, null));
        if(mobileAccessToken!=null && !mobileAccessToken.equals("") && isNeedToken){
            params.put("mobileAccessToken",mobileAccessToken);
        }
        //params.put("sign", SignUtils.sign(params));
        return  params;
    }

    public static HashMap<String, String> commonURL() {
        return commonURL(true);
    }

    /**
     * 无需提交参数的get请求
     * @return
     */
    public static HashMap<String,String> commonSignPramas(){
        HashMap<String,String> parmas = commonURL();
        parmas.put("sign", SignUtils.sign(parmas));
        return parmas;
    }

    /**
     * 扫一扫 type为1时用这个接口
     * @return
     */
    public static HashMap<String, String> upLoadPosition(double latitude, double lontitude, String addr){
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String,String> params = commonURL();
        params.put("userId",userId);
        params.put("location",addr);
        params.put("longitude",lontitude+"");
        params.put("latitude",latitude+"");
        params.put("sts","A");
        params.put("userTimeStr", System.currentTimeMillis()+"");
        params.put("sign", SignUtils.sign(params));
        return params;
    }


    /**
     * 上传视频学习记录
     * @return
     */
    public static HashMap<String, String> uploadVideos(String json){
        HashMap<String,String> params = commonURL();
        params.put("jsonParams",json);
        params.put("sign",SignUtils.sign(params));
        return params;
    }

    /**
     * 扫一扫 type为1时用这个接口
     * @return
     */
    public static HashMap<String, String> checkMachine(){
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String,String> params = commonURL();
        params.put("userId",userId);
        params.put("sign",SignUtils.sign(params));
        return params;
    }

    /**
     * 获取播放路径
     * @return
     */
    public static HashMap<String, String> getPlayPath(String cwId){
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String,String> params = commonURL();
        if(userId.equals("0")){
            params.put("userId","");
        }else{
            params.put("userId",userId);
        }
        params.put("lectureId",cwId);//"34"
        params.put("isDown","1");
        params.put("sign",SignUtils.sign(params));
        return params;
    }

    /**
     * 扫一扫 type类型
     * @return
     */
    public static HashMap<String, String> getType(String qrUrl){
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String,String> params = commonURL();
        if(userId.equals("0")){
            params.put("userId","");
        }else{
            params.put("userId",userId);
        }
        params.put("pUrl",qrUrl);
        params.put("sign",SignUtils.sign(params));
        return params;
    }

    /**
     * 扫一扫 type为1时用这个接口
     * @return
     */
    public static HashMap<String, String> getDataType1(String qrCodeId){
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String,String> params = commonURL();
        if(!TextUtils.isEmpty(userId)){
            params.put("userId",userId );
        }
        params.put("qrCodeId",qrCodeId);
        params.put("type", "book");
        params.put("sign",SignUtils.sign(params));
        return params;
    }

    /**
     * 扫一扫 type为2时用这个接口
     * @return
     */
    public static HashMap<String, String> getDataType2(String qrCodeId){
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String,String> params = commonURL();
        if(!TextUtils.isEmpty(userId)){
            params.put("userId",userId );
        }
        params.put("qrCodeId",qrCodeId);
        params.put("sign",SignUtils.sign(params));
        return params;
    }

    /**
     * 课程下载信息
     * @return
     */
    public static HashMap<String, String> getPlayInfo(String lectureId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("lectureId", lectureId);
        params.put("isDown", "0");
        params.put("userId", userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 运维上传
     * @return
     */
    public static HashMap<String, String> postDatas(List<Opera> operas){
        String username=SharedPrefHelper.getInstance().getLoginUsername();
        HashMap<String,String> params = commonURL();
        params.put("userId",username);   //userID
        params.put("operationItems", JSON.toJSONString(operas));
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 激活卡片
     * @return
     */
    public static HashMap<String, String> bookActivate(String cardNo){
        String userId = SharedPrefHelper.getInstance().getUserId()+"";
        HashMap<String,String> params = commonURL();
        params.put("userId", userId);
        params.put("couponNo", cardNo);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 激活天猫订单
     * @return
     */
    public static HashMap<String, String> tmallActivate(String tmailOrder){
        String userId = SharedPrefHelper.getInstance().getUserId()+"";
        String userName = SharedPrefHelper.getInstance().getLoginUsername()+"";
        HashMap<String,String> params = commonURL();
        params.put("userId", userId);
        params.put("userName", userName);
        params.put("tmailOrder", tmailOrder);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 激活卡片列表
     * @return
     */
    public static HashMap<String, String> bookActivateList(){

        String userId = SharedPrefHelper.getInstance().getUserId()+"";
        HashMap<String,String> params = commonURL();
        params.put("userId", userId);
//        params.put("page", "1");
//        params.put("pageSize", "1000");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 首页列表
     * @return
     */
    public static HashMap<String, String> homeList(){

        String userId = SharedPrefHelper.getInstance().getUserId()+"";
        HashMap<String,String> params = commonURL();
        params.put("userId", userId);
        params.put("page", "1");
        params.put("rows", "1000");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**首页最新的接口*/
    public static HashMap<String, String> homeListByLocal(String jsonParams){

        String userId = SharedPrefHelper.getInstance().getUserId()+"";
        HashMap<String,String> params = commonURL();

        if(SharedPrefHelper.getInstance().isLogin()){
            params.put("userId", userId);
        }else{
            params.put("userId", "0");
        }
        if (!jsonParams.equals("[]")){
            params.put("jsonParams", jsonParams);
        }
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取域名集合
     */
    public static HashMap<String, String> getDomainName(){
        HashMap<String,String> params = commonURL();
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 首页列表——新
     * @return
     */
    public static HashMap<String, String> homeListByList(String id, int selectOrder){

        String userId = SharedPrefHelper.getInstance().getUserId()+"";
        HashMap<String,String> params = commonURL();

        if(!StringUtil.isEmpty(id)&&SharedPrefHelper.getInstance().isLogin()){
            params.put("id", id);
            params.put("userId", userId);
        }else{
            params.put("userId", "0");
        }
        params.put("selectOrder", selectOrder+"");
        params.put("rows", "1000");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 我的
     * @return
     */
    public static HashMap<String,String> persenal(){
        String userId = SharedPrefHelper.getInstance().getUserId()+"";
        HashMap<String,String> params = commonURL();
        params.put("userId", userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }
    /**
     * 获取试卷列表
     * @return
     */
    public static HashMap<String, String> getExamPaperList(int page){//需要新加examId
        String userId = SharedPrefHelper.getInstance().getUserId()+"";
        String subjectId=SharedPrefHelper.getInstance().getMainSubjectId();
        String typeId;//=SharedPrefHelper.getInstance().getMainTypeId();
        typeId="";
        String examId=SharedPrefHelper.getInstance().getExamId();
//        subjectId="54";
//        typeId="49";
//        userId="1";
//        examId="42";
        HashMap<String,String> params = commonURL();
        params.put("userId", userId);
        params.put("examId", examId);
        params.put("subjectId", subjectId);
        params.put("typeId", typeId);
        params.put("page", page+"");
        params.put("pageSize", Constants.PAGE_NUMBER+"");
        params.put("sign", SignUtils.sign(params));
        return params;
    }
    
    /**
     * 获取试卷接口
     * @return
     */
    public static HashMap<String, String> getExamPaper(String examinationId){
        String userId = SharedPrefHelper.getInstance().getUserId();
//        userId="1";
//        examinationId="28";
        HashMap<String,String> params = commonURL();
        params.put("userId", userId);
        params.put("examinationId", examinationId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取开课的字段
     * @return
     */
    public static HashMap<String, String> payOpen(String ip){
        String orderId = SharedPrefHelper.getInstance().getOrderId();
        HashMap<String,String> params = commonURL();
        params.put("orderId", orderId);
        params.put("ip", ip);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取原题接口
     * @return
     */
    public static HashMap<String, String> getOriginalQuestion(String questionId, String subjectId){
        HashMap<String,String> params = commonURL();
        params.put("questionId", questionId);
        params.put("subjectId", subjectId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 上传答题记录的接口
     * @return
     */
    public static HashMap<String, String> uploadExamPaper(String examinationId, String subjectId, String time, String datas, long startTime, float totalScore){
        String userId = SharedPrefHelper.getInstance().getUserId()+"";
//        userId="111112";
//        subjectId="1";
//        examinationId="1";

        HashMap<String,String> params = commonURL();
        params.put("userId", userId);
        params.put("examinationId", examinationId);
        params.put("subjectId", subjectId + "");
        params.put("examTime", time + "");
        params.put("answerList", datas);
        params.put("startTime", startTime+"");
        params.put("totalScore", totalScore+"");
        params.put("sign", SignUtils.sign(params));
        return params;
    }
    /**
     * 上传devicetoken的接口
     * isOnline 0未登录 1已登录
     * @return
     */
    public static HashMap<String, String> commitDeviceToken(String deviceToken, int isOnline){
        String userId = SharedPrefHelper.getInstance().getUserId()+"";
        HashMap<String,String> params = commonURL();
        params.put("userId", userId);
        params.put("deviceToken", deviceToken);
        params.put("isOnline", isOnline+"");
        params.put("sign", SignUtils.sign(params));
        return params;
    }
    

    /**
     * 获取学吧列表接口
     * @return
     */
    public static HashMap<String, String> getStudyFragment(){
        String userId = SharedPrefHelper.getInstance().getUserId()+"";
        HashMap<String,String> params = commonURL();
        params.put("userId", userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 新增教材答疑
     * @return
     */
    public static HashMap<String,String> addBookQuestion(String bookId, String sectionId, String page, String subjectId, String title, String content, String imageUrls){
        String userId = SharedPrefHelper.getInstance().getUserId()+"";
        HashMap<String, String> params = commonURL();
        params.put("sectionId", sectionId);
        params.put("bookId", bookId);
        params.put("page",page);
        params.put("subjectId", subjectId);
        params.put("title",title);
        params.put("content", content);
        params.put("userId", userId);
        if(imageUrls!=null && !imageUrls.equals(""))
            params.put("imageUrls",imageUrls);
        params.put("sign",SignUtils.sign(params));
        return params;
    }

    /**
     * 获取最新的消息列表
     * @return
     */
    public static HashMap<String, String> getMyMessage(){
        HashMap<String,String> params = commonURL();
        params.put("sign", SignUtils.sign(params));
        return params;
    }



    /**
     * 获取私教班列表接口
     * @return
     */
    public static HashMap<String, String> getPrivateTeacher(String type, String classId, int page){
        String userId = SharedPrefHelper.getInstance().getUserId()+"";
        HashMap<String,String> params = commonURL();
        params.put("userId", userId);
        params.put("type", type);
        params.put("classId", classId);
        params.put("page", page+"");
        params.put("pageSize", Constants.PAGE_NUMBER+"");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取答疑列表接口
     * @return
     */
    public static HashMap<String, String> getQuestionSolution(String type, String classId, int page){
        String userId = SharedPrefHelper.getInstance().getUserId()+"";
        HashMap<String,String> params = commonURL();
        params.put("userId", userId);
//        params.put("type", type);
//        params.put("classId", classId);
        params.put("page", page+"");
        params.put("pageSize", Constants.PAGE_NUMBER+"");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取站内信列表接口
     * @return
     */
    public static HashMap<String, String> getInnerMessage(String type, String classId){
        String userId = SharedPrefHelper.getInstance().getUserId()+"";
        HashMap<String,String> params = commonURL();
        params.put("userId", userId);
        params.put("type", type);
        params.put("classId", classId);
        params.put("page", "0");
        params.put("pageSize", Constants.PAGE_NUMBER+"");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 我的答疑
     * @param subjectId
     * @param page
     * @param pageSize
     * @return
     */
    public static HashMap<String,String> myQuestion(String subjectId, String page, String pageSize){
        String userId = SharedPrefHelper.getInstance().getUserId()+"";
        HashMap<String,String> params = commonURL();
        params.put("userId",userId);
        params.put("subjectId",subjectId);
        params.put("page",page);
        params.put("pageSize",pageSize);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 推荐答疑
     * @param subjectId
     * @param questionId
     * @return
     */
    public static HashMap<String,String> recommQuestion(String subjectId, String questionId){
        String userId = SharedPrefHelper.getInstance().getUserId()+"";
        HashMap<String,String> params = commonURL();
        params.put("userId",userId);
        params.put("subjectId",subjectId);
        params.put("questionId",questionId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取新增答疑时根据科目id查询可选章节
     * @param subjectId
     * @return
     */
    public static HashMap<String,String> getSectionsBySubjectId(String subjectId){
        String userId = SharedPrefHelper.getInstance().getUserId()+"";
        HashMap<String,String> params = commonURL();
        params.put("userId",userId);
        params.put("subjectId",subjectId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 课程详情
     * @param subjectId
     * @param courseId
     * @return
     */
    public static HashMap<String,String> playDetail(String subjectId, String courseId){
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String,String> params = commonURL();
        params.put("userId",userId);
        params.put("subjectId",subjectId);
        params.put("courseId",courseId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 新增答疑上传图片
     * @param imageData
     * @return
     */
    public static HashMap<String,Object> commitQuesiontImg(byte[] imageData){
        HashMap<String,Object> params = new HashMap<>();
        HashMap<String,String> params1 = commonURL();
        params1.put("sign",SignUtils.sign(params1));
        params.put("file",imageData);
        params.putAll(params1);
        return params;
    }

    /**
     * 修改我的答疑
     * @param title
     * @param content
     * @return
     */
    public static HashMap<String,String> modifyQuestion(String title, String content, String questionId){
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String,String> params = commonURL();
        params.put("title",title);
        params.put("content",content);
        params.put("userId",userId);
        params.put("questionId",questionId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 追问
     * @param title
     * @param content
     * @param qaFatherId
     * @return
     */
    public static HashMap<String,String> continueAsk(String title, String content, String qaFatherId){
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String,String> params = commonURL();
        params.put("title",title);
        params.put("content",content);
        params.put("userId",userId);
        params.put("qaFatherId",qaFatherId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

//    /**
//     * 学吧进答疑
//     * @param questionAnswerId
//     * @return
//     */
//    public static HashMap<String,String> questionDetail(String questionAnswerId){
//        String userId = SharedPrefHelper.getInstance().getUserId();
//        HashMap<String,String> params = commonURL();
////        params.put("userId",userId);
//        params.put("questionAnswerId",questionAnswerId);
//        params.put("sign", SignUtils.sign(params));
//        return params;
//    }

    /**
     * 试题推荐答疑
     * @param examinationQuestionId
     * @param page
     * @param pageSize
     * @return
     */
    public static HashMap<String,String> examRecommQuestion(String examinationQuestionId, String page, String pageSize){
        HashMap<String,String> params = commonURL();
        params.put("examinationQuestionId",examinationQuestionId);
        params.put("page",page);
        params.put("pageSize",pageSize);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 用户是否可以提问
     * @param examinationQuestionId
     * @return
     */
    public static HashMap<String,String> isCanAsk(String examinationQuestionId){
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String,String> params = commonURL();
        params.put("examinationQuestionId",examinationQuestionId);
        params.put("userId",userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }



    /**
     * 课程首页 请求科目列表
     * @return
     */
    public static HashMap<String, String> subjectList(){
        String userId = SharedPrefHelper.getInstance().getUserId()+"";
        HashMap<String,String> params = commonURL();
        params.put("userId",userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 每日一练获取问题
     * @param subjectId
     * @param currentDate
     * @return
     */
    public static HashMap<String,String> dayTestQuestion(String subjectId, String currentDate){
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String,String> params = commonURL();
        params.put("subjectId",subjectId);
        params.put("currentDate",currentDate);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 新增答疑（非教材）
     * @param examinationId
     * @param title
     * @param content
     * @param examquestionId
     * @return
     */
    public static HashMap<String,String> addQuestionNoraml(String examinationId, String title, String content, String examquestionId){
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String,String> params = commonURL();
        params.put("userId",userId);
        params.put("examinationId",examinationId);
        params.put("title",title);
        params.put("content",content);
        params.put("examquestionId",examquestionId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 精华答疑
     * @param subjectId
     * @return
     */
    public static HashMap<String,String> jinghuaQuestion(String subjectId, String page, String pageSize){
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String,String> params = commonURL();
        params.put("subjectId",subjectId);
        params.put("userId",userId);
        params.put("page",page);
        params.put("pageSize",pageSize);
        params.put("sign", SignUtils.sign(params));
        return params;
    }


    /**
     * 获取验证码
     * @param phoneNum
     *
     *  phoneNum	手机号	String
        userName	用户	string
        sendType	手机或者邮箱 （1手机或2邮箱）	String
        contentType	发送内容的类型：10注册，11找回密码，12修改密码，99其它 非空	String
     * @return
     */
    public static HashMap<String,String> getMobileValid(String phoneNum){
        HashMap<String,String> params = commonURL();
       // String username = SharedPrefHelper.getInstance().getLoginUsername();
        params.put("phoneNum",phoneNum);
        params.put("userName",phoneNum);
        params.put("sendType","1");
        params.put("contentType","10");//10注册，11找回密码，12修改密码，99其它 非空
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 支付宝支付
     * @param orderId
     * @return
     */
    public static HashMap<String,String> payByZFB(String orderId){
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String,String> params = commonURL();
        params.put("orderId",orderId);
        params.put("userId",userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 微信支付
     * @param orderId
     * @return
     */
    public static HashMap<String,String> payByWX(String orderId){
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String,String> params = commonURL();
        params.put("orderId",orderId);
        params.put("userId",userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 修改密码获取验证码
     */
    public static HashMap<String,String> modifyPswGetCode(String phoneNum){
        HashMap<String,String> params = commonURL();
        params.put("phoneNum",phoneNum);
//        params.put("userName",phoneNum);
        params.put("sendType","1");
        params.put("contentType","12");//发送内容的类型：10注册，11找回密码，12修改密码，99其它 非空
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 新答疑
     */
    //图书列表
    public static HashMap<String,String> questionBookList(String subjectId, String type){
        HashMap<String,String> params = commonURL();
        String userId = SharedPrefHelper.getInstance().getUserId();
        params.put("userId",userId);
//        params.put("userId","335");
        params.put("subjectId",subjectId);
//        params.put("subjectId","137");
        params.put("type",type);
        params.put("sign", SignUtils.sign(params));
        return params;
    }
    //知识点列表
    public static HashMap<String,String> questionKnowLedgePointList(String subjectId, String bookId, String pageNum){
        HashMap<String,String> params = commonURL();
        params.put("subjectId",subjectId);
//        params.put("subjectId","137");
        params.put("bookId",bookId);
//        params.put("pageNum",pageNum);
        params.put("pageNum",pageNum);
        params.put("sign", SignUtils.sign(params));
        return params;
    }
    //推荐答疑列表
    public static HashMap<String,String> questionRecommList(String qaType, String bookQaType, String bookQaId, String examQuestionId){
        HashMap<String,String> params = commonURL();
        params.put("qaType",qaType);
        params.put("bookQaType",bookQaType);
        params.put("bookQaId",bookQaId);
        params.put("examQuestionId",examQuestionId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }
    //我的答疑列表
    public static HashMap<String,String> questionMyList(String subjectId, String page, String pageSize){
        HashMap<String,String> params = commonURL();
        String userId = SharedPrefHelper.getInstance().getUserId();
        params.put("userId",userId);
//        params.put("userId","335");
        params.put("subjectId",subjectId);
//        params.put("subjectId","137");
        params.put("page",page);
        params.put("pageSize",pageSize);
        params.put("sign", SignUtils.sign(params));
        return params;
    }
    //答疑详情
    public static HashMap<String,String> questionDetail(String questionId){
        HashMap<String,String> params = commonURL();
        String userId = SharedPrefHelper.getInstance().getUserId();
        params.put("userId",userId);
//        params.put("userId","335");
        params.put("questionId",questionId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }
    //答疑修改
    public static HashMap<String,String> questionModify(String questionId, String qaInfoId, String title, String content){
        HashMap<String,String> params = commonURL();
        String userId = SharedPrefHelper.getInstance().getUserId();
        params.put("userId",userId);
//        params.put("userId","335");
        params.put("questionId",questionId);
        params.put("qaInfoId",qaInfoId);
        params.put("title",title);
        params.put("content",content);
        params.put("sign", SignUtils.sign(params));
        return params;
    }
    //答疑追问
    public static HashMap<String,String> questionZhui(String questionId, String content){
        HashMap<String,String> params = commonURL();
        String userId = SharedPrefHelper.getInstance().getUserId();
        params.put("userId",userId);
//        params.put("userId","335");
        params.put("questionId",questionId);
        params.put("content",content);
        params.put("sign", SignUtils.sign(params));
        return params;
    }
    /**
     * 删除答疑
     */
    public static HashMap<String,String> questionDelete(String questionId){
        HashMap<String,String> params = commonURL();
        String userId = SharedPrefHelper.getInstance().getUserId();
        params.put("questionId",questionId);
        params.put("userId",userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }
    /**
     * 试题推荐答疑
     */
    public static HashMap<String,String> exmRecommQuestion(String examQuestionId){
        HashMap<String,String> params = commonURL();
        params.put("qaType","1");
        params.put("bookQaType","1");
        params.put("examQuestionId",examQuestionId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

}
