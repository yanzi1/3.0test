package com.me.fanyin.zbme.views.course.play.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.me.data.model.play.CourseDetail;
import com.me.data.model.play.CourseWare;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.course.play.PlayActivity;
import com.me.fanyin.zbme.views.course.play.adapter.CourseWareEpListAdapter;
import com.me.fanyin.zbme.views.course.play.adapter.CwListAdapter;
import com.me.fanyin.zbme.views.course.play.widget.FloatingGroupExpandableListView;
import com.me.fanyin.zbme.views.course.play.widget.WrapperExpandableListAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by fengzongwei on 2016/1/4.
 */
public class CourseListFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener{

    private View view;
    private FloatingGroupExpandableListView myExpandablelistview_course;//有章节区分时显示此控件
    private ListView listView;//不存在章节区分时显示此控件
    private CourseWareEpListAdapter courseWareListAdapter_courseEp;
    private CwListAdapter courseListAdapter;
    private LinearLayout linearLayout_loading, linearLayout_error;
    private RelativeLayout relativeLayout_loadingBody;

    private List<Object> list_nosection = new ArrayList<>();


    private PlayActivity playActivity;

    private CourseDetail courseDetail;
    private List<CourseWare> courseWareList;
    private boolean isChapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            if (msg.what == 1 && msg.obj != null && msg.obj instanceof CourseWare) {
//                onLongClickCheckDownload((CourseWare) msg.obj);
//                return;
//            }
//            if (msg.what == 2 && msg.obj != null && msg.obj instanceof CourseWare) {//有章节切换课程
//
//                CourseWare courseWare = courseDetail.getMobileSectionList().get(msg.arg1).getMobileCourseWareList().get(msg.arg2);
//                playActivity.playVedio(courseWare, msg.arg1, msg.arg2);
//                courseWareListAdapter_courseEp.notifyDataSetChanged();
//            }
//            if (msg.what == 3) {//没有章节时切换或暂停视频
//                CourseWare courseWare = courseWareList.get(msg.arg1);
//                playActivity.playVedio(courseWare, -1, msg.arg1);
//                courseListAdapter.notifyDataSetChanged();
//            }
//            if (courseWareListAdapter_courseEp != null) {
//                courseWareListAdapter_courseEp.notifyDataSetChanged();
//            }
//            if (courseListAdapter != null) {
//                courseListAdapter.notifyDataSetChanged();
//            }

        }
    };

    public static CourseListFragment getInstance(CourseDetail courseDetail,boolean isChapter) {
        CourseListFragment f = new CourseListFragment();
        Bundle args = new Bundle();
        args.putSerializable("courseDetail", (Serializable) courseDetail);
        args.putBoolean("isChapter", isChapter);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        playActivity = (PlayActivity) getActivity();
        view = inflater.inflate(R.layout.play_courselist_fragment, container, false);
        ButterKnife.bind(this, view);
        this.courseDetail=(CourseDetail)getArguments().getSerializable("courseDetail");
        this.isChapter=getArguments().getBoolean("isChapter");
        initView();
        if(isChapter){
            showGroupData(courseDetail);
        }else{
            showCwData(courseDetail.getResultList().get(0).getPcClientCourseWareList());
        }
        return view;
    }

    public void initView() {
        relativeLayout_loadingBody = (RelativeLayout) view.findViewById(R.id.courselist_fragment_loading_body_rl);
        linearLayout_loading = (LinearLayout) view.findViewById(R.id.courselist_fragment_loading_body_ll);
        linearLayout_error = (LinearLayout) view.findViewById(R.id.courselist_fragment_error_body_rl);
        myExpandablelistview_course = (FloatingGroupExpandableListView) view.findViewById(R.id.play_courselist_fragment_course_exlv);
        myExpandablelistview_course.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                CourseWare courseWare = courseDetail.getResultList().get(groupPosition).getPcClientCourseWareList().get(childPosition);
//                playActivity.playVedio(courseWare, groupPosition, childPosition);
                playActivity.holdToPlay(courseWare,groupPosition,childPosition,false);
                return true;
            }
        });
        myExpandablelistview_course.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
        listView = (ListView) view.findViewById(R.id.play_courselist_fragment_course_lv);

