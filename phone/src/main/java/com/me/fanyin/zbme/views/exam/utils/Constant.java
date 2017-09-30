package com.me.fanyin.zbme.views.exam.utils;

/**
 * Created by wyc on 2016/4/20.
 */
public class Constant {
    //聊天的类型
    public static final int CHATTYPE_SINGLE = 1;
    public static final int CHATTYPE_GROUP = 2;
    public static final int CHATTYPE_CHATROOM = 3;
    //当前显示的最大聊天数
    public static final int pagesize=5;
    //聊天adapter中数据
    public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
    public static final String GROUP_USERNAME = "item_groups";
    public static final String CHAT_ROOM = "item_chatroom";
    public static final String MESSAGE_ATTR_IS_VOICE_CALL = "is_voice_call";
    public static final String MESSAGE_ATTR_IS_VIDEO_CALL = "is_video_call";
    public static final String ACCOUNT_REMOVED = "account_removed";
    public static final String CHAT_ROBOT = "item_robots";
    public static final String MESSAGE_ATTR_ROBOT_MSGTYPE = "msgtype";

    public static final int REQUEST_CODE_EMPTY_HISTORY = 2;
    public static final int REQUEST_CODE_CONTEXT_MENU = 3;
    private static final int REQUEST_CODE_MAP = 4;
    public static final int REQUEST_CODE_TEXT = 5;
    public static final int REQUEST_CODE_VOICE = 6;
    public static final int REQUEST_CODE_PICTURE = 7;
    public static final int REQUEST_CODE_LOCATION = 8;
    public static final int REQUEST_CODE_FILE = 10;
    public static final int REQUEST_CODE_VIDEO = 14;
    public static final int REQUEST_CODE_ADD_TO_BLACKLIST = 25;

    public static final int STUDY_BAR_TYPE_1 =1;
    public static final int STUDY_BAR_TYPE_2 = 2;
    public static final int STUDY_BAR_TYPE_3 = 3;
    public static final int STUDY_BAR_TYPE_4 = 4;
    
    /**显示页面类型 0:数据正常  1：网络连接失败  2：内容为空  3:服务器错误*/
    public static final int VIEW_TYPE_0 =0;
    public static final int VIEW_TYPE_1 = 1;
    public static final int VIEW_TYPE_2 = 2;
    public static final int VIEW_TYPE_3 = 3;

    /*** 收藏的类型  1.题  2.答疑 3.视频 4.web文件 */
    public static final String COLLECTION_TYPE_1 =1+"";
    public static final String COLLECTION_TYPE_2 = 2+"";
    public static final String COLLECTION_TYPE_3 = 3+"";
    public static final String COLLECTION_TYPE_4 = 4+"";

    /*** 设置友盟推送的别称的类型 */
    public static final String UMENG_BIECHENG_TYPE = "DONGAO";
    /**
     * 进入选择购买页面的方式
     * cart是首页进入  myOrder是订单进入
     */
    public static final String PAY_WAY_FROM_SHOUYE = "cart";
    public static final String PAY_WAY_FROM_DINGDAN = "myOrder";

    public static final String EXAM_TYPE = "exam_type"; //试题类型
    public static final String QUESTION_COLLECT = "question_collect";//精华答疑-收藏答疑
    public static final String EXAM_COLLECT = "exam_collect";//做题-收藏

}
