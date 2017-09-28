package com.me.fanyin.zbme.views.download.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.me.core.utils.NetWorkUtils;
import com.me.core.utils.StringUtils;
import com.me.data.local.SharedPrefHelper;
import com.me.data.remote.rxjava.RxUtils;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.views.download.DownloadManager;
import com.me.fanyin.zbme.views.download.DownloadManagerView;
import com.me.fanyin.zbme.widget.DialogManager;
import com.me.rxdownload.entity.DownloadBundle;
import com.me.rxdownload.entity.DownloadEvent;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.me.rxdownload.entity.DownloadStatus.DELETED;
import static com.me.rxdownload.entity.DownloadStatus.DOWNLOADING;
import static com.me.rxdownload.entity.DownloadStatus.ERROR;
import static com.me.rxdownload.entity.DownloadStatus.FINISH;
import static com.me.rxdownload.entity.DownloadStatus.PAUSE;
import static com.me.rxdownload.entity.DownloadStatus.QUEUE;

/**
 * Created by mayunfei on 17-4-7.
 */

public class DownloadManagerAdapter extends BaseQuickAdapter<DownloadBundle, BaseViewHolder> {
    private MyDownloadAdapter.OnSelectListener onSelectListener;
    private boolean isEdit;
    private final DownloadManagerView mainView;
    private HashSet<String> selectList;


    private HashMap<BaseViewHolder, Disposable> disposableHashMap;

    public void setOnSelectListener(MyDownloadAdapter.OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    public DownloadManagerAdapter(List<DownloadBundle> data, DownloadManagerView mvpView) {
        super(R.layout.download_manager_item, data);
        disposableHashMap = new HashMap<>();
        this.mainView = mvpView;
        selectList = new HashSet<>();
    }

    public void setEdit(boolean edit) {
        this.isEdit = edit;
        if (isEdit) {
            if (mData.size() > 0) {
                if (onSelectListener != null) {
                    onSelectListener.onSelect(getSelectCount());
                }
                notifyDataSetChanged();
            }

        } else {
            notifyDataSetChanged();
        }
    }

    private int getSelectCount() {
        return selectList.size();
    }

    @Override
    protected void convert(final BaseViewHolder holder, final DownloadBundle bundle) {
        RxUtils.dispose(disposableHashMap.get(holder));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DialogManager.showConfirmWithCancelDialog(holder.itemView.getContext(), new DialogManager.ConfirmWithCancelDialogListener() {
                    @Override
                    public void confirm() {
                        DownloadManager.getInstance().delete(bundle.getKey())
                                .doOnSubscribe(new Consumer<Disposable>() {
                                    @Override
                                    public void accept(@NonNull Disposable disposable) throws Exception {
                                        mainView.showDialogLoading();
                                    }
                                })
                                .doFinally(new Action() {
                                    @Override
                                    public void run() throws Exception {
                                        mainView.hideDialogLoading();
                                    }
                                }).subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(@NonNull Object o) throws Exception {
                                mainView.deleteSuccess();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                mainView.deleteFail();
                            }
                        })
                        ;
                    }

                    @Override
                    public void cancel() {

                    }
                },  "确认删除？ ",0, null, null);
                return false;
            }
        });

        if (isEdit) {
            holder.setVisible(R.id.checkbox, true);
            if (selectList.contains(bundle.getKey())) {
                holder.setChecked(R.id.checkbox, true);

            } else {
                holder.setChecked(R.id.checkbox, false);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (selectList.contains(bundle.getKey())) {
                        selectList.remove(bundle.getKey());
                        holder.setChecked(R.id.checkbox, false);
                    } else {
                        holder.setChecked(R.id.checkbox, true);
                        selectList.add(bundle.getKey());
                    }

                    if (onSelectListener != null) {
                        onSelectListener.onSelect(getSelectCount());
                    }
                }
            });


        } else {
            holder.setVisible(R.id.checkbox, false);
        }


        Disposable disposable = DownloadManager.getInstance().getDownloadEvent(bundle.getKey())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DownloadEvent>() {
                    @Override
                    public void accept(@NonNull DownloadEvent downloadEvent) throws Exception {
                        ProgressBar progressBar = holder.getView(R.id.progress);
                        progressBar.setMax((int) downloadEvent.getTotalSize());
                        progressBar.setProgress((int) downloadEvent.getCompletedSize());

                        holder.setText(R.id.tv_key, bundle.getCwName());
                        ImageView imageButton = holder.getView(R.id.btn_status);
                        LinearLayout download_manager_status_ll = holder.getView(R.id.download_manager_status_ll);

                        switch (downloadEvent.getStatus()) {
                            case PAUSE:
                                imageButton.setImageResource(R.mipmap.btn_download_start);
                                download_manager_status_ll.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        onButtonClick(holder.itemView.getContext(), new Runnable() {
                                            @Override
                                            public void run() {
                                                DownloadManager.getInstance().addDownloadTask(bundle).doOnSubscribe(new Consumer<Disposable>() {
                                                    @Override
                                                    public void accept(@NonNull Disposable disposable) throws Exception {
                                                        mainView.showLoading();
                                                    }
                                                }).doFinally(new Action() {
                                                    @Override
                                                    public void run() throws Exception {
                                                        mainView.hideLoading();
                                                    }
                                                }).subscribe(new Consumer<Object>() {
                                                    @Override
                                                    public void accept(@NonNull Object o) throws Exception {

                                                    }
                                                }, new Consumer<Throwable>() {
                                                    @Override
                                                    public void accept(@NonNull Throwable throwable) throws Exception {
                                                        Logger.e("error error %s ", throwable);
                                                    }
                                                });
                                            }
                                        });

                                    }
                                });
                                holder.setText(R.id.tv_status, "暂停").setTextColor(R.id.tv_status, ContextCompat.getColor(holder.itemView.getContext(),R.color.text_color_primary_light_more));
                                break;
                            case QUEUE:
                                imageButton.setImageResource(R.mipmap.ico_download_waiting);
                                holder.setText(R.id.tv_status, "等待").setTextColor(R.id.tv_status, ContextCompat.getColor(holder.itemView.getContext(),R.color.text_color_primary_light_more));
                                download_manager_status_ll.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DownloadManager.getInstance().pause(bundle.getKey()).doOnSubscribe(new Consumer<Disposable>() {
                                            @Override
                                            public void accept(@NonNull Disposable disposable) throws Exception {
                                                mainView.showLoading();
                                            }
                                        }).doFinally(new Action() {
                                            @Override
                                            public void run() throws Exception {
                                                mainView.hideLoading();
                                            }
                                        }).subscribe(new Consumer<Object>() {
                                            @Override
                                            public void accept(@NonNull Object o) throws Exception {

                                            }
                                        }, new Consumer<Throwable>() {
                                            @Override
                                            public void accept(@NonNull Throwable throwable) throws Exception {
                                                Logger.e("error error %s ", throwable);
                                            }
                                        });
                                    }
                                });
                                break;
                            case FINISH:

