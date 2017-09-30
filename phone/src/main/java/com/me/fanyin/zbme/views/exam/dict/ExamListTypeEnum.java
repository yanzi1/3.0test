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

package com.me.fanyin.zbme.views.exam.dict;


import com.me.data.common.Constants;

/**
 * 是卷列表类型
 * @author wyc
 */
public enum ExamListTypeEnum implements BaseEnum {

//          1:课后作业, 3:模拟考试, 4:历年真题, 29:随堂练习

    EXAMLIST_TYPE_KEHOU {
        @Override
        public int getId() {
            return Constants.EXAMLIST_TYPE_KEHOU;
        }

        @Override
        public String getName() {
            return "课后作业";
        }
    },
    EXAMLIST_TYPE_MONI {
        @Override
        public int getId() {
            return Constants.EXAMLIST_TYPE_MONI;
        }

        @Override
        public String getName() {
            return "模拟考试";
        }
    },
    EXAMLIST_TYPE_LINIAN {
        @Override
        public int getId() {
            return Constants.EXAMLIST_TYPE_LINIAN;
        }

        @Override
        public String getName() {
            return "历年真题";
        }
    },
    EXAMLIST_TYPE_SUITANG {
        @Override
        public int getId() {
            return Constants.EXAMLIST_TYPE_SUITANG;
        }

        @Override
        public String getName() {
            return "随堂练习";
        }
    };




    public static String getValue(int key) {
        String value = "";
        switch (key) {
            case Constants.EXAMLIST_TYPE_KEHOU:
                value = ExamListTypeEnum.EXAMLIST_TYPE_KEHOU.getName();
                break;
            case Constants.EXAMLIST_TYPE_MONI:
                value = ExamListTypeEnum.EXAMLIST_TYPE_MONI.getName();
                break;
            case Constants.EXAMLIST_TYPE_LINIAN:
                value = ExamListTypeEnum.EXAMLIST_TYPE_LINIAN.getName();
                break;
            case Constants.EXAMLIST_TYPE_SUITANG:
                value = ExamListTypeEnum.EXAMLIST_TYPE_SUITANG.getName();
                break;
            default:
                break;
        }
        return value;
    }

}
