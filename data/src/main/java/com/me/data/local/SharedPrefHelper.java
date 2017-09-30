/*
 *
 *  * Copyright (C) 2015 by  xunice@qq.com
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *
 */

package com.me.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.me.core.app.AppContext;
import com.me.data.event.LogoutSuccessEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * SharedPreferences的工具类
 *
 * @author xunice
 */
public class SharedPrefHelper {
    /**
     * SharedPreferences的名字
     */
    private static final String SP_FILE_NAME = "project";
    private static SharedPrefHelper sharedPrefHelper = null;
    private static SharedPreferences sharedPreferences;


    private static final String ISFIRSTIN_TAG = "isFirstIn";//第一次进入应用
    private static final String IS_LOGIN = "isLogin";// 是否登录
    private static final String IS_THIRD_PARTY_LOGIN = "isThirdPartyLogin";// 是否登录
    private static final String DOWNLOAD_ROOT_PATH = "download_root_path";// sd卡/外置存储 根目录

    private static final String ACCESSTOKEN = "AccessToken";
    private static final String LOGIN_USERNAME = "login_username";// 登录用户名
    private static final String LOGIN_PASSWORD = "login_password";// 登录密码
    private static final String LOGIN_USER_ID = "login_user_id";// user id
    private static final String USER_PHONE_NUMBER = "user_phone_number";// user phoneNumber
    private static final String USER_EMAIL = "user_email";// user eamil
    private static final String USER_AVATAR_IMAGE_URL = "user_avatar_image_url";//user avatar url
    private static final String USER_LEVEL_NAME= "user_level_name";//user level name
    private static final String USER_LEVEL= "user_level";//user level
    private static final String USER_NOWINTEGRAL= "user_nowIntegral";//user nowIntegral
    private static final String USER_GROWTHVALUE= "user_growthValue";//user growthValue
    private static final String OPEN_COURSE_EXPAND_GROUP = "open_course_expand_group";

    private static final String MINI_SCREEN="MINI_screen";//设置小屏播放高度
	private static final String FULL_SCREEN="FULL_screen";//设置全屏播放高度
    private static final String PHONE_FULL_SCREEN="FULL_screen";//设置全屏播放高度
	private static final String IS_NOWIFI_PLAY = "is_wifi_play";//是否nowifi允许播放
	private static final String ISPLAY_ONET = "isplay_onet";

	private static final String SUBJECTID = "subject_id";
    private static final String SUBJECTNAME = "subject_name";
    private static final String COURSE_SUBJECTNAME = "course_subject_name";
    private static final String COURSE_SUBJECTID = "course_subject_id";
	private static final String EXAM_ID = "exam_id";
	private static final String INTEL_EXAM_ID = "intel_exam_id";
	private static final String INTEL_EXAM_NAME = "intel_exam_name";
	private static final String UM_DEVICE_TOKEN = "um_device_token";
    private static final String IS_INTEL = "is_intel";
	private static final String EXAM_NAME = "exam_name";
    private static final String PLAY_LECTURE_SYNC = "play_lecture_sync";
    private static final String PLAY_VOICE_ONLY = "play_voice_only";
    private static final String PLAY_VIDEO_DD = "play_video_dd";
    private static final String PLAY_VIDEO_speed = "play_video_speed";
    private static final String QUERY_MAIN_TYPE = "query_main_type";
    private static final String MAIN_EXAM_ID = "main_exam_id";

    private static final String EXAM_PAPER_TEXT_FONT = "exam_paper_text_font";//试题字体


    private static final String PAY_FROME_WAY = "pay_from_way";//进入支付
    private static final String PAY_ORDERID = "pay_orderid";

    private static final String EXAM_SUBJECT_LIST_JSON = "examSubjectListJson";//用户相关考种科目列表的json字符串

    private static final String MAIN_SUBJECT_ID = "main_subject_id";//用户题库首页选择的科目id
    private static final String MAIN_SSUBJECT_ID = "main_ssubject_id";//用户题库首页选择的年份id
    private static final String MALL_EXAM_ID = "mall_exam_id";
    private static final String MALL_SUBJECT_ID = "mall_subject_id";
    private static final String MALL_TYPE = "mall_type";
    private static final String MAIN_SUBJECT_JSON = "main_subject_json";//科目数据json

