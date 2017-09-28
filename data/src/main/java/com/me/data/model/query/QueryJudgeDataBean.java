package com.me.data.model.query;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wyc
 */
public class QueryJudgeDataBean implements Serializable {
    private float level;
    private List<QueryJudgeBean> list;

    public float getLevel() {
        return level;
    }

    public void setLevel(float level) {
        this.level = level;
    }

    public List<QueryJudgeBean> getList() {
        return list;
    }

    public void setList(List<QueryJudgeBean> list) {
        this.list = list;
    }
}
