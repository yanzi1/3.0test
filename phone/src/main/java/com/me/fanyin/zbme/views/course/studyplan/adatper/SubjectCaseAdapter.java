package com.me.fanyin.zbme.views.course.studyplan.adatper;

import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.me.data.model.play.Subject;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.studyplan.StudyCaseActivity;

import java.util.List;

public class SubjectCaseAdapter extends BaseAdapter {


    List<Subject> data;
    private StudyCaseActivity activity;

    public SubjectCaseAdapter(StudyCaseActivity activity) {
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_item, null);
            viewHolder.mText=(TextView)convertView.findViewById(R.id.text);
            viewHolder.line=convertView.findViewById(R.id.line_top);
            convertView.setTag(viewHolder);
        }
        if(position==0){
            viewHolder.line.setVisibility(View.VISIBLE);
        }else{
            viewHolder.line.setVisibility(View.GONE);
        }
        viewHolder.mText.setText(data.get(position).getSubjectName());
        if (activity.subjectPostion==position){
            viewHolder.mText.setTextColor(ContextCompat.getColor(parent.getContext(),R.color.color_primary));
        }else {
            viewHolder.mText.setTextColor(ContextCompat.getColor(parent.getContext(),R.color.text_color_primary));
        }
        return convertView;
    }

    public void setDatas(List<Subject> data){
        this.data = data;
    }

    static class ViewHolder {
        TextView mText;
        View line;
    }
}
