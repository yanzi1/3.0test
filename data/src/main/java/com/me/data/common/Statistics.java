package com.me.data.common;

/**
 * Created by fzw on 2017/7/20 0020.
 * 统计事件的id
 */

public interface Statistics {
    /**
     * 启动页
     */
    String MAIN_ADVERTISEMENTPAGE = "Main_advertisementPage";//启动页-启动页广告

    /**
     * 登录、注册
     */
    String PROFILE_REGIST = "Profile_regist";//注册
    String PROFILE_LOGIN_PHONE = "Profile_login_phone";//登录-手机登录
    String PROFILE_LOGIN_WEIBO = "Profile_login_weibo";//登录-微博登录
    String PROFILE_FINDPASSWORD_PHONE = "Profile_findPassword_phone";//忘记密码-手机找回
    String PROFILE_FINDPASSWORD_MAIL = "Profile_findPassword_mail";//忘记密码-邮箱找回

    /**
     * app主页
     */
    String HOMEPAGE = "HomePage";//功能入口-首页
    String CLASSROOM = "Classroom";//功能入口-课堂
    String SHOPPINGMALL = "ShoppingMall";//功能入口-商城
    String PROFILE = "Profile";//功能入口-我的

    /**
     * 首页
     */
    String HOMEPAGE_FEATURED_BANNER = "HomePage_featured_banner";//首页-精选-轮播广告位和更多
    String HOMEPAGE_FEATURED_HEADLINE = "HomePage_featured_headline";//首页-精选-东奥头条和更多
    String HOMEPAGE_FEATURED_INFOMATION = "HomePage_featured_infomation";//首页-精选-热点资讯和更多
    String HOMEPAGE_FEATURED_EXAMTREND = "HomePage_featured_examTrend";//首页-精选-考试动态和更多
    String HOMEPAGE_FEATURED_PERMANENTAD = "HomePage_featured_permanentAd";//首页-精选-固定广告位和更多
    String HOMEPAGE_FEATURED_TEACHER = "HomePage_featured_teacher";//首页-精选-东奥名师和更多
    String HOMEPAGE_FEATURED_LISTENFORFREE = "HomePage_featured_listenForFree";//首页-精选-免费试听和更多
    String HOMEPAGE_FEATURED_HOTCOURSE = "HomePage_featured_hotCourse";//首页-精选-热门课程和更多
    String HOMEPAGE_FEATURED_HOTBOOK = "HomePage_featured_hotBook";//首页-精选-热门图书和更多
    String HOMEPAGE_OTHERS_BANNER = "HomePage_others_banner";//首页-其他频道-轮播广告位和更多
    String HOMEPAGE_OTHERS_EXAMCOUNTDOWN = "HomePage_others_examCountdown";//首页-其他频道-考试倒计时和更多
    String HOMEPAGE_OTHERS_EXAMTREND = "HomePage_others_examTrend";//首页-其他频道-考试动态和更多
    String HOMEPAGE_OTHERS_BOOKCORRENT = "HomePage_others_bookCorrent";//首页-其他频道-图书勘误和更多
    String HOMEPAGE_OTHERS_EXAMSTRATEGY = "HomePage_others_examStrategy";//首页-其他频道-备考攻略和更多
    String HOMEPAGE_OTHERS_PERMANENTAD1 = "HomePage_others_permanentAd1";//首页-其他频道-固定广告位1和更多
    String HOMEPAGE_OTHERS_TEACHER = "HomePage_others_teacher";//首页-其他频道-东奥名师和更多
    String HOMEPAGE_OTHERS_PERMANENTAD2 = "HomePage_others_permanentAd2";//首页-其他频道-固定广告位2和更多
    String HOMEPAGE_OTHERS_LISTENFORFREE = "HomePage_others_listenForFree";//首页-其他频道-免费试听和更多
    String HOMEPAGE_OTHERS_HOTCOURSE = "HomePage_others_hotCourse";//首页-其他频道-热门课程和更多
    String HOMEPAGE_OTHERS_HOTBOOK = "HomePage_others_hotBook";//首页-其他频道-热门图书和更多
    String MAIN_ADVERTISEVIEW = "Main_advertiseView";//首页-推送广告
    String HOMEPAGE_EDITEXAM = "HomePage_editExam";//编辑考种-编辑类目排序

