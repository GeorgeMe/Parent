package com.dmd.zsb.parent.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmd.tutor.adapter.ListViewDataAdapter;
import com.dmd.tutor.adapter.ViewHolderBase;
import com.dmd.tutor.adapter.ViewHolderCreator;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.lbs.LocationManager;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.ninelayout.NineGridlayout;
import com.dmd.tutor.rollviewpager.RollPagerView;
import com.dmd.tutor.utils.BusHelper;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.tutor.widgets.XSwipeRefreshLayout;
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.HomePresenterImpl;
import com.dmd.zsb.mvp.view.HomeView;
import com.dmd.zsb.parent.R;
import com.dmd.zsb.parent.activity.ReleaseOrderActivity;
import com.dmd.zsb.parent.activity.UserDetailActivity;
import com.dmd.zsb.parent.activity.base.BaseFragment;
import com.dmd.zsb.parent.adapter.HomeCarouselAdapter;
import com.dmd.zsb.parent.adapter.HomeCoursesAdapter;
import com.dmd.zsb.protocol.response.homeResponse;
import com.dmd.zsb.protocol.table.SubjectsBean;
import com.dmd.zsb.protocol.table.UsersBean;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment implements HomeView, LoadMoreListView.OnLoadMoreListener, AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, NineGridlayout.OnItemClickListerner {

    @Bind(R.id.bar_home_address)
    TextView barHomeAddress;
    @Bind(R.id.bar_home_demand)
    TextView barHomeDemand;
    @Bind(R.id.fragment_home_list_view)
    LoadMoreListView fragmentHomeListView;
    @Bind(R.id.fragment_home_list_swipe_layout)
    XSwipeRefreshLayout fragmentHomeListSwipeLayout;

    private View mHeaderView;
    private RollPagerView mRollPagerView;
    private NineGridlayout mNineGridlayout;
    private ListViewDataAdapter<UsersBean> mListViewAdapter;
    private List<SubjectsBean> subjectList;
    private int page = 1;
    private HomePresenterImpl mHomePresenter = null;

    /**
     *
     */
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    /**
     * 第一次用户可见
     */
    @Override
    protected void onFirstUserVisible() {


    }

    /**
     * 用户可见
     */
    @Override
    protected void onUserVisible() {

    }

    /**
     * 用户不可见
     */
    @Override
    protected void onUserInvisible() {

    }

    /**
     * 加载视图的根视图
     */
    @Override
    protected View getLoadingTargetView() {
        return fragmentHomeListSwipeLayout;
    }

    /**
     * 初始化视图事件
     */
    @Override
    protected void initViewsAndEvents() {
        mHomePresenter = new HomePresenterImpl(mContext, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            if (null != fragmentHomeListSwipeLayout) {
                fragmentHomeListSwipeLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //提交的参数封装
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("appkey", Constants.ZSBAPPKEY);
                            jsonObject.put("version", Constants.ZSBVERSION);
                            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                            jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
                            jsonObject.put("page", 1);//页码
                            jsonObject.put("subid", "402881e953795aab0153795bffa90005");//科目id
                        } catch (JSONException j) {

                        }

                        mHomePresenter.loadListData(Constants.EVENT_REFRESH_DATA, jsonObject);
                    }
                }, ApiConstants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
            }
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //提交的参数封装
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("appkey", Constants.ZSBAPPKEY);
                        jsonObject.put("version", Constants.ZSBVERSION);
                        jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                        jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                        jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
                        jsonObject.put("page", 1);//页码
                        jsonObject.put("subid", "402881e953795aab0153795bffa90005");//科目id
                    } catch (JSONException j) {

                    }

                    mHomePresenter.loadListData(Constants.EVENT_REFRESH_DATA, jsonObject);
                }
            });
        }

        barHomeAddress.setText(XmlDB.getInstance(mContext).getKeyString("BDLocation", "定位"));
        barHomeDemand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin", false)) {
                    readyGo(ReleaseOrderActivity.class);
                } else {
                    showToast("请登录后发布需求");
                }
            }
        });
        mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.tutor_home_list_header, null);
        mRollPagerView = (RollPagerView) ButterKnife.findById(mHeaderView, R.id.fragment_home_list_header_roll_view_pager);
        mNineGridlayout = (NineGridlayout) ButterKnife.findById(mHeaderView, R.id.fragment_home_list_header_nine_grid_layout);
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

        if (fragmentHomeListView.getHeaderViewsCount() == 0)
            fragmentHomeListView.addHeaderView(mHeaderView);
        fragmentHomeListView.setAdapter(mListViewAdapter);
        fragmentHomeListView.setOnItemClickListener(this);
        fragmentHomeListView.setOnLoadMoreListener(this);
        mNineGridlayout.setOnItemClickListerner(this);

        fragmentHomeListSwipeLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        fragmentHomeListSwipeLayout.setOnRefreshListener(this);

    }

    /**
     * 绑定事件 true
     */
    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    /**
     * 接受事件
     */
    @Subscribe
    @Override
    public void onEventComming(EventCenter eventCenter) {

    }
