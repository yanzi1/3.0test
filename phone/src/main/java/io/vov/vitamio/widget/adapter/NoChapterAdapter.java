package io.vov.vitamio.widget.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.data.model.play.CourseWare;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.play.PlayActivity;
import com.me.fanyin.zbme.views.download.DownloadManager;

import java.util.List;

/**
 * Created by dell on 2016/6/7.
 */
public class NoChapterAdapter extends BaseAdapter {

    private CourseWare courseWare;
    private List<CourseWare> list;
    private PlayActivity context;

    public NoChapterAdapter(List<CourseWare> list, CourseWare courseWare, PlayActivity context) {
        this.courseWare = courseWare;
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        } else return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        } else return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= View.inflate(context, R.layout.mediacontroller_chaper_item, null);
            holder.name=(TextView)convertView.findViewById(R.id.tv_name);
            holder.img=(ImageView) convertView.findViewById(R.id.img_play);
            holder.local=(TextView)convertView.findViewById(R.id.tv_local);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        boolean isDownload= DownloadManager.getInstance().isCWDownlaoded(list.get(position));
        if(isDownload){
            holder.local.setVisibility(View.VISIBLE);
        }else{
            holder.local.setVisibility(View.GONE);
        }

        holder.name.setText(list.get(position).getLectureOrder()+" "+list.get(position).getName());
        if (list.get(position).getId().equals(courseWare.getId())) {
            holder.name.setTextColor(context.getResources().getColor(R.color.color_primary_select));
            holder.img.setImageResource(R.mipmap.course_play_icodeep);
        } else {
            holder.name.setTextColor(context.getResources().getColor(R.color.white));
            holder.img.setImageResource(R.mipmap.course_play_icolight);
        }
        return convertView;
    }

    class ViewHolder {
        TextView name,local;
        ImageView img;
    }
}
