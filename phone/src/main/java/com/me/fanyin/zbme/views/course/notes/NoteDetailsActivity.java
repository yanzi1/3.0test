package com.me.fanyin.zbme.views.course.notes;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.me.core.utils.NetWorkUtils;
import com.me.data.event.PostNoteEvent;
import com.me.data.model.play.NoteClassDetail;
import com.me.data.model.play.NoteDetail;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.base.CommonWebViewActivity;
import com.me.fanyin.zbme.views.course.adapter.NotesDetailAdapter;
import com.me.fanyin.zbme.views.course.play.widget.SelectDialog;
import com.me.fanyin.zbme.widget.CommonToolbar;
import com.me.fanyin.zbme.widget.DialogManager;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Created by dell on 2017/5/13.
 */

public class NoteDetailsActivity extends BaseMvpActivity<NotesDetailView, NotesDetailPersenter> implements NotesDetailView {

//    @BindView(R.id.note_detail_title)
    TextView noteDetailTitle;
//    @BindView(R.id.note_detail_time)
    TextView noteDetailTime;
//    @BindView(R.id.note_detail_content)
    TextView noteDetailContent;
    @BindView(R.id.note_detail_list)
    ListView noteDetailList;
    @BindView(R.id.toolbar)
    CommonToolbar toolbar;

