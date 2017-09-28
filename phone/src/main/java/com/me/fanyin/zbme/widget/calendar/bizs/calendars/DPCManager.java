package com.me.fanyin.zbme.widget.calendar.bizs.calendars;

import android.content.Context;
import android.text.TextUtils;

import com.me.data.local.DayTestDB;
import com.me.data.model.daytest.DayExercise;
import com.me.fanyin.zbme.widget.calendar.entities.DPInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


/**
 * 日期管理器
 * The manager of date picker.
 *
 * @author AigeStudio 2015-06-12
 */
public final class DPCManager {
    private static final HashMap<Integer, HashMap<Integer, DPInfo[][]>> DATE_CACHE = new HashMap<>();

    private static final HashMap<String, Set<String>> DECOR_CACHE_BG = new HashMap<>();
    private static final HashMap<String, Set<String>> DECOR_CACHE_TL = new HashMap<>();
    private static final HashMap<String, Set<String>> DECOR_CACHE_T = new HashMap<>();
    private static final HashMap<String, Set<String>> DECOR_CACHE_TR = new HashMap<>();
    private static final HashMap<String, Set<String>> DECOR_CACHE_L = new HashMap<>();
    private static final HashMap<String, Set<String>> DECOR_CACHE_R = new HashMap<>();

    private static DPCManager sManager;

    private DPCalendar c;

    private DayTestDB dayTestDB;

    private DPCManager(Context context) {
        // 默认显示为中文日历
        String locale = Locale.getDefault().getCountry().toLowerCase();
        if (locale.equals("cn")) {
            initCalendar(new DPCNCalendar());
        } else {
            initCalendar(new DPUSCalendar());
        }
        dayTestDB = new DayTestDB();
    }

    /**
     * 获取月历管理器
     * Get calendar manager
     *
     * @return 月历管理器
     */
    public static DPCManager getInstance(Context context) {
        if (null == sManager) {
            sManager = new DPCManager(context);
        }

        return sManager;
    }

    /**
     * 初始化日历对象
     * <p/>
     * Initialization Calendar
     *
     * @param c ...
     */
    public void initCalendar(DPCalendar c) {
        this.c = c;
    }

    /**
     * 设置有背景标识物的日期
     * <p/>
     * Set date which has decor of background
     *
     * @param date 日期列表 List of date
     */
    public void setDecorBG(List<String> date) {
        setDecor(date, DECOR_CACHE_BG);
    }

    /**
     * 设置左上角有标识物的日期
     * <p/>
     * Set date which has decor on Top left
     *
     * @param date 日期列表 List of date
     */
    public void setDecorTL(List<String> date) {
        setDecor(date, DECOR_CACHE_TL);
    }

    /**
     * 设置顶部有标识物的日期
     * <p/>
     * Set date which has decor on Top
     *
     * @param date 日期列表 List of date
     */
    public void setDecorT(List<String> date) {
        setDecor(date, DECOR_CACHE_T);
    }

    /**
     * 设置右上角有标识物的日期
     * <p/>
     * Set date which has decor on Top right
     *
     * @param date 日期列表 List of date
     */
    public void setDecorTR(List<String> date) {
        setDecor(date, DECOR_CACHE_TR);
    }

    /**
     * 设置左边有标识物的日期
     * <p/>
     * Set date which has decor on left
     *
     * @param date 日期列表 List of date
     */
    public void setDecorL(List<String> date) {
        setDecor(date, DECOR_CACHE_L);
    }

    /**
     * 设置右上角有标识物的日期
     * <p/>
     * Set date which has decor on right
     *
     * @param date 日期列表 List of date
     */
    public void setDecorR(List<String> date) {
        setDecor(date, DECOR_CACHE_R);
    }

    /**
     * 获取指定年月的日历对象数组
     *
     * @param year  公历年
     * @param month 公历月
     * @return 日历对象数组 该数组长度恒为6x7 如果某个下标对应无数据则填充为null
     */
    public DPInfo[][] obtainDPInfo(int year, int month, String examId, String subjectId) {
        HashMap<Integer, DPInfo[][]> dataOfYear = DATE_CACHE.get(year);//当前年份对应的12个月的 月份-月份信息 map
//        if (null != dataOfYear && dataOfYear.size() != 0) {
//            DPInfo[][] dataOfMonth = dataOfYear.get(month);//获取当前月份对应的月份信息
//            if (dataOfMonth != null) {
//                return dataOfMonth;
//            }
//            dataOfMonth = buildDPInfo(year, month,examId,subjectId);
//            dataOfYear.put(month, dataOfMonth);
//            return dataOfMonth;
//        }
        if (null == dataOfYear) dataOfYear = new HashMap<>();
        DPInfo[][] dataOfMonth = buildDPInfo(year, month,examId,subjectId);
        dataOfYear.put((month), dataOfMonth);
        DATE_CACHE.put(year, dataOfYear);
        return dataOfMonth;
    }

