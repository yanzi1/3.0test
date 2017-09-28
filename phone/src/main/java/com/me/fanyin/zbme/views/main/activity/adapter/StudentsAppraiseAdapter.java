package com.me.fanyin.zbme.views.main.activity.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.me.data.model.main.StudentsEvaluateInfo;
import com.me.fanyin.zbme.R;

/**
 * Created by jjr on 2017/5/11.
 */

public class StudentsAppraiseAdapter extends BaseQuickAdapter<StudentsEvaluateInfo.ListBean, BaseViewHolder> {

    public StudentsAppraiseAdapter() {
        super(R.layout.main_students_appraise_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, StudentsEvaluateInfo.ListBean item) {
        Glide.with(getRecyclerView().getContext()).load(item.getAvatarSmall()).placeholder(R.mipmap.ico_evaluate_user).error(R.mipmap.ico_evaluate_user).into((ImageView) helper.getView(R.id.students_appraise_icon_iv));
        helper.setText(R.id.students_appraise_user_iv, item.getUsername());
        helper.setText(R.id.students_appraise_date_iv, item.getCreateDate().substring(0, item.getCreateDate().indexOf(" ")));
        helper.setText(R.id.students_appraise_content_iv, item.getContent());
        if (helper.getLayoutPosition() == getData().size() - 1) {
            helper.setVisible(R.id.app_line, false);
        } else {
            helper.setVisible(R.id.app_line, true);
        }
    }
}
