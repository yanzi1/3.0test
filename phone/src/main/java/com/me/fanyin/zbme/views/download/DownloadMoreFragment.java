package com.me.fanyin.zbme.views.download;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.me.core.exception.ApiException;
import com.me.core.utils.NetWorkUtils;
import com.me.core.utils.ToastBarUtils;
import com.me.data.common.Statistics;
import com.me.data.local.SharedPrefHelper;
import com.me.data.model.play.CourseChapter;
import com.me.data.model.play.CourseDetail;
import com.me.data.model.play.CourseWare;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpFragment;
import com.me.fanyin.zbme.views.course.play.PlayActivity;
import com.me.fanyin.zbme.views.course.play.widget.FloatingGroupExpandableListView;
import com.me.fanyin.zbme.views.course.play.widget.SelectDialog;
import com.me.fanyin.zbme.views.course.play.widget.WrapperExpandableListAdapter;
import com.me.fanyin.zbme.views.download.adapter.DownloadMoreChapterAdapter;
import com.me.fanyin.zbme.views.download.adapter.DownloadMoreNoChapterAdapter;
import com.me.fanyin.zbme.widget.dropdown.DropDownMenuTitle;
import com.me.fanyin.zbme.widget.DialogManager;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 课程下载
 * Created by mayunfei on 17-6-3.
 */

public class DownloadMoreFragment extends BaseMvpFragment<DownloadMoreView, DownloadMorePresenter> implements DownloadMoreView, AdapterView.OnItemClickListener, ExpandableListView.OnChildClickListener {
    @BindView(R.id.play_courselist_fragment_course_exlv)
    FloatingGroupExpandableListView expandableListView;
    @BindView(R.id.play_courselist_fragment_course_lv)
    ListView listView;
    @BindView(R.id.download_size_menu)
    DropDownMenuTitle download_size_menu;
    @BindView(R.id.download_layout)
    View downloadLayout;
    @BindView(R.id.close)
    ImageView close;
    @BindView(R.id.menu_layout)
    View menu_layout;
    List<String> quaitys;
    private String defDownloadQuaity;


    @BindView(R.id.download_hot)
    TextView downloadHot;

    private List<CourseWare> listData;
    private List<CourseChapter> expandData;

    private DownloadMoreNoChapterAdapter listViewAdapter;
    private DownloadMoreChapterAdapter expandAdapter;
    private CourseDetail courseDetail;
    private CourseWare playingCourseWare;

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download_layout:
                gotoActivity(MyDownloadActivity.class);
                MobclickAgent.onEvent(getActivity(), Statistics.PROFILE_DOWNLOAD);
                break;
            case R.id.close:
                FragmentActivity activity = getActivity();
                if (activity instanceof PlayActivity) {
                    ((PlayActivity) activity).hideSetting(this);
                }
                break;
            case R.id.download_size_menu:

                showPlayQuaityDialog(new SelectDialog.SelectDialogListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        switch (position) {
                            case 0:
                                defDownloadQuaity = "流畅";
                                break;
                            case 1:
                                defDownloadQuaity = "标清";
                                break;
                            case 2:
                                defDownloadQuaity = "高清";
                                break;
                            default:
                                break;
                        }
                        download_size_menu.setText(defDownloadQuaity);
                        download_size_menu.closeMenu();
                    }
                }, new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        download_size_menu.closeMenu();
                    }
                }, defDownloadQuaity);
                download_size_menu.openMenu();
                break;
        }
    }


    public void showPlayQuaityDialog(SelectDialog.SelectDialogListener selectDialogListener, DialogInterface.OnDismissListener dismissListener, String select) {
        showDialog(selectDialogListener, quaitys, dismissListener, select);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void initView() {
        expandData = new ArrayList<>();
        listData = new ArrayList<>();
        listViewAdapter = new DownloadMoreNoChapterAdapter(listData, mPresenter);
        expandAdapter = new DownloadMoreChapterAdapter(expandData, mPresenter);
        listView.setAdapter(listViewAdapter);
        expandableListView.setAdapter(new WrapperExpandableListAdapter(expandAdapter));
        download_size_menu.setOnClickListener(this);
        downloadLayout.setOnClickListener(this);
        close.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        expandableListView.setOnChildClickListener(this);
        quaitys = new ArrayList<>();
        quaitys.add("流畅");
        quaitys.add("标清");
        quaitys.add("高清");
        defDownloadQuaity = SharedPrefHelper.getInstance().getPlayQuaity();
        download_size_menu.setText(defDownloadQuaity);
        if (this.courseDetail != null) {
            setData(this.courseDetail);
        } else {
            menu_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getDownloading();
    }

    @Override
    public void initData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            CourseWare courseWare = (CourseWare) arguments.getSerializable("data");
            if (courseWare != null) {
                mPresenter.getData(courseWare);
            }
        }

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.download_more_fragment;
    }

    public void setDetail(CourseDetail courseDetail, CourseWare courseWare) {
        this.playingCourseWare = courseWare;
        if (courseDetail.getResultList().size() > 1) {    //包含章节
            if (expandAdapter != null) {
                expandAdapter.setPlayingCourseWare(playingCourseWare);
                expandAdapter.notifyDataSetChanged();
            }
        } else {
            if (listViewAdapter != null) {
                listViewAdapter.setPlayingCourseWare(playingCourseWare);
                listViewAdapter.notifyDataSetChanged();
            }
        }
        this.courseDetail = courseDetail;
        if (download_size_menu != null) {
            defDownloadQuaity = SharedPrefHelper.getInstance().getPlayQuaity();
            download_size_menu.setText(defDownloadQuaity);
        }
    }

    @Override
    public void setData(CourseDetail courseDetail) {
        if (courseDetail != null) {
            if (courseDetail.getResultList().size() > 1) {    //包含章节
//                isHavaChapter = true;
//                showData(courseDetail);
                expandableListView.setVisibility(View.VISIBLE);
                listView.setVisibility(View.GONE);
                expandData.clear();
                expandData.addAll(courseDetail.getResultList());
                expandAdapter.notifyDataSetChanged();
                expandAdapter.setPlayingCourseWare(playingCourseWare);
                for (int i = 0; i < expandAdapter.getGroupCount(); i++) {
                    expandableListView.expandGroup(i);
                }

            } else {
//                isHavaChapter = false;
//                showCwData(courseDetail.getResultList().get(0).getPcClientCourseWareList());
                expandableListView.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                listViewAdapter.setPlayingCourseWare(playingCourseWare);
                List<CourseWare> pcClientCourseWareList = courseDetail.getResultList().get(0).getPcClientCourseWareList();
                listData.clear();
                listData.addAll(pcClientCourseWareList);
                listViewAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void setdownloadCount(int size) {
        if (size > 0) {
            downloadHot.setVisibility(View.VISIBLE);
            downloadHot.setText("" + size);
        } else {
            downloadHot.setVisibility(View.GONE);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        listViewAdapter.disposable();
        expandAdapter.disposable();
    }


    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names, DialogInterface.OnDismissListener dismissListener, String select) {
        SelectDialog dialog = new SelectDialog(getActivity(), R.style.transparentFrameWindowStyle, listener, names);
        if (!getActivity().isFinishing()) {
            dialog.setSelectStr(select);
            dialog.show();
        }
        dialog.setOnDismissListener(dismissListener);
        return dialog;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        download(listData.get(position));

    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        download(expandData.get(groupPosition).getPcClientCourseWareList().get(childPosition));
        return true;
    }

    private void download(final CourseWare courseWare) {
        if (DownloadManager.getInstance().isCWDownlaoded(courseWare)) {
            return;
        }
        if (DownloadManager.getInstance().isAddDownloadTask(courseWare)){
            return;
        }
        String type = NetWorkUtils.getNetworkTypeName(context());
        if (type.equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {//
            DialogManager.showOneButtonDialog(context(), null, context().getResources().getString(R.string.dialog_message_vedio), 0, null);
        } else if (type.equals(NetWorkUtils.NETWORK_TYPE_WIFI)) {
            if (DownloadManager.getInstance().haveEnoughSpace()) {
                downloadVideo(courseWare);

            } else {
                DialogManager.showOneButtonDialog(context(), null, "您的存储空间不足，无法下载该视频", 0, null);
            }
        } else {
            if (DownloadManager.getInstance().haveEnoughSpace()) {

                //3/4G
                if (SharedPrefHelper.getInstance().canUseIn4G()) //34G可下
                {
                    DialogManager.showConfirmWithCancelDialog(context(), new DialogManager.ConfirmWithCancelDialogListener() {
                        @Override
                        public void confirm() {
                            downloadVideo(courseWare);
                        }

                        @Override
                        public void cancel() {

                        }
                    }, context().getResources().getString(R.string.dialog_allow_download), 0, null, null);
                } else {
                    DialogManager.showConfirmWithCancelDialog(context(), new DialogManager.ConfirmWithCancelDialogListener() {
                        @Override
                        public void confirm() {
                            SharedPrefHelper.getInstance().set4GCanUse(true);
                            FragmentActivity activity = getActivity();
                            if (activity instanceof PlayActivity){
                                ((PlayActivity) activity).refreshSetting();
                            }
                            DialogManager.showConfirmWithCancelDialog(context(), new DialogManager.ConfirmWithCancelDialogListener() {
                                @Override
                                public void confirm() {
                                    downloadVideo(courseWare);
                                }

                                @Override
                                public void cancel() {

                                }
                            }, context().getResources().getString(R.string.dialog_allow_download), 0, null, null);
                        }

                        @Override
                        public void cancel() {

                        }
                    }, context().getResources().getString(R.string.dialog_warnning_download), 0, null, null);
                }


            } else {
                DialogManager.showOneButtonDialog(context(), null, "您的存储空间不足，无法下载该视频", 0, null);
            }
        }

    }

    private void downloadVideo(CourseWare courseWare) {

        switch (defDownloadQuaity) {
            case "流畅":
                MobclickAgent.onEvent(mActivity, Statistics.COURSE_SMALLSCREEN_DOWNLOADFLUENT);
                break;
            case "标清":
                MobclickAgent.onEvent(mActivity, Statistics.COURSE_SMALLSCREEN_DOWNLOADSD);
                break;
            case "高清":
                MobclickAgent.onEvent(mActivity, Statistics.COURSE_SMALLSCREEN_DOWNLOADHD);
                break;
        }

        DownloadManager.getInstance().download(courseWare, defDownloadQuaity)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        showDialogLoading();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        hideDialogLoading();
                    }
                }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                mPresenter.getDownloading();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull final Throwable throwable) throws Exception {
                Log.e("999999", "************************* throwable = " + throwable);
//                ToastBarUtils.showToast(context(), throwable.getMessage());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (throwable instanceof ApiException) {
                            ToastBarUtils.showToast(context(), throwable.getMessage());
                        } else {
                            ToastBarUtils.showToast(context(), "解析下载地址失败");
                        }
                    }
                });
            }
        });
    }


}