//===============================HomeView============================================

    @Override
    public void navigateToUserDetail(UsersBean itemData) {
        if (itemData!=null) {
            Bundle bundle = new Bundle();
            bundle.putString("user", itemData.user_id);
            readyGo(UserDetailActivity.class, bundle);
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mListViewAdapter != null) {
            int j = position + -1;
            if (j >= 0 && j < mListViewAdapter.getDataList().size()) {
                UsersBean data = (UsersBean) parent.getItemAtPosition(position);
                navigateToUserDetail(data);
            }
        }
    }

    @Override
    public void refreshListData(homeResponse response) {
        if (fragmentHomeListSwipeLayout != null)
            fragmentHomeListSwipeLayout.setRefreshing(false);
        if (response != null) {
            if (response.users.size() >= 1) {//用户列表
                if (mListViewAdapter != null) {
                    mListViewAdapter.getDataList().clear();
                    mListViewAdapter.getDataList().addAll(response.users);
                    mListViewAdapter.notifyDataSetChanged();
                }
            }else {
                mListViewAdapter.getDataList().clear();
                mListViewAdapter.notifyDataSetChanged();
            }
            if (response.advertisements.size() >= 1) {//广告
                mRollPagerView.setAdapter(new HomeCarouselAdapter(mContext, response.advertisements));
            }
            if (response.subjects.size() >= 1) {//科目
                subjectList = new ArrayList<SubjectsBean>();
                subjectList.addAll(response.subjects);
                mNineGridlayout.setAdapter(new HomeCoursesAdapter(mContext, response.subjects, 5));
            }
            if (fragmentHomeListView != null) {
                if (UriHelper.getInstance().calculateTotalPages(response.total_count) > page) {
                    fragmentHomeListView.setCanLoadMore(true);
                } else {
                    fragmentHomeListView.setCanLoadMore(false);
                }
            }
        }
    }

    @Override
    public void addMoreListData(homeResponse response) {
        if (fragmentHomeListView != null)
            fragmentHomeListView.onLoadMoreComplete();
        if (response != null) {
            if (mListViewAdapter != null) {
                mListViewAdapter.getDataList().addAll(response.users);
                mListViewAdapter.notifyDataSetChanged();
            }
            if (fragmentHomeListView != null) {
                if (UriHelper.getInstance().calculateTotalPages(response.total_count) > page) {
                    fragmentHomeListView.setCanLoadMore(true);
                } else {
                    fragmentHomeListView.setCanLoadMore(false);
                }
            }
        }
    }
    //==============================AdapterView.OnItemClickListener=============================================


    @Override
    public void onItemClick(View view, int position) {
        SubjectsBean entity = subjectList.get(position);
        BusHelper.post(new EventCenter(Constants.EVENT_RECOMMEND_COURSES_HOME,entity));
    }

    //==============================LoadMoreListView.OnLoadMoreListener=============================================
    @Override
    public void onLoadMore() {
        page = 1 + page;
        //提交的参数封装
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("appkey", Constants.ZSBAPPKEY);
            jsonObject.put("version", Constants.ZSBVERSION);
            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
            jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
            jsonObject.put("page", page);//页码
            jsonObject.put("subid", "402881e953795aab0153795bffa90005");//科目id
        } catch (JSONException j) {

        }

        mHomePresenter.loadListData(Constants.EVENT_LOAD_MORE_DATA, jsonObject);
    }
    //==============================SwipeRefreshLayout.OnRefreshListener=============================================


    @Override
    public void onRefresh() {

        //提交的参数封装
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("appkey", Constants.ZSBAPPKEY);
            jsonObject.put("version", Constants.ZSBVERSION);
            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
            jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
            jsonObject.put("page", 1);//页码
            jsonObject.put("subid", "402881e953795aab0153795bffa90005");//科目id
        } catch (JSONException j) {

        }

        mHomePresenter.loadListData(Constants.EVENT_REFRESH_DATA, jsonObject);
    }
}
