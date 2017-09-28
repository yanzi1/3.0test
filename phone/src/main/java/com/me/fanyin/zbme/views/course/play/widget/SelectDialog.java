package com.me.fanyin.zbme.views.course.play.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.me.data.local.SharedPrefHelper;
import com.me.fanyin.zbme.R;

import java.util.List;

public class SelectDialog extends Dialog implements OnClickListener,OnItemClickListener {
    private SelectDialogListener mListener;
    private Activity mActivity;
    private Button mMBtn_Cancel;
    private TextView mTv_Title;
    private List<String> mName;
    private String mTitle;
    private boolean mUseCustomColor = false;
    private int mFirstItemColor;
    private int mOtherItemColor;
    private String selectStr;
    private DialogAdapter dialogAdapter;

    public interface SelectDialogListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id);
    }


    /**
     * 取消事件监听接口
     *
     */
    private SelectDialogCancelListener mCancelListener;

    public interface SelectDialogCancelListener {
        public void onCancelClick(View v);
    }

    public SelectDialog(Activity activity, int theme,
                        SelectDialogListener listener, List<String> names) {
        super(activity, theme);
        mActivity = activity;
        mListener = listener;
        this.mName=names;

        setCanceledOnTouchOutside(true);
    }

    /**
     * @param activity 调用弹出菜单的activity
     * @param theme 主题
     * @param listener 菜单项单击事件
     * @param names 菜单项名称
     * @param title 菜单标题文字
     *
     */
    public SelectDialog(Activity activity, int theme, SelectDialogListener listener, List<String> names, String title) {
        super(activity, theme);
        mActivity = activity;
        mListener = listener;
        this.mName=names;
        mTitle = title;

        // 设置是否点击外围可解散
        setCanceledOnTouchOutside(true);
    }

    private String flag;
    public SelectDialog(Activity activity, int theme, SelectDialogListener listener, List<String> names, String title,String flag) {
        super(activity, theme);
        mActivity = activity;
        mListener = listener;
        this.mName=names;
        this.flag = flag;

        // 设置是否点击外围可解散
        setCanceledOnTouchOutside(true);
    }

    /**
     * @param activity 调用弹出菜单的activity
     * @param theme 主题
     * @param listener 菜单项单击事件
     * @param cancelListener 取消事件
     * @param names 菜单项名称
     *
     */
    public SelectDialog(Activity activity, int theme, SelectDialogListener listener, SelectDialogCancelListener cancelListener , List<String> names) {
        super(activity, theme);
        mActivity = activity;
        mListener = listener;
        mCancelListener = cancelListener;
        this.mName=names;

        // 设置是否点击外围不解散
        setCanceledOnTouchOutside(false);
    }

    public SelectDialog(Activity activity, int theme, SelectDialogListener listener, SelectDialogCancelListener cancelListener, List<String> names, String title) {
        super(activity, theme);
        mActivity = activity;
        mListener = listener;
        mCancelListener = cancelListener;
        this.mName=names;
        mTitle = title;

        // 设置是否点击外围可解散
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.view_dialog_select,
                null);
        setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        Window window = getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.take_photo_anim);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = mActivity.getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = LayoutParams.MATCH_PARENT;
        wl.height = LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        onWindowAttributesChanged(wl);

        initViews();
    }

    private void initViews() {
        dialogAdapter=new DialogAdapter(mName);
        ListView dialogList=(ListView) findViewById(R.id.dialog_list);
        dialogList.setOnItemClickListener(this);
        dialogList.setAdapter(dialogAdapter);
        mMBtn_Cancel = (Button) findViewById(R.id.mBtn_Cancel);
        mTv_Title = (TextView) findViewById(R.id.mTv_Title);


        mMBtn_Cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(mCancelListener != null){
                    mCancelListener.onCancelClick(v);
                }
                dismiss();
            }
        });

        if(!TextUtils.isEmpty(mTitle) && mTv_Title != null){
            mTv_Title.setVisibility(View.VISIBLE);
            mTv_Title.setText(mTitle);
        }else{
            mTv_Title.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        dismiss();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

        mListener.onItemClick(parent, view, position, id);
        dismiss();
    }
    private class DialogAdapter extends BaseAdapter {
        private List<String> mStrings;
        private Viewholder viewholder;
        private LayoutInflater layoutInflater;
        public DialogAdapter(List<String> strings) {
            this.mStrings = strings;
            this.layoutInflater=mActivity.getLayoutInflater();
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mStrings.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mStrings.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                viewholder=new Viewholder();
                convertView=layoutInflater.inflate(R.layout.view_dialog_item, null);
                viewholder.dialogItemButton=(TextView) convertView.findViewById(R.id.dialog_item_bt);
                convertView.setTag(viewholder);
            }else{
                viewholder=(Viewholder) convertView.getTag();
            }
            viewholder.dialogItemButton.setText(mStrings.get(position));
            if (!mUseCustomColor) {
                mFirstItemColor = mActivity.getResources().getColor(R.color.text_color_primary_dark);
                mOtherItemColor = mActivity.getResources().getColor(R.color.text_color_primary_dark);
            }
            if (1 == mStrings.size()) {
                viewholder.dialogItemButton.setTextColor(mFirstItemColor);
                viewholder.dialogItemButton.setBackgroundResource(R.drawable.dialog_item_bg_only);
            } else if (position == 0) {
                viewholder.dialogItemButton.setTextColor(mFirstItemColor);
                viewholder.dialogItemButton.setBackgroundResource(R.drawable.select_dialog_item_bg_top);
            } else if (position == mStrings.size() - 1) {
                viewholder.dialogItemButton.setTextColor(mOtherItemColor);
                viewholder.dialogItemButton.setBackgroundResource(R.drawable.select_dialog_item_bg_buttom);
            } else {
                viewholder.dialogItemButton.setTextColor(mOtherItemColor);
                viewholder.dialogItemButton.setBackgroundResource(R.drawable.select_dialog_item_bg_center);
            }

            if(!TextUtils.isEmpty(flag)){
                if(flag.equals("setting_midea")){
                    String quality=SharedPrefHelper.getInstance().getPlayQuaity();
                    if(mStrings.get(position).equals(quality)){
                        viewholder.dialogItemButton.setTextColor(mActivity.getResources().getColor(R.color.color_primary));
                    }
                }else if(flag.equals("setting_speed")){
                    float speed=SharedPrefHelper.getInstance().getPlaySpeed();
                    String spd="";
                    if(speed==1.0f){
                        spd="1.0x";
                    }else if(speed==1.2f){
                        spd="1.2x";
                    }else if(speed==1.5f){
                        spd="1.5x";
                    }else if(speed==1.8f){
                        spd="1.8x";
                    }
                    if(mStrings.get(position).equals(spd)){
                        viewholder.dialogItemButton.setTextColor(mActivity.getResources().getColor(R.color.color_primary));
                    }
                }
            }
            if (!TextUtils.isEmpty(selectStr)){
                if (selectStr.equals(mStrings.get(position))){
                    viewholder.dialogItemButton.setTextColor(ContextCompat.getColor(mActivity,R.color.color_primary));
                }else {
                    viewholder.dialogItemButton.setTextColor(ContextCompat.getColor(mActivity,R.color.text_color_primary_dark));
                }
            }

            return convertView;
        }

    }

    public void setSelectStr(String selectStr){
        this.selectStr = selectStr;
        if (dialogAdapter!=null){
            dialogAdapter.notifyDataSetChanged();
        }

    }

    public static class Viewholder {
        public TextView dialogItemButton;
    }

    /**
     * 设置列表项的文本颜色
     */
    public void setItemColor(int firstItemColor, int otherItemColor) {
        mFirstItemColor = firstItemColor;
        mOtherItemColor = otherItemColor;
        mUseCustomColor = true;
    }
}
