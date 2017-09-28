package com.me.fanyin.zbme.views.download;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.core.utils.ToastBarUtils;
import com.me.data.model.play.CourseWare;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.views.download.adapter.MyDownloadAdapter;
import com.me.fanyin.zbme.views.download.adapter.MyDownloadCourseAdapter;
import com.me.fanyin.zbme.widget.CommonToolbar;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.me.data.common.Constants.CLASS_ID;
import static com.me.data.common.Constants.EXAM_ID;
import static com.me.data.common.Constants.SUBJECT_ID;

/**
 * 我的下载课程详情
 */
public class MyDownloadCourseActivity extends BaseMvpActivity<MyDownloadCourseView, MyDownloadCoursePresenter> implements MyDownloadCourseView, MyDownloadAdapter.OnSelectListener {

    @BindView(R.id.tv_select)
    TextView tvSelect;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.layout_edit_button)
    LinearLayout layoutEditButton;
    private List<CourseWare> data;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private MyDownloadCourseAdapter adapter;
    private boolean isEdit = false;
    private CourseWare courseWareData;

    public static void StartMyDownloadCourseActivity(Context context, String examId, String subjectId, String classId) {
        Intent intent = new Intent(context, MyDownloadCourseActivity.class);
        intent.putExtra(EXAM_ID, examId);
        intent.putExtra(CLASS_ID, classId);
        intent.putExtra(SUBJECT_ID, subjectId);
        context.startActivity(intent);
    }

    @Override
    protected void initView() {
        data = new ArrayList<>();
        recycler.setLayoutManager(new LinearLayoutManager(context()));
        adapter = new MyDownloadCourseAdapter(data, mPresenter);
        recycler.setAdapter(adapter);
        tvSelect.setOnClickListener(this);
        adapter.setOnSelectListener(this);
        mToolbar.setTitleText("我的下载");
        View header = LayoutInflater.from(this).inflate(R.layout.download_my_course_download_more_header, null, false);
        adapter.addHeaderView(header);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (courseWareData != null) {
                    DownloadMoreActivity.startActivity(context(), courseWareData);
                }
            }
        });
        setMenu();
    }

    private void setMenu() {
        if (isEdit) {
            mToolbar.setTextMenuText("取消");
            mToolbar.settextMenuTextColor(R.color.color_primary);
            showEdit();

        } else {
            mToolbar.settextMenuTextColor(R.color.text_color_primary);
            mToolbar.setTextMenuText("编辑");
            showNormal();
        }

        mToolbar.setOnMenuClickListener(new CommonToolbar.CommonToolbarClickListener() {
            @Override
            public void onClick(View view) {
                isEdit = !isEdit;
                setMenu();
            }
        });
    }

    @Override
    protected void initData() {
        String examId = getIntent().getStringExtra(EXAM_ID);
        String classId = getIntent().getStringExtra(CLASS_ID);
        String subjectId = getIntent().getStringExtra(SUBJECT_ID);
        Logger.i("classid = %s subjectid = %s", classId, subjectId);
        if (TextUtils.isEmpty(classId) || TextUtils.isEmpty(subjectId) || TextUtils.isEmpty(examId)) {
            finish();
            return;
        }
        mPresenter.getData(examId, subjectId, classId);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.download_my_course_activity;
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_select:
                adapter.selectAll();
                break;
        }
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showData(List<CourseWare> courseWares) {
        this.data.clear();
        this.data.addAll(courseWares);
        if (data.size() > 0 && courseWareData == null) {
            this.courseWareData = data.get(0);
        }

        //编辑按钮不可点击
        if (data.size() == 0) {
            isEdit = false;
            setMenu();
            mToolbar.settextMenuTextColor(R.color.text_color_primary_light_more);
            mToolbar.setOnMenuClickListener(null);
        } else {
            setMenu();
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void showEdit() {
        adapter.setEdit(true);
        layoutEditButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNormal() {
        adapter.setEdit(false);
        layoutEditButton.setVisibility(View.GONE);
        adapter.clearAll();
    }


    @Override
    public void deleteSuccess(List<String> select) {
        ToastBarUtils.showToast(context(), "删除成功");
        adapter.clearSelect(select);
    }

    @Override
    public void deleteFail() {
        ToastBarUtils.showToast(context(), "删除失败");
    }

    @Override
    public void onSelect(int count) {
        if (count == 0) {
            tvDelete.setText("删除");
            tvDelete.setTextColor(ContextCompat.getColor(context(), R.color.delete_un_click));
            tvDelete.setOnClickListener(null);
        } else {

            tvDelete.setText("删除(" + count + ")");
            tvDelete.setTextColor(ContextCompat.getColor(context(), R.color.color_accent));
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.deleteSelect(adapter.getSelect());
                }
            });
        }
        if (adapter.isSellectAll()){
            tvSelect.setText("取消全选");
        }else {
            tvSelect.setText("全选");
        }
    }
}
