package com.me.data.remote.utils;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.me.core.app.AppContext;
import com.me.core.utils.AppUtils;
import com.me.core.utils.CryptoUtils;
import com.me.core.utils.StringUtils;
import com.me.core.utils.SystemUtils;
import com.me.data.common.Constants;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.mall.OrderConfirmBean;
import com.me.data.model.mine.AddressBean;
import com.me.data.model.mine.InvoiceRequestBean;
import com.me.data.model.play.NoteClassDetail;
import com.me.data.model.play.NoteDetail;
import com.me.data.model.play.Opera;
import com.me.data.remote.ApiInterface;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by xunice on 16/5/5.
 * 组拼所有的参数
 */
public class ParamsUtils {

    private static ParamsUtils paramsUtils;
    private static Context context = AppContext.getInstance();

    public static synchronized ParamsUtils getInstance() {
        if (null == paramsUtils) {
            paramsUtils = new ParamsUtils();
        }
        return paramsUtils;
    }

    /***
     *  deviceType	设备类型	iphone：0 andorid Phone：1 ipad：2 android pad：3
     uniqueId	设备唯一标识	F56EA7F0-2034-48A8-9D92-8F708C3715C5
     token	token	UUID生成
     appName	app名称	da-exam-app
     sign	签名	a457542649a2aeaa208e5461e0057a89
     osType	操作系统类型	ios andorid
     osVersion	操作系统版本号	10.0
     model	手机型号	HUAWEI MATE9
     appVersion	app版本号	4.0.0
     * @return
     */
    public static HashMap<String, String> commonURL() {

        String app = AppUtils.getPackageName(context);
        String appName = "da-cloudclass-app"; //app名字，应该不会变得
        String deviceType = "1"; //标示为android phone
        String uniqueId = AppUtils.getDeviceUUID(context);
        String appVersion = AppUtils.getAppVersionName(context, app);
        String model = AppUtils.getSystemModel();
        String osType = "android";
        String osVersion = AppUtils.getSystemVersion();
        String timeStamp = System.currentTimeMillis() + "";

        if (uniqueId == null || uniqueId.equals("")) {
            uniqueId = "dongao_123456789";
        }
        if (appVersion == null) {
            appVersion = "1.0.0";
        }
        String token = SharedPrefHelper.getInstance().getAccesstoken();

        HashMap<String, String> params = new HashMap<>();
        params.put("deviceType", deviceType);
        params.put("appName", appName);
        params.put("appVersion", appVersion);
        params.put("uniqueId", uniqueId);
        params.put("model", model.replace(" ",""));
        params.put("osType", osType);
        params.put("osVersion", osVersion);
        params.put("timeStamp", timeStamp);
        if (!StringUtils.isEmpty(token)) {
            params.put("token", token);
        }
        return params;
    }

    public static HashMap<String, String> commonShopCartURL() {
        HashMap<String, String> params = commonURL();
        //以下两个参数商城特有
        params.put("pcode", "1");
        params.put("userKey", UserKeyUtils.userkey(SharedPrefHelper.getInstance().getUserId()));
        return params;
    }

    /**
     * 无需提交参数的get请求
     *
     * @return
     */
    public static HashMap<String, String> commonSignPramas() {
        HashMap<String, String> parmas = commonURL();
        parmas.put("sign", SignUtils.sign(parmas));
        return parmas;
    }

