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

/**
 * 题目类型
 *
 * @author wyc
 */
public enum ExamTypeEnum implements BaseEnum {

//            1:  	// 单项选择题	
//            2:   	// 多项选择题	
//            3:      // 判断题	
//            11: 	// 不定向选择题
//            4:  	// 简答题	
//            5:      // 计算题	
//            6:     	// 综合题	
//            7:      // 其它	
//            8:      // 计算分析题
//            9:      // 案例分析题
//            10:		// 操作题
//            12:		// 实操题
//            13:  	// 计算题（单选模式）	
//            14:   	// 综合分析题（不定项模式）	
//            15:		// 从业计算题
    /*****
     * 上面是2016.12.08之前使用的旧版本的boss系统使用 ，下面是新版本使用
     *************/
//            1:  	// 判断题模式
//            2:   	// 单选模式
//            3:      //多选题
//            4:  	// 简答题
//            5:      //从业计算题模式
//            6:      //不定项
//            7:      //综合分析题


//    EXAM_TYPE_DANXUAN {
//        @Override
//        public int getId() {
//            return 1;
//        }
//
//        @Override
//        public String getName() {
//            return "单选题";
//        }
//    },
//    EXAM_TYPE_DUOXUAN {
//        @Override
//        public int getId() {
//            return 2;
//        }
//
//        @Override
//        public String getName() {
//            return "多选题";
//        }
//    },
//    EXAM_TYPE_PANDUAN {
//        @Override
//        public int getId() {
//            return 3;
//        }
//
//        @Override
//        public String getName() {
//            return "判断题";
//        }
//    },
//    EXAM_TYPE_BUDINGXIANG {
//        @Override
//        public int getId() {
//            return 11;
//        }
//
//        @Override
//        public String getName() {
//            return "不定项选择题";
//        }
//    },
//    //题冒题
//    EXAM_TYPE_TIMAOTI {
//        @Override
//        public int getId() {
//            return 0;
//        }
//
//        @Override
//        public String getName() {
//            return "综合题";
//        }
//    },
//    //简答题
//    EXAM_TYPE_JIANDA{
//        @Override
//        public int getId() {
//            return 4;
//        }
//
//        @Override
//        public String getName() {
//            return "简答题";
//        }
//    },
//    //计算题
//    EXAM_TYPE_JISUANTI{
//        @Override
//        public int getId() {
//            return 5;
//        }
//
//        @Override
//        public String getName() {
//            return "计算题";
//        }
//    },
//    //综合体
//    EXAM_TYPE_ZHONGHE{
//        @Override
//        public int getId() {
//            return 6;
//        }
//
//        @Override
//        public String getName() {
//            return "综合题";
//        }
//    },
//    //其他
//    EXAM_TYPE_QITA{
//        @Override
//        public int getId() {
//            return 7;
//        }
//
//        @Override
//        public String getName() {
//            return "其他";
//        }
//    },
//    //计算分析题
//    EXAM_TYPE_JISUANFENXITI{
//        @Override
//        public int getId() {
//            return 8;
//        }
//
//        @Override
//        public String getName() {
//            return "计算分析题";
//        }
//    },
//    //案例分析题
//    EXAM_TYPE_ANLIFENXITI{
//        @Override
//        public int getId() {
//            return 9;
//        }
//
//        @Override
//        public String getName() {
//            return "案例分析题";
//        }
//    },
//    //操作题
//    EXAM_TYPE_CAOZUO{
//        @Override
//        public int getId() {
//            return 10;
//        }
//
//        @Override
//        public String getName() {
//            return "操作题";
//        }
//    },
//    //实操讲解题
//    EXAM_TYPE_SHICAOJIANGJIE{
//        @Override
//        public int getId() {
//            return 12;
//        }
//
//        @Override
//        public String getName() {
//            return "实操讲解题";
//        }
//    },
//    //计算题（单选模式）
//    EXAM_TYPE_JISUANDANXUANMOSHI{
//        @Override
//        public int getId() {
//            return 13;
//        }
//
//        @Override
//        public String getName() {
//            return "计算题（单选模式）";
//        }
//    },
//    //综合分析题
//    EXAM_TYPE_ZHONGHEFENXI{
//        @Override
//        public int getId() {
//            return 14;
//        }
//
//        @Override
//        public String getName() {
//            return "综合分析题";
//        }
//    },
//    //从业计算题
//    EXAM_TYPE_CONGYEJISUAN{
//        @Override
//        public int getId() {
//            return 15;
//        }
//
//        @Override
//        public String getName() {
//            return "从业计算题";
//        }
//    };


//    public static String getValue(int key) {
//        String value = "";
//        switch (key) {
//            case 1:
//                value = ExamTypeEnum.EXAM_TYPE_DANXUAN.getName();
//                break;
//            case 2:
//                value = ExamTypeEnum.EXAM_TYPE_DUOXUAN.getName();
//                break;
//            case 3:
//                value = ExamTypeEnum.EXAM_TYPE_PANDUAN.getName();
//                break;
//            case 4:
//                value = ExamTypeEnum.EXAM_TYPE_JIANDA.getName();
//                break;
//            case 5:
//                value = ExamTypeEnum.EXAM_TYPE_JISUANTI.getName();
//                break;
//            case 6:
//                value = ExamTypeEnum.EXAM_TYPE_ZHONGHE.getName();
//                break;
//            case 7:
//                value = ExamTypeEnum.EXAM_TYPE_QITA.getName();
//                break;
//            case 8:
//                value = ExamTypeEnum.EXAM_TYPE_JISUANFENXITI.getName();
//                break;
//            case 9:
//                value = ExamTypeEnum.EXAM_TYPE_ANLIFENXITI.getName();
//                break;
//            case 10:
//                value = ExamTypeEnum.EXAM_TYPE_CAOZUO.getName();
//                break;
//            case 11:
//            value = ExamTypeEnum.EXAM_TYPE_BUDINGXIANG.getName();
//                break;
//            case 12:
//                value = ExamTypeEnum.EXAM_TYPE_SHICAOJIANGJIE.getName();
//                break;
//            case 13:
//                value = ExamTypeEnum.EXAM_TYPE_JISUANDANXUANMOSHI.getName();
//                break;
//            case 14:
//                value = ExamTypeEnum.EXAM_TYPE_ZHONGHEFENXI.getName();
//                break;
//            case 15:
//                value = ExamTypeEnum.EXAM_TYPE_CONGYEJISUAN.getName();
//                break;
//            case 100:
//                value = ExamTypeEnum.EXAM_TYPE_TIMAOTI.getName();
//                break;
//            default:
//                break;
//        }
//        return value;
//    }
    EXAM_TYPE_PANDUAN {
        @Override
        public int getId() {
            return 1;
        }

        @Override
        public String getName() {
            return "判断题";
        }
    },
    EXAM_TYPE_DANXUAN {
        @Override
        public int getId() {
            return 2;
        }

        @Override
        public String getName() {
            return "单选题";
        }
    },
    EXAM_TYPE_DUOXUAN {
        @Override
        public int getId() {
            return 3;
        }

        @Override
        public String getName() {
            return "多选题";
        }
    },
    //简答题
    EXAM_TYPE_JIANDA {
        @Override
        public int getId() {
            return 4;
        }

        @Override
        public String getName() {
            return "简答题";
        }
    },
    //从业计算题
    EXAM_TYPE_CONGYEJISUAN {
        @Override
        public int getId() {
            return 5;
        }

        @Override
        public String getName() {
            return "从业计算题";
        }
    },
    EXAM_TYPE_BUDINGXIANG {
        @Override
        public int getId() {
            return 6;
        }

        @Override
        public String getName() {
            return "不定项选择题";
        }
    },
    //综合分析题
    EXAM_TYPE_ZHONGHEFENXI {
        @Override
        public int getId() {
            return 7;
        }

        @Override
        public String getName() {
            return "综合分析题";
        }
    },
    //综合体
    EXAM_TYPE_ZHONGHE {
        @Override
        public int getId() {
            return 117;
        }

        @Override
        public String getName() {
            return "综合题";
        }
    },
    //题冒题
    EXAM_TYPE_TIMAOTI {
        @Override
        public int getId() {
            return 100;
        }

        @Override
        public String getName() {
            return "综合题";
        }
    },
    //其他
    EXAM_TYPE_QITA {
        @Override
        public int getId() {
            return 107;
        }

        @Override
        public String getName() {
            return "其他";
        }
    },
    //计算分析题
    EXAM_TYPE_JISUANFENXITI {
        @Override
        public int getId() {
            return 108;
        }

        @Override
        public String getName() {
            return "计算分析题";
        }
    },
    //案例分析题
    EXAM_TYPE_ANLIFENXITI {
        @Override
        public int getId() {
            return 109;
        }

        @Override
        public String getName() {
            return "案例分析题";
        }
    },
    //操作题
    EXAM_TYPE_CAOZUO {
        @Override
        public int getId() {
            return 110;
        }

        @Override
        public String getName() {
            return "操作题";
        }
    },
    //实操讲解题
    EXAM_TYPE_SHICAOJIANGJIE {
        @Override
        public int getId() {
            return 112;
        }

        @Override
        public String getName() {
            return "实操讲解题";
        }
    },
    //计算题（单选模式）
    EXAM_TYPE_JISUANDANXUANMOSHI {
        @Override
        public int getId() {
            return 113;
        }

        @Override
        public String getName() {
            return "计算题（单选模式）";
        }
    },
    //计算题
    EXAM_TYPE_JISUANTI {
        @Override
        public int getId() {
            return 105;
        }

        @Override
        public String getName() {
            return "计算题";
        }
    }
    ;
    public static String getValue(int key) {
        String value = "";
        switch (key) {
            case 1:
                value = ExamTypeEnum.EXAM_TYPE_PANDUAN.getName();
                break;
            case 2:
                value = ExamTypeEnum.EXAM_TYPE_DANXUAN.getName();
                break;
            case 3:
                value = ExamTypeEnum.EXAM_TYPE_DUOXUAN.getName();
                break;
            case 4:
                value = ExamTypeEnum.EXAM_TYPE_JIANDA.getName();
                break;
            case 5:
                value = ExamTypeEnum.EXAM_TYPE_CONGYEJISUAN.getName();
                break;
            case 6:
                value = ExamTypeEnum.EXAM_TYPE_BUDINGXIANG.getName();
                break;
            case 7:
                value = ExamTypeEnum.EXAM_TYPE_ZHONGHEFENXI.getName();
                break;
            case 108:
                value = ExamTypeEnum.EXAM_TYPE_JISUANFENXITI.getName();
                break;
            case 109:
                value = ExamTypeEnum.EXAM_TYPE_ANLIFENXITI.getName();
                break;
            case 110:
                value = ExamTypeEnum.EXAM_TYPE_CAOZUO.getName();
                break;
            case 111:
            value = ExamTypeEnum.EXAM_TYPE_BUDINGXIANG.getName();
                break;
            case 112:
                value = ExamTypeEnum.EXAM_TYPE_SHICAOJIANGJIE.getName();
                break;
            case 113:
                value = ExamTypeEnum.EXAM_TYPE_JISUANDANXUANMOSHI.getName();
                break;
            case 114:
                value = ExamTypeEnum.EXAM_TYPE_ZHONGHEFENXI.getName();
                break;
            case 115:
                value = ExamTypeEnum.EXAM_TYPE_JISUANTI.getName();
                break;
            case 100:
                value = ExamTypeEnum.EXAM_TYPE_TIMAOTI.getName();
                break;
            default:
                value = ExamTypeEnum.EXAM_TYPE_JIANDA.getName();
                break;
        }
        return value;
    }
}
