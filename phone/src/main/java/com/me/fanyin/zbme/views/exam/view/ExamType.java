package com.me.fanyin.zbme.views.exam.view;

import com.yunqing.core.db.annotations.Id;
import com.yunqing.core.db.annotations.Table;

import java.io.Serializable;

/**
 *
 *  题型 单选题  多选题  判断题  简单题等
 *
 */

@Table(name="t_exam_type")
public class ExamType implements Serializable{

    @Id(autoIncrement = false)
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