    /**
     * 智能系统首页
     *
     * @return
     */
    public static HashMap<String, String> intelHome() {
        String examId = SharedPrefHelper.getInstance().getIntelExamId();
        String userId = SharedPrefHelper.getInstance().getUserId();
//        examId="133";
//        userId="257";
        HashMap<String, String> params = commonURL();
        params.put("examId", examId);
        params.put("userId", userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 无需提交额外参数的请求参数String
     *
     * @return
     */
    public static String commonSignPramasString() {
        String paramString = "";
        for(Map.Entry<String, String> map : commonSignPramas().entrySet()){
            paramString = paramString + map.getKey() + "=" + map.getValue() + "&";
        }
        paramString = paramString.substring(0, paramString.length() - 1);
        return paramString;
    }

    /**
     * 登录
     *
     * @param username
     * @param passowrd
     * @return
     */
    public static HashMap<String, String> login(String username, String passowrd, String ip) {
        String pwd = CryptoUtils.md5HexDigest(passowrd);
        HashMap<String, String> params = commonURL();
        params.put("username", username);
        params.put("password", pwd);
        params.put("loginIp", ip);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 登出
     *
     * @return
     */
    public static HashMap<String, String> logout() {
        HashMap<String, String> params = commonURL();
        params.put("userId", SharedPrefHelper.getInstance().getUserId() + "");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 第三方登录
     *
     * @return
     */
    public static HashMap<String, String> thirdPartyLogin(String weiboId, String ip) {
        HashMap<String, String> params = commonURL();
        params.put("weiboId", weiboId);
        params.put("loginIp", ip);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 注册请求验证码
     *
     * @param cell_phone_number 手机号
     * @param userSentTypePhone 验证码发送（phone，email）
     * @param userTypeRegister  发送的验证码类型
     * @return
     */
    public static HashMap<String, String> requestVerificationCode(String cell_phone_number, String userSentTypePhone, String userTypeRegister) {
        HashMap<String, String> params = commonURL();
        params.put("dest", cell_phone_number);
        params.put("sendType", userSentTypePhone);
        params.put("type", userTypeRegister);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 注册
     *
     * @param cell_phone_number
     * @param pswText
     * @param verifyCode
     * @return
     */
    public static HashMap<String, String> register(String cell_phone_number, String pswText, String verifyCode) {
        HashMap<String, String> params = commonURL();
        String pwd = CryptoUtils.md5HexDigest(pswText);
        params.put("mobilePhone", cell_phone_number);
        params.put("password", pwd);
        params.put("validateCode", verifyCode);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 校验验证码
     *
     * @param cell_phone_number
     * @param verifyCode
     * @return
     */
    public static HashMap<String, String> verifyTheCode(String cell_phone_number, String verifyCode) {
        HashMap<String, String> params = commonURL();
        params.put("dest", cell_phone_number);
        params.put("code", verifyCode);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 运维上传
     *
     * @return
     */
    public static HashMap<String, String> postDatas(List<Opera> operas) {
        String username = SharedPrefHelper.getInstance().getLoginUsername();
        HashMap<String, String> params = commonURL();
        params.put("userId", username);   //userID
        params.put("operationItems", JSON.toJSONString(operas));
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 根据email获取对应的用户
     *
     * @param email
     * @return
     */
    public static HashMap<String, String> reqeuestAccountInfoByEmail(String email) {
        HashMap<String, String> params = commonURL();
        params.put("email", email);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 根据email获取对应的用户
     *
     * @param phone
     * @return
     */
    public static HashMap<String, String> reqeuestAccountInfoByPhone(String phone) {
        HashMap<String, String> params = commonURL();
        params.put("mobilePhone", phone);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 根据phoneNumber获取对应的用户
     *
     * @param phoneNumber
     * @return
     */
    public static HashMap<String, String> reqeuestAccountInfoByPhoneNumber(String phoneNumber) {
        HashMap<String, String> params = commonURL();
        params.put("phoneNumber", phoneNumber);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 找回密码
     *
     * @param phoneOrEmail
     * @param sendType
     * @param pswText
     * @param verifyCode
     * @return
     */
    public static HashMap<String, String> getBackPw(String phoneOrEmail, String sendType, String pswText, String verifyCode) {
        HashMap<String, String> params = commonURL();
        String pwd = CryptoUtils.md5HexDigest(pswText);
        params.put("dest", phoneOrEmail);
        params.put("sendType", sendType);
        params.put("password", pwd);
        params.put("verifyCode", verifyCode);
        params.put("sign", SignUtils.sign(params));
        return params;
    }


    public static HashMap<String, String> getMainTypeData(String showPlace) {
        HashMap<String, String> params = commonURL();
//		HashMap<String, String> params = new HashMap<>();
        params.put("showPlace", showPlace);
        params.put("sign", SignUtils.sign(params));
        return params;
    }
    public static HashMap<String, String> getIntelTypeData() {
        String userId=SharedPrefHelper.getInstance().getUserId();
//        userId="257";
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    public static HashMap<String, String> getMainADData() {
        HashMap<String, String> params = commonURL();
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    public static HashMap<String, String> getMainTypeFragmentData(String examId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        if (SharedPrefHelper.getInstance().isLogin()) {
            params.put("userId", userId);
        }
        params.put("examId", examId);
        params.put("shopId", Constants.APP_VENDER_ID);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 课程详情
     *
     * @param courseId
     * @return
     */
    public static HashMap<String, String> playDetail(String courseId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("courseId", courseId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 收藏视频
     *
     * @param courseId
     * @return
     */
    public static HashMap<String, String> collectCw(String courseId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("lectrueId", courseId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 是否已评价
     *
     * @param lectureId
     * @return
     */
    public static HashMap<String, String> isCommented(String lectureId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("lectureId", lectureId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 视频是否收藏
     *
     * @param courseId
     * @return
     */
    public static HashMap<String, String> isCollectCw(String courseId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("lectrueId", courseId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取讲义url
     *
     * @param courseId
     * @return
     */
    public static String getLectureUrl(String courseId) {

        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("lectureId", courseId);
        params.put("userId", userId);
        params.put("sign", SignUtils.sign(params));
        String url=getParams("get",params);
        String lecture = ApiInterface.BASE_URL + "courseApi/V4/handout" +url;
        return lecture;
    }

    /**
     * 获取讲义url
     *
     * @param lectureId
     * @return
     */
    public static String getSmartLectureUrl(String lectureId) {

        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("lectureId", lectureId);
        params.put("sign", SignUtils.sign(params));
        String url=getParams("get",params);
        String lecture = ApiInterface.BASE_URL + "intelligentExamApi/V4/courseHandout" +url;
        return lecture;
    }

    public static String getParams(String method, Map<String, String> paramValues) {
        String params = "";
        Set<String> key = paramValues.keySet();
        String beginLetter = "";
        if (method.equalsIgnoreCase("get")) {
            beginLetter = "?";
        }

        for (Iterator<String> it = key.iterator(); it.hasNext(); ) {
            String s = (String) it.next();
            if (params.equals("")) {
                params += beginLetter + s + "=" + paramValues.get(s);
            } else {
                params += "&" + s + "=" + paramValues.get(s);
            }
        }
        return params;
    }

    /**
     * 笔记详情
     *
     * @param noteId
     * @return
     */
    public static HashMap<String, String> getNoteDetail(String noteId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("noteId", noteId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 笔记详情
     *
     * @param noteId
     * @return
     */
    public static HashMap<String, String> getNoteDetailClass(String noteId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("noteId", noteId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 笔记删除 课堂
     *
     * @param noteId
     * @return
     */
    public static HashMap<String, String> delNoteClass(String noteId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("noteId", noteId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 笔记删除
     *
     * @param noteId
     * @return
     */
    public static HashMap<String, String> delNote(String noteId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("noteId", noteId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 图片上传
     *
     * @param path
     * @return
     */
    public static MultipartBody.Part postImage(String path) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("aFile", file.getName(), requestFile);
        return filePart;
    }

    /**
     * 笔记上传
     *
     * @param
     * @return
     */
    public static HashMap<String, String> postNotes(NoteDetail detail, String title, String content, String urls) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("subjectId", detail.getSubjectId());
        params.put("sSubjectId", detail.getsSubjectId());
        params.put("questionId", detail.getQuestionId());
        params.put("choicetypeId", detail.getChoicetypeId());
        params.put("title", title);
        params.put("content", content);
        params.put("files", urls);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 笔记上传 ketang
     *
     * @param
     * @return
     */
    public static HashMap<String, String> postNotesClass(NoteClassDetail noteDetails, String title, String content, String files) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("lectrueId", noteDetails.getLectrueId());
        params.put("platformCode", "1");
        params.put("coursewareTime", noteDetails.getCoursewareTime());
        params.put("hanConId", noteDetails.getHanConId());
        params.put("title", title);
        params.put("content", content);
        params.put("sSubjectId", noteDetails.getsSubjectId());
        params.put("courseId", noteDetails.getCourseId());
        params.put("files", files);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 笔记更新 ketang
     *
     * @return
     */
    public static HashMap<String, String> updateNotesClass(String noteId, String title, String content, String files) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("noteId", noteId);
        params.put("title", title);
        params.put("content", content);
        params.put("files", files);
        params.put("sign", SignUtils.sign(params));
        return params;
    }


    /**
     * 笔记更新
     *
     * @return
     */
    public static HashMap<String, String> updateNotes(NoteDetail detail, String title, String content, String urls) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("id", detail.getId());
        params.put("subjectId", detail.getSubjectId());
        params.put("sSubjectId", detail.getsSubjectId());
        params.put("choicetypeId", detail.getChoicetypeId());
        params.put("questionId", detail.getQuestionId());
        params.put("content", content);
        params.put("title", title);
        params.put("files", urls);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 科目列表
     *
     * @param examId
     * @return
     */
    public static HashMap<String, String> subjectList(String examId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("examId", examId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 继续播放
     *
     * @param subjectId
     * @return
     */
    public static HashMap<String, String> playContinue(String subjectId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("subjectId", subjectId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 继续播放
     *
     * @param examId
     * @return
     */
    public static HashMap<String, String> classPlayContinue(String examId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("examId", examId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 学习计划
     *
     * @param subjectId
     * @return
     */
    public static HashMap<String, String> studyPlan(String subjectId,String date) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("subjectId", subjectId);
        params.put("date", date);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 课堂首页
     *
     * @return
     */
    public static HashMap<String, String> classHome() {
        String examId = SharedPrefHelper.getInstance().getExamId();
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("shopId", Constants.APP_VENDER_ID);
        params.put("examId", examId);
        params.put("userId", userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 课程评价
     *
     * @return
     */
    public static HashMap<String, String> postComment(String cwId, String comment, String cwst, String diffst, String teachst) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("lectureId", cwId);
        params.put("source", "0");
        params.put("reasonableScore", cwst);
        params.put("beautifulScore", diffst);
        params.put("attractScore", teachst);
        params.put("content", comment);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 课程列表
     *
     * @param sSubjectId
     * @return
     */
    public static HashMap<String, String> courseList(String sSubjectId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("sSubjectId", sSubjectId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取播放路径
     *
     * @return
     */
    public static HashMap<String, String> upLoadVideos(String studyLog) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("tdata", studyLog);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 我会了
     *
     * @return
     */
    public static HashMap<String, String> changeStatus(String nodeId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("nodeId", nodeId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 上传听课记录
     *
     * @return
     */
    public static HashMap<String, String> getPlayPath(String cwId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("lectureId", cwId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * @return
     */
    public static HashMap<String, String> getSmartPlayPath(String cwId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("nodeId", cwId);
        params.put("appType", "android_phone");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * @return
     */
    public static HashMap<String, String> getSmartLecturePath(String lectureId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("lectureId", lectureId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取下载路径
     *
     * @return
     */
    public static HashMap<String, String> getDownloadPath(String cwId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("lectureId", cwId);//"34"
        params.put("isDown", "0");
        params.put("sign", SignUtils.sign(params));
        return params;
    }


    /** 商城模块 ***/
    /**
     * 获取商品详情
     *
     * @param goodsId ?venderId=1&goodsId=584&userId=1
     * @return
     */
    public static HashMap<String, String> getGoodsDetail(String venderId, String goodsId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("venderId", venderId);
        params.put("pid", goodsId);
        params.put("userId", userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 添加商品到购物车
     * pcode	平台代码	int	3
     * userId	用户ID	Long	56
     * userKey	Cookies值	String	78b9d677-a2a8-4bab-8cdd-9bc68bf14803
     * venderId	店铺ID	Long	1
     * pid	商品ID	Long	92
     * promoId	购买数量	Long	1
     * pcount	促销ID	int	1
     * pclassific	商品类别	int	2
     * ccode	渠道代码	String	1
     *
     * @return
     */
    public static HashMap<String, String> getShopCartAddGoods(String venderId, String pid
            , String promoId, String pcount, String pclassific) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonShopCartURL();
        params.put("userId", userId);
        params.put("venderId", venderId);
        params.put("pid", pid);
        params.put("promoId", promoId);
        params.put("pcount", pcount);
        params.put("pclassific", pclassific);
        params.put("ccode", "1"); //暂时写死
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    public static HashMap<String, String> getShopCartBatchs(String goods) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonShopCartURL();
        params.put("userId", userId);
        params.put("goods", goods);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取购物车详情
     *
     * @return
     */
    public static HashMap<String, String> getShopCartDetail() {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonShopCartURL();
        params.put("userId", userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取购物车详情-赠品
     * pcode	平台代码	int	3
     * userId	用户ID	Long	56
     * userKey	Cookies值	String	78b9d677-a2a8-4bab-8cdd-9bc68bf14803
     * venderId	店铺ID	Long	56
     * promoId	促销优惠ID	Long	56
     * pids	赠品列表	String	"23,45,12"
     *
     * @return
     */
    public static HashMap<String, String> getShopCartDetailGift(String venderId, String pids
            , String promoId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonShopCartURL();
        params.put("userId", userId);
        params.put("venderId", venderId);
        params.put("pids", pids);
        params.put("promoId", promoId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取购物车详情-赠品清空
     * pcode	平台代码	int	是	3
     * userId	用户ID	Long	否	56
     * userKey	Cookies值	String	是	78b9d677-a2a8-4bab-8cdd-9bc68bf14803
     * venderId	店铺ID	Long	是	56
     * promoId	促销优惠ID	Long	是	56
     *
     * @return
     */
    public static HashMap<String, String> getShopCartDetailClearGift(String venderId, String promoId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonShopCartURL();
        params.put("userId", userId);
        params.put("venderId", venderId);
        params.put("promoId", promoId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取购物车详情-促销
     * pcode	平台代码	int	3
     * userId	用户ID	Long	56
     * userKey	Cookies值	String	78b9d677-a2a8-4bab-8cdd-9bc68bf14803
     * venderId	店铺ID	Long	1
     * pid	商品ID	Long	93
     * promoId	促销优惠ID	Long	93
     **/
    public static HashMap<String, String> getShopCartDetailPromo(String venderId, String pid
            , String promoId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonShopCartURL();
        params.put("userId", userId);
        params.put("venderId", venderId);
        params.put("pid", pid);
        params.put("promoId", promoId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取购物车详情-选中单个商品，取消选中单个商品
     * pcode	平台代码	int	3
     * userId	用户ID	Long	56
     * userKey	Cookies值	String	78b9d677-a2a8-4bab-8cdd-9bc68bf14803
     * venderId	店铺ID	Long	1
     * pid	商品ID	Long	93
     */
    public static HashMap<String, String> getShopCartDetailProduct(String venderId, String pid) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonShopCartURL();
        params.put("userId", userId);
        params.put("venderId", venderId);
        params.put("pid", pid);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取购物车详情-选择店铺，取消选择店铺
     * pcode	平台代码	int	3
     * userId	用户ID	Long	56
     * userKey	Cookies值	String	78b9d677-a2a8-4bab-8cdd-9bc68bf14803
     * venderId	店铺ID	Long	1
     * pid	商品ID	Long	93
     */
    public static HashMap<String, String> getShopCartDetailVender(String venderId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonShopCartURL();
        params.put("userId", userId);
        params.put("venderId", venderId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取购物车详情-删除多个商品
     * pcode	平台代码	int	3
     * userId	用户ID	Long	56
     * userKey	Cookies值	String	78b9d677-a2a8-4bab-8cdd-9bc68bf14803
     * goods	商品列表	String	是	[{"venderId":1,"pid":196},{"venderId":1,"pid":198},{"venderId":1,"pid":453}]
     * venderId	店铺ID	Long	是	56
     */
    public static HashMap<String, String> getShopCartDetailDelAll(String goods) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonShopCartURL();
        params.put("userId", userId);
        params.put("goods", goods);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    public static HashMap<String, String> getShopCartDetailDel(String venderId, String pid) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonShopCartURL();
        params.put("userId", userId);
        params.put("venderId", venderId);
        params.put("pid", pid);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取购物车详情-修改数量
     *
     * @return pcode    平台代码	int	3
     * userId	用户ID	Long	56
     * userKey	Cookies值	String	78b9d677-a2a8-4bab-8cdd-9bc68bf14803
     * venderId	店铺ID	Long	1
     * pid	商品ID	Long	93
     * pcount	商品数量	int	93
     * pclassific	商品类别	int	93 取值：2（课程）或者3（图书）
     */
    public static HashMap<String, String> getShopCartDetailNum(String venderId, String pid
            , String pcount, String pclassific) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonShopCartURL();
        params.put("userId", userId);
        params.put("venderId", venderId);
        params.put("pid", pid);
        params.put("pcount", pcount);
        params.put("pclassific", pclassific);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * userId	用户ID	Long	必填	12
     * consigneeId	收获地址id	Long	非必填	12
     * payment	支付类型	int	必填	12
     * couponId	使用优惠券id	Long	非必填	12
     * cashCouponId	使用现金券id	Long	非必填	12
     * learningPrice	使用学习卡金额	double	必填【若没有使用请传 0】	12
     * cashAccountPrice	使用先进账户金额	double	必填【若没有使用请传 0】	12
     * orderRemark	订单备注	String	非必填	备注
     * ipAddress	订单备注	String	必填	192.168.0.1
     * pcode	平台	int	必填	1
     * jobNum	客服工号	String	非必填	156245xx
     *
     * @return
     */

    public static HashMap<String, String> saveOrder(OrderConfirmBean orderConfirmBean) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonShopCartURL();
        params.put("userId", userId);
        params.put("consigneeId", orderConfirmBean.getConsigneeId());
        params.put("payment", orderConfirmBean.getPayment());
        params.put("couponId", orderConfirmBean.getCouponId());
        params.put("cashCouponId", orderConfirmBean.getCashCouponId());
        params.put("learningPrice", orderConfirmBean.getLearningPrice() + "");
        params.put("cashAccountPrice", orderConfirmBean.getCashAccountPrice() + "");
        params.put("orderRemark", orderConfirmBean.getOrderRemark());
        params.put("ipAddress", orderConfirmBean.getIpAddress());
        params.put("jobNum", orderConfirmBean.getJobNum());
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    public static HashMap<String, String> userAccount() {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonShopCartURL();
        params.put("userId", userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    public static HashMap<String, String> getOrderDetail(String orderNo) {
        HashMap<String, String> params = commonURL();
        params.put("orderNo", orderNo);
        params.put("sign", SignUtils.sign(params));
        return params;
    }


    public static HashMap<String, String> pay(String type, String orderNo) {
        HashMap<String, String> params = commonURL();
        if (type.equals(com.me.core.payment.payutils.Constants.zfb_pay)) {
            params.put("payId", "24");
        } else if (type.equals(com.me.core.payment.payutils.Constants.wx_pay)) {
            params.put("payId", "47");
        }
        params.put("mid", "38");
        params.put("orderNo", orderNo);
        params.put("goodsInfo", "东奥手机支付");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 商品推荐
     *
     * @param venderId
     * @return
     */
    //pid=556&venderId=1&recommendNum=2&userId=1
    public static HashMap<String, String> getGoodRecommond(String venderId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonShopCartURL();
        params.put("userId", userId);
        params.put("venderId", venderId);
        params.put("recommendNum", "2");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 商品详情推荐  pid=285
     *
     * @param venderId
     * @param pid
     * @return
     */
    public static HashMap<String, String> getGoodDetailRecommond(String venderId, String pid) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonShopCartURL();
        params.put("userId", userId);
        params.put("venderId", venderId);
        params.put("recommendNum", "2");
        params.put("pid", pid);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    //提问的知识点列表
    public static HashMap<String, String> queryKnowLedgePointList(String sSubjectId, String bookId, String pageNum) {
        HashMap<String, String> params = commonURL();
//        sSubjectId="136";
//        bookId="136";
//        pageNum="22";
//        params.put("subjectId","137");
//        params.put("pageNum",pageNum);
        params.put("sSubjectId", sSubjectId);
        params.put("bookId", bookId);
        params.put("pageNum", pageNum);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    //提问的图书列表
    public static HashMap<String, String> queryBookList(String sSubjectId, String type) {
        String userId = SharedPrefHelper.getInstance().getUserId();
//        userId="322";
//        sSubjectId="136";
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("sSubjectId", sSubjectId);
//        params.put("type",type);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    //推荐图书知识点对应答疑列表
    public static HashMap<String, String> queryRecommList(String qaType, String bookId, String bookQaType, String bookQaId, String questionId, String hanConId) {
//        bookId="134";
//        hanConId="136";
//        qaType="2";
//        bookQaType="0";
//        bookQaId="85861";

        HashMap<String, String> params = commonURL();
        params.put("qaType", changeNull(qaType));
        params.put("bookId", changeNull(bookId));
        params.put("bookQaType", changeNull(bookQaType));
        params.put("bookQaId", changeNull(bookQaId));
        params.put("questionId", changeNull(questionId));
        params.put("hanConId", changeNull(hanConId));
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    //推荐图书知识点对应答疑列表
    public static HashMap<String, String> queryJiangyiRecommList(String lectureId) {
        HashMap<String, String> params = commonURL();
        params.put("lectureId", lectureId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    //智能系统推荐答疑列表
    public static HashMap<String, String> queryIntelRecommList(String nodeId) {
//        nodeId = "201";
        HashMap<String, String> params = commonURL();
        params.put("nodeId", nodeId);
//		params.put("pageNo", Constants.PAGE_NUMBER + "");
//		params.put("pageSize", "1");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    //获取提问首页的
    public static HashMap<String, String> queryMainFragment(String sSubjectId, String pageNo) {
        HashMap<String, String> params = commonURL();
        String userId = SharedPrefHelper.getInstance().getUserId();
//        HashMap<String,String> params = new HashMap<>();
//        userId="322";
//        sSubjectId="136";
//        pageNo="1";
        params.put("userId", userId);
        params.put("sSubjectId", sSubjectId);
        params.put("pageNo", pageNo);
        params.put("pageSize", Constants.PAGE_NUMBER + "");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    //获取答疑搜索的
    public static HashMap<String, String> querySearch(String sSubjectId, String pageNo, String searchContent) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
//        HashMap<String,String> params = new HashMap<>();
//        userId="322";
//        sSubjectId="136";
//        pageNo="1";
        params.put("userId", userId);
        params.put("searchContent", searchContent);
        params.put("sSubjectId", sSubjectId);
        params.put("pageNo", pageNo);
        params.put("pageSize", Constants.PAGE_NUMBER + "");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    //获取自动提示
    public static HashMap<String, String> queryWord(String sSubjectId, String searchContent) {
        HashMap<String, String> params = commonURL();
//        HashMap<String,String> params = new HashMap<>();
//        userId="322";
//        sSubjectId="136";
//        pageNo="1";
        params.put("searchContent", searchContent);
        params.put("sSubjectId", sSubjectId);
        params.put("pageSize", Constants.PAGE_NUMBER + "");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 试题答疑提问
     */
    public static HashMap<String, String> queryQuestionAdd(String examinationId, String examquestionId, String paperName,
                                                           String largeSegmentName, String subsectionName, String title, String content) {
        HashMap<String, String> params = commonURL();
        String userId = SharedPrefHelper.getInstance().getUserId();
        String examId = "";
        String subjectId = SharedPrefHelper.getInstance().getMainSubjectId();
        params.put("examId", examId);
        params.put("subjectId", subjectId);
        params.put("userId", userId);
        params.put("examinationId", examinationId);
        params.put("examquestionId", examquestionId);
        params.put("paperName", paperName);
        params.put("largeSegmentName", largeSegmentName);
        params.put("subsectionName", subsectionName);
        params.put("title", title);
        params.put("content", content);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    //答疑新增
    public static HashMap<String, String> queryAddBookQuestion(String subjectId, String qaType, String title, String content,
                                                               String bookId, String bookQaType, String bookQaId,
                                                               String chapterIds, String pageNum, String questionId, String paperId,
                                                               String paperName, String largeSegmentName, String subsectionName,
                                                               String questionNum, String courseId, String coursewareId, String hanConId
    ) {
        HashMap<String, String> params = commonURL();
        String userId = SharedPrefHelper.getInstance().getUserId();
//		String subjectId = SharedPrefHelper.getInstance().getSsubjectId();
//        HashMap<String,String> params=new  HashMap<String,String>();
//        subjectId=136+"";qaType=2+"";bookId=136+"";bookQaType=0+"";bookQaId=185861+"";chapterIds=1040+"";pageNum=1+"";
//        userId="322";
//        subjectId=136+"";
//        bookId="136";
        params.put("qaType", changeNull(qaType));
        params.put("userId", userId);
        params.put("title", title);
        params.put("content", content);
        params.put("sSubjectId", subjectId);
        params.put("isSendMail", "0");
        params.put("bookId", changeNull(bookId));
        params.put("bookQaType", changeNull(bookQaType));
        params.put("bookQaId", changeNull(bookQaId));
        params.put("chapterIds", changeNull(chapterIds));
        params.put("pageNum", changeNull(pageNum));
        params.put("questionId", changeNull(questionId));
        params.put("paperId", changeNull(paperId));
        params.put("paperName", changeNull(paperName));
        params.put("largeSegmentName", changeNull(largeSegmentName));
        params.put("subsectionName", changeNull(subsectionName));
        params.put("questionNum", changeNull(questionNum));
        params.put("courseId", changeNull(courseId));
        params.put("coursewareId", changeNull(coursewareId));
        params.put("hanConId", changeNull(hanConId));
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 修改答疑
     *
     * @return
     */
    public static Map<String, String> updateQuery(String title, String content, String qaId, String qaInfoId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
//        userId="322";
//        HashMap<String,String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("title", title);
        params.put("content", content);
        params.put("qaId", qaId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 删除答疑
     *
     * @return
     */
    public static Map<String, String> deleteQuery(String qaId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
//        userId="322";
//        HashMap<String,String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("qaId", qaId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 追问
     *
     * @return
     */
    public static Map<String, String> addOtherQuery(String qaId, String content) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
//        userId="322";
//        HashMap<String,String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("content", content);
        params.put("qaId", qaId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 收藏
     *
     * @return
     */
    public static Map<String, String> collectrQuery(String qaId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
//        userId="322";
//        HashMap<String,String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("qaId", qaId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 答疑详情
     *
     * @return
     */
    public static Map<String, String> getQueryDetail(String qaId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
//        userId="322";
//        HashMap<String,String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("qaId", qaId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取答疑评价列表
     *
     * @return
     */
    public static Map<String, String> getQueryJudgeData() {
        HashMap<String, String> params = commonURL();
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 提交评价
     *
     * @return
     */
    public static Map<String, String> submitQueryJudge(String qaId, String score, String evaluateIds) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("qaId", qaId);
        params.put("score", score);
        params.put("evaluateIds", evaluateIds);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    public static String changeNull(String str) {
        if (str == null)
            str = "";
        return str;
    }


    public static Map<String, String> getConsigneeList() {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    public static Map<String, String> getConsigneeSaveAndUpdate(AddressBean addressBean, boolean isDef) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        if (addressBean.getId() > 0) {
            params.put("id", addressBean.getId() + "");
        }
        if (isDef) {
            params.put("isDefault", "1");
        } else {
            params.put("address", addressBean.getAddress());
            params.put("addressArea", addressBean.getAddressArea());
            params.put("area", addressBean.getArea() + "");
            params.put("city", addressBean.getCity() + "");
            params.put("province", addressBean.getProvince() + "");
            params.put("consigneeMobile", addressBean.getConsigneeMobile());
            params.put("consigneeName", addressBean.getConsigneeName());
            params.put("isDefault", addressBean.getIsDefault() + "");
            params.put("postCode", TextUtils.isEmpty(addressBean.getPostCode()) ? " " : addressBean.getPostCode());
            params.put("remark", TextUtils.isEmpty(addressBean.getRemark()) ? " " : addressBean.getRemark());
        }


        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 商城商品列表
     *
     * @param page 当前页码
     * @param rows 页面大小
     */
    public static Map<String, String> getShopGoodsList(int page, int rows, int categoryId, int goodsType) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("page", page + "");
        params.put("rows", rows + "");
//        params.put("falg",1+""); //1，升序 2，降序
//        params.put("sort",3+""); //1，价格 2，人气 3，综合
//        params.put("years",0+""); // 年份 查询考季（查全部传0）
        params.put("categoryId", categoryId + ""); //类型
        params.put("venderId", Constants.APP_VENDER_ID);
        params.put("goodsType", goodsType + "");
        params.put("sign", SignUtils.sign(params));

        return params;
    }

    public static Map<String, String> getOrderList(int page, int rows, int type) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("page", page + "");
        params.put("rows", rows + "");
        params.put("orderStatus", type + "");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    public static Map<String, String> getCategoryByPid(int catPid) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("catPid", catPid + "");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 订单详情
     */
    public static Map<String, String> getOrderDetailByOrderId(long orderId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("orderId", orderId + "");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 凑单列表
     *
     * @param venderId 店铺
     * @param promoId  活动
     */
    public static Map<String, String> getCoudan(String venderId, String promoId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("venderId", venderId);
        params.put("promoId", promoId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    public static Map<String, String> deleteConsignee(int id) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("id", id + "");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 随书赠卡激活
     *
     * @return
     */
    public static HashMap<String, String> bookCardActivate(String bookCard) {
        HashMap<String, String> params = commonURL();
        params.put("userId", SharedPrefHelper.getInstance().getUserId());
        params.put("couponNo", bookCard);
        params.put("ip", SystemUtils.getLocalIpAddress());
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 随书赠卡激活明细列表
     *
     * @return
     */
    public static HashMap<String, String> bookCardActivateList(long page) {
        HashMap<String, String> params = commonURL();
        params.put("userId", SharedPrefHelper.getInstance().getUserId());
        params.put("page", page + "");
        params.put("rows", 10 + "");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 随书赠卡激活服务内容查询
     *
     * @return
     */
    public static HashMap<String, String> bookCardActivateServicesInfo(long ruleId) {
        HashMap<String, String> params = commonURL();
        params.put("ruleId", ruleId + "");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 天猫订单号激活
     *
     * @return
     */
    public static HashMap<String, String> tmallOrderActivate(String tmallOrder) {
        HashMap<String, String> params = commonURL();
        params.put("userId", SharedPrefHelper.getInstance().getUserId());
        params.put("userName", SharedPrefHelper.getInstance().getLoginUsername());
        params.put("tmailOrder", tmallOrder);
        params.put("ipAddress", SystemUtils.getLocalIpAddress());
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 天猫订单号激活明细列表
     *
     * @return
     */
    public static HashMap<String, String> tmallOrderActivateList(int page) {
        HashMap<String, String> params = commonURL();
        params.put("userId", SharedPrefHelper.getInstance().getUserId());
        params.put("page", page + "");
        params.put("pageSize", "10");//默认10条
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 二级页面标签
     *
     * @return
     */
    public static HashMap<String, String> getTabList(String examId, String forumId) {
        HashMap<String, String> params = commonURL();
        params.put("examId", examId);
        params.put("forumId", forumId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 用户个人信息
     *
     * @return
     */
    public static HashMap<String, String> getUserInfo() {
        HashMap<String, String> params = commonURL();
        params.put("userId", SharedPrefHelper.getInstance().getUserId());
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 优惠券or现金券
     */
    public static Map<String, String> getUserCoupon(int status, int page, int rows, String couponType) {
        HashMap<String, String> params = commonURL();
        params.put("userId", SharedPrefHelper.getInstance().getUserId() + "");
        params.put("status", status + "");
        params.put("page", page + "");
        params.put("rows", rows + "");
        params.put("couponType", couponType);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取账户安全等级
     *
     * @param userId
     * @return
     */
    public static HashMap<String, String> requestSecurLevel(String userId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 修改email
     *
     * @param userId
     * @param email
     * @param verifyCode
     * @return
     */
    public static HashMap<String, String> modifyEmail(String userId, String email, String verifyCode) {
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("email", email);
        params.put("verifyCode", verifyCode);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 修改phone
     *
     * @param userId
     * @param phone
     * @param verifyCode
     * @return
     */
    public static HashMap<String, String> modifyPhone(String userId, String phone, String verifyCode) {
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("mobilePhone", phone);
        params.put("verifyCode", verifyCode);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param oldPswText
     * @param pswText
     * @return
     */
    public static HashMap<String, String> modifyPsw(String userId, String oldPswText, String pswText) {
        HashMap<String, String> params = commonURL();
        oldPswText = CryptoUtils.md5HexDigest(oldPswText);
        pswText = CryptoUtils.md5HexDigest(pswText);
        params.put("userId", userId);
        params.put("oldPassword", oldPswText);
        params.put("newPassword", pswText);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    public static HashMap<String, String> activityCoupon(String couponNo, boolean isCoupon) {
        HashMap<String, String> params = commonURL();
        params.put("userId", SharedPrefHelper.getInstance().getUserId() + "");
        params.put("couponNo", couponNo);

        if (isCoupon) {
            params.put("couponType", "1");
        } else {
            params.put("couponType", "3");
        }
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 请求我的账户的明细
     *
     * @param userId
     * @param page
     * @param rows
     * @return
     */
    public static HashMap<String, String> requestAccountDetail(String userId, String page, String rows) {
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("page", page);
        params.put("rows", rows);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    public static Map<String, String> getInvoicePage(int page, int rows) {
        HashMap<String, String> params = commonURL();
        params.put("userId", SharedPrefHelper.getInstance().getUserId() + "");
        params.put("page", page + "");
        params.put("rows", rows + "");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取我的开课列表
     */
    public static Map<String, String> requestOpenClassList(String userId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 开通课程
     *
     * @param userId
     * @param userName
     * @param orderId
     * @param orderNo
     * @param detailId
     * @param detailGroupIds
     * @return
     */
    public static HashMap<String, String> openCourse(String userId, String userName, String orderId
            , String orderNo, String detailId, String detailGroupIds, String ip) {
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("userName", userName);
        params.put("orderId", orderId);
        params.put("orderNo", orderNo);
        params.put("detailId", detailId);
        params.put("detailGroupIds", detailGroupIds);
        params.put("ip", ip);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取购物车详情
     *
     * @return
     */
    public static HashMap<String, String> getInvoiceTitle() {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonShopCartURL();
        params.put("userId", userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    public static Map<String, String> findUserByJobnum(String jobNum) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonShopCartURL();
        params.put("userId", userId);
        params.put("jobNum", jobNum);
        params.put("sign", SignUtils.sign(params));
        return params;
    }
    /******************* 试题相关 ↓↓ ***********************/

    /**
     * 开始做题或重新做题
     *
     * @param paperId 试卷id
     * @return
     */
    public static HashMap<String, String> getExamPaper(String paperId) {
        HashMap<String, String> params = commonURL();
        params.put("paperId", paperId);
        params.put("userId", SharedPrefHelper.getInstance().getUserId());
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 我的错题列表
     *
     * @param memberId 学员id
     * @param sid      科目ID
     * @param ctid     题型ID
     * @param page     页码 默认1
     * @param rows     每页记录数默认10 （默认先使用200）
     * @return
     */
    public static HashMap<String, String> getMyWrongExamPaper(String memberId, String sid, String ctid, String page, String rows) {
        HashMap<String, String> params = commonURL();
        params.put("userId", memberId);
        params.put("sid", sid);
        params.put("ctid", ctid);
        params.put("page", page);
        params.put("rows", "200");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 智能系统做复习包中的习题
     * @param userId
     * @param reviewId 复习包id
     * @param choiceTypeId 题型id
     * @return
     */
    public static HashMap<String, String> intelReviewExam(String userId, String reviewId, String choiceTypeId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("reviewId", reviewId);
        params.put("choiceTypeId", choiceTypeId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 更改复习包中习题的状态为复习
     * @param userId
     * @param reviewId
     * @param questionId
     * @return
     */
    public static HashMap<String, String> intelReviewExamChangeStatus(String userId, String reviewId, String questionId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("reviewId", reviewId);
        params.put("questionId", questionId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 删除错题
     * memberId 学员id
     * errorId 错题id
     *
     * @return
     */
    public static HashMap<String, String> deleteWrongQues(String memberId, String errorId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", memberId);
        params.put("errorId", errorId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 从做题记录继续做题
     *
     * @param memberId     学员id
     * @param examRecordId 做题记录id
     * @return
     */
    public static HashMap<String, String> getExamPaperContinueFromRecord(String memberId, String examRecordId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", memberId);
        params.put("examRecordId", examRecordId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 智能系统做题、做真题
     * @param userId
     * @param nodeId 节点Id
     * @param source 'task'：从任务学习该考点;'mind'：从思维导图学习该考点;'extend'：从延展节点学习;'kp'：从知识点学习
     * @param type 考点学习类型：1做题，2做真题，3答疑
     * @return
     */
    public static HashMap<String, String> intelExamStart(String userId, String nodeId,String source,String type,String examRecordId,boolean againDoQuestion) {
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("nodeId", nodeId);
        params.put("source", source);
        params.put("type", type);
        params.put("examRecordId", examRecordId);
        params.put("againDoQuestion",againDoQuestion+"");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 查看全部解析
     *
     * @param memberId     学员id
     * @param examRecordId 做题记录id
     * @return
     */
    public static HashMap<String, String> getExamPaperAnys(String memberId, String examRecordId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", memberId);
        params.put("examRecordId", examRecordId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 推荐做题
     *
     * @param kpId       知识点id
     * @param sSubjectId 考季ID
     * @return
     */
    public static HashMap<String, String> getRecommPaper(String kpId, String sSubjectId) {
        HashMap<String, String> params = commonURL();
        params.put("kpId", kpId);
        params.put("sSubjectId", sSubjectId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 试题详情
     * questionId 试题id
     * sSubjectId 考季ID
     * subjectId 科目ID
     * userID
     *
     * @return
     */
    public static HashMap<String, String> getExamPaperDetail(String questionId, String sSubjectId, String subjectId, String userId) {
        HashMap<String, String> params = commonURL();
        params.put("questionId", questionId);
        params.put("sSubjectId", sSubjectId);
        params.put("subjectId", subjectId);
        params.put("userId", userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 答题报告
     * memberId 学员id
     * examRecordId 做题记录id
     *
     * @return
     */
    public static HashMap<String, String> getExampaperReport(String memberId, String examRecordId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", memberId);
        params.put("examRecordId", examRecordId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 删除收藏的试题
     *
     * @param memberId        学员id
     * @param memberCollectId 试题收藏记录ID
     * @return
     */
    public static HashMap<String, String> deleteFavor(String memberId, String memberCollectId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", memberId);
        params.put("memberCollectId", memberCollectId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取知识点视频播放需要的各种id
     *
     * @param userId     学员id
     * @param sSubjectId 考季id
     * @param kpId       知识点id
     * @return
     */
    public static HashMap<String, String> getKnowledgeVideo(String userId, String sSubjectId, String kpId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("sSubjectId", sSubjectId);
        params.put("kpId", kpId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 试题添加收藏
     *
     * @param memberId     学员id
     * @param sSubjectId   考季ID
     * @param subjectId    科目ID
     * @param choicetypeId 题型ID
     * @param questionId   试题型ID
     * @return
     */
    public static HashMap<String, String> getExampaperAddFavor(String memberId, String sSubjectId, String subjectId, String choicetypeId, String questionId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", memberId);
        params.put("sSubjectId", sSubjectId);
        params.put("subjectId", subjectId);
        params.put("choicetypeId", choicetypeId);
        params.put("questionId", questionId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 查询该题是否有笔记
     *
     * @param userId     学员id
     * @param sSubjectId 考季ID
     * @param questionId 试题ID
     * @return
     */
    public static HashMap<String, String> getQuesIsHaveNotes(String userId, String sSubjectId, String questionId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("sSubjectId", sSubjectId);
        params.put("questionId", questionId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 题库首页
     *
     * @param memberId   学员ID
     * @param subjectId  科目ID，当考季ID不为空时可空（“获取做题记录需要”）
     * @param year       年，当考季ID不为空时可空
     * @param sSubjectId 考季ID，当科目ID和年信息不空时，可以为空
     * @return
     */
    public static HashMap<String, String> getExamMain(String memberId, String subjectId, String year, String sSubjectId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", memberId);
        params.put("sSubjectId", sSubjectId);
        params.put("subjectId", subjectId);
        params.put("year", year);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 历年真题列表
     *
     * @param memberId   学员id
     * @param sSubjectId 考季ID
     * @param paperType  试卷类型ID
     * @param page       页码 默认1
     * @param rows       每页记录数默认10
     * @return
     */
    public static HashMap<String, String> getExamPreviousPaperList(String memberId, String sSubjectId, String paperType, String page, String rows) {
        HashMap<String, String> params = commonURL();
        params.put("userId", memberId);
        params.put("sSubjectId", sSubjectId);
        params.put("paperType", paperType);
        params.put("page", page);
        params.put("rows", rows);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 提交试题反馈
     *
     * @param questionId    试题ID
     * @param sSubjectId    考季ID
     * @param context       勘误内容
     * @param memberId      学员id
     * @param errataTypeIds 勘误类型代码列表（用“,”分隔）
     * @return
     */
    public static HashMap<String, String> postQuestionFeedback(String questionId, String sSubjectId, String context, String memberId, String errataTypeIds) {
        HashMap<String, String> params = commonURL();
        params.put("userId", memberId);
        params.put("sSubjectId", sSubjectId);
        params.put("questionId", questionId);
        params.put("context", context);
        params.put("errataTypeIds", errataTypeIds.substring(0, errataTypeIds.length() - 1));
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 提交试卷查看解析
     *
     * @param memberId       学员id
     * @param examRecordId   做题记录id 没有保存进度的，可以为空，已经保存过进度的，此id必填，否则重新创建新的做题记录
     * @param startTime      开始作答时间，没有保存过进度的必填
     * @param memberExamJson 学员答题明细，只要普通题和题帽小题的信息，题帽大题不要。是CacheMemberExamVo对象组成的列表的json串
     * @param commitType     提交类型：1学员交卷，2系统自动交卷
     * @param paperId        试卷id，提交空白卷（memberExamJson为""）的时候该属性必填
     * @return
     */
    public static HashMap<String, String> postExamPaper(String memberId, String examRecordId, String startTime, String memberExamJson, String commitType, String paperId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", memberId);
        params.put("examRecordId", examRecordId);
        params.put("startTime", startTime);
        params.put("memberExamJson", memberExamJson);
        params.put("commitType", commitType);
        params.put("paperId", paperId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 智能系统交卷
     * @param memberId       学员id
     * @param examRecordId   做题记录id 没有保存进度的，可以为空，已经保存过进度的，此id必填，否则重新创建新的做题记录
     * @param startTime      开始作答时间，没有保存过进度的必填
     * @param memberExamJson 学员答题明细，只要普通题和题帽小题的信息，题帽大题不要。是CacheMemberExamVo对象组成的列表的json串
     * @param commitType     提交类型：1学员交卷，2系统自动交卷
     * @param paperId        试卷id，提交空白卷（memberExamJson为""）的时候该属性必填
     * @param nodeId         节点Id
     * @param source         'task'：从任务学习该考点;'mind'：从思维导图学习该考点;'extend'：从延展节点学习;'kp'：从知识点学习
     * @param type           考点学习类型：1做题，2做真题，3答疑
     * @param examModel      入门测试答题模式:7 节点考试答题模式:8 节点练习答题模式:9 该值从开始做题接口的返回值获取
     * @return
     */
    public static HashMap<String, String> postExamPaperIntel(String memberId, String examRecordId, String startTime, String memberExamJson, String commitType, String paperId
                    ,String nodeId,String source,String type,String examModel) {
        HashMap<String, String> params = commonURL();
        params.put("userId", memberId);
        params.put("examRecordId", examRecordId);
        params.put("startTime", startTime);
        params.put("memberExamJson", memberExamJson);
        params.put("commitType", commitType);
        params.put("paperId", paperId);
        params.put("nodeId", nodeId);
        params.put("source", source);
        params.put("type", type);
        params.put("examModel", examModel);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 保存试卷查看解析
     *
     * @param memberId       学员id
     * @param examRecordId   做题记录id 没有保存进度的，可以为空，已经保存过进度的，此id必填，否则重新创建新的做题记录
     * @param startTime      开始作答时间，没有保存过进度的必填
     * @param memberExamJson 学员答题明细，只要普通题和题帽小题的信息，题帽大题不要。是CacheMemberExamVo对象组成的列表的json串
     * @param platform       平台码，根据所属平台的code决定做题记录的来源(目前废弃，不用传)
     * @return
     */
    public static HashMap<String, String> saveExamPaper(String memberId, String examRecordId, String startTime, String memberExamJson, String platform) {
        HashMap<String, String> params = commonURL();
        params.put("userId", memberId);
        params.put("examRecordId", examRecordId);
        params.put("startTime", startTime);
        params.put("memberExamJson", memberExamJson);
        params.put("platform", platform);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 智能系统保存做题进度
     * @param memberId
     * @param examRecordId
     * @param startTime
     * @param memberExamJson
     * @param platform
     * @param nodeId
     * @param source
     * @param type
     * @param examModel
     * @return
     */
    public static HashMap<String, String> saveExamPaperIntel(String memberId, String examRecordId, String startTime, String memberExamJson, String platform,
                                                             String nodeId,String source,String type,String examModel) {
        HashMap<String, String> params = commonURL();
        params.put("userId", memberId);
        params.put("examRecordId", examRecordId);
        params.put("startTime", startTime);
        params.put("memberExamJson", memberExamJson);
        params.put("platform", platform);
        params.put("nodeId", nodeId);
        params.put("source", source);
        params.put("type", type);
        params.put("examModel", examModel);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 我的错题统计
     *
     * @param userId    学员ID
     * @param subjectId 科目ID
     * @return
     */
    public static HashMap<String, String> mineErrorQuestionStatistic(String userId, String subjectId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("subjectId", subjectId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }


    /**
     * 获取答题报告排名
     * @param userId
     * @param paperId 试卷id
     * @param top 排名的数量，最大50超出抛异常
     * @return
     */
    public static HashMap<String, String> getExamRank(String userId, String paperId,String top) {
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("paperId", paperId);
        params.put("top", top);
        params.put("sign", SignUtils.sign(params));
        return params;
    }


    /********************* 试题相关 ↑↑ *********************/

    /**
     * 我的账户
     *
     * @param userId
     * @return
     */
    public static HashMap<String, String> requestMyAccount(String userId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    public static Map<String, String> orderCancel(String orderIdAndNo, String ip) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonShopCartURL();
        params.put("userId", userId);
        params.put("orderIdAndNo", orderIdAndNo);
        params.put("ip", ip);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 推荐商品(课程、图书)列表
     *
     * @param examId
     * @param shopId
     * @param goodsType 2:课程 3:图书
     * @return
     */
    public static HashMap<String, String> getRecommendedGoodsList(String examId, String shopId, String goodsType) {
        HashMap<String, String> params = commonURL();
        String userId = SharedPrefHelper.getInstance().getUserId();
        params.put("examId", examId);
        params.put("userId", userId);
        params.put("shopId", Constants.APP_VENDER_ID);
        params.put("goodsType", goodsType);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 讲次学员评价
     *
     * @return
     */
    public static HashMap<String, String> getStudentsEvaluateInfo(String lectureId, int pageNo, int pageSize) {
        HashMap<String, String> params = commonURL();
        params.put("lectureId", lectureId);
        params.put("pageNo", pageNo + "");
        params.put("pageSize", pageSize + "");
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 用户相关考种科目列表
     *
     * @return
     */
    public static HashMap<String, String> getExamSubjectSubList() {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 用户相关考种错题数量以及已错题型列表
     *
     * @return
     */
    public static HashMap<String, String> getCountAndChoiceTypes(String subjectId) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("subjectId", subjectId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 用户相关考季下已收藏<答疑>列表
     *
     * @return
     */
    public static HashMap<String, String> getCollectAnswerList(String sSubjectId, int pageNo) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("sSubjectId", sSubjectId);
        params.put("pageNo", pageNo + "");
        params.put("pageSize", "10");//默认10条
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 用户相关考季下已收藏<课程>列表
     *
     * @return
     */
    public static HashMap<String, String> getCollectCourseList(String sSubjectId, int pageNo) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("sSubjectId", sSubjectId);
        params.put("pageNo", pageNo + "");
        params.put("pageSize", "10");//默认10条
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 用户相关考季下已收藏<练习题>列表
     *
     * @return
     */
    public static HashMap<String, String> getCollectExercisesList(String sSubjectId, int page) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("sSubjectId", sSubjectId);
        params.put("page", page + "");
        params.put("rows", "10");//默认10条
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 用户相关考季下<试题笔记>列表
     *
     * @return
     */
    public static HashMap<String, String> getNotesExercisesList(String subjectId, String sSubjectId, int pageNo) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("subjectId", subjectId);
        params.put("sSubjectId", sSubjectId);
        params.put("pageNo", pageNo + "");
        params.put("pageSize", "10");//默认10条
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 用户相关考季下<课程笔记>列表
     *
     * @return
     */
    public static HashMap<String, String> getNotesCourseList(String subjectId, String sSubjectId, int pageNo) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("subjectId", subjectId);
        params.put("sSubjectId", sSubjectId);
        params.put("pageNo", pageNo + "");
        params.put("pageSize", "10");//默认10条
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 我的消息列表
     *
     * @return
     */
    public static HashMap<String, String> getMessageList(int pageNo) {
        HashMap<String, String> params = commonURL();
        params.put("pageNo", pageNo + "");
        params.put("pageSize", "10");//默认10条
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 扫码看课、做题
     *
     * @return
     */
    public static HashMap<String, String> captureQrCode(String pUrl) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("pUrl", pUrl);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 学习记录<课程>列表
     *
     * @return
     */
    public static HashMap<String, String> getLearningRecordCourseList(String subjectId, String sSubjectId, int pageNo) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("subjectId", subjectId);
        params.put("sSubjectId", sSubjectId);
        params.put("pageNo", pageNo + "");
        params.put("pageSize", "10");//默认10条
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 学习记录<题库>列表
     *
     * @return
     */
    public static HashMap<String, String> getLearningRecordExercisesList(String subjectId, String sSubjectId, int page) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("subjectId", subjectId);
        params.put("sSubjectId", sSubjectId);
        params.put("page", page + "");
        params.put("rows", "10");//默认10条
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 欢迎页
     *
     * @return
     */
    public static HashMap<String, String> requestWelcomeData() {
        HashMap<String, String> params = commonURL();
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 图书勘误菜单列表
     *
     * @return
     */
    public static HashMap<String, String> getBooksErrataMenuList(String examId) {
        HashMap<String, String> params = commonURL();
        params.put("examId", examId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 图书勘误列表
     *
     * @return
     */
    public static HashMap<String, String> getBooksErrataList(String bookId, int page) {
        HashMap<String, String> params = commonURL();
        params.put("bookId", bookId);
        params.put("page", page + "");
        params.put("rows", "10");//默认10条
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    public static Map<String, String> addDianZiInvoice(InvoiceRequestBean invoiceRequestBean) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("header", invoiceRequestBean.getHeader() + "");
//		params.put("moblie",invoiceRequestBean.getMoblie()+"");
        if (invoiceRequestBean.getHeader() == Constants.INVOICE_HEADER_COMPANY) {
            params.put("registNumber", invoiceRequestBean.getRegistNumber() + "");
        }
        params.put("type", invoiceRequestBean.getType() + "");
        params.put("orderid", invoiceRequestBean.getOrderid() + "");
        params.put("orderno", invoiceRequestBean.getOrderno());
        params.put("titleId", invoiceRequestBean.getTitleId() + "");
        params.put("title", invoiceRequestBean.getTitle());
        params.put("remark", invoiceRequestBean.getRemark());
        params.put("invoiceList", JSON.toJSONString(invoiceRequestBean.getInvoiceList()));
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    public static Map<String, String> addPuTongZiInvoice(InvoiceRequestBean invoiceRequestBean) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("header", invoiceRequestBean.getHeader() + "");
        params.put("moblie", invoiceRequestBean.getMoblie() + "");
        params.put("receiveName", invoiceRequestBean.getReceiveName());
        params.put("postCode", "0000");
        if (invoiceRequestBean.getHeader() == Constants.INVOICE_HEADER_COMPANY) {
            params.put("registNumber", invoiceRequestBean.getRegistNumber() + "");
        }
        params.put("sendAddress", invoiceRequestBean.getSendAddress());
        params.put("remark", invoiceRequestBean.getRemark());
        params.put("type", invoiceRequestBean.getType() + "");
        params.put("orderid", invoiceRequestBean.getOrderid() + "");
        params.put("orderno", invoiceRequestBean.getOrderno());
        params.put("titleId", invoiceRequestBean.getTitleId() + "");
        params.put("title", invoiceRequestBean.getTitle());
        params.put("invoiceList", JSON.toJSONString(invoiceRequestBean.getInvoiceList()));
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    public static Map<String, String> addInVoiceTitle(String title) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("title", title);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 提交意见反馈
     */
    public static HashMap<String, String> submitFeedback(String title, String content) {
        String userId = SharedPrefHelper.getInstance().getUserId();
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("title", title);
        params.put("content", content);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取版本信息
     */
    public static HashMap<String, String> getVersionDesc() {
        HashMap<String, String> params = commonURL();
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取听课时长或做题数量统计信息
     *
     * @param userId
     * @param sSubjectId
     * @return
     */
    public static HashMap<String, String> requestStatiscalDetail(String userId, String sSubjectId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("subjectId", sSubjectId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 复习讲义
     */
    public static HashMap<String, String> reviewHandout(String reviewId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", SharedPrefHelper.getInstance().getUserId());
        params.put("reviewId", reviewId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    public static String reviewHandoutUrl(String reviewId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", SharedPrefHelper.getInstance().getUserId());
        params.put("reviewId", reviewId);
        params.put("sign", SignUtils.sign(params));
        return ApiInterface.BASE_URL + ApiInterface.REVIEW_HANDOUT + getParams("get", params);
    }

    /**
     * 复习题题型
     */
    public static HashMap<String, String> reviewQusetionType(String reviewId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", SharedPrefHelper.getInstance().getUserId());
        params.put("reviewId", reviewId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 已复习
     */
    public static HashMap<String, String> haveReviewed(String reviewId, String questionId) {
        HashMap<String, String> params = commonURL();
        params.put("userId", SharedPrefHelper.getInstance().getUserId());
        params.put("reviewId", reviewId);
        params.put("questionId", questionId);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 获取知识点统计数据
     * @param userId
     * @param nodeId
     * @param source
     * @return
     */
    public static HashMap<String, String> getKPStatiscialData(String userId, String nodeId, String source) {
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("nodeId", nodeId);
        params.put("source", source);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

    /**
     * 学习统计
     * @param userId
     * @param subjectId
     * @param countType
     * @return
     */
    public static HashMap<String, String> getStudyStatiscialData(String userId,String subjectId,String countType) {
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("subjectId", subjectId);
        params.put("countType", countType);
        params.put("sign", SignUtils.sign(params));
        return params;
    }
    /**
     * 获取学习记录  每日记录、周记录
     * @param userId
     * @param subjectId 科目id
     * @param date yyyy-MM-dd
     * @return
     */
    public static HashMap<String, String> getStudyRecordDayRecord(String userId, String subjectId,String date) {
        HashMap<String, String> params = commonURL();
        params.put("userId", userId);
        params.put("subjectId", subjectId);
        params.put("date", date);
        params.put("sign", SignUtils.sign(params));
        return params;
    }

}
