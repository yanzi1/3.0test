package com.me.fanyin.zbme.views.course.notes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.me.core.utils.DensityUtils;
import com.me.core.utils.NetWorkUtils;
import com.me.core.utils.StringUtils;
import com.me.data.event.PostNoteEvent;
import com.me.data.model.play.NoteClassDetail;
import com.me.data.model.play.NoteDetail;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.views.course.play.adapter.ImagePickerAdapter;
import com.me.fanyin.zbme.views.course.play.widget.GlideImageLoader;
import com.me.fanyin.zbme.views.course.play.widget.SelectDialog;
import com.me.fanyin.zbme.views.main.activity.divider.SpacesItemDecoration;
import com.me.fanyin.zbme.widget.CommonToolbar;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dell on 2017/5/13.
 */

public class NotesActivity extends BaseMvpActivity<NotesView, NotesPersenter> implements NotesView, ImagePickerAdapter.OnRecyclerViewItemClickListener {

    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.tv_limit)
    TextView tvLimit;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.note_post)
    Button notePost;


    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    public static final int POST_SUCCESS = 13;
    public static final int POST_COURSE_SUCCESS = 14;
    public static final int POST_ERROR = 44;
    public static final int POST_IMG_ERROR = 46;
    @BindView(R.id.toolbar)
    CommonToolbar toolbar;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 3;               //允许选择图片最大数
    private String from;   //1 课堂笔记编辑 2试题笔记编辑 3 课堂进来 4试题进来

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case POST_SUCCESS:
                    hideDialogLoading();
                    String result = (String) msg.obj;
                    Toast.makeText(appContext, result, Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case POST_COURSE_SUCCESS:
                    hideDialogLoading();
                    String noteId = (String) msg.obj;
                    String hanConId=noteDetails.getHanConId();
                    Toast.makeText(appContext, "笔记添加成功", Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new PostNoteEvent(noteId,hanConId,"0"));
                    finish();
                    break;
                case POST_ERROR:
                    urls = "";
                    hideDialogLoading();
                    String message = (String) msg.obj;
                    Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
                    break;
                case POST_IMG_ERROR:
                    urls = "";
                    backResult=0;
                    hideDialogLoading();
                    Toast.makeText(appContext, "图片上传失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstnceState) {
        super.onCreate(savedInstnceState);
        initImagePicker();
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(NotesActivity.this);
    }

    @Override
    public void showError(String message) {
        Message msg = Message.obtain();
        msg.what = POST_ERROR;
        msg.obj = message;
        handler.sendMessage(msg);
    }

    @Override
    public void showPostImgError() {
        Message msg = Message.obtain();
        msg.what = POST_IMG_ERROR;
        handler.sendMessage(msg);
    }

    @Override
    public void showResult(String result) {
        Message msg = Message.obtain();
        msg.what = POST_SUCCESS;
        msg.obj = result;
        handler.sendMessage(msg);
    }

    @Override
    public void showCourseResult(String result) {
        Message msg = Message.obtain();
        msg.what = POST_COURSE_SUCCESS;
        msg.obj = result;
        handler.sendMessage(msg);
    }

    private int backResult;

    String urls = "";

    @Override
    public void postResult(String result) {
        backResult++;
        if (TextUtils.isEmpty(urls)) {
            urls = urls + result;
        } else {
            urls = urls + "," + result;
        }
        if (backResult >= selImageList.size()) {
            backResult = 0;
            if (from.equals("1")) {
                mPresenter.updaNoteClass(noteId, title.getText().toString(), content.getText().toString(), urls);
            } else if (from.equals("2")) {
                mPresenter.updaNote(detail, title.getText().toString(), content.getText().toString(), urls);
            } else if (from.equals("3")) {
                mPresenter.postCommentClass(noteDetails, title.getText().toString(), content.getText().toString(), urls);
            } else if (from.equals("4")) {
                mPresenter.postComment(detail, title.getText().toString(), content.getText().toString(), urls);
            }
        }else{
            if(selImageList.get(backResult).path.startsWith("http")){
                postResult(selImageList.get(backResult).path);
            }else{
                mPresenter.postImage(selImageList.get(backResult).path);
            }
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.play_lecture_notes;
    }

    private SpacesItemDecoration itemLine;
    @Override
    protected void initView() {
//        toolbar.setNavigationIcon(R.drawable.go_back);
        toolbar.setTitleText("笔记");
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        title.setFilters(new InputFilter[]{
                new StringUtils.SpecialCharFilter()
                , new StringUtils.MaxLengthShowToastFilter(this,30,"标题")});
        content.setFilters(new InputFilter[]{
                new StringUtils.SpecialCharFilter()
                , new StringUtils.MaxLengthShowToastFilter(this,500,"内容")});
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvLimit.setText(s.length() + "");
                if (500 - s.length() >0 && 500 - s.length()<500) {
                    tvLimit.setTextColor(getResources().getColor(R.color.color_accent_2));
                }else if(500 - s.length()==0){
                    tvLimit.setTextColor(getResources().getColor(R.color.color_accent));
                }else if(500 - s.length()==500){
                    tvLimit.setTextColor(getResources().getColor(R.color.text_color_primary_light_more));
                }

                if(!TextUtils.isEmpty(s) && !TextUtils.isEmpty(title.getText())){
                    notePost.setEnabled(true);
                }else{
                    notePost.setEnabled(false);
                }
            }
        });

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(s) && !TextUtils.isEmpty(content.getText())){
                    notePost.setEnabled(true);
                }else{
                    notePost.setEnabled(false);
                }
            }
        });

        int divider = DensityUtils.dip2px(this,10);
        itemLine=new SpacesItemDecoration(divider,divider);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(itemLine);
        recyclerView.setAdapter(adapter);
        notePost.setOnClickListener(this);
        notePost.requestFocus();
        preOutData();
        tvLimit.setText(content.getText().toString().length() + "");
        if(from.equals("1") || from.equals("2")){
            notePost.setEnabled(true);
        }else{
            notePost.setEnabled(false);
        }
        if(content.getText().toString().length()==0){
            tvLimit.setTextColor(getResources().getColor(R.color.text_color_primary_light_more));
        }else if(content.getText().toString().length()>0 && content.getText().toString().length()<500){
            tvLimit.setTextColor(getResources().getColor(R.color.color_accent_2));
        }else if(content.getText().toString().length()>=500){
            tvLimit.setTextColor(getResources().getColor(R.color.color_accent));
        }
    }

    String noteId;
    private NoteDetail detail;
    private NoteClassDetail noteDetails;

    private void preOutData() {
        from = getIntent().getStringExtra("from");
        if (TextUtils.isEmpty(from)) {
            return;
        }
        if (from.equals("2")) { //试题笔记编辑
            detail = (NoteDetail) getIntent().getSerializableExtra("noteDetail");
            title.setText(detail.getTitle());
            content.setText(detail.getContent());
            List<String> urls = detail.getUrls();
            if (urls != null && urls.size() > 0) {
                for (int i = 0; i < urls.size(); i++) {
                    ImageItem image = new ImageItem();
                    image.path = urls.get(i);
                    if (i <= 2) {
                        selImageList.add(image);
                    }
                }
                adapter.setImages(selImageList);
            }
        } else if (from.equals("1")) { //课堂笔记编辑
            noteDetails = (NoteClassDetail) getIntent().getSerializableExtra("noteClassDetail");
            noteId = noteDetails.getId();
            title.setText(noteDetails.getTitle());
            content.setText(noteDetails.getContent());
            List<String> urls = noteDetails.getImgPaths();
            if (urls != null && urls.size() > 0) {
                for (int i = 0; i < urls.size(); i++) {
                    ImageItem image = new ImageItem();
                    image.path = urls.get(i);
                    if (i <= 2) {
                        selImageList.add(image);
                    }
                }
                adapter.setImages(selImageList);
            }
        } else if (from.equals("4")) {
            detail = (NoteDetail) getIntent().getSerializableExtra("noteDetail");
        } else if (from.equals("3")) {
            noteDetails = (NoteClassDetail) getIntent().getSerializableExtra("noteClassDetail");
        }
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.note_post:
                if(TextUtils.isEmpty(title.getText().toString())){
                    Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(content.getText().toString())){
                    Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!NetWorkUtils.isConnected(this)){
                    Toast.makeText(this, getResources().getString(R.string.app_nonetwork_message), Toast.LENGTH_SHORT).show();
                    return;
                }
                showDialogLoading();
                if (from.equals("1")) {
                    if (selImageList.size() > 0) {    //上传图片
//                        for (int i = 0; i < selImageList.size(); i++) {
//                            if (selImageList.get(i).path.startsWith("http")) {
//                                postResult(selImageList.get(i).path);
//                            } else {
//                                mPresenter.postImage(selImageList.get(i).path);
//                            }
//                        }
                        if(selImageList.get(0).path.startsWith("http")){
                            postResult(selImageList.get(0).path);
                        }else{
                            mPresenter.postImage(selImageList.get(0).path);
                        }
                    } else {
                        mPresenter.updaNoteClass(noteId, title.getText().toString(), content.getText().toString(), urls);
                    }
                } else if (from.equals("2")) {
                    if (selImageList.size() > 0) {    //上传图片
//                        for (int i = 0; i < selImageList.size(); i++) {
//                            if (selImageList.get(i).path.startsWith("http")) {
//                                postResult(selImageList.get(i).path);
//                            } else {
//                                mPresenter.postImage(selImageList.get(i).path);
//                            }
//                        }
                        if(selImageList.get(0).path.startsWith("http")){
                            postResult(selImageList.get(0).path);
                        }else{
                            mPresenter.postImage(selImageList.get(0).path);
                        }
                    } else {
                        mPresenter.updaNote(detail, title.getText().toString(), content.getText().toString(), urls);
                    }
                } else if (from.equals("3")) {
                    if (selImageList != null && selImageList.size() > 0) {    //上传图片
//                        for (int i = 0; i < selImageList.size(); i++) {
//                            mPresenter.postImage(selImageList.get(i).path);
//                        }
                        if(selImageList.get(0).path.startsWith("http")){
                            postResult(selImageList.get(0).path);
                        }else{
                            mPresenter.postImage(selImageList.get(0).path);
                        }
                    } else {
                        mPresenter.postCommentClass(noteDetails, title.getText().toString(), content.getText().toString(), urls);
                    }
                } else if (from.equals("4")) {
                    if (selImageList != null && selImageList.size() > 0) {    //上传图片
//                        for (int i = 0; i < selImageList.size(); i++) {
//                            mPresenter.postImage(selImageList.get(i).path);
//                        }
                        if(selImageList.get(0).path.startsWith("http")){
                            postResult(selImageList.get(0).path);
                        }else{
                            mPresenter.postImage(selImageList.get(0).path);
                        }
                    } else {
                        mPresenter.postComment(detail, title.getText().toString(), content.getText().toString(), urls);
                    }
                }
                break;
        }
    }

    ArrayList<ImageItem> images = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
    }


    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                String title="亲,您还可以上传"+(maxImgCount-selImageList.size())+"张图片";
                List<String> names = new ArrayList<>();
                names.add("拍照");
                names.add("从手机相册选择");
                showDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0: // 直接调起相机
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent = new Intent(NotesActivity.this, ImageGridActivity.class);
                                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                                startActivityForResult(intent, REQUEST_CODE_SELECT);
                                break;
                            case 1:
                                //打开选择,本次允许选择的数量
                                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                                Intent intent1 = new Intent(NotesActivity.this, ImageGridActivity.class);
                                /* 如果需要进入选择的时候显示已经选中的图片，
                                 * 详情请查看ImagePickerActivity
                                 * */
//                                intent1.putExtra(ImageGridActivity.EXTRAS_IMAGES,images);
                                startActivityForResult(intent1, REQUEST_CODE_SELECT);
                                break;
                            default:
                                break;
                        }

                    }
                }, names,title);


                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names,String title) {
        SelectDialog dialog = new SelectDialog(this, R.style
                .transparentFrameWindowStyle,
                listener, names,title);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }


}
