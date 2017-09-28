package com.me.fanyin.zbme.views.download.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.download.MyDownloadCourseActivity;
import com.me.fanyin.zbme.views.download.MyDownloadPresenter;
import com.me.fanyin.zbme.widget.DialogManager;

import java.util.List;

/**
 * Created by mayunfei on 17-4-13.
 */

public class MyDownloadAdapter extends BaseExpandableListAdapter {
    private final MyDownloadPresenter myDownloadPresenter;
    private OnSelectListener onSelectListener;
    private List<GroupItem> data;
    private boolean isEdit;

    public MyDownloadAdapter(List<GroupItem> data, MyDownloadPresenter myDownloadPresenter) {
        super();
        this.data = data;
        this.myDownloadPresenter = myDownloadPresenter;

    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).childItems.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).childItems.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new GroupViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.download_my_group_item, null);
            viewHolder.subjectName = (TextView) convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GroupViewHolder) convertView.getTag();
        }
        viewHolder.subjectName.setText(data.get(groupPosition).subjectName);
        final Drawable close = ContextCompat.getDrawable(parent.getContext(), R.mipmap.ico_chapter_unfold);
        final Drawable open = ContextCompat.getDrawable(parent.getContext(), R.mipmap.ico_chapter_fold);
        close.setBounds(0, 0, close.getMinimumWidth(), close.getMinimumHeight());
        open.setBounds(0, 0, open.getMinimumWidth(), open.getMinimumHeight());
        if (isExpanded) {
            viewHolder.subjectName.setCompoundDrawables(open, null, null, null);
        } else {
            viewHolder.subjectName.setCompoundDrawables(close, null, null, null);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder holder;
        if (convertView == null) {
            holder = new ChildViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.download_my_child_item, null);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            holder.title = (TextView) convertView.findViewById(R.id.tv_classname);
            holder.count = (TextView) convertView.findViewById(R.id.tv_count);
            holder.img = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }

        final ChildItem childItem = data.get(groupPosition).childItems.get(childPosition);

        holder.title.setText(childItem.className);
        holder.count.setText("已缓存" + childItem.count + "讲");
        Glide.with(parent.getContext()).load(childItem.imageUrl).placeholder(R.mipmap.img_default_course).error(R.mipmap.img_default_course).into(holder.img);


        if (isEdit) {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(childItem.checked);

        } else {
            holder.checkBox.setVisibility(View.GONE);
            holder.checkBox.setChecked(childItem.checked);
        }

        final View finalConvertView = convertView;
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (isEdit) {
                    return false;
                }

                DialogManager.showConfirmWithCancelDialog(finalConvertView.getContext(), new DialogManager.ConfirmWithCancelDialogListener() {
                    @Override
                    public void confirm() {
                        myDownloadPresenter.delete(childItem);
                    }

                    @Override
                    public void cancel() {

                    }
                },  "确认删除？ ", 0,null, null);


                return false;
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit) {
                    childItem.checked = !childItem.checked;
                    holder.checkBox.setChecked(childItem.checked);
                    if (onSelectListener != null) {
                        onSelectListener.onSelect(getSelectCount());
                    }
                } else {
                    MyDownloadCourseActivity.StartMyDownloadCourseActivity(v.getContext(), childItem.examId, childItem.subjectId, childItem.classId);
                }
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public interface OnSelectListener {
        void onSelect(int count);
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    class GroupViewHolder {
        TextView subjectName;
    }

    class ChildViewHolder {
        TextView title;
        TextView count;
        ImageView img;
        CheckBox checkBox;
    }


    /**
     * 选择的个数
     */
    private int getSelectCount() {

        int count = 0;

        for (GroupItem groupItem : data) {
            for (ChildItem childItem : groupItem.childItems) {
                if (childItem.checked) {
                    count++;
                }
            }
        }
        return count;
    }

    public void setEdit(boolean edit) {
        this.isEdit = edit;
        if (isEdit) {
            if (data.size() > 0) {
                if (onSelectListener != null) {
                    onSelectListener.onSelect(getSelectCount());
                }
                notifyDataSetChanged();
            }

        } else {
            notifyDataSetChanged();
        }
    }

    /**
     * 全选
     */
    public void selectAll() {

        if (isSellectAll()) { //如果全选则反选

            clearAll();
            return;
        }


        for (GroupItem groupItem : data) {
            for (ChildItem childItem : groupItem.childItems) {
                childItem.checked = true;
            }
        }
        onSelectListener.onSelect(getSelectCount());
        notifyDataSetChanged();
    }

    public boolean isSellectAll() {

        for (GroupItem groupItem : data) {
            for (ChildItem childItem : groupItem.childItems) {
                if (!childItem.checked) {
                    return false;
                }
            }
        }

        return true;
    }

    public void clearAll(){
        for (GroupItem groupItem : data) {
            for (ChildItem childItem : groupItem.childItems) {
                childItem.checked = false;
            }
        }
        if (onSelectListener!=null){
            onSelectListener.onSelect(getSelectCount());
        }
        notifyDataSetChanged();
    }


    public static class GroupItem {
        public String subjectName;
        public String className;
        public int subjectId;
        public List<ChildItem> childItems;
    }

    public static class ChildItem {
        public String examId;
        public String subjectId;
        public String classId;
        public String className;
        public String subjectName;
        public String imageUrl;
        public int count;
        public boolean checked = false;
    }


}