//                                imageButton.setImageResource(R.mipmap.btn_download_start);
//                                holder.setText(R.id.tv_status, "完成").setTextColor(R.id.tv_status, ContextCompat.getColor(holder.itemView.getContext(),R.color.text_color_primary_light_more));
//                                download_manager_status_ll.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        String args2 = bundle.getCwStr();
//                                        CourseWare courseWare = JSON.parseObject(args2, CourseWare.class);
//                                        Intent intent = new Intent(holder.itemView.getContext(), PlayActivity.class);
//                                        intent.putExtra("cwBean", courseWare);
//                                        holder.itemView.getContext().startActivity(intent);
//                                    }
//                                });
                                mData.remove(bundle);
                                notifyDataSetChanged();


                                break;
                            case DOWNLOADING:
                                imageButton.setImageResource(R.mipmap.btn_download_pause);
                                holder.setText(R.id.tv_status, "下载中").setTextColor(R.id.tv_status, ContextCompat.getColor(holder.itemView.getContext(),R.color.text_color_primary_light_more));
                                double speed = downloadEvent.getSpeed();
                                if (speed < 0) {
                                    holder.setText(R.id.tv_status, 0 + " b/s  ");
                                }
                                else if (speed > 1024&& speed/1024<1024) {
                                    holder.setText(R.id.tv_status, StringUtils.formatPrice(speed / 1024) + " kb/s  ");
                                } else if (speed /1024 > 1024) {
                                    holder.setText(R.id.tv_status, StringUtils.formatPrice(speed / 1024 / 1024) + " m/s ");
                                } else {
                                    holder.setText(R.id.tv_status, Double.valueOf(speed).intValue() + " b/s ");
                                }

                                download_manager_status_ll.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DownloadManager.getInstance().pause(bundle.getKey()).doOnSubscribe(new Consumer<Disposable>() {
                                            @Override
                                            public void accept(@NonNull Disposable disposable) throws Exception {
                                                mainView.showLoading();
                                            }
                                        }).doFinally(new Action() {
                                            @Override
                                            public void run() throws Exception {
                                                mainView.hideLoading();
                                            }
                                        }).subscribe(new Consumer<Object>() {
                                            @Override
                                            public void accept(@NonNull Object o) throws Exception {

                                            }
                                        }, new Consumer<Throwable>() {
                                            @Override
                                            public void accept(@NonNull Throwable throwable) throws Exception {
                                                Logger.e("error error %s ", throwable);
                                            }
                                        });
                                    }
                                });

                                break;
                            case ERROR:
                                imageButton.setImageResource(R.mipmap.ico_download_fail);
                                holder.setText(R.id.tv_status, "获取失败").setTextColor(R.id.tv_status, ContextCompat.getColor(holder.itemView.getContext(),R.color.color_accent));
                                download_manager_status_ll.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        onButtonClick(holder.itemView.getContext(), new Runnable() {
                                            @Override
                                            public void run() {
                                                DownloadManager.getInstance().addDownloadTask(bundle).subscribe();
                                            }
                                        });
                                    }
                                });

                                break;
                            case DELETED:
                                mData.remove(bundle);
                                selectList.remove(bundle.getKey());
                                onSelectListener.onSelect(getSelectCount());
                                notifyDataSetChanged();
                                break;

                        }


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                });
        disposableHashMap.put(holder, disposable);
    }


    public void onButtonClick(final Context context, final Runnable runnable) {
        String type = NetWorkUtils.getNetworkTypeName(context);
        if (type.equals(NetWorkUtils.NETWORK_TYPE_DISCONNECT)) {//
            DialogManager.showOneButtonDialog(context, null, context.getResources().getString(R.string.dialog_message_vedio), 0, null);
        } else if (type.equals(NetWorkUtils.NETWORK_TYPE_WIFI)) {
            runnable.run();
        } else {

            //3/4G
            if (SharedPrefHelper.getInstance().canUseIn4G()) //34G可下
            {
                DialogManager.showConfirmWithCancelDialog(context, new DialogManager.ConfirmWithCancelDialogListener() {
                    @Override
                    public void confirm() {
                        runnable.run();
                    }

                    @Override
                    public void cancel() {

                    }
                }, context.getResources().getString(R.string.dialog_allow_download), 0, null, null);
            } else {
                DialogManager.showConfirmWithCancelDialog(context, new DialogManager.ConfirmWithCancelDialogListener() {
                    @Override
                    public void confirm() {
                        SharedPrefHelper.getInstance().set4GCanUse(true); //仅wifi
                        DialogManager.showConfirmWithCancelDialog(context, new DialogManager.ConfirmWithCancelDialogListener() {
                            @Override
                            public void confirm() {
                                runnable.run();
                            }

                            @Override
                            public void cancel() {

                            }
                        }, context.getResources().getString(R.string.dialog_allow_download), 0, null, null);
                    }

                    @Override
                    public void cancel() {

                    }
                }, context.getResources().getString(R.string.dialog_warnning_download), 0, null, null);
            }
        }
    }

    public void disposable() {
        for (Disposable disposable : disposableHashMap.values()) {
            RxUtils.dispose(disposable);
        }
    }

    public List<String> getSelect() {
        List<String> select = new ArrayList<>(selectList.size());
        for (String key : selectList) {
            select.add(key);
        }
        return select;
    }

    /**
     * 全选
     */
    public void selectAll() {

        if (isSellectAll()) {
           clearAll();
            return;
        }

        for (DownloadBundle downloadBundle : mData) {
            selectList.add(downloadBundle.getKey());
        }
        if (onSelectListener!=null){
            onSelectListener.onSelect(getSelectCount());
        }
        notifyDataSetChanged();
    }

    public void clearAll() {
        selectList.clear();
        onSelectListener.onSelect(getSelectCount());
        notifyDataSetChanged();
    }

    public boolean isSellectAll() {
        for (DownloadBundle downloadBundle : mData) {
            if (!selectList.contains(downloadBundle.getKey())) {
                return false;
            }
        }

        return true;
    }
}
