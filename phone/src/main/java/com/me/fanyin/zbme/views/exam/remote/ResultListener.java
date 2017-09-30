package com.me.fanyin.zbme.views.exam.remote;

/**
 * Created by xunice on 16/3/24.
 */
public interface ResultListener {
    public void onSuccess(String json);
    public void onError(Exception e);
}