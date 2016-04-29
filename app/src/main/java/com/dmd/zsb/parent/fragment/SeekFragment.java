package com.dmd.zsb.parent.fragment;


import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dmd.tutor.adapter.ListViewDataAdapter;
import com.dmd.tutor.adapter.ViewHolderBase;
import com.dmd.tutor.adapter.ViewHolderCreator;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.lbs.LocationManager;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.search.MaterialSearchView;
import com.dmd.tutor.utils.TLog;
import com.dmd.tutor.widgets.XSwipeRefreshLayout;
import com.dmd.zsb.parent.R;
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.db.ZSBDataBase;
import com.dmd.zsb.db.dao.GradeDao;
import com.dmd.zsb.db.dao.SubjectDao;
import com.dmd.zsb.entity.GradeEntity;
import com.dmd.zsb.entity.SubjectEntity;
import com.dmd.zsb.entity.UserEntity;
import com.dmd.zsb.entity.response.SeekResponse;
import com.dmd.zsb.mvp.presenter.SeekPresenter;
import com.dmd.zsb.mvp.presenter.impl.SeekPresenterIml;
import com.dmd.zsb.mvp.view.SeekView;
import com.dmd.zsb.parent.activity.UserDetailActivity;
import com.dmd.zsb.parent.activity.base.BaseFragment;
import com.dmd.zsb.parent.adapter.SeekGradeAdapter;
import com.dmd.zsb.parent.adapter.SeekSortAdapter;
import com.dmd.zsb.parent.adapter.SeekSubjectAdapter;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.google.gson.JsonObject;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SeekFragment extends BaseFragment implements SeekView, LoadMoreListView.OnLoadMoreListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {


    @Bind(R.id.seek_group_menu_course)
    RadioButton seekGroupMenuCourse;
    @Bind(R.id.seek_group_menu_sort)
    RadioButton seekGroupMenuSort;
    @Bind(R.id.seek_group_menu_audition)
    RadioButton seekGroupMenuAudition;
    @Bind(R.id.fragment_seek_list_list_view)
    LoadMoreListView fragmentSeekListListView;
    @Bind(R.id.fragment_seek_list_swipe_layout)
    XSwipeRefreshLayout fragmentSeekListSwipeLayout;
    ListView seek_list_view_grade, seek_list_view_subject, seek_list_view_sort;
    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;

    private ListViewDataAdapter<UserEntity> mListViewAdapter;
    private SeekPresenter mSeekPresenter = null;
    private JsonObject gson;
    private int page = 1;
    private SeekGradeAdapter seekGradeAdapter;
    private SeekSubjectAdapter seekSubjectAdapter;
    private SeekSortAdapter seekSortAdapter;

    private int screenWidth;
    private int screenHeight;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_seek;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return fragmentSeekListSwipeLayout;
    }

    @Override
    protected void initViewsAndEvents() {

        gson = new JsonObject();
        gson.addProperty("page", page);
        gson.addProperty("subid", "");//科目id
        if (mSeekPresenter==null){
            mSeekPresenter = new SeekPresenterIml(mContext, this);
        }
        if (NetUtils.isNetworkConnected(mContext)) {
            if (null != fragmentSeekListSwipeLayout) {
                fragmentSeekListSwipeLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSeekPresenter.loadListData(Constants.EVENT_REFRESH_DATA,gson);
                    }
                }, ApiConstants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
            }
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSeekPresenter.loadListData(Constants.EVENT_REFRESH_DATA,gson);
                }
            });
        }
        topBarBack.setVisibility(View.GONE);
        topBarTitle.setText("找老师");
        initScreenWidth();

        mListViewAdapter = new ListViewDataAdapter<UserEntity>(new ViewHolderCreator<UserEntity>() {

            @Override
            public ViewHolderBase<UserEntity> createViewHolder(int position) {
                return new ViewHolderBase<UserEntity>() {
                    ImageView tutor_list_teacher_header_img;
                    TextView tutor_list_teacher_name,
                            tutor_list_teacher_type,
                            tutor_list_teacher_sex,
                            tutor_list_teacher_time,
                            tutor_list_teacher_Level,
                            tutor_list_teacher_content,
                            tutor_list_teacher_one2one,
                            tutor_list_teacher_one2more,
                            tutor_list_teacher_distance;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View view = layoutInflater.inflate(R.layout.tutor_teacher_list_item, null);
                        tutor_list_teacher_header_img = ButterKnife.findById(view, R.id.tutor_list_teacher_header_img);
                        tutor_list_teacher_name = ButterKnife.findById(view, R.id.tutor_list_teacher_name);
                        tutor_list_teacher_type = ButterKnife.findById(view, R.id.tutor_list_teacher_type);
                        tutor_list_teacher_sex = ButterKnife.findById(view, R.id.tutor_list_teacher_sex);
                        tutor_list_teacher_time = ButterKnife.findById(view, R.id.tutor_list_teacher_time);
                        tutor_list_teacher_Level = ButterKnife.findById(view, R.id.tutor_list_teacher_Level);
                        tutor_list_teacher_content = ButterKnife.findById(view, R.id.tutor_list_teacher_content);
                        tutor_list_teacher_one2one = ButterKnife.findById(view, R.id.tutor_list_teacher_one2one);
                        tutor_list_teacher_one2more = ButterKnife.findById(view, R.id.tutor_list_teacher_one2more);
                        tutor_list_teacher_distance = ButterKnife.findById(view, R.id.tutor_list_teacher_distance);
                        return view;
                    }

                    @Override
                    public void showData(int position, UserEntity itemData) {
                        Picasso.with(mContext).load(ApiConstants.Urls.API_IMG_BASE_URLS + itemData.getAvatar()).into(tutor_list_teacher_header_img);
                        tutor_list_teacher_name.setText(itemData.getUsername());
                        tutor_list_teacher_type.setText("(" + itemData.getRole() + ")");
                        tutor_list_teacher_sex.setText(itemData.getGender());
                        tutor_list_teacher_time.setText(itemData.getTotal_hours() + "");
                        tutor_list_teacher_Level.setText("未认证");
                        tutor_list_teacher_content.setText(itemData.getSignature());
                        tutor_list_teacher_one2one.setText("");
                        tutor_list_teacher_one2more.setText("");
                        tutor_list_teacher_distance.setText(LocationManager.getLocation(Double.parseDouble(itemData.getLat()), Double.parseDouble(itemData.getLon())));
                    }
                };
            }
        });

        //TODO 数据适配

        fragmentSeekListListView.setAdapter(mListViewAdapter);
        fragmentSeekListListView.setOnItemClickListener(this);
        fragmentSeekListListView.setOnLoadMoreListener(this);

        fragmentSeekListSwipeLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        fragmentSeekListSwipeLayout.setOnRefreshListener(this);


        seekGroupMenuCourse.setChecked(true);

        seekGroupMenuCourse.setOnClickListener(this);
        seekGroupMenuSort.setOnClickListener(this);

        seekGroupMenuAudition.setOnClickListener(this);
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Subscribe
    @Override
    public void onEventComming(EventCenter eventCenter) {
        if (eventCenter.getEventCode() == Constants.EVENT_RECOMMEND_COURSES_SEEK) {

        }
    }

    @Override
    public void onClick(View v) {

        // readyGoForResult(MaterialSearchActivity.class, MaterialSearchView.REQUEST_VOICE);
        if (v == seekGroupMenuCourse) {
            if (seekGroupMenuCourse.isChecked()) {
                onCreateCoursePopWindow(seekGroupMenuCourse);
            }
        } else if (v == seekGroupMenuSort) {
            if (seekGroupMenuSort.isChecked()) {
                onCreateSortPopWindow(seekGroupMenuSort);
            }
        } else if (v == seekGroupMenuAudition) {
            gson.addProperty("page", 0);
            gson.addProperty("subid", "");
            mSeekPresenter.loadListData(Constants.EVENT_REFRESH_DATA,gson);
        }
    }

    /*            new MaterialDialog.Builder(getActivity())
                        .title(R.string.title)
                        .content("筛选")
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .show();*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserEntity data=(UserEntity)parent.getItemAtPosition(position);
        navigateToUserDetail(data);
    }

    @Override
    public void onLoadMore() {
        page = 1 + page;
        gson.addProperty("page", page);
        gson.addProperty("subid", "");
        mSeekPresenter.loadListData(Constants.EVENT_LOAD_MORE_DATA,gson);
    }

    @Override
    public void onRefresh() {
        gson.addProperty("page", 0);
        gson.addProperty("subid", "");

        mSeekPresenter.loadListData(Constants.EVENT_REFRESH_DATA,gson);
    }

    @Override
    public void navigateToUserDetail(UserEntity itemData) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("data",itemData);
        readyGo(UserDetailActivity.class,bundle);
    }

    @Override
    public void refreshListData(SeekResponse data) {
        if (fragmentSeekListSwipeLayout != null)
            fragmentSeekListSwipeLayout.setRefreshing(false);
        if (data != null) {
            if (data.getUsers().size() >= 2) {
                if (mListViewAdapter != null) {
                    mListViewAdapter.getDataList().clear();
                    mListViewAdapter.getDataList().addAll(data.getUsers());
                    mListViewAdapter.notifyDataSetChanged();
                }
            }
            if (fragmentSeekListListView!=null){
                if (data.getTotal_page() > page){
                    fragmentSeekListListView.setCanLoadMore(true);
                }else{
                    fragmentSeekListListView.setCanLoadMore(false);
                }
            }

        }
    }

    @Override
    public void addMoreListData(SeekResponse data) {
        if (fragmentSeekListListView != null)
            fragmentSeekListListView.onLoadMoreComplete();
        if (data != null) {
            if (mListViewAdapter != null) {
                mListViewAdapter.getDataList().addAll(data.getUsers());
                mListViewAdapter.notifyDataSetChanged();
            }
            if (fragmentSeekListListView!=null){
                if (data.getTotal_page() > page){
                    fragmentSeekListListView.setCanLoadMore(true);
                }else{
                    fragmentSeekListListView.setCanLoadMore(false);
                }
            }
        }
    }

    //排序
    public void onCreateSortPopWindow(View view) {
        final PopupWindow popupWindow = new PopupWindow(mContext);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.seek_menu_sort_popwindow, null);
        seek_list_view_sort = (ListView) contentView.findViewById(R.id.seek_list_view_sort);
        String[] strings = getActivity().getResources().getStringArray(R.array.sort_category_list);
        seekSortAdapter = new SeekSortAdapter(mContext, strings);
        seek_list_view_sort.setAdapter(seekSortAdapter);
        seek_list_view_sort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //排序
                gson.addProperty("page", 0);
                gson.addProperty("subid", parent.getAdapter().getItem(position).toString());

                mSeekPresenter.loadListData(Constants.EVENT_REFRESH_DATA,gson);
                popupWindow.dismiss();
            }
        });
        popupWindow.setWidth(screenWidth);
        popupWindow.setHeight(RadioGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(contentView);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.showAsDropDown(view);

    }

    //课程
    public void onCreateCoursePopWindow(View view) {
        final PopupWindow popupWindow = new PopupWindow(mContext);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.seek_menu_class_popupwindow, null);
        seek_list_view_grade = (ListView) contentView.findViewById(R.id.seek_list_view_grade);
        seek_list_view_subject = (ListView) contentView.findViewById(R.id.seek_list_view_subject);

        if (seek_list_view_grade.getVisibility() == View.INVISIBLE) {
            seek_list_view_grade.setVisibility(View.VISIBLE);
        }
        seekGradeAdapter = new SeekGradeAdapter(getGrades(), mContext);
        seek_list_view_grade.setAdapter(seekGradeAdapter);
        seek_list_view_grade.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (parent.getAdapter() instanceof SeekGradeAdapter) {

                    if (seek_list_view_subject.getVisibility() == View.INVISIBLE) {
                        seek_list_view_subject.setVisibility(View.VISIBLE);
                    }

                    if (!getSubjects(getGrades().get(position).getGrade_id()).isEmpty()) {
                        seekSubjectAdapter = new SeekSubjectAdapter(getSubjects(getGrades().get(position).getGrade_id()), mContext);
                        seek_list_view_subject.setAdapter(seekSubjectAdapter);
                        seekSubjectAdapter.notifyDataSetChanged();
                        seek_list_view_subject.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //请求数据
                                gson.addProperty("page", 0);
                                gson.addProperty("subid", ((SubjectEntity) parent.getAdapter().getItem(position)).getSub_id());
                                mSeekPresenter.loadListData(Constants.EVENT_REFRESH_DATA,gson);
                                popupWindow.dismiss();
                            }
                        });
                    }
                }
            }
        });


        popupWindow.setWidth(screenWidth);
        popupWindow.setHeight(screenHeight * 4);
        popupWindow.setContentView(contentView);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.showAsDropDown(view);
    }

    private void initScreenWidth() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels / 10;
        screenWidth = dm.widthPixels;
        TLog.v("屏幕宽高", "宽度" + screenWidth + "高度" + screenHeight);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == MaterialSearchView.RESULT_OK) {
            gson.addProperty("page", 0);
            gson.addProperty("subid", data.getStringExtra("search"));
            mSeekPresenter.loadListData(Constants.EVENT_REFRESH_DATA,gson);
            //showToast(data.getStringExtra("search"));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private List<GradeEntity> getGrades() {
        GradeDao gradeDao = new GradeDao(ZSBDataBase.getInstance(mContext));
        return gradeDao.getGrades();
    }

    private List<SubjectEntity> getSubjects(String grade_id) {
        SubjectDao subjectDao = new SubjectDao(ZSBDataBase.getInstance(mContext));
        return subjectDao.getGrades(grade_id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
