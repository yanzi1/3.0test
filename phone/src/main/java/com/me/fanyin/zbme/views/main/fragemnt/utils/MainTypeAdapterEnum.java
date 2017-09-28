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

package com.me.fanyin.zbme.views.main.fragemnt.utils;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.me.fanyin.zbme.views.main.fragemnt.adapter.MainTypeAdapter;
import com.me.fanyin.zbme.views.main.fragemnt.adapter.MainTypeDetailAdapter2;
import com.me.fanyin.zbme.views.main.fragemnt.adapter.MainTypeDetailAdapter3;
import com.me.fanyin.zbme.views.main.fragemnt.adapter.MainTypeDetailAdapter4;
import com.me.fanyin.zbme.views.main.fragemnt.adapter.MainTypeDetailAdapter5;
import com.me.fanyin.zbme.views.main.fragemnt.adapter.MainTypeDetailAdapter6;
import com.me.fanyin.zbme.views.main.fragemnt.adapter.MainTypeDetailAdapter7;

/**
 * 首页列表类型
 * @author wyc
 */
public enum MainTypeAdapterEnum implements BaseEnum {


//    MAIN_TYPE_ADAPTER_1 {
//        @Override
//        public int getId() {
//            return Constants.MAIN_TYPE_ADAPTER_1;
//        }
//
//        @Override
//        public BaseQuickAdapter getAdapter() {
//            return new MainTypeAdapter();
//        }
//    },
    MAIN_TYPE_ADAPTER_2 {
        @Override
        public int getId() {
            return MainTypeAdapter.TypeEntity.TYPE_2;
        }

        @Override
        public BaseQuickAdapter getAdapter() {
            return new MainTypeDetailAdapter2();
        }
    },
    MAIN_TYPE_ADAPTER_3 {
        @Override
        public int getId() {
            return MainTypeAdapter.TypeEntity.TYPE_3;
        }

        @Override
        public BaseQuickAdapter getAdapter() {
            return new MainTypeDetailAdapter3();
        }
    },
    MAIN_TYPE_ADAPTER_4 {
        @Override
        public int getId() {
            return MainTypeAdapter.TypeEntity.TYPE_4;
        }

        @Override
        public BaseQuickAdapter getAdapter() {
            return new MainTypeDetailAdapter4();
        }
    },
    MAIN_TYPE_ADAPTER_5 {
        @Override
        public int getId() {
            return MainTypeAdapter.TypeEntity.TYPE_5;
        }

        @Override
        public BaseQuickAdapter getAdapter() {
            return new MainTypeDetailAdapter5();
        }
    },
    MAIN_TYPE_ADAPTER_6 {
        @Override
        public int getId() {
            return MainTypeAdapter.TypeEntity.TYPE_6;
        }

        @Override
        public BaseQuickAdapter getAdapter() {
            return new MainTypeDetailAdapter6();
        }
    },
    MAIN_TYPE_ADAPTER_7 {
        @Override
        public int getId() {
            return MainTypeAdapter.TypeEntity.TYPE_7;
        }

        @Override
        public BaseQuickAdapter getAdapter() {
            return new MainTypeDetailAdapter7();
        }
    };


    public static BaseQuickAdapter getAdapter(int key) {
        BaseQuickAdapter value = null;
        switch (key) {
//            case MainTypeAdapter.TypeEntity.TYPE_1:
//                value = MainTypeAdapterEnum.MAIN_TYPE_ADAPTER_1.getAdapter();
//                break;
            case MainTypeAdapter.TypeEntity.TYPE_2:
                value = MainTypeAdapterEnum.MAIN_TYPE_ADAPTER_2.getAdapter();
                break;
            case MainTypeAdapter.TypeEntity.TYPE_3:
                value = MainTypeAdapterEnum.MAIN_TYPE_ADAPTER_3.getAdapter();
                break;
            case MainTypeAdapter.TypeEntity.TYPE_4:
                value = MainTypeAdapterEnum.MAIN_TYPE_ADAPTER_4.getAdapter();
                break;
            case MainTypeAdapter.TypeEntity.TYPE_5:
                value = MainTypeAdapterEnum.MAIN_TYPE_ADAPTER_5.getAdapter();
                break;
            case MainTypeAdapter.TypeEntity.TYPE_6:
                value = MainTypeAdapterEnum.MAIN_TYPE_ADAPTER_6.getAdapter();
                break;
            case MainTypeAdapter.TypeEntity.TYPE_7:
                value = MainTypeAdapterEnum.MAIN_TYPE_ADAPTER_7.getAdapter();
                break;
            default:
                break;
        }
        return value;
    }

}
