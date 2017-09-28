package com.me.data.model.exam.newmodle;

/**
 * Created by fzw on 2017/6/2 0002.
 * 试题返回题型列表modle
 */

public class ExamFeedbackType {

    /**
     * errataTypeName : 勘误类型名称
     * errataTypeId : 勘误类型标识
     */

    private String errataTypeName;
    private String errataTypeId;
    private boolean isChecked;//是否选中

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getErrataTypeName() {
        return errataTypeName;
    }

    public void setErrataTypeName(String errataTypeName) {
        this.errataTypeName = errataTypeName;
    }

    public String getErrataTypeId() {
        return errataTypeId;
    }

    public void setErrataTypeId(String errataTypeId) {
        this.errataTypeId = errataTypeId;
    }
}
