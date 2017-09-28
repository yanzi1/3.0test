package com.me.data.event;

/**
 * Created by wyc on 2017/5/24.
 */

public class PostNoteEvent {

    private String noteId;
    private String hanConId;
    private String type;    //0 添加 1 删除

    public PostNoteEvent(String noteId,String hanConId,String type){
        this.noteId=noteId;
        this.hanConId=hanConId;
        this.type=type;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getHanConId() {
        return hanConId;
    }

    public void setHanConId(String hanConId) {
        this.hanConId = hanConId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
