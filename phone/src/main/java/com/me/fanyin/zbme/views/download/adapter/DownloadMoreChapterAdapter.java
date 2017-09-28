package com.me.fanyin.zbme.views.download.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.core.utils.DensityUtils;
import com.me.data.model.play.CourseChapter;
import com.me.data.model.play.CourseWare;
import com.me.data.remote.rxjava.RxUtils;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.download.DownloadManager;
import com.me.fanyin.zbme.views.download.DownloadMorePresenter;
import com.me.rxdownload.entity.DownloadEvent;

import java.util.HashMap;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.me.rxdownload.entity.DownloadStatus.DELETED;
import static com.me.rxdownload.entity.DownloadStatus.DOWNLOADING;
import static com.me.rxdownload.entity.DownloadStatus.ERROR;
import static com.me.rxdownload.entity.DownloadStatus.FINISH;
import static com.me.rxdownload.entity.DownloadStatus.PAUSE;
import static com.me.rxdownload.entity.DownloadStatus.QUEUE;

/**
 * 带章节 下载更多
 * Created by mayunfei on 17-6-5.
 */

public class DownloadMoreChapterAdapter extends BaseExpandableListAdapter {

    private final DownloadMorePresenter mPresenter;
    List<CourseChapter> courseChapters;
    private HashMap<View, Disposable> disposableHashMap;
    private CourseWare playingCourseWare;

    public void setPlayingCourseWare(CourseWare playingCourseWare) {
        this.playingCourseWare = playingCourseWare;
    }

    public DownloadMoreChapterAdapter(List<CourseChapter> courseChapters, DownloadMorePresenter mPresenter) {
        this.courseChapters = courseChapters;
        disposableHashMap = new HashMap<>();
        this.mPresenter = mPresenter;
    }

    @Override
    public int getGroupCount() {
        return courseChapters.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return courseChapters.get(groupPosition).getPcClientCourseWareList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return courseChapters.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return courseChapters.get(groupPosition).getPcClientCourseWareList().get(childPosition);
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
        ViewHolderGroup viewHolderGroup = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.download_more_chapter_group_item, parent, false);
            viewHolderGroup = new ViewHolderGroup();
            viewHolderGroup.subjectName = (TextView) convertView.findViewById(R.id.tv_subject);
            viewHolderGroup.line = convertView.findViewById(R.id.line);
            convertView.setTag(viewHolderGroup);
        } else {
            viewHolderGroup = (ViewHolderGroup) convertView.getTag();
        }
        viewHolderGroup.subjectName.setText(courseChapters.get(groupPosition).getName());
        final Drawable close = ContextCompat.getDrawable(parent.getContext(), R.mipmap.ico_chapter_unfold);
        final Drawable open = ContextCompat.getDrawable(parent.getContext(), R.mipmap.ico_chapter_fold);
        close.setBounds(0, 0, close.getMinimumWidth(), close.getMinimumHeight());
        open.setBounds(0, 0, open.getMinimumWidth(), open.getMinimumHeight());
        if (isExpanded) {
            viewHolderGroup.subjectName.setCompoundDrawables(open, null, null, null);
            viewHolderGroup.line.setVisibility(View.INVISIBLE);
        } else {
            viewHolderGroup.subjectName.setCompoundDrawables(close, null, null, null);
            viewHolderGroup.line.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        RxUtils.dispose(disposableHashMap.get(convertView));
        Context context = parent.getContext();
        DownloadMoreNoChapterAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.download_more_child_item, parent, false);
            viewHolder = new DownloadMoreNoChapterAdapter.ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.frameLayout = (FrameLayout) convertView.findViewById(R.id.item_main);
            viewHolder.downladStatus = (ImageView) convertView.findViewById(R.id.download_status_img);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DownloadMoreNoChapterAdapter.ViewHolder) convertView.getTag();
        }


        final CourseWare courseWare = courseChapters.get(groupPosition).getPcClientCourseWareList().get(childPosition);
        viewHolder.title.setText(courseWare.getLectureOrder() +" "+ courseWare.getName() + "");
        List<CourseWare> pcClientCourseWareList = courseChapters.get(groupPosition).getPcClientCourseWareList();

        if (pcClientCourseWareList.size() ==1){
            viewHolder.frameLayout.setPadding(0, DensityUtils.dip2px(context,12),0,DensityUtils.dip2px(context,12));
        }else {
            if (childPosition ==0){
                viewHolder.frameLayout.setPadding(0, DensityUtils.dip2px(context,12),0,DensityUtils.dip2px(context,2.5f));
            }else if (childPosition==(pcClientCourseWareList.size()-1)){
                viewHolder.frameLayout.setPadding(0, DensityUtils.dip2px(context,2.5f),0,DensityUtils.dip2px(context,12));
            }else {
                viewHolder.frameLayout.setPadding(0, DensityUtils.dip2px(context,2.5f),0,DensityUtils.dip2px(context,2.5f));
            }
        }



        final DownloadMoreNoChapterAdapter.ViewHolder finalViewHolder = viewHolder;
        Disposable disposable = DownloadManager.getInstance().getDownloadEvent(courseWare)
                .subscribe(new Consumer<DownloadEvent>() {
                    @Override
                    public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
                        switch (downloadEvent.getStatus()) {
                            case PAUSE:
                            case DOWNLOADING:
                            case QUEUE:
                            case ERROR:
                                finalViewHolder.downladStatus.setImageResource(R.mipmap.ico_downloading);
                                finalViewHolder.downladStatus.setVisibility(View.VISIBLE);
                                break;
                            case FINISH:
                                finalViewHolder.downladStatus.setImageResource(R.mipmap.ico_downloaded);
                                finalViewHolder.downladStatus.setVisibility(View.VISIBLE);
                                mPresenter.getDownloading();
                                break;
                            case DELETED:
                                finalViewHolder.downladStatus.setVisibility(View.INVISIBLE);
                                mPresenter.getDownloading();
                                break;
                        }
                    }
                });

        if (playingCourseWare != null && courseWare.getId().equals(playingCourseWare.getId())) {
            viewHolder.title.setTextColor(ContextCompat.getColor(context, R.color.color_primary));
        } else {
            viewHolder.title.setTextColor(ContextCompat.getColor(context, R.color.text_color_primary_dark));
        }
        disposableHashMap.put(convertView, disposable);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class ViewHolderGroup {
        TextView subjectName;
        View line;
    }

    static class ViewHolderChildCourse {
        TextView title;
        ImageView downladStatus;
    }

    public void disposable() {
        for (Disposable disposable : disposableHashMap.values()) {
            RxUtils.dispose(disposable);
        }
    }

}
