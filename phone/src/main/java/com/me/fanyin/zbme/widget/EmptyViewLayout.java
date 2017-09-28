package com.me.fanyin.zbme.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.fanyin.zbme.R;


public class EmptyViewLayout {

    // ---------------------------
    // static variables
    // ---------------------------
    /**
     * The empty state
     */
    public final static int TYPE_EMPTY = 1;
    /**
     * The loading state
     */
    public final static int TYPE_LOADING = 2;
    /**
     * The error state
     */
    public final static int TYPE_ERROR = 3;
    /**
     * The content view state
     */
    public final static int TYPE_CONTENT_VIEW_STATE = 4;
    /**
     * The net error state
     */
    public final static int TYPE_NET_ERROR = 5;

    /**
     * The other error state
     */
    public final static int TYPE_OTHER_ERROR = 6;

    // ---------------------------
    // members variables
    // ---------------------------
    private Context mContext;
    private LinearLayout mBackgroundViews;
    private ViewGroup mLoadingView;
    private ViewGroup mEmptyView;
    private ViewGroup mErrorView;
    private View mContentView;
    private LayoutInflater mInflater;
    private boolean mViewsAdded;
    private View.OnClickListener mEmptyButtonClickListener;
    private View.OnClickListener mErrorButtonClickListener;
    private View.OnClickListener mOtherClickListener;

    // ---------------------------
    // default values
    // ---------------------------
    private int mEmptyType = TYPE_LOADING;
    private String mErrorMessage;
    private String mEmptyMessage;
    private String mErroButtonTitle = null;
    private String mEmptyButtonTitle = null;
    private boolean mShowEmptyButton = true;
    private boolean mShowErrorButton = true;
    private boolean mShowNetErrorButton = true;
    private int mShortAnimationDuration;

    private boolean mShowEmptyImage = false; //是否显示图片
    private int mImageId; //空白页面的图片Id
    private ViewGroup netErrorView = null;
    private ViewGroup dataEmptyView = null;
    private ViewGroup dataOtherView = null;

    // ---------------------------
    // getters and setters
    // ---------------------------

    /**
     * Gets the loading layout
     *
     * @return the loading layout
     */
    public ViewGroup getLoadingView() {
        return mLoadingView;
    }

    /**
     * Gets the empty layout
     *
     * @return the empty layout
     */
    public ViewGroup getEmptyView() {
        return mEmptyView;
    }

    /***
     * 设置无数据显示的view
     */
    public void setEmptyView(ViewGroup mEmptyView) {
        this.mEmptyView = mEmptyView;
    }

    /**
     * Gets the error layout
     *
     * @return the error layout
     */
    public ViewGroup getErrorView() {
        return mErrorView;
    }

    /***
     * 设置错误显示的view
     */
    public void setErrorView(ViewGroup mErrorView) {
        this.mErrorView = mErrorView;
    }

    /**
     * Gets the content view for which this library is being used
     *
     * @return the content view
     */
    public View getMainView() {
        return mContentView;
    }

    /**
     * Gets the last set state of the content view
     *
     * @return loading or empty or error
     */
    public int getEmptyType() {
        return mEmptyType;
    }

    /**
     * Sets the state of the empty view of the content view
     *
     * @param emptyType loading or empty or error
     */
    public void setEmptyType(int emptyType) {
        this.mEmptyType = emptyType;
        changeEmptyType();
    }

    /**
     * Gets the message which is shown when the content view could not be loaded
     * due to some error
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return mErrorMessage;
    }

    /**
     * Sets the message to be shown when the content view could not be loaded
     * due to some error
     *
     * @param errorMessage the error message
     */
    public void setErrorMessage(String errorMessage) {
        this.mErrorMessage = errorMessage;
    }

    /**
     * Sets the title for the error button
     *
     * @param titleErrorButton
     */
    public void setTitleErrorButton(String titleErrorButton) {
        this.mErroButtonTitle = titleErrorButton;
    }

    public String getTitleErrorButton() {
        return mErroButtonTitle;
    }

    /**
     * Gets the message which will be shown when the content view is empty
     *
     * @return the message which will be shown when the content view will be
     * empty
     */
    public String getEmptyMessage() {
        return mEmptyMessage;
    }