    /**
     * 扫一扫
     */
    String SCANNER_LAUNCH = "Scanner_launch";//扫一扫-启动扫一扫
    String SCANNER_PLAY = "Scanner_play";//扫一扫-扫一扫播放
    String SCANNER_EXERCISE = "Scanner_exercise";//扫一扫-扫一扫看题
    String SCANNER_BUY = "Scanner_buy";//扫一扫-扫一扫购买
    String SCANNER_ACTIVE = "Scanner_Active";//扫一扫-激活

    /**
     * 课程
     */
    String COURSE_PLAY_SMALLSCREEN = "Course_play_smallScreen";//课程-小屏播放
    String COURSE_PLAY_FULLSCREEN = "Course_play_fullScreen";//课程-全屏播放
    String COURSE_PLAY_MP3 = "Course_play_MP3";//课程-音频播放
    String COURSE_FULLSCREEN_DOWNLOADFLUENT = "Course_fullScreen_downloadFluent";//课程-全屏点击下载-下载流畅
    String COURSE_FULLSCREEN_DOWNLOADSD = "Course_fullScreen_downloadSD";//课程-全屏点击下载-下载标清
    String COURSE_FULLSCREEN_DOWNLOADHD = "Course_fullScreen_downloadHD";//课程-全屏点击下载-下载高清
    String COURSE_SMALLSCREEN_DOWNLOADFLUENT = "Course_smallScreen_downloadFluent";//课程-小屏点下载-下载流畅
    String COURSE_SMALLSCREEN_DOWNLOADSD = "Course_smallScreen_downloadSD";//课程-小屏点下载-下载标清
    String COURSE_SMALLSCREEN_DOWNLOADHD = "Course_smallScreen_downloadHD";//课程-小屏点下载-下载高清
    String COURSE_SMALLSCREEN_EXERCISE = "Course_smallScreen_exercise";//课程-小屏随堂练习
    String COURSE_FULLSCREEN_EXERCISE = "Course_fullScreen_exercise";//课程-大屏随堂练习
    String COURSE_FULLSCREEN_CHANGELECTURE = "Course_fullScreen_changeLecture";//课程-大屏切换讲义
    String COURSE_PLAY_FASTPLAY10 = "Course_play_fastplay10";//课程-语速切换-使用播放速度1.0
    String COURSE_PLAY_FASTPLAY12 = "Course_play_fastplay12";//课程-语速切换-使用播放速度1.2
    String COURSE_PLAY_FASTPLAY15 = "Course_play_fastplay15";//课程-语速切换-使用播放速度1.5
    String COURSE_PLAY_FASTPLAY18 = "Course_play_fastplay18";//课程-语速切换-使用播放速度1.8
    String COURSE_CONTINUEPLAY = "Course_continuePlay";//课程-课堂继续播放

    /**
     * 题库
     */
    String EXERCISE_HOMEPAGE = "Exercise_homePage";//试题-题库首页
    String COURSE_CONTINUEEXERCISE = "Course_continueExercise";//试题-继续做题
    String EXERCISE_ACTIVEBOOK = "Exercise_activeBook";//试题-激活图书
    String EXERCISE_TESTPATTERN = "Exercise_testPattern";//试题-做题模式
    String EXERCISE_RECITEPATTERN = "Exercise_recitePattern";//试题-背题模式
    String EXERCISE_SCRATCHPAPER = "Exercise_ScratchPaper";//试题-草稿纸
    String EXERCISE_SUBMITPAPER = "Exercise_submitPaper";//试题-交卷