    private static final String NOTES_MENU_CACHE = "notes_menu_cache";//我的笔记菜单缓存
    private static final String ERRORS_MENU_CACHE = "errors_menu_cache";//我的错题菜单缓存
    private static final String COLLECT_MENU_CACHE = "collect_menu_cache";//我的收藏菜单缓存
    private static final String ANSWER_MENU_CACHE = "answer_menu_cache";//我的答疑菜单缓存
    private static final String LEARNING_RECORD_MENU_CACHE = "learning_record_menu_cache";//学习记录菜单缓存

	private static final String EXAMID = "exam_id";
	private static final String EXAMINATIONID = "examination_id";
	private static final String SECTIONID = "section_id";
	private static final String MAINTYPEID = "main_type_id";
	private static final String EXAMINATION_TITTLE="examination_tittle";
	private static final String EXAM_LIST_REFRESH="exam_list_refresh_time";
	private static final String EXAMTAG = "exam_tag";
	private static final String SUBJECT_ID = "subject_id";
	private static final String ISFIRSTIN_DUOXUAN = "isFirstInDuoxuan";
	private static final String CLASS_ID = "class_id";
	private static final String FAULTTYPEID = "my_fault_type_id";


	public static synchronized SharedPrefHelper getInstance() {
		if (null == sharedPrefHelper) {
			sharedPrefHelper = new SharedPrefHelper(AppContext.getInstance());
		}
		return sharedPrefHelper;
	}

