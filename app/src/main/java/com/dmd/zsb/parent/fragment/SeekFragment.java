package com.dmd.zsb.parent.fragment;


import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
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
import com.dmd.tutor.utils.XmlDB;
import com.dmd.tutor.widgets.XSwipeRefreshLayout;
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.SeekPresenter;
import com.dmd.zsb.mvp.presenter.impl.SeekPresenterIml;
import com.dmd.zsb.mvp.view.SeekView;
import com.dmd.zsb.parent.R;
import com.dmd.zsb.parent.activity.UserDetailActivity;
import com.dmd.zsb.parent.activity.base.BaseFragment;
import com.dmd.zsb.parent.adapter.SeekGradeAdapter;
import com.dmd.zsb.parent.adapter.SeekSortAdapter;
import com.dmd.zsb.parent.adapter.SeekSubjectAdapter;
import com.dmd.zsb.protocol.response.seekResponse;
import com.dmd.zsb.protocol.table.GradesBean;
import com.dmd.zsb.protocol.table.SubjectsBean;
import com.dmd.zsb.protocol.table.UsersBean;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SeekFragment extends BaseFragment implements SeekView, LoadMoreListView.OnLoadMoreListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {


    @Bind(R.id.seek_group_menu_course)
    RadioButton seekGroupMenuCourse;
    @Bind(R.id.seek_group_menu_sort)
    RadioButton seekGroupMenuSort;
    @Bind(R.id.seek_group_menu_follow)
    RadioButton seekGroupMenuFollow;
    @Bind(R.id.fragment_seek_list_list_view)
    LoadMoreListView fragmentSeekListListView;
    @Bind(R.id.fragment_seek_list_swipe_layout)
    XSwipeRefreshLayout fragmentSeekListSwipeLayout;
    ListView seek_list_view_grade, seek_list_view_subject, seek_list_view_sort;
    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;

    private ListViewDataAdapter<UsersBean> mListViewAdapter;
    private SeekPresenter mSeekPresenter = null;
    private int page = 1;
    private int sort=0;
    private String subid="";
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
        subid= XmlDB.getInstance(mContext).getKeyString("subid","");
        XmlDB.getInstance(mContext).saveKey("subid","");
        if (mSeekPresenter==null){
            mSeekPresenter = new SeekPresenterIml(mContext, this);
        }
        if (NetUtils.isNetworkConnected(mContext)) {
            if (null != fragmentSeekListSwipeLayout) {
                fragmentSeekListSwipeLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject=new JSONObject();
                        try {
                            jsonObject.put("page", page);
                            jsonObject.put("subid", subid);//科目id
                        }catch (JSONException j){

                        }

                        mSeekPresenter.loadListData(Constants.EVENT_REFRESH_DATA,jsonObject);
                    }
                }, ApiConstants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
            }
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("page", page);
                        jsonObject.put("subid", subid);//科目id
                    }catch (JSONException j){

                    }

                    mSeekPresenter.loadListData(Constants.EVENT_REFRESH_DATA,jsonObject);
                }
            });
        }
        topBarBack.setVisibility(View.GONE);
        topBarTitle.setText("找老师");
        initScreenWidth();

        mListViewAdapter = new ListViewDataAdapter<UsersBean>(new ViewHolderCreator<UsersBean>() {

            @Override
            public ViewHolderBase<UsersBean> createViewHolder(int position) {
                return new ViewHolderBase<UsersBean>() {
                    ImageView teacher_avatar;
                    TextView teacher_name,
                            teacher_gender,
                            teacher_signature,
                            teacher_total_hours,
                            teacher_goodrate,
                            teacher_distance;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View view = layoutInflater.inflate(R.layout.tutor_teacher_list_item, null);
                        teacher_avatar = ButterKnife.findById(view, R.id.teacher_avatar);
                        teacher_name = ButterKnife.findById(view, R.id.teacher_name);
                        teacher_gender = ButterKnife.findById(view, R.id.teacher_gender);
                        teacher_signature = ButterKnife.findById(view, R.id.teacher_signature);
                        teacher_total_hours = ButterKnife.findById(view, R.id.teacher_total_hours);
                        teacher_goodrate = ButterKnife.findById(view, R.id.teacher_goodrate);
                        teacher_distance = ButterKnife.findById(view, R.id.teacher_distance);
                        return view;
                    }

                    @Override
                    public void showData(int position, UsersBean itemData) {
                        Picasso.with(mContext).load(itemData.avatar).into(teacher_avatar);
                        teacher_name.setText(itemData.username);
                        teacher_gender.setText(itemData.gender);
                        teacher_signature.setText(itemData.signature);
                        teacher_total_hours.setText(itemData.total_hours + "");
                        teacher_goodrate.setText(itemData.goodrate + "");
                        teacher_distance.setText(LocationManager.getDistance(Double.parseDouble(itemData.lat), Double.parseDouble(itemData.lon)));
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

        seekGroupMenuFollow.setOnClickListener(this);
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Subscribe
    @Override
    public void onEventComming(EventCenter eventCenter) {
        if (eventCenter.getEventCode() == Constants.EVENT_RECOMMEND_COURSES_SEEK) {
            //subid=eventCenter.getData().toString();
            // showToast("000");
        }
    }

/*
    @Subscribe
    @Override
    public void onEventMainThread(EventCenter eventCenter){
        super.onEventMainThread(eventCenter);
        if (eventCenter.getEventCode() == Constants.EVENT_RECOMMEND_COURSES_SEEK) {
            subid=eventCenter.getData().toString();
           // showToast("000");
        }
    }
*/

    @Override
    public void onClick(View v) {

        if (v == seekGroupMenuCourse) {
            if (seekGroupMenuCourse.isChecked()) {
                onCreateCoursePopWindow(seekGroupMenuCourse);
            }
        } else if (v == seekGroupMenuSort) {
            if (seekGroupMenuSort.isChecked()) {
                onCreateSortPopWindow(seekGroupMenuSort);
            }
        } else if (v == seekGroupMenuFollow) {
            if (seekGroupMenuFollow.isChecked()){
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("page", 1);
                    jsonObject.put("subid", subid);//科目id
                    jsonObject.put("sort", 100);//科目id
                }catch (JSONException j){

                }
                mSeekPresenter.loadListData(Constants.EVENT_REFRESH_DATA,jsonObject);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UsersBean data=(UsersBean)parent.getAdapter().getItem(position);
        navigateToUserDetail(data);
    }

    @Override
    public void onLoadMore() {
        page = 1 + page;
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("page", page);
            jsonObject.put("subid", subid);//科目id
            jsonObject.put("sort", sort);//科目id
        }catch (JSONException j){

        }

        mSeekPresenter.loadListData(Constants.EVENT_LOAD_MORE_DATA,jsonObject);
    }

    @Override
    public void onRefresh() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("page", 1);
            jsonObject.put("subid", subid);//科目id
            jsonObject.put("sort", sort);//科目id
        }catch (JSONException j){

        }

        mSeekPresenter.loadListData(Constants.EVENT_REFRESH_DATA,jsonObject);
    }

    @Override
    public void navigateToUserDetail(UsersBean itemData) {
        if (itemData!=null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", itemData);
            readyGo(UserDetailActivity.class, bundle);
        }
    }

    @Override
    public void refreshListData(seekResponse response) {
        if (fragmentSeekListSwipeLayout != null)
            fragmentSeekListSwipeLayout.setRefreshing(false);
        if (response.users != null) {
            if (response.users.size() >= 1) {
                if (mListViewAdapter != null) {
                    mListViewAdapter.getDataList().clear();
                    mListViewAdapter.getDataList().addAll(response.users);
                    mListViewAdapter.notifyDataSetChanged();
                }
            }else {
                mListViewAdapter.getDataList().clear();
                mListViewAdapter.notifyDataSetChanged();
            }
            if (fragmentSeekListListView!=null){
                if (UriHelper.getInstance().calculateTotalPages(response.total_count)> page){
                    fragmentSeekListListView.setCanLoadMore(true);
                }else{
                    fragmentSeekListListView.setCanLoadMore(false);
                }
            }

        }
    }

    @Override
    public void addMoreListData(seekResponse response) {
        if (fragmentSeekListListView != null)
            fragmentSeekListListView.onLoadMoreComplete();
        if (response != null) {
            if (mListViewAdapter != null) {
                mListViewAdapter.getDataList().addAll(response.users);
                mListViewAdapter.notifyDataSetChanged();
            }
            if (fragmentSeekListListView!=null){
                if (UriHelper.getInstance().calculateTotalPages(response.total_count)> page){
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
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("page", 1);
                    jsonObject.put("subid", subid);//科目id

                    //jsonObject.put("","");
                    if (parent.getAdapter().getItem(position).toString().equals("综合排序")){
                        sort=0;
                    }else if (parent.getAdapter().getItem(position).toString().equals("价钱 低--高")){
                        sort=1;
                    }else if (parent.getAdapter().getItem(position).toString().equals("价钱 高--低")){
                        sort=2;
                    }else if (parent.getAdapter().getItem(position).toString().equals("人气 高--低")){
                        sort=3;
                    }else if (parent.getAdapter().getItem(position).toString().equals("距离 近--远")){
                        sort=4;
                    }
                    jsonObject.put("sort", sort);//科目id
                }catch (JSONException j){

                }

                mSeekPresenter.loadListData(Constants.EVENT_REFRESH_DATA,jsonObject);
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
        seekGradeAdapter = new SeekGradeAdapter(GradesBean.listAll(GradesBean.class), mContext);
        seek_list_view_grade.setAdapter(seekGradeAdapter);
        seek_list_view_grade.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (parent.getAdapter() instanceof SeekGradeAdapter) {

                    if (seek_list_view_subject.getVisibility() == View.INVISIBLE) {
                        seek_list_view_subject.setVisibility(View.VISIBLE);
                    }

                    if (!SubjectsBean.find(SubjectsBean.class,"GRADEID=?",GradesBean.listAll(GradesBean.class).get(position).grade_id).isEmpty()) {
                        seekSubjectAdapter = new SeekSubjectAdapter(SubjectsBean.find(SubjectsBean.class,"GRADEID=?",GradesBean.listAll(GradesBean.class).get(position).grade_id), mContext);
                        seek_list_view_subject.setAdapter(seekSubjectAdapter);
                        seekSubjectAdapter.notifyDataSetChanged();
                        seek_list_view_subject.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //请求数据
                                JSONObject jsonObject=new JSONObject();
                                try {
                                    sort=0;
                                    subid=((SubjectsBean) parent.getAdapter().getItem(position)).sub_id;
                                    jsonObject.put("page", 1);
                                    jsonObject.put("subid", subid);//科目id
                                    jsonObject.put("sort", sort);//科目id
                                }catch (JSONException j){

                                }
                                mSeekPresenter.loadListData(Constants.EVENT_REFRESH_DATA,jsonObject);
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
    }
}