    private NotesDetailAdapter adapter;
    private String noteId;
    private NoteDetail noteBean;
    private NoteClassDetail noteClassBean;
    private String from;    //1 课堂 2 试题
    private String fromQuestion;//从原题进来隐藏查看原题
    public static final int SUCCESS = 13;
    public static final int CLASS_SUCCESS = 14;
    public static final int ERROR = 44;
    private String lectureName;
    private View view;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESS:
                    hideDialogLoading();
                    String data=(String)msg.obj;
                    Toast.makeText(NoteDetailsActivity.this, data, Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case CLASS_SUCCESS:
                    hideDialogLoading();
                    String mssg=(String)msg.obj;
                    Toast.makeText(NoteDetailsActivity.this, mssg, Toast.LENGTH_SHORT).show();
                    if(noteClassBean!=null){
                        EventBus.getDefault().post(new PostNoteEvent(noteId,noteClassBean.getHanConId(),"1"));
                    }
                    finish();
                    break;
                case ERROR:

                    hideDialogLoading();
                    String message=(String)msg.obj;
                    Toast.makeText(NoteDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstnceState) {
        super.onCreate(savedInstnceState);
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(NoteDetailsActivity.this);
    }

    @Override
    public void showError(String message) {
        Message msg=Message.obtain();
        msg.what=ERROR;
        msg.obj=message;
        handler.sendMessage(msg);
    }

    @Override
    public void showResult(String data) {
        Message msg=Message.obtain();
        msg.what=SUCCESS;
        msg.obj=data;
        handler.sendMessage(msg);
    }

    @Override
    public void classDelResult(String data) {
        Message msg=Message.obtain();
        msg.what=CLASS_SUCCESS;
        msg.obj=data;
        handler.sendMessage(msg);
    }

    @Override
    public void showResult(NoteDetail noteDetail) {
        hideDialogLoading();
        if (noteDetail != null) {
            this.noteBean = noteDetail;
            noteDetailTitle.setText(noteDetail.getTitle());
            noteDetailContent.setText(noteDetail.getContent());

            if (!TextUtils.isEmpty(noteDetail.getUpdateTime())) {
                Date datePre = new Date(Long.parseLong(noteDetail.getUpdateTime()));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                noteDetailTime.setText(sdf.format(datePre));
            }else{
                noteDetailTime.setText(noteDetail.getShowTime());
            }

            if (noteDetail.getUrls() != null && noteDetail.getUrls().size() > 0) {
                adapter.setList(noteDetail.getUrls());
                noteDetailList.setAdapter(adapter);
            }else{
                adapter.setList(new ArrayList<String>());
                noteDetailList.setAdapter(adapter);
            }

        } else {
            Toast.makeText(appContext, "数据错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showClassResult(NoteClassDetail noteClassDetail) {
        hideDialogLoading();
        if (noteClassDetail != null) {
            this.noteClassBean = noteClassDetail;
            noteDetailTitle.setText(noteClassDetail.getTitle());
            noteDetailContent.setText(noteClassDetail.getContent());

            if (!TextUtils.isEmpty(noteClassDetail.getUpdateDate())) {
//                Date datePre = new Date(Long.parseLong(noteClassDetail.getServerStartTime()));
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                noteDetailTime.setText(noteClassDetail.getUpdateDate());
            }else{
                noteDetailTime.setText(noteClassDetail.getCreateDate());
            }

            if (noteClassDetail.getImgPaths() != null && noteClassDetail.getImgPaths().size() > 0) {
                adapter.setList(noteClassDetail.getImgPaths());
                noteDetailList.setAdapter(adapter);
            }else{
                adapter.setList(new ArrayList<String>());
                noteDetailList.setAdapter(adapter);
            }

        } else {
            Toast.makeText(appContext, "数据错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.play_notes_details;
    }

    @Override
    protected void initView() {

//        toolbar.setNavigationIcon(R.drawable.go_back);
        toolbar.setTitleText("笔记详情");
        toolbar.setImageMenuRes(R.mipmap.query_view_detailed);
        toolbar.setOnMenuClickListener(new CommonToolbar.CommonToolbarClickListener() {
            @Override
            public void onClick(View view) {
                initDialog();
            }
        });
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        adapter = new NotesDetailAdapter(this);

        from = getIntent().getStringExtra("detailFrom");
        noteId = getIntent().getStringExtra("noteId");
        fromQuestion = getIntent().getStringExtra("fromQuestion");

        view=View.inflate(this,R.layout.play_notes_header,null);
        noteDetailTitle=(TextView) view.findViewById(R.id.note_detail_title);
        noteDetailTime=(TextView) view.findViewById(R.id.note_detail_time);
        noteDetailContent=(TextView) view.findViewById(R.id.note_detail_content);
        noteDetailList.addHeaderView(view);
    }

    @Override
    protected void initData() {
        if(NetWorkUtils.isConnected(this)){
            showDialogLoading();
            if (!TextUtils.isEmpty(noteId)) {
                if (from.equals("1")) {
                    lectureName="查看讲义";
                    mPresenter.getNotesClassDetail(noteId);
                } else if (from.equals("2")) {
                    lectureName="查看原题";
                    mPresenter.getNotesDetail(noteId);
                }
            }
        }else{
            Toast.makeText(this, getResources().getString(R.string.app_nonetwork_message), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    private void initDialog(){
        List<String> names = new ArrayList<>();
        names.add("修改");
        names.add("删除");
        if(TextUtils.isEmpty(fromQuestion)){
            names.add(lectureName);
        }
        SelectDialog dialog = showDialog(new SelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (from.equals("1")) {
                            if (noteClassBean == null) {
                                Toast.makeText(NoteDetailsActivity.this, "该笔记不可编辑", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Intent intent = new Intent(NoteDetailsActivity.this, NotesActivity.class);
                                intent.putExtra("noteClassDetail", noteClassBean);
                                intent.putExtra("from", from);
                                startActivity(intent);
                            }
                        } else if (from.equals("2")) {
                            if (noteBean == null) {
                                Toast.makeText(NoteDetailsActivity.this, "该笔记不可编辑", Toast.LENGTH_SHORT).show();
                                return;
                            } else {
                                Intent intent = new Intent(NoteDetailsActivity.this, NotesActivity.class);
                                intent.putExtra("noteDetail", noteBean);
                                intent.putExtra("from", from);
                                startActivity(intent);
                            }
                        }
                        break;
                    case 1:
                        DialogManager.showConfirmWithCancelDialog(context(), new DialogManager.ConfirmWithCancelDialogListener() {
                            @Override
                            public void confirm() {
                                showDialogLoading();
                                if (from.equals("1")) {
                                    mPresenter.delteNotesClass(noteId);
                                } else if (from.equals("2")) {
                                    mPresenter.delteNotes(noteId);
                                }
                            }

                            @Override
                            public void cancel() {

                            }
                        }, "是否确认删除？", 0, null, null);
                        break;
                    case 2: //讲义详情、试题详情
                        if(from.equals("1")){
                            CommonWebViewActivity.startActivity(NoteDetailsActivity.this, "讲义详情", noteClassBean.getHdDetailUrl());
                        }else if(from.equals("2")){
                        }
                        break;
                    default:
                        break;
                }

            }
        }, names);
    }

    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    private boolean isFirst;
    @Override
    protected void onResume() {
        super.onResume();
        if(!isFirst){
            isFirst=true;
        }else{
            initData();
        }
    }
}
