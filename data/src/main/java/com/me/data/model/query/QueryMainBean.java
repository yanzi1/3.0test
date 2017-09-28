package com.me.data.model.query;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wyc
 */
public class QueryMainBean implements Serializable {
    private List<QueryFragmentBean> list;
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<QueryFragmentBean> getList() {
        return list;
    }

    public void setList(List<QueryFragmentBean> list) {
        this.list = list;
    }
}