    /**
     * Sets the message to be shown when content view will be empty
     *
     * @param emptyMessage the message
     */
    public void setEmptyMessage(String emptyMessage) {
        this.mEmptyMessage = emptyMessage;
    }

    /**
     * Sets the title for the empty button
     *
     * @param titleEmptyButton
     */
    public void setTitleEmptyButton(String titleEmptyButton) {
        this.mEmptyButtonTitle = titleEmptyButton;
    }

    public String getTitleEmptyButton() {
        return mEmptyButtonTitle;
    }

    /**
     * Gets the OnClickListener which perform when EmptyView was click
     *
     * @return
     */
    public View.OnClickListener getEmptyButtonClickListener() {
        return mEmptyButtonClickListener;
    }

    /**
     * Sets the OnClickListener to EmptyView
     *
     * @param emptyButtonClickListener OnClickListener Object
     */
    public void setEmptyButtonClickListener(
            View.OnClickListener emptyButtonClickListener) {
        this.mEmptyButtonClickListener = emptyButtonClickListener;
    }

    /**
     * Gets the OnClickListener which perform when ErrorView was click
     *
     * @return
     */
    public View.OnClickListener getErrorButtonClickListener() {
        return mErrorButtonClickListener;
    }

    /**
     * Sets the OnClickListener to ErrorView
     *
     * @param errorButtonClickListener OnClickListener Object
     */
    public void setErrorButtonClickListener(
            View.OnClickListener errorButtonClickListener) {
        this.mErrorButtonClickListener = errorButtonClickListener;
    }

    public void setOtherOnClickListener(
            View.OnClickListener onClickListener) {
        this.mOtherClickListener = onClickListener;
    }

    /**
     * Gets the ImageView which will be shown when the content view is empty
     *
     * @return the ImageView which will be shown when the content view will be
     * empty
     */
    public int getEmptyImageId() {
        return mImageId;
    }

    /**
     * Sets the ImageView to be shown when content view will be empty
     *
     * @param mImageId the message
     */
    public void setEmptyImageId(int mImageId) {
        this.mImageId = mImageId;
    }

    /**
     * Gets if a ImageView is shown in the empty view
     *
     * @return if a ImageView is shown in the empty view
     */
    public boolean isEmptyImageShown() {
        return mShowEmptyButton;
    }

    /**
     * Sets if a ImageView will be shown in the empty view
     *
     * @param showEmptyImage will a ImageView be shown in the empty view
     */
    public void setShowEmptyImage(boolean showEmptyImage) {
        this.mShowEmptyButton = showEmptyImage;
    }

    /**
     * Gets if a button is shown in the empty view
     *
     * @return if a button is shown in the empty view
     */
    public boolean isEmptyButtonShown() {
        return mShowEmptyButton;
    }

    /**
     * Sets if a button will be shown in the empty view
     *
     * @param showEmptyButton will a button be shown in the empty view
     */
    public void setShowEmptyButton(boolean showEmptyButton) {
        this.mShowEmptyButton = showEmptyButton;
    }

    /**
     * Gets if a button is shown in the error view
     *
     * @return if a button is shown in the error view
     */
    public boolean isErrorButtonShown() {
        return mShowErrorButton;
    }

    /**
     * Sets if a button will be shown in the error view
     *
     * @param showErrorButton will a button be shown in the error view
     */
    public void setShowErrorButton(boolean showErrorButton) {
        this.mShowErrorButton = showErrorButton;
    }

    // ---------------------------
    // private methods
    // ---------------------------

    public void setmShowEmptyImage(boolean mShowEmptyImage) {
        this.mShowEmptyImage = mShowEmptyImage;
    }