//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
////                onLongClickCheckDownload(courseWareList.get(position));
//                return false;
//            }
//        });
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }

    public void showGroupData(CourseDetail courseDetail) {
        if (relativeLayout_loadingBody != null)
            relativeLayout_loadingBody.setVisibility(View.GONE);
        this.courseDetail = courseDetail;
        if(myExpandablelistview_course == null){
            return;
        }
        myExpandablelistview_course.setVisibility(View.VISIBLE);
        listView.setVisibility(View.GONE);
        courseWareListAdapter_courseEp = new CourseWareEpListAdapter((PlayActivity) getActivity(), courseDetail, handler);
        final WrapperExpandableListAdapter wrapperAdapter = new WrapperExpandableListAdapter(courseWareListAdapter_courseEp);
        myExpandablelistview_course.setAdapter(wrapperAdapter);
        for (int i = 0; i < courseWareListAdapter_courseEp.getGroupCount(); i++) {
            myExpandablelistview_course.expandGroup(i);
        }

        myExpandablelistview_course.setSelectedChild(playActivity.getGourPosition(),playActivity.getChildPosition()-1,true);
    }

    public void showCwData(List<CourseWare> courseWareList) {
        if (relativeLayout_loadingBody != null)
            relativeLayout_loadingBody.setVisibility(View.GONE);
        this.courseWareList = courseWareList;
        myExpandablelistview_course.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        courseListAdapter = new CwListAdapter(playActivity, courseWareList, handler);
        listView.setAdapter(courseListAdapter);

        listView.setSelection(playActivity.getChildPosition()-1);
    }

    /**
     * 设置课程目录数据加载失败显示
     */
    public void setLoadDataError() {
        linearLayout_loading.setVisibility(View.GONE);
        linearLayout_error.setVisibility(View.VISIBLE);
    }

    public void notifyAdapter() {
        if(courseListAdapter!=null){
            courseListAdapter.notifyDataSetChanged();
        }
        if(courseWareListAdapter_courseEp!=null){
            courseWareListAdapter_courseEp.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        playActivity.holdToPlay(courseWareList.get(position),0,position,false);
    }

    /**
     * 条目长按时进行课件下载状态的判断，如果可以离线观看则提示
     *
     * @param cw
     */
//    private void onLongClickCheckDownload(final CourseWare cw) {
//        int percent = db.getPercent(SharedPrefHelper.getInstance(getActivity()).getUserId(), cw.getClassId(), cw.getCwId());
//        if (percent >= 70 && percent < 100) {
//            playActivity.pauseVideo();
//            DialogManager.showConfirmWithCancelDialog(playActivity, new DialogManager.ConfirmWithCancelDialogListener() {
//                @Override
//                public void confirm() {
//                    //TODO  选择离线观看
//                    if (playActivity.getPlayingCWId().equals(cw.getCwId())) {
//                        if (playActivity.isPlaying())
//                            playActivity.startVideo();
//                        else {
//                            playActivity.setIsPlayFromLocal(true);
//                            playActivity.startPlayVideo(cw);
//                        }
//                    } else {
//                        playActivity.setIsPlayFromLocal(true);
//                        playActivity.startPlayVideo(cw);
//                    }
//                    courseListAdapter.notifyDataSetChanged();
//                    Toast.makeText(playActivity, "离线观看", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void cancel() {
//                    if (NetWorkUtils.isNetworkAvailable(playActivity)) {
//                        if (playActivity.getPlayingCWId().equals(cw.getCwId())) {
//                            if (!playActivity.isPlaying())
//                                playActivity.startVideo();
//                            else
//                                playActivity.startPlayVideo(cw);
//                        } else
//                            playActivity.startPlayVideo(cw);
//                    } else {
//                        DialogManager.showOneButtonDialog(getActivity(), null, getResources().getString(R.string.dialog_message_vedio), 0, "确认", true, getResources().getString(R.string.dialog_title_vedio));
//                    }
//                }
//            }, "该视频可离线观看，是否进行离线观看", 0, "离线观看", "在线观看");
//        }
//    }

}
