package com.me.fanyin.zbme.views.download.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.me.core.app.AppManager;
import com.me.data.model.play.CourseWare;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.play.PlayActivity;
import com.me.fanyin.zbme.views.download.DownloadManager;
import com.me.fanyin.zbme.views.download.MyDownloadCoursePresenter;
import com.me.fanyin.zbme.widget.DialogManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mayunfei on 17-4-14.
 */

public class MyDownloadCourseAdapter extends BaseQuickAdapter<CourseWare, BaseViewHolder> {
    private final MyDownloadCoursePresenter myDownloadCoursePresenter;
    boolean isEdit = false;
    HashSet<String> selectedHashMap;
    MyDownloadAdapter.OnSelectListener onSelectListener;

    public MyDownloadCourseAdapter(List<CourseWare> data, MyDownloadCoursePresenter myDownloadCoursePresenter) {
        super(R.layout.download_my_course_item, data);
        selectedHashMap = new HashSet<>();
        this.myDownloadCoursePresenter = myDownloadCoursePresenter;
    }

    public void setOnSelectListener(MyDownloadAdapter.OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CourseWare item) {
        helper.setText(R.id.tv_title, item.getLectureOrder() + "  " + item.getName());
        helper.setVisible(R.id.checkbox, isEdit);

        final String key = DownloadManager.getKey(item);
        if (selectedHashMap.contains(key)) {
            helper.setChecked(R.id.checkbox, true);
        } else {
            helper.setChecked(R.id.checkbox, false);
        }

        helper.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (isEdit) {
                    return false;
                }
                DialogManager.showConfirmWithCancelDialog(helper.itemView.getContext(), new DialogManager.ConfirmWithCancelDialogListener() {
                    @Override
                    public void confirm() {
                        List<String> stringList = new ArrayList<String>(1);
                        stringList.add(DownloadManager.getKey(item));
                        myDownloadCoursePresenter.deleteSelect(stringList);
                    }

                    @Override
                    public void cancel() {

                    }
                }, "确认删除？ ",0, null, null);

                return false;
            }
        });

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit) {
                    if (selectedHashMap.contains(key)) {
                        selectedHashMap.remove(key);
                        helper.setChecked(R.id.checkbox, false);
                    } else {
                        selectedHashMap.add(key);
                        helper.setChecked(R.id.checkbox, true);
                    }
                    if (onSelectListener != null) {
                        onSelectListener.onSelect(selectedHashMap.size());
                    }
                } else {
                    AppManager.getAppManager().finishActivity(PlayActivity.class);
                    Intent intent = new Intent(v.getContext(), PlayActivity.class);
                    intent.putExtra("cwBean", item);
                    v.getContext().startActivity(intent);
//                    DownloadMoreActivity.startActivity(v.getContext(),item);
                }

            }
        });
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
        if (isEdit) {
            if (onSelectListener != null) {
                onSelectListener.onSelect(selectedHashMap.size());
            }
        }
        notifyDataSetChanged();
    }

    public List<String> getSelect() {
        List<String> select = new ArrayList<>(selectedHashMap.size());
        for (String key : selectedHashMap) {
            select.add(key);
        }
        return select;
    }

    public void clearSelect(List<String> select) {
        for (String key : select) {
            selectedHashMap.remove(key);
            Iterator<CourseWare> it = mData.iterator();
            while (it.hasNext()) {
                CourseWare item = it.next();
                if (DownloadManager.getKey(item).equals(key)) {
                    it.remove();
                }
            }
        }

        onSelectListener.onSelect(selectedHashMap.size());
        notifyDataSetChanged();
    }

    /**
     * 全选
     */
    public void selectAll() {

        if (isSellectAll()) { //如果全选则反选
            clearAll();
            return;
        }


        for (CourseWare item : mData) {
            selectedHashMap.add(DownloadManager.getKey(item));
        }
        onSelectListener.onSelect(selectedHashMap.size());
        notifyDataSetChanged();
    }

    public void clearAll(){
        selectedHashMap.clear();
        if (onSelectListener!=null){
            onSelectListener.onSelect(0);
        }
        notifyDataSetChanged();
    }

    public boolean isSellectAll() {

        for (CourseWare item : mData) {
            if (!selectedHashMap.contains(DownloadManager.getKey(item))) {
                return false;
            }
        }

        return true;
    }
}
