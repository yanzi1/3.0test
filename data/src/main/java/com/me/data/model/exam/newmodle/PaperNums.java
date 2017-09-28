package com.me.data.model.exam.newmodle;

/**
 * Created by fzw on 2017/7/19 0019.
 * 答题报告中各种题数
 */

public class PaperNums {

    /**
     * small_obj : 普通题（客观）+小题（客观）
     * big_obj : 普通题（客观）+大题（客观）
     * big_sub : 普通题（主观）+大题（主观）
     * small_sub : 普通题（主观）+小题（主观）
     * big : 普通题（全部）+大题（全部）
     * small : 普通题（全部）+小题（全部）
     */

    private String small_obj;
    private String big_obj;
    private String big_sub;
    private String small_sub;
    private String big;
    private String small;

    public String getSmall_obj() {
        return small_obj;
    }

    public void setSmall_obj(String small_obj) {
        this.small_obj = small_obj;
    }

    public String getBig_obj() {
        return big_obj;
    }

    public void setBig_obj(String big_obj) {
        this.big_obj = big_obj;
    }

    public String getBig_sub() {
        return big_sub;
    }

    public void setBig_sub(String big_sub) {
        this.big_sub = big_sub;
    }

    public String getSmall_sub() {
        return small_sub;
    }

    public void setSmall_sub(String small_sub) {
        this.small_sub = small_sub;
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }
}
