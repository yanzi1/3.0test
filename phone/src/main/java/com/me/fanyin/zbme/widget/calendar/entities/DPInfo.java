package com.me.fanyin.zbme.widget.calendar.entities;

/**
 * 日历数据实体
 * 封装日历绘制时需要的数据
 * 
 * Entity of calendar
 *
 * @author AigeStudio 2015-03-26
 */
public class DPInfo {
    public String strG, strF;
    public boolean isHoliday;
    public boolean isChoosed;
    public boolean isToday, isWeekend;
    public boolean isSolarTerms, isFestival, isDeferred;
    public boolean isDecorBG;
    public boolean isDecorTL, isDecorT, isDecorTR, isDecorL, isDecorR;
    public boolean isDoExcercise = false;//这天是否有做过练习
    public boolean isRight = false;//是否做对了
}