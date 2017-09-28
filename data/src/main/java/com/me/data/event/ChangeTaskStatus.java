package com.me.data.event;

/**
 * Created by fzw on 2017/9/14 0014.
 * 改变知识点为 我会了 状态
 */

public class ChangeTaskStatus {
    private int position;
    private String nodeId;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