    private void changeEmptyType() {

        setDefaultValues();
        refreshMessages();
        refreshOnClickListeners();

        if (mShowEmptyImage && mImageId != 0) {
            ImageView emptyViewImage = (ImageView) mEmptyView.findViewById(R.id.app_view_iv);
            emptyViewImage.setImageResource(mImageId);
        }

        // insert views in the root view
        if (!mViewsAdded) {
            // init background views

            // TODO is better inflate the views
            // getLayoutInflater().inflate(layoutResID,ViewGroup);
            mBackgroundViews = new LinearLayout(mContext);
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            mBackgroundViews.setBackgroundColor(Color.WHITE);
            mBackgroundViews.setGravity(Gravity.CENTER);
            mBackgroundViews.setLayoutParams(lp);
            mBackgroundViews.setOrientation(LinearLayout.VERTICAL);

            if (mEmptyView != null) {
                mBackgroundViews.addView(mEmptyView);
            }
            if (mLoadingView != null) {
                mBackgroundViews.addView(mLoadingView);
            }
            if (mErrorView != null) {
                mBackgroundViews.addView(mErrorView);
            }
            if (netErrorView != null) {
                mBackgroundViews.addView(netErrorView);
            }
            if (dataOtherView != null) {
                mBackgroundViews.addView(dataOtherView);
            }
            mViewsAdded = true;

            ((ViewGroup) mContentView.getParent()).addView(mBackgroundViews);
        }

        if(mEmptyType==TYPE_OTHER_ERROR){
            mBackgroundViews.setBackgroundColor(mContext.getResources().getColor(R.color.exam_complex_drag_view_bg));
        }else{
            mBackgroundViews.setBackgroundColor(Color.WHITE);
        }

        // change empty type
        if (mContentView != null) {
            mBackgroundViews.setGravity(Gravity.CENTER);
            refreshMessages();
            switch (mEmptyType) {
                case TYPE_NET_ERROR:
                    mBackgroundViews.setVisibility(View.VISIBLE);
                    if (dataOtherView != null) {
                        dataOtherView.setVisibility(View.GONE);
                    }
                    if (mEmptyView != null) {
                        mEmptyView.setVisibility(View.GONE);
                    }
                    if (mErrorView != null) {
                        mErrorView.setVisibility(View.GONE);
                    }
                    if (mLoadingView != null) {
                        mLoadingView.setVisibility(View.GONE);

                    }
                    if (mContentView != null) {
                        mContentView.setVisibility(View.GONE);
                        mContentView.setEnabled(false);
                    }

                    if (netErrorView != null) {
                        mBackgroundViews.setGravity(Gravity.TOP);
                        netErrorView.setVisibility(View.VISIBLE);
                    }
                    break;
                case TYPE_EMPTY:
                    mBackgroundViews.setVisibility(View.VISIBLE);
                    if (dataOtherView != null) {
                        dataOtherView.setVisibility(View.GONE);
                    }
                    if (mEmptyView != null) {
                        mBackgroundViews.setGravity(Gravity.TOP);
                        mEmptyView.setVisibility(View.VISIBLE);
                    }
                    if (netErrorView != null) {
                        netErrorView.setVisibility(View.GONE);
                    }
                    if (mErrorView != null) {
                        mErrorView.setVisibility(View.GONE);
                    }
                    if (mLoadingView != null) {
                        mLoadingView.setVisibility(View.GONE);

                    }

                    if (mContentView != null) {
                        mContentView.setVisibility(View.GONE);
                        mContentView.setEnabled(false);
                    }
                    break;
                case TYPE_ERROR:
                    mBackgroundViews.setVisibility(View.VISIBLE);
                    if (dataOtherView != null) {
                        dataOtherView.setVisibility(View.GONE);
                    }
                    if (netErrorView != null) {
                        netErrorView.setVisibility(View.GONE);
                    }
                    if (mEmptyView != null) {
                        mEmptyView.setVisibility(View.GONE);
                    }
                    if (mErrorView != null) {
                        mBackgroundViews.setGravity(Gravity.TOP);
                        mErrorView.setVisibility(View.VISIBLE);
                    }

                    if (mLoadingView != null) {
                        mLoadingView.setVisibility(View.GONE);

                    }
                    if (mContentView != null) {
                        mContentView.setVisibility(View.GONE);
                        mContentView.setEnabled(false);
                    }
                    break;
                case TYPE_LOADING:
                    mBackgroundViews.setVisibility(View.VISIBLE);
                    if (netErrorView != null) {
                        netErrorView.setVisibility(View.GONE);
                    }
                    if (mEmptyView != null) {
                        mEmptyView.setVisibility(View.GONE);
                    }
                    if (mErrorView != null) {
                        mErrorView.setVisibility(View.GONE);
                    }
                    if (dataOtherView != null) {
                        dataOtherView.setVisibility(View.GONE);
                    }
                    if (mLoadingView != null) {
                        mLoadingView.setVisibility(View.VISIBLE);
                    }

                    if (mContentView != null) {
                        mContentView.setVisibility(View.GONE);
                        mContentView.setEnabled(false);
                    }
                    break;
                case TYPE_CONTENT_VIEW_STATE:
                    if (netErrorView != null) {
                        netErrorView.setVisibility(View.GONE);
                    }
                    if (mEmptyView != null) {
                        mEmptyView.setVisibility(View.GONE);
                    }
                    if (mErrorView != null) {
                        mErrorView.setVisibility(View.GONE);
                    }
                    if (dataOtherView != null) {
                        dataOtherView.setVisibility(View.GONE);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
                        if (mContentView != null) {
                            if (mLoadingView != null
                                    && mLoadingView.getVisibility() == View.VISIBLE) {
                                crossfadeView();
                            } else {
                                mBackgroundViews.setVisibility(View.GONE);
                                mContentView.setVisibility(View.VISIBLE);
                            }
                            mContentView.setEnabled(true);
                        }
                    } else {
                        if (mLoadingView != null) {
                            mLoadingView.setVisibility(View.GONE);
                        }
                        if (mContentView != null) {
                            mBackgroundViews.setVisibility(View.GONE);
                            mContentView.setVisibility(View.VISIBLE);
                            mContentView.setEnabled(true);
                        }
                    }
                    break;
                case TYPE_OTHER_ERROR:
                    mBackgroundViews.setVisibility(View.VISIBLE);
                    if (mEmptyView != null) {
                        mEmptyView.setVisibility(View.GONE);
                    }
                    if (mErrorView != null) {
                        mErrorView.setVisibility(View.GONE);
                    }
                    if (mLoadingView != null) {
                        mLoadingView.setVisibility(View.GONE);

                    }
                    if (mContentView != null) {
                        mContentView.setVisibility(View.GONE);
                        mContentView.setEnabled(false);
                    }
                    if (netErrorView != null) {
                        netErrorView.setVisibility(View.GONE);
                    }
                    if (dataOtherView != null) {
                        mBackgroundViews.setGravity(Gravity.TOP);
                        dataOtherView.setVisibility(View.VISIBLE);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void refreshMessages() {
        if (mEmptyView != null) {
            if (mEmptyMessage != null) {
                ((TextView) mEmptyView.findViewById(R.id.app_message_tv))
                        .setText(mEmptyMessage);
            } else {
                ((TextView) mEmptyView.findViewById(R.id.app_message_tv))
                        .setText(mContext.getString(R.string.app_empty_message));
            }
        }
        if (mErrorView != null) {

            if (mErrorMessage != null) {
                ((TextView) mErrorView.findViewById(R.id.app_message_tv))
                        .setText(mErrorMessage);
            } else {
                if (dataEmptyView == null) {
                    ((TextView) mErrorView.findViewById(R.id.app_message_tv))
                            .setText(mContext.getString(R.string.app_error_message));
                }

            }
        }
        if (mEmptyView != null) {
            if (mEmptyButtonTitle != null) {
                ((Button) mEmptyView.findViewById(R.id.app_action_empty_btn))
                        .setText(mEmptyButtonTitle);
            } else {
                if (dataEmptyView == null) {
                    ((Button) mEmptyView.findViewById(R.id.app_action_empty_btn))
                            .setText(mContext.getString(R.string.app_empty_button));

                }
            }
        }
        if (mErrorView != null) {

            if (mErroButtonTitle != null) {
                ((Button) mErrorView.findViewById(R.id.app_action_error_btn))
                        .setText(mErroButtonTitle);
            } else {
                if (netErrorView == null && dataEmptyView == null) {
                    ((Button) mEmptyView.findViewById(R.id.app_action_error_btn))
                            .setText(mContext.getString(R.string.app_empty_button));

                }
            }
        }
    }

    private void refreshOnClickListeners() {

        if (mShowEmptyButton && mEmptyButtonClickListener != null) {
            View emptyViewButton = mEmptyView.findViewById(R.id.app_action_empty_btn);
            View emptyViewLl = mEmptyView.findViewById(R.id.app_action_empty_ll);
            if (emptyViewButton != null) {
                emptyViewButton.setOnClickListener(mEmptyButtonClickListener);
                emptyViewButton.setVisibility(View.GONE);
            }
            if (emptyViewLl != null) {
                emptyViewLl.setOnClickListener(mEmptyButtonClickListener);
            }
        }

        if (mShowEmptyImage && mImageId != 0) {
            ImageView emptyViewImage = (ImageView) mEmptyView.findViewById(R.id.app_view_iv);
            emptyViewImage.setImageResource(mImageId);
        }

        if (mShowErrorButton && mErrorButtonClickListener != null) {
            View errorViewButton = mErrorView.findViewById(R.id.app_action_error_btn);
            if (errorViewButton != null) {
                errorViewButton.setOnClickListener(mErrorButtonClickListener);
                errorViewButton.setVisibility(View.VISIBLE);
            }
        }

        if (mShowNetErrorButton && mErrorButtonClickListener != null) {
            View errorViewButton = netErrorView.findViewById(R.id.app_action_nonetwork_btn);
            if (errorViewButton != null) {
                errorViewButton.setOnClickListener(mErrorButtonClickListener);
                errorViewButton.setVisibility(View.VISIBLE);
            }
        }

    }

    /**
     * 设置联网失败显示的view
     */
    public void setNetErrorView(ViewGroup view) {
        netErrorView = view;
    }

    /***
     * 设置无数据显示的view
     */
    public void setDataEmptyView(ViewGroup view) {
        this.dataEmptyView = view;
    }

    private void setDefaultValues() {

        if (dataEmptyView != null) {
            mEmptyView = dataEmptyView;
        }
        if (netErrorView == null) {
            netErrorView = (ViewGroup) mInflater.inflate(
                    R.layout.app_view_nonetwork, null);
            if (mShowEmptyButton && mEmptyButtonClickListener != null) {
                View emptyViewButton = netErrorView.findViewById(R.id.app_action_nonetwork_btn);
                if (emptyViewButton != null) {
                    emptyViewButton
                            .setOnClickListener(mEmptyButtonClickListener);
                    emptyViewButton.setVisibility(View.VISIBLE);
                }
            } else {
                View emptyViewButton = netErrorView.findViewById(R.id.app_action_nonetwork_btn);
                emptyViewButton.setVisibility(View.GONE);
            }
        }
        if (mEmptyView == null) {
            mEmptyView = (ViewGroup) mInflater.inflate(
                    R.layout.app_view_empty, null);

            if (mShowEmptyButton && mEmptyButtonClickListener != null) {
                View emptyViewButton = mEmptyView.findViewById(R.id.app_action_empty_btn);
                View emptyViewLl = mEmptyView.findViewById(R.id.app_action_empty_ll);
                if (emptyViewButton != null) {
                    emptyViewButton
                            .setOnClickListener(mEmptyButtonClickListener);
                    emptyViewButton.setVisibility(View.GONE);
                }
                if (emptyViewLl != null) {
                    emptyViewLl
                            .setOnClickListener(mEmptyButtonClickListener);
                }
            } else {
                View emptyViewButton = mEmptyView.findViewById(R.id.app_action_empty_btn);
                emptyViewButton.setVisibility(View.GONE);
            }
        }

        if (mLoadingView == null) {
            mLoadingView = (ViewGroup) mInflater.inflate(
                    R.layout.app_view_loading, null);
            ImageView imageView_loading = (ImageView) mLoadingView.findViewById(R.id.app_loading_iv);
            AnimationDrawable animationDrawable = (AnimationDrawable) imageView_loading.getDrawable();
            animationDrawable.start();
        }

        if (mErrorView == null) {
            /*mErrorView = (ViewGroup) mInflater.inflate(
					R.layout.app_view_error, null);*/
//			if (netErrorView!=null){
//				mErrorView=netErrorView;
//			}else {
            mErrorView = (ViewGroup) mInflater.inflate(
                    R.layout.app_view_error, null);
//			}


            if (mShowErrorButton && mErrorButtonClickListener != null) {
                View errorViewButton = mErrorView.findViewById(R.id.app_action_error_btn);
                if (errorViewButton != null) {
                    errorViewButton
                            .setOnClickListener(mErrorButtonClickListener);
                    errorViewButton.setVisibility(View.VISIBLE);
                }
            } else {
                View errorViewButton = mErrorView.findViewById(R.id.app_action_error_btn);
                errorViewButton.setVisibility(View.GONE);
            }
        }

        if (dataOtherView == null) {
            dataOtherView = (ViewGroup) mInflater.inflate(
                    R.layout.app_view_other, null);
            TextView tv=(TextView)dataOtherView.findViewById(R.id.app_message_tv);
            playImage=(ImageView)dataOtherView.findViewById(R.id.play_smart_more);
            if(!TextUtils.isEmpty(playTitle)){
                tv.setText(playTitle);
                playImage.setVisibility(View.VISIBLE);
                playImage.setOnClickListener(mOtherClickListener);
            }
        }else{
            TextView tv=(TextView)dataOtherView.findViewById(R.id.app_message_tv);
            playImage=(ImageView)dataOtherView.findViewById(R.id.play_smart_more);
            if(!TextUtils.isEmpty(playTitle)){
                tv.setText(playTitle);
                playImage.setVisibility(View.VISIBLE);
                playImage.setOnClickListener(mOtherClickListener);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private void crossfadeView() {
        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        mContentView.setAlpha(0f);
        mContentView.setVisibility(View.VISIBLE);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        mContentView.animate().alpha(1f).setDuration(mShortAnimationDuration)
                .setListener(null);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        mBackgroundViews.animate().alpha(0f)
                .setDuration(mShortAnimationDuration);
        mLoadingView.animate().alpha(0f).setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mLoadingView.setVisibility(View.GONE);
                        mLoadingView.setAlpha(1f);
                        mBackgroundViews.setVisibility(View.GONE);
                        mBackgroundViews.setAlpha(1f);
                    }
                });
    }

    // ---------------------------
    // public methods
    // ---------------------------

    /**
     * Constructor
     *
     * @param context     the context (preferred context is any activity)
     * @param contentView the contentView for which this library is being used, this
     *                    view can't be the root view of the hierarchy, It has to be a
     *                    view (Any LinearLayout, RelativeLayout, View, etc) inside the
     *                    root view
     *                    <p>
     *                    El content view para la cual estas usando esta lib, este
     *                    content view no puede ser el view raiz de la jerarquia, tiene
     *                    que ser un view cualquiera como LinearLayout, RelativeLayout,
     *                    etc. que este dentro del view raiz.
     */
    public EmptyViewLayout(Context context, View contentView) {
        mContext = context;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContentView = contentView;
        mShortAnimationDuration = mContext.getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        mEmptyMessage = mContext.getString(R.string.app_empty_message);
    }

    /**
     * Shows the empty layout if the content view is empty
     */
    public void showEmpty() {
        this.mEmptyType = TYPE_EMPTY;
        changeEmptyType();
    }

    /**
     * Shows loading layout when a long task is doing
     */
    public void showLoading() {
        this.mEmptyType = TYPE_LOADING;
        changeEmptyType();
    }

    /**
     * Shows error layout when is there an error
     */
    public void showError() {
        this.mEmptyType = TYPE_ERROR;
        changeEmptyType();
    }

    /**
     * Shows the content view and hides the others overlays
     */
    public void showContentView() {
        this.mEmptyType = TYPE_CONTENT_VIEW_STATE;
        changeEmptyType();
    }

    public void showNetErrorView() {
        this.mEmptyType = TYPE_NET_ERROR;
        changeEmptyType();
    }

    public void showOtherErrorView() {
        this.mEmptyType = TYPE_OTHER_ERROR;
        changeEmptyType();
    }

    private String playTitle;
    private ImageView playImage;

    public void setOtherData(String title){
        this.playTitle=title;
    }

    public ImageView getPlayImage(){
        return playImage;
    }

    public boolean isEmptyViewShow(){
        if(mEmptyView!=null){
            return mEmptyView.isShown();
        }
        return false;
    }

    public boolean isOtherViewShow(){
        if(dataOtherView!=null){
            return dataOtherView.isShown();
        }
        return false;
    }

}