    /**
     * 答疑
     */
    String ANSWERQUESTION_MY = "AnswerQuestion_my";//答疑-我的答疑
    String ANSWERQUESTION_ESSENCE = "AnswerQuestion_essence";//答疑-精华答疑
    String ANSWERQUESTION_ALL = "AnswerQuestion_all";//答疑-全部答疑
    String ANSWERQUESTION_SEARCH = "AnswerQuestion_search";//答疑-搜索
    String ANSWERQUESTION_BOOKASK = "AnswerQuestion_bookAsk";//答疑-图书提问
    String PROFILE_ACTIVE_BYCODE = "AnswerQuestion_bookActive";//答疑-激活码激活

    /**
     * 商城
     */
    String SHOPPINGMALL_SCANGOODS = "ShoppingMall_scanGoods";//商城-浏览商品页
    String SHOPPINGMALL_ADDTOCART = "ShoppingMall_addToCart";//商城-放入购物车
    String SHOPPINGMALL_SHOPPINGCART = "ShoppingMall_shoppingCart";//商城-购物车
    String SHOPPINGMALL_CHECKOUT = "ShoppingMall_checkOut";//商城-去结算
    String SHOPPINGMALL_SUBMITORDER = "ShoppingMall_SubmitOrder";//商城-提交订单
    String SHOPPINGMALL_PAY_BYWEXIN = "ShoppingMall_pay_byWeXin";//商城-微信支付
    String SHOPPINGMALL_PAY_BYALIPAY = "ShoppingMall_pay_byAlipay";//商城-支付宝支付
    String SHOPPINGMALL_PAY_ORDER = "ShoppingMall_pay_order";//商城-支付订单
    String SHOPPINGMALL_PAY_FINISHED = "ShoppingMall_pay_finished";//商城-完成交易

    /**
     * 我的
     */
    String PROFILE_DOWNLOAD = "Profile_download";//我的-我的下载主页
    String PROFILE_WRONGEXERCISE = "Profile_wrongExercise";//我的-我的错题
    String PROFILE_NOTE = "Profile_note";//我的-我的笔记
    String PROFILE_ORDER = "Profile_order";//我的-我的订单
    String PROFILE_COLLECTION = "Profile_collection";//我的-我的收藏
    String PROFILE_ANSWERQUESTION = "Profile_answerQuestion";//我的-我的答疑
    String PROFILE_STUDYRECORD_CONTINUEPLAY = "Profile_studyRecord_continuePlay";//我的-学习记录-继续播放
    String PROFILE_STUDYRECORD_CONTINUEEXERCISE = "Profile_studyRecord_continueExercise";//我的-学习记录-继续做题
    String PROFILE_MESSAGE = "Profile_message";//我的-我的消息
    String PROFILE_ACCOUNT_ACTIVECOUPON = "Profile_account_activeCoupon";//我的-我的账户-激活优惠券
    String PROFILE_ACCOUNT_ACTIVECASHCOUPONS = "Profile_account_activeCashCoupons";//我的-我的账户-激活现金券
    String PROFILE_INVOICE_APPLYINVOICE = "Profile_invoice_applyInvoice";//我的-我的发票-申请发票
    String PROFILE_ACTIVEBOOK_BYCARD = "Profile_activeBook_byCard";//我的-激活图书-随书赠卡
    String PROFILE_ACTIVEBOOK_BYTMALL = "Profile_activeBook_byTMall";//我的-激活图书-天猫预定
    String PROFILE_DOWNLOAD_FLUENT = "Profile_Download_fluent";//我的-下载和播放设置页-下载流畅
    String PROFILE_DOWNLOAD_SD = "Profile_Download_SD";//我的-下载和播放设置页-下载标清
    String PROFILE_DOWNLOAD_HD = "Profile_Download_HD";//我的-下载和播放设置页-下载高清
    String PROFILE_ONLINECUSTOMERSERVICE = "Profile_onlineCustomerService";//我的-在线客服
    String PROFILE_ACTIVEBOOK = "Profile_activeBook";//我的-激活图书我的入口

}
