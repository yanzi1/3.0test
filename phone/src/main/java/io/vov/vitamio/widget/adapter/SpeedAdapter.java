package io.vov.vitamio.widget.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.me.data.local.SharedPrefHelper;
import com.me.fanyin.zbme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2016/6/7.
 */
public class SpeedAdapter extends BaseAdapter {

    private List<String> list;
    private Context context;
    private String speedName;

    public SpeedAdapter(Context context) {
        List<String> list=new ArrayList();
        list.add("1.0x");
        list.add("1.2x");
        list.add("1.5x");
        list.add("1.8x");
        this.list=list;
        this.context = context;
    }

    public void setData() {
        if (SharedPrefHelper.getInstance().getPlaySpeed() == 1.0f) {
            speedName="1.0x";
        } else if (SharedPrefHelper.getInstance().getPlaySpeed() == 1.2f) {
            speedName="1.2x";
        } else if (SharedPrefHelper.getInstance().getPlaySpeed() == 1.5f) {
            speedName="1.5x";
        } else if (SharedPrefHelper.getInstance().getPlaySpeed() == 1.8f) {
            speedName="1.8x";
        }
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

        if(list.get(position).equals(speedName)){
            holder.name.setTextColor(context.getResources().getColor(R.color.color_primary));
        }else{
            holder.name.setTextColor(context.getResources().getColor(R.color.white));
        }
        holder.name.setText(list.get(position));

        return convertView;
    }

    class ViewHolder {
        TextView name;
    }
}
