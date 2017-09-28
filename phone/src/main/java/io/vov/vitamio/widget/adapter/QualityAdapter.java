package io.vov.vitamio.widget.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.me.data.model.play.CourseWare;
import com.me.data.model.play.CwClarity;
import com.me.fanyin.zbme.R;

import java.util.List;

/**
 * Created by dell on 2016/6/7.
 */
public class QualityAdapter extends BaseAdapter {

    private List<CwClarity> list;
    private Context context;
    private CourseWare courseWare;

    public QualityAdapter(CourseWare courseWare, Context context) {
        this.list = courseWare.getClaritys();
        this.courseWare=courseWare;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= View.inflate(context, R.layout.mediacontroller_quality_item, null);
            holder.name=(TextView)convertView.findViewById(R.id.quality_name);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        String name= courseWare.getQualityName();
        if(list.get(position).getName().equals(name)){
            holder.name.setTextColor(context.getResources().getColor(R.color.color_primary));
        }else{
            holder.name.setTextColor(context.getResources().getColor(R.color.white));
        }
        holder.name.setText(list.get(position).getName());

        return convertView;
    }

    class ViewHolder {
        TextView name;
    }
}
