/*
 * Copyright 2011 - AndroidQuery.com (tinyeeliu@gmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.me.data.common;

import android.Manifest;

public interface Constants {

    String SCHEME = "da-kq-app";

    String TOKEN_ERROR="com.dongao.kaoqian.phone.widget.TokenErrorActivity";

    int SERVER_PORT = 12364;//httpServer端口
    String M3U8_KEY_SUB = ",URI=\"http://localhost:" + SERVER_PORT + "/";

    String APP_VENDER_ID = "1";//移动端商城id

    String APP_WEBVIEW_TITLE = "app_webview_title"; //显示的名称
    String APP_WEBVIEW_URL = "app_webview_url"; //要加载的url

    String APP_WEBVIEW_TYPE = "app_webview_type"; //从什么地方进入的webview

    int MAX_CACHE_SPACE = 200 * 1024 * 1024; //最小存储空间(MB)

    String REGEX_PHONE = "^1\\d{10}$"; //检测手机号 正则

    /***********       wyc          ***********/
    //没有答疑权限
    int QUERY_NO_AUTHORITY = 49999;       
    
    int VIEW_STATUS_INIT = 1;
    int VIEW_STATUS_SUCCESS = 2;
    int VIEW_STATUS_ERROR_NET = 3;
    int VIEW_STATUS_ERROR_OTHER = 4;
    int VIEW_STATUS_EMPTY = 5;
    int VIEW_STATUS_NO_AUTHORITY = 6;

    int MAIN_FRAGMENT_CHANGE_TYPE_REQUEST = 2001;//获取新的界面的回调
    int MAIN_FRAGMENT_CHANGE_TYPE_RESULT = 2002;//获取新的界面的回调

    int MAIN_FRAGMENT_CLICK_TYPE1 = 2101;
    int MAIN_FRAGMENT_CLICK_TYPE2 = 2102;
    int MAIN_FRAGMENT_CLICK_TYPE3 = 2103;
    int MAIN_FRAGMENT_CLICK_TYPE4 = 2104;
    int MAIN_FRAGMENT_CLICK_TYPE5 = 2105;
    int MAIN_FRAGMENT_CLICK_TYPE6 = 2106;
    int MAIN_FRAGMENT_CLICK_TYPE7 = 2107;
    int MAIN_FRAGMENT_CLICK_TYPE8 = 2108;
    int MAIN_FRAGMENT_CLICK_TYPE9 = 2109;
    int MAIN_FRAGMENT_CLICK_TYPE10 = 2110;
    int MAIN_FRAGMENT_CLICK_TYPE11 = 2111;
    /***********       wyc          ***********/

    /**
     * 页面获取数据的三种方式
     */
    int DATA_FROM_LOADING = 1 << 1;//由普通加载
    int DATA_FROM_REFRESH = 1 << 2;//由下拉刷新
    int DATA_FROM_LOADMORE = 1 << 3;//由上拉加载

    /**
     * 二级页面参数
     */
    String MOULD_CODE = "mouldCode";//模板的 code
    String EXAM_ID = "examId";//考种id
    String SUBJECT_ID = "subjectId";//科目id
    String SSUBJECT_ID = "sSubjectId";//考季id
    String CLASS_ID = "classId";//班级/课程 id
    String FORUM_NAME = "forumName";//版块名称
    String FORUM_ID = "forumId";//版块id
    String TAB_ID = "tabId";//栏目标签id
    String LINK = "link";//链接
    String LECTURE_ID = "lectureId";//讲次id
    String COURSE_ID = "courseId";//课程id
    String END_TIME = "endTime";//视频上次观看进度
    String IS_FREE_PLAY = "isFreePlay";//是否是免费试听
    String COURSE_WARE_BEAN = "cwBean";//课件bean
    String TYPE_ID = "typeId";//分为   1:课程   2:题库    3:答疑
    String TITLE = "title";//分为   1:课程   2:题库    3:答疑



    String VENDER_ID = "vender_id";//店铺Id
    String GOODS_ID = "goods_id";//商品Id
    String ORDER_ID = "order_id";//订单id
    String ADDRESS_ID = "address_id";//地址id

    String PROMO_ID = "promo_id";//活动Id

    String MALL_DETAIL_TYPE = "mall_detail_type";
    String MALL_DETAIL_TYPE_SHOP_CART = "mall_detail_type_shop_cart";

    String COUPONID = "couponId";
    String CASHCOUPONID = "cashCouponId";
    String LEARNINGPRICE = "learningPrice";
    String CASHACCOUNTPRICE = "cashAccountPrice";

    String ORDERUSERACCOUNTBEAN = "orderUserAccountBean";
    String ORPRICEBEAN = "orderPriceBean";

    String ADDRESS_BEAN = "address_bean";//地址实体类
    String CONFIRM_ORDER_COUPONID = "coupon";//
    String CONFIRM_ORDER_YAOQING = "yaoqing";//
    String CONFIRM_ORDER_XIANJIN = "xianjin";//
    String CONFIRM_ORDER_XIANJINZHANGHU = "xianjinzhanghu";//

    String CONFIRM_ORDER_JOBNUM = "jobnum";//
    String CONFIRM_ORDER_ADDRESS = "address";//

    String SHOP_CART_SELECT_TYPE = "shop_cart_select_type"; //购物车选择类型
    int SHOP_CART_SELECT_TYPE_DETAIL = 1; //购物车详情
    int SHOP_CART_SELECT_TYPE_ALLSELECT = 2; //购物车全选
    int SHOP_CART_SELECT_TYPE_ALLSELECT_CANCEL = 3; //购物车取消全选
    int SHOP_CART_SELECT_TYPE_VENDER = 4; //购物车选择店铺
    int SHOP_CART_SELECT_TYPE_VENDER_CANCEL = 5; //购物车取消店铺
    int SHOP_CART_SELECT_TYPE_PRODUCT = 6; //购物车选择商品
    int SHOP_CART_SELECT_TYPE_PRODUCT_CANCEL = 7; //购物车取消选择商品
    int SHOP_CART_SELECT_TYPE_PROMO = 8; //购物车选择促销
    int SHOP_CART_SELECT_TYPE_PRODUCT_NUM = 9; //购物车修改数量
    int SHOP_CART_SELECT_TYPE_SELECT_GIFT = 10; //购物车选择赠品
    int SHOP_CART_SELECT_TYPE_CLEAR_GIFT = 11; //购物车清空赠品
    int SHOP_CART_SELECT_DELETE_PRODUCT = 12; //购物车删除商品
    int SHOP_CART_SELECT_DELETE_ALL_PRODUCT = 13; //购物车删除多个商品

   /* int SHOP_CART_SELECT_TYPE_DETAIL = "shop_cart_select_type_detail"; //购物车详情
    int SHOP_CART_SELECT_TYPE_ALLSELECT = "shop_cart_select_type_allselect"; //购物车全选
    int SHOP_CART_SELECT_TYPE_ALLSELECT_CANCEL = "shop_cart_select_type_cancel"; //购物车取消全选
    int SHOP_CART_SELECT_TYPE_VENDER = "shop_cart_select_type_vender"; //购物车选择店铺
    int SHOP_CART_SELECT_TYPE_VENDER_CANCEL = "shop_cart_select_type_vender_cancel"; //购物车取消店铺
    int SHOP_CART_SELECT_TYPE_PRODUCT = "shop_cart_select_type_product"; //购物车选择商品
    int SHOP_CART_SELECT_TYPE_PRODUCT_CANCEL = "shop_cart_select_type_product_cancel"; //购物车取消选择商品
    int SHOP_CART_SELECT_TYPE_PROMO = "shop_cart_select_type_promo"; //购物车选择促销
    int SHOP_CART_SELECT_TYPE_PRODUCT_NUM = "shop_cart_select_type_product_num"; //购物车修改数量
    int SHOP_CART_SELECT_TYPE_SELECT_GIFT = "shop_cart_select_type_select_type_gift"; //购物车选择赠品
    int SHOP_CART_SELECT_DELETE_PRODUCT = "shop_cart_select_type_delete_product"; //购物车删除商品
*/

    String TAB_CATEGORY = "tabCategory";//标签的分类

    //用户登录注册 请求验证码的发送类型
    String USER_SENT_TYPE_PHONE = "1";
    String USER_SENT_TYPE_EMAIL = "2";
    //用户登录注册 发送内容类型
    String USER_TYPE_REGISTER = "10";
    String USER_TYPE_GET_BACK_PW = "11";
    String USER_TYPE_MODIFY_PW = "12";
    String USER_TYPE_MODIFY_EMAIL = "13";
    String USER_TYPE_MODIFY_PHONE = "14";

    /**
     * 订单状态
     */
    int ORDER_STATUS_ALL = -1; // 全部
    int ORDER_STATUS_WAIT = 0; // 等待付款
    int ORDER_STATUS_COMPLETE = 1; //已经付款
    int ORDER_STATUS_CANCEL = 2; //取消付款
    /**
     * 答疑状态:0=非精华 1=精华
     */
    int QUERY_READ_ESSENCE_0 = 0;
    int QUERY_READ_ESSENCE_1 = 1;
    /**
     * 读取状态:0=新建1=已回复未读3=已读
     */
    int QUERY_READ_STATUS_0 = 0;
    int QUERY_READ_STATUS_1 = 1;
    int QUERY_READ_STATUS_3 = 3;
    /**
     *回答状态:0=未回答1=已回答
     */
    int QUERY_ANSWER_STATUS_0 = 0;
    int QUERY_ANSWER_STATUS_1 = 1;

    /**
     * 答疑问题出处的类型: 1=试题 2= 图书，3课件，4教辅
     */
    int QUERY_QUESTION_TYPE_1 = 1;
    int QUERY_QUESTION_TYPE_2 = 2;
    int QUERY_QUESTION_TYPE_3 = 3;
    int QUERY_QUESTION_TYPE_4 = 4;
    int QUERY_QUESTION_TYPE_5 = 5;
    //首页跳转界面
    String SKIP_TYPE_ID = "skipTypeId";//跳转到的二级页面的类型

    int PAGE_NUMBER=20;
    String POSITION="position";

    /**
     * 优惠券 请求参数类型
     */
    int COUPON_TYPE_EXPIRED = 0;//过期
    int COUPON_TYPE_UN_USE = 1;//未使用
    int COUPON_TYPE_USED = 2;//已经使用


    int COUPON_RULE_TYPE_YOUHUI_XIANJIN = 1;//优惠 现金
    int COUPON_RULE_TYPE_YOUHUI_DAZHE = 2;//优惠 打折
    int COUPON_RULE_TYPE_XIANJIN = 3;//现金圈
    int COUPON_RULE_TYPE_SHENGJI = 4;//升级券
    int COUPON_RULE_TYPE_KECHENG = 5;//课程券
    int COUPON_RULE_TYPE_KAIKE = 6;//开课券

    //答疑的接口的位置
    int QUERY_ACTIVITY_POSITION_0=0;
    //答疑的回答状态 回答状态:0=未回答1=已回答
    int QUERY_STATUS_0=0;

    /**
     * 答疑问题出处的类型: 1：首页 2：课堂 3：商城
     */
    int MAIN_TYPE_1 = 1;
    int MAIN_TYPE_2 = 2;
    int MAIN_TYPE_3 = 3;

    /**
     * 图书激活分类:
     */
    int BOOKS_CARD_ACTIVATE = 0;//随书赠卡
    int TMALL_ORDER_ACTIVATE = 1;//天猫预定

    /**
     * 商城 筛选类型
     */
    int MALL_CATEGORY_TYPE_ALL = 0;   //全部
    int MALL_CATEGORY_TYPE_VIDEO = 2; //课程
    int MALL_CATEGORY_TYPE_BOOK = 3;  //图书

    /**
     * 开通课程
     */
    String OPEN_COURSE_WEI_KAI_TONG="0";
    String OPEN_COURSE_YI_KEI_TONG="2";
    String OPEN_COURSE_CLOSE="3";

    String GOODS_TYPE = "goodsType";//推荐商品的商品类型(1题库 2课程 3图书)

    /**
     * 申请发票
     */
    int INVOICE_TYPE_NO = -1; //未选
    int INVOICE_TYPE_PUTONG = 0; //普通发票
    int INVOICE_TYPE_DIANZI = 1; //电子发票

    int INVOICE_HEADER_NO = -1; //发票抬头
    int INVOICE_HEADER_PERSION = 0; //个人
    int INVOICE_HEADER_COMPANY = 1; //公司
    int INVOICE_FAIL = 7;

    String LOGIN_SUCCESS_TO_PAGE_NAME="loginSuccessToPageName";//要跳转的包名的string
    String IS_TOKEN_ERROR="isTokenError";//要跳转的包名的string

    /**
     * 小能SDK
     */
    String SDK_KEY = "35B8E458-1234-4FE6-8C37-C97DEB111225";
    String SETTING_ID = "kf_9517_1499936898910";//默认客服组id
    String MAIN_SETTING_ID = "kf_9517_1500621806015";//首页客服组id
    String SITE_ID = "kf_9517";
    String[] PERMISSIONS_XN_SDK = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    int COLLECT_ANSWER_NO_PERMISSION = 49999;//收藏答疑无权限
    int COLLECT_EXERCISES_NO_PERMISSION = 30101;//收藏试题无权限
    int LEARNING_RECORD_EXERCISES_NO_PERMISSION = 30101;//学习记录试题无权限
    int LEARNING_RECORD_COURSE_NO_PERMISSION = 29999;//学习记录课程无权限


    /**连接购买信息的userId的MD5加密的言*/
    public static final String USER_MD5_YAN = "4b44a096eaa3c94f33845ae3c6e3d14c";

    String REVIEW_LIST = "review_list";

    public static final int VIEW_STATUS_SAVE_DATA = 10;//保存数据中
    public static final int VIEW_STATUS_SUBMIT = 11;//提交数据中
    public static final int EXAM_TAG_COLLECTION = 20001; //收藏
    public static final int EXAM_TAG_FALT = 20002; //错题
    public static final int EXAM_TAG_ABILITY = 20000; //首页能力评估
    public static final int EXAM_TAG_KNOWLEDGE = 20003; //知识点
    public static final int EXAM_TAG_HIGHFREQUENCY = 20008;//高频考点
    public static final int EXAM_TAG_EVERY_YEAR = 20005; //历年真题

    public static final int EXAM_TAG_REPORT = 20004; //答题报告
    public static final int EXAM_TAG_CONTINU = 20006; //继续做题
    public static final int EXAM_KNOWLEDGE_CONTINU = 20009; //继续做题(knowledgeList里边的重新做题)
    public static final int EXAM_KNOWLEDGE_RESTART = 20010; //重新做题
    public static final int EXAM_ORIGINAL_QUESTION = 20011;//原题
    public static final int EXAM_DO_CONTINUE = 20012;//继续做题，做到当前题
    public static final String EXAM_COLLECT = "exam_collect";//做题-收藏
    public static final String EXAM_TO_RECOMMQUESTION = "exam_to_recommquestion";//做题-查看推荐答疑
    public static final String EXAM_TO_ASKQUESTION = "exam_to_askquestion";//做题-从推荐答疑提问
    public static String ARG_POSITION = "ARG_POSITION";
    public static final String EXAM_TYPE = "exam_type"; //试题类型
    /**
     * 是卷列表的参数
     * 1:课后作业, 3:模拟考试, 4:历年真题, 29:随堂练习
     */
    public static final int EXAMLIST_TYPE_KEHOU = 1;
    public static final int EXAMLIST_TYPE_MONI = 3;
    public static final int EXAMLIST_TYPE_LINIAN = 4;
    public static final int EXAMLIST_TYPE_SUITANG = 29;
}