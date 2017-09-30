package com.me.fanyin.zbme.views.exam.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alibaba.fastjson.JSON;
import com.me.data.local.KaoQianDBHelper;
import com.me.data.model.exam.FaltQuestion;
import com.me.data.model.exam.Question;
import com.me.fanyin.zbme.views.exam.activity.myfault.bean.ExaminationClass;
import com.yunqing.core.db.DBExecutor;
import com.yunqing.core.db.sql.Sql;
import com.yunqing.core.db.sql.SqlFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyc on 3/5/16.
 * 此类DAO只用于查询和特殊操作
 */
public class FaltQuestionDB {
    Context mContext;
    DBExecutor dbExecutor = null;
    Sql sql = null;

    public FaltQuestionDB(Context mContext) {
        this.mContext = mContext;
        dbExecutor = DBExecutor.getInstance(KaoQianDBHelper.getInstance());
    }

    public void insert(FaltQuestion faltQuestion) {
        dbExecutor.insert(faltQuestion);
    }

    public void update(FaltQuestion faltQuestion) {
        dbExecutor.updateById(faltQuestion);
    }

    public FaltQuestion find(String id) {
        return dbExecutor.findById(FaltQuestion.class, id);
    }

    /**
     * 删除答题记录
     */

    public void delete(FaltQuestion faltQuestion) {
        dbExecutor.deleteById(FaltQuestion.class, faltQuestion.getDbId());//   .delete(Collection.class, collection);
    }

    /**
     * 搜索某一类型的所有题
     *
     * @param choiceType 题的类型
     * @return
     */
    public List<FaltQuestion> findAllByChiceTypeId(String userId, String examId, String subjectId, int choiceType) {
        sql = SqlFactory.find(FaltQuestion.class).where("userId=?  and subjectId=? and choiceType=?", new Object[]{userId, subjectId, choiceType});
        ;
        return dbExecutor.executeQuery(sql);
    }

    /**
     * 搜索全部错题或者收藏
     *
     * @return
     */
    public List<FaltQuestion> findAll(String userId, String examId, String subjectId) {
        sql = SqlFactory.find(FaltQuestion.class).where("userId=? and  subjectId=?", new Object[]{userId, subjectId});
        return dbExecutor.executeQuery(sql);
    }


    /**
     * 搜索全部错题或者收藏数量
     *
     * @return
     */
    public int findAllCount(String userId, String examId, String subjectId) {
//        sql = SqlFactory.find(FaltQuestion.class).where("userId=? and examId=? and subjectId=?", new Object[]{userId, examId, subjectId});
//        return dbExecutor.executeQuery(sql).size();
        int num = 0;
        List<FaltQuestion> faltQuestions = findAll(userId, examId, subjectId);
        for (int i = 0; i < faltQuestions.size(); i++) {
            Question question = JSON.parseObject(faltQuestions.get(i).getContent(), Question.class);
            if (question.getQuestionList() == null || question.getQuestionList().size() == 0) {
                num++;
            } else {
                num += question.getQuestionList().size();
            }
        }
        return num;
    }

    public FaltQuestion findAllByQuestionId(String userId, String examId, String subjectId, String examinationId, String questionId) {
        sql = SqlFactory.find(FaltQuestion.class).where("userId=?  and subjectId=? and examinationId=? and questionId=?", new Object[]{userId, subjectId, examinationId, questionId});
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }

    public List<FaltQuestion> findAllByExaminationId(String userId, String examId, String subjectId, String examinationId) {
        sql = SqlFactory.find(FaltQuestion.class).where("userId=?  and subjectId=? and examinationId=?", new Object[]{userId, subjectId, examinationId});
        return dbExecutor.executeQuery(sql);
    }

    /**
     * 删除所有
     */
    public void deleteAll() {
        dbExecutor.deleteAll(FaltQuestion.class);
    }

    public void deleteByQuestionId(String userId, String examId, String subjectId, String examinationId, String questionId) {
        FaltQuestion question = findAllByQuestionId(userId, examId, subjectId, examinationId, questionId);
        if (question != null) {
            dbExecutor.deleteById(FaltQuestion.class, question.getDbId());//   .delete(Collection.class, collection);
        }
    }

    public List<ExaminationClass> findAllByName(String userId, String examId, String subjectId, String typeId) {
        KaoQianDBHelper db = new KaoQianDBHelper();
        List<ExaminationClass> list = new ArrayList();
        SQLiteDatabase sqd = db.getReadableDatabase();
        Cursor cursor = sqd.query("t_falt_question", null, "userId=? and subjectId=? and typeId=?",
                new String[]{userId, subjectId, typeId}, "examinationId", null, "dbId");
        while (cursor.moveToNext()) {
            ExaminationClass faultClass = new ExaminationClass();
            int position = cursor.getColumnIndex("examinationId");
            String examinationId = cursor.getString(position);
            faultClass.setExaminationId(examinationId);
            sql = SqlFactory.find(FaltQuestion.class).where("userId=? and subjectId=? and typeId=? and examinationId=?", new Object[]{userId, subjectId, typeId, examinationId});
            List<FaltQuestion> faList = dbExecutor.executeQuery(sql);
            int num = 0;
            for (int i = 0; i < faList.size(); i++) {
                if (faList.get(i).getContent() != null && !faList.get(i).getContent().isEmpty()) {
                    Question question = JSON.parseObject(faList.get(i).getContent(), Question.class);
                    if (question.getQuestionList() == null || question.getQuestionList().size() == 0) {
                        num++;
                    } else {
                        num += question.getQuestionList().size();
                    }
                }
            }
            faultClass.setNumber(num);
            if (faList.size() == 0) {
                continue;
            }
            faultClass.setExaminationName(faList.get(0).getExaminationName());
            list.add(faultClass);
        }
        cursor.close();
        sqd.close();
        return list;
    }
}