    private SharedPrefHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(
                SP_FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 是否为第一次进入
     *
     * @param flag
     */
    public void setFirstIn(boolean flag) {
        sharedPreferences.edit().putBoolean(ISFIRSTIN_TAG, flag).apply();
    }
    public boolean isFirstIn() {
        return sharedPreferences.getBoolean(ISFIRSTIN_TAG, true);
    }

    /**
     * 是否登录
     *
     * @param flag
     */
    public void setIsLogin(boolean flag) {
        sharedPreferences.edit().putBoolean(IS_LOGIN, flag).apply();
    }
    public boolean isLogin() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    /**
     * 是否登录
     *
     * @param flag
     */
    public void setIsThirdPartyLogin(boolean flag) {
        sharedPreferences.edit().putBoolean(IS_THIRD_PARTY_LOGIN, flag).apply();
    }
    public boolean isThirdPartyLogin() {
        return sharedPreferences.getBoolean(IS_THIRD_PARTY_LOGIN, false);
    }


    /**
     * 退出登录
     */
    public void logout(){
        setIsLogin(false);
        setUserEmail("");
        setUserAvatarImageUrl("");
        setUserPhoneNumber("");
        setUserId("");
        if (isThirdPartyLogin()){
            setLoginUsername("");
            setIsThirdPartyLogin(false);
        }
        setLevelName("");
        setLevel("");
        setNowIntegral("");
        setGrowthValue("");
        setAccessToken("");
//        setIntel(false);
        EventBus.getDefault().post(new LogoutSuccessEvent());
    }
	/**
	 * 科目id
	 * @param subjectId
	 */
	public void setIntelSubjectId(String subjectId){
		sharedPreferences.edit().putString(SUBJECTID, subjectId).commit();
	}
	public String getIntelSubjectId(){
		return sharedPreferences.getString(SUBJECTID,"");
	}


    /**
     * 考季id
     * @param id
     */
    public void setSsubjectId(String id){
        sharedPreferences.edit().putString(COURSE_SUBJECTID, id).commit();
    }

    public String getSsubjectId(){
        return sharedPreferences.getString(COURSE_SUBJECTID,"");
    }

	/**
	 * 首页考种id
	 * @param subjectId
	 */
	public void setMainExamId(String subjectId){
		sharedPreferences.edit().putString(MAIN_EXAM_ID, subjectId).commit();
	}
	public String getMainExamId(){
		return sharedPreferences.getString(MAIN_EXAM_ID,"");
	}
	
	/**
	 * 智能系统考种id
	 * @param examId
	 */
	public void setIntelExamId(String examId){
		sharedPreferences.edit().putString(getUserId()+INTEL_EXAM_ID, examId).commit();
	}
	public String getIntelExamId(){
		return sharedPreferences.getString(getUserId()+INTEL_EXAM_ID,"");
	}

    /**
     * 和用户绑定
     * 设置智能系统
     */
    public void setIntel(boolean is_intel){
        sharedPreferences.edit().putBoolean(getUserId()+IS_INTEL, is_intel).apply();
    }

    /**
     * 和用户绑定
     * 是否是只能系统
     * @return true 是智能系统
     */
    public boolean isIntel(){
        return sharedPreferences.getBoolean(getUserId()+IS_INTEL, false);
    }
	/**
	 * 智能系统考种名称
	 * @param examId
	 */
	public void setIntelExamName(String examId){
		sharedPreferences.edit().putString(getUserId()+INTEL_EXAM_NAME, examId).apply();
	}
	public String getIntelExamName(){
		return sharedPreferences.getString(getUserId()+INTEL_EXAM_NAME,"");
	}
	/**
	 * 存储deviceToken
	 * @param deviceToken
	 */
	public void setUMDeviceToken(String deviceToken){
		sharedPreferences.edit().putString(UM_DEVICE_TOKEN, deviceToken).apply();
	}
	public String getUMDeviceToken(){
		return sharedPreferences.getString(UM_DEVICE_TOKEN,"");
	}
	
	/**
	 * 考种name
	 * @param examId
	 */
	public void setExamName(String examId){
		sharedPreferences.edit().putString(EXAM_NAME, examId).commit();
	}
	public String getExamName(){
		return sharedPreferences.getString(EXAM_NAME,"");
	}

	/**
	 * 设置手机小屏播放高度
	 * @param hight
	 */
	public void setMiniScreenHight(int hight){
		sharedPreferences.edit().putInt(MINI_SCREEN, hight).commit();
	}
	public int getMiniScreenHight(){
		return sharedPreferences.getInt(MINI_SCREEN, -1);
	}

	/**
	 * 设置手机全屏播放高度
	 * @param hight
	 */
	public void setFullScreenHight(int hight){
		sharedPreferences.edit().putInt(FULL_SCREEN, hight).commit();
	}
	public int getFullScreenHight(){
		return sharedPreferences.getInt(FULL_SCREEN, -1);
	}

    /**
     * 设置手机高度
     * @param hight
     */
    public void setScreenHight(int hight){
        sharedPreferences.edit().putInt(PHONE_FULL_SCREEN, hight).commit();
    }
    public int getScreenHight(){
        return sharedPreferences.getInt(PHONE_FULL_SCREEN, -1);
    }

	/**
	 * 仅在wifi可以用
	 */
	public void set4GCanUse(boolean isPlay){
		sharedPreferences.edit().putBoolean(IS_NOWIFI_PLAY, isPlay).commit();
	}

    /**
     * 仅在wifi下使用
     * @return
     */
	public boolean canUseIn4G(){
		return sharedPreferences.getBoolean(IS_NOWIFI_PLAY, false);
	}

	/**
	 * 是否允许播放
	 *
	 * @param isPlayOnet
	 */
	public void setIsPlayOnet(boolean isPlayOnet) {
		sharedPreferences.edit().putBoolean(ISPLAY_ONET,isPlayOnet).commit();
	}
	public boolean getIsPlayOnet() {
		return sharedPreferences.getBoolean(ISPLAY_ONET, false);
	}

    /**
     * 设置下载根路径
     *
     * @param rootPath
     */
    public void setDownloadRootPath(String rootPath) {
        sharedPreferences.edit().putString(DOWNLOAD_ROOT_PATH, rootPath).apply();
    }
    public String getDownloadRootPath() {
        return sharedPreferences.getString(DOWNLOAD_ROOT_PATH, "");
    }

    /**
     * 3.0登陆 UserId
     *
     * @param userId
     */
    public void setUserId(String userId) {
        sharedPreferences.edit().putString(LOGIN_USER_ID, userId).apply();
    }
    public String getUserId() {
        return sharedPreferences.getString(LOGIN_USER_ID, "");
    }

    /**
     * 登陆 ACCESSTOKEN
     * @param accessToken
     */
    public void setAccessToken(String accessToken){
        sharedPreferences.edit().putString(ACCESSTOKEN,accessToken).apply();
    }
    public String getAccesstoken(){
        return sharedPreferences.getString(ACCESSTOKEN,"");
    }

    /**
     * 登录用户名
     * @param username
     */
    public void setLoginUsername(String username){
        sharedPreferences.edit().putString(LOGIN_USERNAME, username).apply();
    }
    public String getLoginUsername(){
        return sharedPreferences.getString(LOGIN_USERNAME, "");
    }

    /**
     * 登录密码
     * @param password
     */
    public void setLoginPassword(String password){
        sharedPreferences.edit().putString(LOGIN_PASSWORD, password).apply();
    }
    public String getLoginPassword(){
        return sharedPreferences.getString(LOGIN_PASSWORD, "");
    }

    /**
     * 设置用户PhoneNumber
     * @param phoneNumber
     */
    public void setUserPhoneNumber(String phoneNumber){
        sharedPreferences.edit().putString(USER_PHONE_NUMBER, phoneNumber).apply();
    }
    public String getUserPhoneNumber(){
        return sharedPreferences.getString(USER_PHONE_NUMBER, "");
    }

    /**
     * 设置用户Email
     * @param email
     */
    public void setUserEmail(String email){
        sharedPreferences.edit().putString(USER_EMAIL, email).apply();
    }
    public String getUserEmail(){
        return sharedPreferences.getString(USER_EMAIL, "");
    }

    /**
     * 设置用户头像url
     */
    public void setUserAvatarImageUrl(String userAvatarImageUrl) {
        sharedPreferences.edit().putString(USER_AVATAR_IMAGE_URL,userAvatarImageUrl).apply();
    }
    public String getUserAvatarImageUrl(){
        return sharedPreferences.getString(USER_AVATAR_IMAGE_URL,"");
    }

    /**
     * 用户等级名称
     * @param levelName
     */
    public void setLevelName(String levelName){
        sharedPreferences.edit().putString(USER_LEVEL_NAME,levelName).apply();
    }

    public String getLevelName(){
        return sharedPreferences.getString(USER_LEVEL_NAME,"");
    }

    /**
     * 用户等级
     * @param level
     */
    public void setLevel(String level){
        sharedPreferences.edit().putString(USER_LEVEL,level).apply();
    }

    public String getLevel(){
        return sharedPreferences.getString(USER_LEVEL,"");
    }

    /**
     * 用户积分
     * @param nowIntegral
     */
    public void setNowIntegral(String nowIntegral){
        sharedPreferences.edit().putString(USER_NOWINTEGRAL,nowIntegral).apply();
    }

    public String setNowIntegral(){
        return sharedPreferences.getString(USER_NOWINTEGRAL,"");
    }

    /**
     * 用户增长积分
     * @param growthValue
     */
    public void setGrowthValue(String growthValue){
        sharedPreferences.edit().putString(USER_GROWTHVALUE,growthValue).apply();
    }

    public String setGrowthValue(){
        return sharedPreferences.getString(USER_GROWTHVALUE,"");
    }
    /**
     * 设置开通课程默认展开
     */
    public void setOpenCourseExpandGroup(int position ) {
        sharedPreferences.edit().putInt(OPEN_COURSE_EXPAND_GROUP+getUserId(),position).apply();
    }
    public int getOpenCourseExpandGroup(){
        return sharedPreferences.getInt(OPEN_COURSE_EXPAND_GROUP+getUserId(),-1);
    }

    /**
     * 讲义是否同步
     *
     * @param isSync
     */
    public void setIsLectureSync(boolean isSync) {
        sharedPreferences.edit().putBoolean(PLAY_LECTURE_SYNC, isSync).commit();
    }
    public boolean isLectureSync() {
        return sharedPreferences.getBoolean(PLAY_LECTURE_SYNC, true);
    }


    /**
     * 设置默认播放清晰度
     * @param quaity
     */
    public void setPlayQuaity(String quaity){
        sharedPreferences.edit().putString(PLAY_VIDEO_DD, quaity).commit();
    }
    public String getPlayQuaity(){
        return sharedPreferences.getString(PLAY_VIDEO_DD, "标清");
    }

    /**
     * 设置默认播放速度
     * @param speed
     */
    public void setPlaySpeed(Float speed){
        sharedPreferences.edit().putFloat(PLAY_VIDEO_speed, speed).commit();
    }
    public float getPlaySpeed(){
        return sharedPreferences.getFloat(PLAY_VIDEO_speed, 1.0f);
    }



    /**
     * 存储用户的订单
     * @param orderId
     */
    public void setOrderId(String orderId){
        sharedPreferences.edit().putString(PAY_ORDERID, orderId).commit();
    }
    public String getOrderId(){
        return sharedPreferences.getString(PAY_ORDERID, "");
    }


    /**
     * 顶入选择购买页的方式：首页进入，还是订单详情进入
     * @param way
     */
    public void setPayFromWay(String way){
        sharedPreferences.edit().putString(PAY_FROME_WAY+getUserId(), way).commit();
    }
    public String getPayFromWay(){
        return sharedPreferences.getString(PAY_FROME_WAY+getUserId(), "");
    }

	public void setQueryPosition(int position) {
		sharedPreferences.edit().putInt(QUERY_MAIN_TYPE+getUserId(), position).commit();
	}

	public int getQueryPosition(){
		return sharedPreferences.getInt(QUERY_MAIN_TYPE+getUserId(), 0);
	}

	public void setExamPaperTextFont(int fontSize){
        sharedPreferences.edit().putInt(EXAM_PAPER_TEXT_FONT,fontSize).commit();
    }
    public int getExamPaperTextFont(){
        return sharedPreferences.getInt(EXAM_PAPER_TEXT_FONT,0);
    }

    /**
     * 设置用户相关考种科目列表的字符串
     * @param json
     */
    public void setExamSubjectSubListJson(String json){
        sharedPreferences.edit().putString(EXAM_SUBJECT_LIST_JSON,json).commit();
    }
    public String getExamSubjectSubListJson(){
        return sharedPreferences.getString(EXAM_SUBJECT_LIST_JSON, "");
    }

    /**
     * 用户题库首页选择的科目id
     * @param subjectId
     */
    public void setMainSubjectId(String subjectId){
        sharedPreferences.edit().putString(MAIN_SUBJECT_ID,subjectId).commit();
    }
    public String getMainSubjectId(){
        return sharedPreferences.getString(MAIN_SUBJECT_ID, "");
    }

    /**
     * 用户题库首页选择的年份id
     * @param sSubjectId
     */
    public void setMainSsubjectId(String sSubjectId){
        sharedPreferences.edit().putString(MAIN_SSUBJECT_ID,sSubjectId).commit();
    }
    public String getMainSsubjectId(){
        return sharedPreferences.getString(MAIN_SSUBJECT_ID, "");
    }

    /**
     * 科目Json
     * @param mainSubjectJson
     */
    public void setMainSubjectJson(String mainSubjectJson){
        sharedPreferences.edit().putString(MAIN_SUBJECT_JSON,mainSubjectJson).commit();
    }
    public String getMainSubjectJson(){
        return sharedPreferences.getString(MAIN_SUBJECT_JSON, "");
    }


    public void setMallExamId(String examId){
        sharedPreferences.edit().putString(MALL_EXAM_ID,examId).commit();
    }

    public String getMallExamId(){
        return sharedPreferences.getString(MALL_EXAM_ID,"");
    }

    public void setMallSubjectId(String subjectId){
        sharedPreferences.edit().putString(MALL_SUBJECT_ID,subjectId).commit();
    }

    public String getMallSubjectId(){
        return sharedPreferences.getString(MALL_SUBJECT_ID,"");
    }
    public void setMallType(int type){
        sharedPreferences.edit().putInt(MALL_TYPE,type).commit();
    }

    public int getMallType(){
        return sharedPreferences.getInt(MALL_TYPE, -1);
    }

    /**
     * 我的笔记菜单缓存
     */
    public void setNotesMenuCache(String menuCache){
        sharedPreferences.edit().putString(NOTES_MENU_CACHE, menuCache).commit();
    }
    public String getNotesMenuCache(){
        return sharedPreferences.getString(NOTES_MENU_CACHE, "");
    }

    /**
     * 我的错题菜单缓存
     */
    public void setErrorsMenuCache(String menuCache){
        sharedPreferences.edit().putString(ERRORS_MENU_CACHE, menuCache).commit();
    }
    public String getErrorsMenuCache(){
        return sharedPreferences.getString(ERRORS_MENU_CACHE, "");
    }

    /**
     * 我的收藏菜单缓存
     */
    public void setCollectMenuCache(String menuCache){
        sharedPreferences.edit().putString(COLLECT_MENU_CACHE, menuCache).commit();
    }
    public String getCollectMenuCache(){
        return sharedPreferences.getString(COLLECT_MENU_CACHE, "");
    }

    /**
     * 我的答疑菜单缓存
     */
    public void setAnswerMenuCache(String menuCache){
        sharedPreferences.edit().putString(ANSWER_MENU_CACHE, menuCache).commit();
    }
    public String getAnswerMenuCache(){
        return sharedPreferences.getString(ANSWER_MENU_CACHE, "");
    }

    /**
     * 学习记录菜单缓存
     */
    public void setLearningRecordMenuCache(String menuCache){
        sharedPreferences.edit().putString(LEARNING_RECORD_MENU_CACHE, menuCache).commit();
    }
    public String getLearningRecordMenuCache(){
        return sharedPreferences.getString(LEARNING_RECORD_MENU_CACHE, "");
    }


	/**
	 * @author wyc
	 * 获取试卷的开头
	 */
	public void setExaminationTittle(String str) {
		sharedPreferences.edit().putString(EXAMINATION_TITTLE+getUserId(), str).commit();
	}

	public String getExaminationTitle() {
		return sharedPreferences.getString(EXAMINATION_TITTLE+getUserId(), "");
	}
	/**
	 * 试卷
	 * @param examinationId
	 */
	public void setExaminationId(String examinationId){
		sharedPreferences.edit().putString(EXAMINATIONID+getUserId(), examinationId).commit();
	}
	public String getExaminationId(){
		return sharedPreferences.getString(EXAMINATIONID+getUserId(), "");
	}
	/**
	 * 试卷
	 * @param sectionId
	 */
	public void setSectionId(String sectionId){
		sharedPreferences.edit().putString(SECTIONID+getUserId(), sectionId).commit();
	}
	public String getSectionId(){
		return sharedPreferences.getString(SECTIONID+getUserId(), "");
	}

	/**
	 * 首页选中的Type
	 * @param typeId  试卷类型
	 */
	public void setMainTypeId(String typeId){
		sharedPreferences.edit().putString(MAINTYPEID+getUserId(), typeId).commit();
	}
	public String getMainTypeId(){
		return sharedPreferences.getString(MAINTYPEID+getUserId(), "");
	}
	/**
	 * 试卷
	 * @param tag
	 */
	public void setExamTag(int tag){
		sharedPreferences.edit().putInt(EXAMTAG + getUserId(), tag).commit();
	}
	public int getExamTag(){
		return sharedPreferences.getInt(EXAMTAG + getUserId(), 0);
	}
	/**
	 * 选择考试科目
	 * @param examId
	 */
	public void setExamId(String examId){
		sharedPreferences.edit().putString(EXAMID + getUserId(), examId).commit();
	}
	public String getExamId(){
		return sharedPreferences.getString(EXAMID + getUserId(), "");
	}

	/**
	 * 选择考试类型名称
	 * @param subjectName
	 */
	public void setSubjectName(String subjectName){
		sharedPreferences.edit().putString(SUBJECTNAME+getUserId(), subjectName).commit();
	}
	public String getSubjectName(){
		return sharedPreferences.getString(SUBJECTNAME+getUserId(), "");
	}

	/**
	 * 选择考试类型
	 * @param subjectId
	 */
	public void setSubjectId(String subjectId){
		sharedPreferences.edit().putString(SUBJECT_ID+getUserId(), subjectId).commit();
	}
	public String getSubjectId(){
		return sharedPreferences.getString(SUBJECTID+getUserId(), "");
	}

	/**
	 * 是否是第一次进入多选题
	 * @param isfirrstinClassroom
	 */
	public void setIsfirstInDuoxuan(boolean isfirrstinClassroom){
		sharedPreferences.edit().putBoolean(ISFIRSTIN_DUOXUAN + getUserId(), isfirrstinClassroom).commit();
	}
	public boolean getIsfirstInDuoxuan(){
		return sharedPreferences.getBoolean(ISFIRSTIN_DUOXUAN + getUserId(), true);
	}

	/**
	 * 选择班次ID
	 * @param subjectId
	 */
	public void setClassId(String subjectId){
		sharedPreferences.edit().putString(CLASS_ID+getUserId(), subjectId).commit();
	}
	public String getClassId(){
		return sharedPreferences.getString(CLASS_ID+getUserId(), "");
	}

	/**
	 * 选择错题类型
	 * @param subjectId
	 */
	public void setFaultTypeId(String subjectId){
		sharedPreferences.edit().putString(FAULTTYPEID+getUserId(), subjectId).commit();
	}
	public String getFaultTypeId(){
		return sharedPreferences.getString(FAULTTYPEID+getUserId(), "");
	}

}
