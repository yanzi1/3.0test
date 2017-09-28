/*
 *
 *  * Copyright (C) 2015 by  xunice@qq.com
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *
 */

package com.me.data.model.play;

import java.io.Serializable;

public class BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2998261183587832144L;
    private String body;
    private VerifyBean result;

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public VerifyBean getResult() {
        return result;
    }

    public void setResult(VerifyBean result) {
        this.result = result;
    }
}
