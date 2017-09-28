package com.me.fanyin.zbme.views.main.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.me.core.utils.ToastBarUtils;
import com.me.data.common.Constants;
import com.me.data.model.main.BooksErrataMenuListBean;
import com.me.fanyin.zbme.R;
import com.me.fanyin.zbme.base.BaseMvpActivity;
import com.me.fanyin.zbme.views.main.activity.adapter.BookMenuAdapter;
import com.me.fanyin.zbme.views.main.activity.adapter.SeasonMenuAdapter;
import com.me.fanyin.zbme.views.main.activity.adapter.SubjectMenuAdapter;
import com.me.fanyin.zbme.widget.dropdown.DropDownMenuLayout;
import com.me.fanyin.zbme.widget.dropdown.DropDownMenuTitle;
import com.me.fanyin.zbme.widget.dropdown.ShopMenuAdapter;
import com.me.fanyin.zbme.widget.loadmoreview.AppViewLoadMore;
import com.me.fanyin.zbme.widget.statuslayoutmanager.StatusLayoutManager;

import org.apache.http.util.TextUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by jjr on 2017/6/12.
 */

public class BooksErrataActivity extends BaseMvpActivity<BooksErrataView, BooksErrataPresenter> implements BooksErrataView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.common_switch_menu)
    LinearLayout common_switch_menu;
    @BindView(R.id.exam_ddmt_ll)
    LinearLayout subject_ddmt_ll;
    @BindView(R.id.exam_ddmt)
    DropDownMenuTitle subject_ddmt;
    @BindView(R.id.subject_ddmt)
    DropDownMenuTitle year_ddmt;
    @BindView(R.id.year_ddmt)
    DropDownMenuTitle book_ddmt;
    @BindView(R.id.ddml)
    DropDownMenuLayout ddml;
    @BindView(R.id.subpage_rcv)
    RecyclerView subpage_rcv;
    @BindView(R.id.subpage_srl)
    SwipeRefreshLayout subpage_srl;

    private BaseQuickAdapter mAdapter;
    private SubjectMenuAdapter subjectMenuAdapter;
    private SeasonMenuAdapter seasonMenuAdapter;
    private BookMenuAdapter bookMenuAdapter;
    private List<BooksErrataMenuListBean.SubjectListBean> subjectList;
    private List<BooksErrataMenuListBean.SubjectListBean.SeasonListBean> seasonList;
    private List<BooksErrataMenuListBean.SubjectListBean.SeasonListBean.BookListBean> bookList;
    private int currentSubjectMenuPosition = 0;
    private int currentSeasonMenuPosition = 0;
    private int currentBookMenuPosition = 0;
    private String bookId;
    private String examId;
    private boolean isMenuInitialized = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        examId = getIntent().getStringExtra(Constants.EXAM_ID);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initInject() {
        getAppComponent().inject(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exam_ddmt:
                ddml.switchMenu(0);
                break;
            case R.id.subject_ddmt:
                ddml.switchMenu(1);
                break;
            case R.id.year_ddmt:
                ddml.switchMenu(2);
                break;
        }
    }

    @Override
    public void showError(String message) {
        ToastBarUtils.showToast(this, message);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.common_switvh_menu_activity;
    }

    @Override
    protected int getContentResId() {
        return R.layout.subpage_fragment;
    }

    @Override
    protected void initView() {
        mToolbar.setTitleText("图书勘误");
        subject_ddmt.setText("科目");
        year_ddmt.setText("年份");
        book_ddmt.setText("图书");

        subpage_rcv.setLayoutManager(new LinearLayoutManager(this));
        subpage_srl.setOnRefreshListener(this);
        mAdapter = mPresenter.initAdapter(subpage_rcv);
        mAdapter.setLoadMoreView(new AppViewLoadMore());
        mAdapter.setOnLoadMoreListener(this, subpage_rcv);

    }

    @Override
    protected void initData() {
        mPresenter.getData();
    }

    @Override
    public void initMenu(List<BooksErrataMenuListBean.SubjectListBean> list) {

        subject_ddmt_ll.setVisibility(View.VISIBLE);
        subject_ddmt.setOnClickListener(this);
        year_ddmt.setOnClickListener(this);
        book_ddmt.setOnClickListener(this);

        subjectList = list;
        subjectMenuAdapter = new SubjectMenuAdapter(subjectList);
        ListView subjectListview = getMenuListview(subjectMenuAdapter);

        resetSubjectMenu(0);

        subjectListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentSubjectMenuPosition == position) {
                    ddml.closeMenu();
                    return;
                }

                resetSubjectMenu(position);

                seasonList.clear();
                seasonList.addAll(subjectList.get(position).getSeasonList());
                seasonMenuAdapter.notifyDataSetChanged();
                resetYearMenu(0);

                bookList.clear();
                bookList.addAll(subjectList.get(position).getSeasonList().get(0).getBookList());
                bookMenuAdapter.notifyDataSetChanged();
                resetBookMenu(0);

                ddml.closeMenu();
            }
        });

        seasonList = new ArrayList<>();
        seasonList.addAll(subjectList.get(0).getSeasonList());
        seasonMenuAdapter = new SeasonMenuAdapter(seasonList);
        ListView yearListview = getMenuListview(seasonMenuAdapter);

        resetYearMenu(0);

        yearListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentSeasonMenuPosition == position) {
                    ddml.closeMenu();
                    return;
                }

                resetYearMenu(position);

                bookList.clear();
                bookList.addAll(seasonList.get(position).getBookList());
                bookMenuAdapter.notifyDataSetChanged();
                resetBookMenu(0);

                ddml.closeMenu();
            }
        });

        bookList = new ArrayList<>();
        bookList.addAll(seasonList.get(0).getBookList());
        bookMenuAdapter = new BookMenuAdapter(bookList);
        ListView bookListview = getMenuListview(bookMenuAdapter);

        resetBookMenu(0);

        bookListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentBookMenuPosition == position) {
                    ddml.closeMenu();
                    return;
                }

                resetBookMenu(position);

                ddml.closeMenu();
            }
        });

        ddml.addMenuView(subjectListview);
        ddml.addMenuView(yearListview);
        ddml.addMenuView(bookListview);

        common_switch_menu.setVisibility(View.VISIBLE);
        isMenuInitialized = true;

        ddml.setOnOpenCloseListener(new DropDownMenuLayout.OnOpenCloseListener() {
            @Override
            public void onCloseMenu(int position) {
                switch (position) {
                    case 0:
                        subject_ddmt.closeMenu();
                        break;
                    case 1:
                        year_ddmt.closeMenu();
                        break;
                    case 2:
                        book_ddmt.closeMenu();
                        break;
                }
            }

            @Override
            public void onOpenMenu(int position) {
                switch (position) {
                    case 0:
                        subject_ddmt.openMenu();
                        break;
                    case 1:
                        year_ddmt.openMenu();
                        break;
                    case 2:
                        book_ddmt.openMenu();
                        break;
                }
            }
        });


    }

    private void resetSubjectMenu(int position) {
        subjectMenuAdapter.setSelect(position);
        currentSubjectMenuPosition = position;
        subject_ddmt.setText(subjectList.get(position).getName());
    }

    private void resetYearMenu(int position) {
        seasonMenuAdapter.setSelect(position);
        currentSeasonMenuPosition = position;
        year_ddmt.setText(seasonList.get(position).getYear());
    }

    private void resetBookMenu(int position) {
        bookMenuAdapter.setSelect(position);
        currentBookMenuPosition = position;
        book_ddmt.setText(bookList.get(position).getName());
        bookId = bookList.get(position).getId();

        if (TextUtils.isEmpty(bookId)) {
            showEmptyData();
            return;
        }

        mPresenter.getErrataList();
    }

    private ListView getMenuListview(ShopMenuAdapter adapter) {
        ListView listView = new ListView(context());
        listView.setFastScrollAlwaysVisible(false);
        listView.setVerticalScrollBarEnabled(false);
        listView.setDividerHeight(0);
        listView.setAdapter(adapter);
        listView.setBackgroundColor(ContextCompat.getColor(context(), R.color.white_menu_list_bg));
        listView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        listView.setVisibility(View.GONE);
        return listView;
    }

    @Override
    public void onRefreshComplete() {
        subpage_srl.setRefreshing(false);
    }

    @Override
    public void onLoadMoreComplete(int status) {
        switch (status) {
            case LoadMoreView.STATUS_DEFAULT:
            case LoadMoreView.STATUS_LOADING:
                mAdapter.loadMoreComplete();
                break;
            case LoadMoreView.STATUS_END:
                mAdapter.loadMoreEnd();
                break;
            case LoadMoreView.STATUS_FAIL:
                mAdapter.loadMoreFail();
                break;
        }
    }

    @Override
    public void onRefresh() {
        if (mAdapter.isLoading()) {
            subpage_srl.setRefreshing(false);
            return;
        }
        mPresenter.onRefresh();
    }

    @Override
    public void onLoadMoreRequested() {
        if (subpage_srl.isRefreshing()) {
            mAdapter.loadMoreEnd(true);
            return;
        }
        mPresenter.onLoadMore();
    }

    @Override
    protected StatusLayoutManager.OnRetryListener addRetryListener() {
        return new StatusLayoutManager.OnRetryListener() {
            @Override
            public void onRetry(View v) {
                if (!isMenuInitialized) {
                    hideLoading();
                    mPresenter.getData();
                } else {
                    mPresenter.getErrataList();
                }
            }
        };
    }

    @Override
    public String getBookId() {
        return bookId;
    }

    @Override
    public String getExamId() {
        return examId;
    }

}
