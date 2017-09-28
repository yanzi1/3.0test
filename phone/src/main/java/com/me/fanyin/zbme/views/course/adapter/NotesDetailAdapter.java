package com.me.fanyin.zbme.views.course.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.notes.NoteDetailsActivity;

import java.util.List;

/**
 * Created by dell on 2017/4/25.
 */

public class NotesDetailAdapter extends BaseAdapter{

    private NoteDetailsActivity context;
    private List<String> list;

    public NotesDetailAdapter(NoteDetailsActivity context){
        this.context = context;
    }

    public void setList(List<String> list){
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView=View.inflate(context, R.layout.notes_detail_item,null);
            holder.img=(ImageView)convertView.findViewById(R.id.note_detail_img);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
//      .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
//      .error(R.mipmap.img_default_course)           //设置错误图片

        Glide.with(context)                             //配置上下文
                .load(list.get(position))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .placeholder(R.mipmap.img_default_course)     //设置占位图片
                .into(holder.img);
        return convertView;
    }

    class ViewHolder{
        ImageView img;
    }
}
