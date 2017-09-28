package com.me.rxdownload.db;

import com.me.rxdownload.entity.DownloadBean;
import com.me.rxdownload.entity.DownloadBundle;
import com.me.rxdownload.entity.DownloadEvent;

import java.util.List;

/**
 * 数据库接口
 * Created by yunfei on 17-3-25.
 */

public interface IDownloadDB {

    /**
     * 更新 单个下载记录
     */
    boolean updateDownloadBean(DownloadBean bean);

    /**
     * 更新这一组数据
     */
    boolean updateDownloadBundle(DownloadBundle downloadBundle);

    /**
     * 插入数据
     */
    boolean insertDownloadBundle(DownloadBundle downloadBundle);

    boolean existsDownloadBundle(String key);

    boolean isDownloadFinishedBy(String key);

    void closeDataBase();

    /**
     * 根据 key 查询 下载状态
     */
    DownloadEvent selectBundleStatus(String key);

    List<DownloadBundle> getAllDownloadBundle();

    /**
     * 根据UserId查询
     */
    List<DownloadBundle> getDownloadBundleByUserId(String userId);


    List<DownloadBundle> getDownloadingNotPauseBundleByUserId(String userId);

    /**
     * 查询已经完成
     */
    List<DownloadBundle> getFinishedBundleByUserId(String userId);

    /**
     * 查询正在下载
     */
    List<DownloadBundle> getDownloadingBundleByUserId(String userId);

    void pauseAll();

    DownloadBundle getDownloadBundle(String key);

    void setBeanFinished(int beanId);

    /**
     * 删除
     */
    boolean delete(String key);

    List<DownloadBundle> getDownloadBundledBySubjectAndClass(String userId, String SubjectId, String classId);

    String getDownloadPathByKey(String key);

//    List<DownloadBundle> getDownloadBundleByExamIdSubjectIdClassId(String userId, String subjectId, String classId);

    /**
     * 根据classId 分组
     */
    List<DownloadDao.DownloadResultByClassId> getDownloadFinishedGroupByClassId(String userId);

    void pause(String key);

    List<DownloadBundle> getDownloadingBundleNoUser();
}