    private void setDecor(List<String> date, HashMap<String, Set<String>> cache) {
        for (String str : date) {
            int index = str.lastIndexOf("-");
            String key = str.substring(0, index).replace("-", ":");
            Set<String> days = cache.get(key);
            if (null == days) {
                days = new HashSet<>();
            }
            days.add(str.substring(index + 1, str.length()));
            cache.put(key, days);
        }
    }

    /**
     * 生成当前年份的当前月份对应的月份信息数据
     * @param year
     * @param month
     * @return
     */
    private DPInfo[][] buildDPInfo(int year, int month, String examId, String subjectId) {
        DPInfo[][] info = new DPInfo[6][7];//恒定的6*7表格

        String[][] strG = c.buildMonthG(year, month);//每个格子中对应的数字
        String[][] strF = c.buildMonthFestival(year, month);//每个格子中对应的节日名字

        Set<String> strHoliday = c.buildMonthHoliday(year, month);//每个月的节假日日期
        Set<String> strWeekend = c.buildMonthWeekend(year, month);//当前月份的周末日期集合

        /**
         * 此部分获取的数据必定都为null
         */
        Set<String> decorBG = DECOR_CACHE_BG.get(year + ":" + month);
        Set<String> decorTL = DECOR_CACHE_TL.get(year + ":" + month);
        Set<String> decorT = DECOR_CACHE_T.get(year + ":" + month);
        Set<String> decorTR = DECOR_CACHE_TR.get(year + ":" + month);
        Set<String> decorL = DECOR_CACHE_L.get(year + ":" + month);
        Set<String> decorR = DECOR_CACHE_R.get(year + ":" + month);

        /**
         * 遍历表格，逐个添加单元格数据
         */
        for (int i = 0; i < info.length; i++) {
            for (int j = 0; j < info[i].length; j++) {
                DPInfo tmp = new DPInfo();//具体单元格中的数据
                tmp.strG = strG[i][j];
                if (c instanceof DPCNCalendar) {
                    tmp.strF = strF[i][j].replace("F", "");
                } else {
                    tmp.strF = strF[i][j];
                }
                if (!TextUtils.isEmpty(tmp.strG) && strHoliday.contains(tmp.strG))
                    tmp.isHoliday = true;
                if (!TextUtils.isEmpty(tmp.strG)) tmp.isToday =
                        c.isToday(year, month, Integer.valueOf(tmp.strG));
                if (strWeekend.contains(tmp.strG)) tmp.isWeekend = true;
                if (c instanceof DPCNCalendar) {
                    if (!TextUtils.isEmpty(tmp.strG)) tmp.isSolarTerms =
                            ((DPCNCalendar) c).isSolarTerm(year, month, Integer.valueOf(tmp.strG));
                    if (!TextUtils.isEmpty(strF[i][j]) && strF[i][j].endsWith("F"))
                        tmp.isFestival = true;
                    if (!TextUtils.isEmpty(tmp.strG))
                        tmp.isDeferred = ((DPCNCalendar) c)
                                .isDeferred(year, month, Integer.valueOf(tmp.strG));
                } else {
                    tmp.isFestival = !TextUtils.isEmpty(strF[i][j]);
                }
                /**
                 * 此部分判断必定都为null，无法设置为true
                 */
                if (null != decorBG && decorBG.contains(tmp.strG)) tmp.isDecorBG = true;
                if (null != decorTL && decorTL.contains(tmp.strG)) tmp.isDecorTL = true;
                if (null != decorT && decorT.contains(tmp.strG)) tmp.isDecorT = true;
                if (null != decorTR && decorTR.contains(tmp.strG)) tmp.isDecorTR = true;
                if (null != decorL && decorL.contains(tmp.strG)) tmp.isDecorL = true;
                if (null != decorR && decorR.contains(tmp.strG)) tmp.isDecorR = true;
                String months ;
                if(month>=10)
                    months = month+"";
                else
                    months = "0"+month;
                String days = "";
                if(!tmp.strG.equals("") && Integer.parseInt(tmp.strG)>=10){
                    days += tmp.strG;
                }else if(!tmp.strG.equals("") && Integer.parseInt(tmp.strG)<10){
                    days += "0"+tmp.strG;
                }
                DayExercise dayExercise = dayTestDB.getTodayDayExercise(examId,subjectId,year+"-"+months+"-"+days);
                if(dayExercise != null){
                    tmp.isDoExcercise = true;
                    if(dayExercise.getAnswerRight().equals("1")){
                        tmp.isRight = true;
                    }
                }
                info[i][j] = tmp;
            }
        }

        return info;
    }

    public void setToday(int Year, int Month, int Day) {
        c.setToday(Year,Month,Day);
    }
}
