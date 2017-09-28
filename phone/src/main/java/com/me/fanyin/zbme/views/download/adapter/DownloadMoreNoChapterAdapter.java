package com.me.fanyin.zbme.views.download.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.core.utils.DensityUtils;
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
 * 不带章节下载更多
 * Created by mayunfei on 17-6-5.
 */

public class DownloadMoreNoChapterAdapter extends BaseAdapter {

    private final DownloadMorePresenter mPresenter;
    private List<CourseWare> courseWareWareList;
    private CourseWare playingCourseWare;

    public void setPlayingCourseWare(CourseWare playingCourseWare) {
        this.playingCourseWare = playingCourseWare;
    }

    public void disposable() {
        for (Disposable disposable : disposableHashMap.values()) {
            RxUtils.dispose(disposable);
        }
    }
    private HashMap<View, Disposable> disposableHashMap;


    public DownloadMoreNoChapterAdapter(List<CourseWare> courseWareWareList, DownloadMorePresenter mPresenter) {
        this.courseWareWareList = courseWareWareList;
        disposableHashMap = new HashMap<>();
        this.mPresenter = mPresenter;
    }

    @Override
    public int getCount() {
        return courseWareWareList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseWareWareList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RxUtils.dispose(disposableHashMap.get(convertView));
        Context context = parent.getContext();
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.download_more_child_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.downladStatus = (ImageView) convertView.findViewById(R.id.download_status_img);
            viewHolder.frameLayout = (FrameLayout) convertView.findViewById(R.id.item_main);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final CourseWare courseWare = courseWareWareList.get(position);
        viewHolder.title.setText(courseWare.getLectureOrder() +" "+ courseWare.getName() + "");

        final ViewHolder finalViewHolder = viewHolder;
        Disposable disposable = DownloadManager.getInstance().getDownloadEvent(courseWare)
                .subscribe(new Consumer<DownloadEvent>() {
                    @Override
                    public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
                        switch (downloadEvent.getStatus()){
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
                                finalViewHolder.downladStatus.setVisibility(View.GONE);
                                mPresenter.getDownloading();
                                break;
                        }
                    }
                });

            if (playingCourseWare!=null && courseWare.getId().equals(playingCourseWare.getId()) ){
                viewHolder.title.setTextColor(ContextCompat.getColor(context,R.color.color_primary));
            }else {
                viewHolder.title.setTextColor(ContextCompat.getColor(context,R.color.text_color_primary_dark));
            }


        if (courseWareWareList.size() ==1){
            viewHolder.frameLayout.setPadding(0, DensityUtils.dip2px(context,12),0,DensityUtils.dip2px(context,12));
        }else {
            if (position ==0){
                viewHolder.frameLayout.setPadding(0, DensityUtils.dip2px(context,12),0,DensityUtils.dip2px(context,2.5f));
            }else if (position==(courseWareWareList.size()-1)){
                viewHolder.frameLayout.setPadding(0, DensityUtils.dip2px(context,2.5f),0,DensityUtils.dip2px(context,12));
            }else {
                viewHolder.frameLayout.setPadding(0, DensityUtils.dip2px(context,2.5f),0,DensityUtils.dip2px(context,2.5f));
            }
        }

        disposableHashMap.put(convertView,disposable);
//                .subscribe(new Consumer<DownloadEvent>() {
//                    @Override
//                    public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
//
//                    }
//                }

        return convertView;
    }

    static class ViewHolder {
        TextView title;
        ImageView downladStatus;
        FrameLayout frameLayout;
    }
}
