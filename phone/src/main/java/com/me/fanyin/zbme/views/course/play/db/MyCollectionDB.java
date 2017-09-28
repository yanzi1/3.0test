package com.me.fanyin.zbme.views.course.play.db;

import com.me.data.local.BaseDB;
import com.me.data.model.play.MyCollection;
import com.yunqing.core.db.sql.Sql;
import com.yunqing.core.db.sql.SqlFactory;

import java.util.List;

/**
 * Created by fengzongwei on 2016/5/11 0011.
 * 我的收藏表
 */
public class MyCollectionDB extends BaseDB{

    Sql sql = null;


    /**
     * 删除所有
     */
    public void deleteAll() {
        dbExecutor.deleteAll(MyCollection.class);
    }

    /**
     * 加入收藏(如果存在则更新数据库中的数据)
     * @param myCollection
     */
    public void insert(MyCollection myCollection){
        MyCollection myCollectionInDb = findCollection(myCollection.getUserId(),myCollection.getCollectionId(),myCollection.getType());
        if(myCollectionInDb==null)
            dbExecutor.insert(myCollection);
        else{
            myCollection.setDbid(myCollectionInDb.getDbid());
            dbExecutor.updateById(myCollection);
        }
    }

    /**
     * 加入收藏(如果存在不更新数据库中的数据)
     * @param myCollection
     */
    public void insertNotUpdate(MyCollection myCollection){
        MyCollection myCollectionInDb = findCollection(myCollection.getUserId(),myCollection.getCollectionId(),myCollection.getType());
        if(myCollectionInDb==null){
            dbExecutor.insert(myCollection);
        }
    }

    /**
     * 是否收藏了当前
     * @param collectionId
     * @return
     */
    public boolean isCollected(String userId, String collectionId){
        sql = SqlFactory.find(MyCollection.class).where("collectionId=? and userId=?","=",new Object[]{collectionId,userId});
        MyCollection myCollection = dbExecutor.executeQueryGetFirstEntry(sql);
        if(myCollection == null)
            return false;
        return true;
    }

    /**
     * 查找某条收藏信息
     * @param userId
     * @param collectionId
     * @param type //收藏类型  1.题  2.答疑 3.视频 4.web文件
     * @return 如果返回null则当前未收藏此信息
     */
    public MyCollection findCollection(String userId, String collectionId, String type){
        sql = SqlFactory.find(MyCollection.class).where("collectionId=? and type=? and userId=?",new Object[]{collectionId,type,userId});
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }

    /**
     * 查找某条收藏信息根据试题ID
     * @param userId
     * @param questionId
     * @param type //收藏类型  1.题  2.答疑 3.视频 4.web文件
     * @return 如果返回null则当前未收藏此信息
     */
    public MyCollection findCollectionByQuestionId(String userId, String questionId, String type){
        sql = SqlFactory.find(MyCollection.class).where("questionId=? and type=? and userId=?",new Object[]{questionId,type,userId});
        return dbExecutor.executeQueryGetFirstEntry(sql);
    }

    /**
     * 删除收藏
     * @param myCollection
     */
    public void delete(MyCollection myCollection){
        dbExecutor.deleteById(MyCollection.class,myCollection.getDbid());
    }

    /**
     * 查找某条试题收藏信息
     * @param userId
     * @param questionId
     * @param type //收藏类型  1.题  2.答疑 3.视频 4.web文件
     * @return 如果返回null则当前未收藏此信息i
     */
    public List<MyCollection> findCollectionQuestion(String userId, String questionId, String type){
        sql = SqlFactory.find(MyCollection.class).where("questionId=? and type=? and userId=?",new Object[]{questionId,type,userId}).orderBy("dbid", true);
        return dbExecutor.executeQuery(sql);
    }

    /**
     * 查找所有试题收藏信息
     * @param userId
     * @param type //收藏类型  1.题  2.答疑 3.视频 4.web文件
     * @param examinationId //试卷ID         
     * @return 如果返回null则当前未收藏此信息i
     */
    public List<MyCollection> findAllCollectionQuestion(String userId, String type , String examinationId){
        sql = SqlFactory.find(MyCollection.class).where("type=? and userId=? and examinationId=?",new Object[]{type,userId,examinationId}).orderBy("dbid", true);
        return dbExecutor.executeQuery(sql);
    }

    /**
     * 删除某条收藏
     * @param userId
     * @param type
     * @param questionId
     */
    public void deleteByQuestionId(String userId, String questionId, String type) {
        MyCollection question=findCollectionByQuestionId(userId, questionId, type);
        if (question!=null){
            dbExecutor.deleteById(MyCollection.class, question.getDbid());//   .delete(Collection.class, collection);
        }
    }

//    /**
//     * 删除答疑根据答疑Id
//     */
//    public void deleteQuestion(String aksId){
//        MyCollection myCollection = findCollection(SharedPrefHelper.getInstance().getUserId(), aksId, MyCollection.TYPE_QUESTION);
//        if(myCollection!=null){
//            delete(myCollection);
//        }
//    }

    /**
     * 查询某个用户所有的收藏
     * @param userId
     * @return
     */
    public List<MyCollection> findUserAllCollection(String userId){
        sql = SqlFactory.find(MyCollection.class).where("userId=?",new Object[]{userId}).orderBy("dbid", true);
        return dbExecutor.executeQuery(sql);
    }

    /**
     * 查询某种type的所有收藏
     * @param userId
     * @param type
     * @return
     */
    public List<MyCollection> findOneTypeCollection(String userId, String type){
        sql = SqlFactory.find(MyCollection.class).where("userId=? and type=?",new Object[]{userId,type}).orderBy("dbid", true);
        return dbExecutor.executeQuery(sql);
    }

    /**
     * 查询某种type的所有收藏subject
     * @param userId
     * @param type
     * @return
     */
    public List<MyCollection> findOneTypeCollection(String userId, String type, String subjectId){
        sql = SqlFactory.find(MyCollection.class).where("userId=? and type=? and subjectId=?",new Object[]{userId,type,subjectId}).orderBy("dbid", true);
        return dbExecutor.executeQuery(sql);
    }

}
