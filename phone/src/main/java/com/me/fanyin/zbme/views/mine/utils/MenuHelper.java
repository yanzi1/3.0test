package com.me.fanyin.zbme.views.mine.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.BaseRes;
import com.me.data.model.mine.ExamMenu;
import com.me.data.model.mine.TypeMenu;
import com.me.data.remote.ApiService;
import com.me.data.remote.rxjava.RxUtils;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by jjr on 2017/6/1.
 *
 * 获取类型菜单集合帮助类
 */

public class MenuHelper {

    //我的收藏类型菜单
    private static final String json1 = "[{\"typeId\": \"1\",\"typeName\": \"课程\"},{\"typeId\": \"2\",\"typeName\": \"试题\"},{\"typeId\": \"3\",\"typeName\": \"答疑\"}]";
    //学习记录、我的笔记类型菜单
    private static final String json2 = "[{\"typeId\": \"1\",\"typeName\": \"课程\"},{\"typeId\": \"2\",\"typeName\": \"试题\"}]";

    public static List<TypeMenu> getTypeMenuAll() {
        return JSON.parseArray(json1, TypeMenu.class);
    }

    public static List<TypeMenu> getTypeMenuWithoutAnswer() {
        return JSON.parseArray(json2, TypeMenu.class);
    }

    /**
     * 从网络获取数据并保存
     */
    public static void saveExamMenu() {
        ApiService.getExamSubjectSubList()
                .compose(RxUtils.<BaseRes<List<ExamMenu>>>ioMain())
                .compose(RxUtils.<List<ExamMenu>>retrofit())
                .subscribe(new Consumer<List<ExamMenu>>() {
                    @Override
                    public void accept(@NonNull List<ExamMenu> list) throws Exception {
                        if (list != null && list.size() > 0)
                        SharedPrefHelper.getInstance().setExamSubjectSubListJson(JSONArray.toJSONString(list));
                    }
                });
    }

    public static List<ExamMenu> getExamMenu() {
        return JSON.parseArray(SharedPrefHelper.getInstance().getExamSubjectSubListJson(), ExamMenu.class);
    }

    public static int getTypeMenuPosition(int typeId) {
        List<TypeMenu> typeMenuAll = getTypeMenuAll();
        for (int i = 0; i < typeMenuAll.size(); i++) {
            if (typeMenuAll.get(i).getTypeId() == typeId) {
                return i;
            }
        }
        return 0;
    }

    public static int getExamMenuPosition(String examId) {
        List<ExamMenu> examList = getExamMenu();
        for (int i = 0; i < examList.size(); i++) {
            if (examList.get(i).getExamId().equals(examId)) {
                return i;
            }
        }
        return 0;
    }

    public static int getSubjectMenuPosition(String examId, String subjectId) {
        List<ExamMenu.SubjectMenu> subjectList = getExamMenu().get(getExamMenuPosition(examId)).getMemberSubjectList();
        for (int i = 0; i < subjectList.size(); i++) {
            if (subjectList.get(i).getSubjectId().equals(subjectId)) {
                return i;
            }
        }
        return 0;
    }

    public static int getSeasonMenuPosition(String examId, String subjectId, String sSubjectId) {
        List<ExamMenu.SubjectMenu.SeasonMenu> seasonList = getExamMenu().get(getExamMenuPosition(examId)).getMemberSubjectList().get(getSubjectMenuPosition(examId, subjectId)).getMemberSeasonSubjectList();
        for (int i = 0; i < seasonList.size(); i++) {
            if (seasonList.get(i).getSSubjectId().equals(sSubjectId)) {
                return i;
            }
        }
        return 0;
    }

}
