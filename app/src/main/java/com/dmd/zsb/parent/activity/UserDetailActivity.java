package com.dmd.zsb.parent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.dmd.tutor.adapter.ListViewDataAdapter;
import com.dmd.tutor.adapter.ViewHolderBase;
import com.dmd.tutor.adapter.ViewHolderCreator;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.tutor.widgets.XSwipeRefreshLayout;
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.entity.response.UserDetailResponse;
import com.dmd.zsb.mvp.view.UserDetailView;
import com.dmd.zsb.parent.R;
import com.dmd.zsb.parent.activity.base.BaseActivity;
import com.dmd.zsb.protocol.response.userdetailsResponse;
import com.dmd.zsb.protocol.table.MyComments;
import com.dmd.zsb.protocol.table.MyServices;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserDetailActivity extends BaseActivity implements UserDetailView, LoadMoreListView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;

    RoundedImageView userAvatar;
    TextView tvName;
    TextView tvSeniority;
    TextView appointmentTime;
    TextView goodSubjects;
    TextView teachingPlace;
    TextView teachingMethods;
    @Bind(R.id.evaluation_list_swipe_layout)
    XSwipeRefreshLayout evaluationListSwipeLayout;

    private View mHeaderView;


    @Bind(R.id.service_list_view)
    LoadMoreListView serviceListView;
    @Bind(R.id.evaluation_list_view)
    LoadMoreListView evaluationListView;
    @Bind(R.id.btn_follow)
    Button btnFollow;
    @Bind(R.id.btn_appointment)
    Button btnAppointment;
    @Bind(R.id.btn_call)
    Button btnCall;
    @Bind(R.id.btn_msg)
    Button btnMsg;

    private ListViewDataAdapter<MyServices> servicesListViewDataAdapter;
    private ListViewDataAdapter<MyComments> commentsListViewDataAdapter;

    private int page=1;
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_user_detail;
    }

    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return evaluationListSwipeLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        topBarTitle.setText("教师详情");
        mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.user_detail_header, null);

        userAvatar = ButterKnife.findById(mHeaderView, R.id.user_avatar);
        tvName = ButterKnife.findById(mHeaderView, R.id.tv_name);
        tvSeniority = ButterKnife.findById(mHeaderView, R.id.tv_seniority);
        appointmentTime = ButterKnife.findById(mHeaderView, R.id.appointment_time);
        goodSubjects = ButterKnife.findById(mHeaderView, R.id.good_subjects);
        teachingPlace = ButterKnife.findById(mHeaderView, R.id.teaching_place);
        teachingMethods = ButterKnife.findById(mHeaderView, R.id.teaching_methods);


        servicesListViewDataAdapter = new ListViewDataAdapter<>(new ViewHolderCreator<MyServices>() {
            @Override
            public ViewHolderBase<MyServices> createViewHolder(int position) {
                return new ViewHolderBase<MyServices>() {
                    ImageView icon;
                    TextView name, price;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View view = layoutInflater.inflate(R.layout.teacher_sevice_list_item, null);
                        icon = ButterKnife.findById(view, R.id.icon);
                        name = ButterKnife.findById(view, R.id.name);
                        price = ButterKnife.findById(view, R.id.price);
                        return view;
                    }

                    @Override
                    public void showData(int position, MyServices itemData) {
                        Picasso.with(mContext).load(ApiConstants.Urls.API_IMG_BASE_URLS + itemData.service_img).into(icon);
                        name.setText(itemData.service_name);
                        price.setText(itemData.service_price);
                    }
                };
            }
        });

        commentsListViewDataAdapter = new ListViewDataAdapter<>(new ViewHolderCreator<MyComments>() {
            @Override
            public ViewHolderBase<MyComments> createViewHolder(int position) {
                return new ViewHolderBase<MyComments>() {
                    ImageView user_avatar;
                    RatingBar ratingBar;
                    TextView nickname, createtime, comment_content;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View view = layoutInflater.inflate(R.layout.review_cell, null);
                        user_avatar = ButterKnife.findById(view, R.id.user_avatar);
                        nickname = ButterKnife.findById(view, R.id.tv_nickname);
                        ratingBar = ButterKnife.findById(view, R.id.ratingBar);
                        createtime = ButterKnife.findById(view, R.id.tv_createtime);
                        comment_content = ButterKnife.findById(view, R.id.tv_comment_content);
                        return view;
                    }

                    @Override
                    public void showData(int position, MyComments itemData) {
                        Picasso.with(mContext).load(ApiConstants.Urls.API_IMG_BASE_URLS + itemData.user_avatar).into(user_avatar);
                        nickname.setText(itemData.user_nickname);
                        createtime.setText(itemData.createtime);
                        comment_content.setText(itemData.comment_content);
                        ratingBar.setRating(Float.parseFloat(itemData.rank));
                    }
                };
            }
        });

        serviceListView.setAdapter(servicesListViewDataAdapter);
        if (evaluationListView.getHeaderViewsCount()==0)
            evaluationListView.addHeaderView(mHeaderView);
        evaluationListView.setAdapter(commentsListViewDataAdapter);
        evaluationListView.setOnLoadMoreListener(this);
        evaluationListSwipeLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        evaluationListSwipeLayout.setOnRefreshListener(this);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void refreshListData(userdetailsResponse response) {
        if (evaluationListSwipeLayout!=null){
            evaluationListSwipeLayout.setRefreshing(false);
        }
        if (response!=null){
            if (response.comments.size()>0){
                if (commentsListViewDataAdapter!=null){
                    commentsListViewDataAdapter.getDataList().clear();
                    commentsListViewDataAdapter.getDataList().addAll(response.comments);
                    commentsListViewDataAdapter.notifyDataSetChanged();
                }
            }
            if (response.services.size()>0){
                if (servicesListViewDataAdapter!=null){
                    servicesListViewDataAdapter.getDataList().clear();
                    servicesListViewDataAdapter.getDataList().addAll(response.services);
                    servicesListViewDataAdapter.notifyDataSetChanged();
                }
            }

            if (evaluationListView!=null){
                if (UriHelper.getInstance().calculateTotalPages(response.total_count) > page) {
                    evaluationListView.setCanLoadMore(true);
                }else {
                    evaluationListView.setCanLoadMore(false);
                }
            }

        }
    }

    @Override
    public void addMoreListData(userdetailsResponse response) {
        if (evaluationListView!=null){
            evaluationListView.onLoadMoreComplete();
        }
        if (response!=null) {
            if (response.comments.size() > 0) {
                if (commentsListViewDataAdapter != null) {
                    commentsListViewDataAdapter.getDataList().clear();
                    commentsListViewDataAdapter.getDataList().addAll(response.comments);
                    commentsListViewDataAdapter.notifyDataSetChanged();
                }
            }
            if (evaluationListView!=null){
                if (UriHelper.getInstance().calculateTotalPages(response.total_count) > page) {
                    evaluationListView.setCanLoadMore(true);
                }else {
                    evaluationListView.setCanLoadMore(false);
                }
            }
        }
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void setUserInfo(UserDetailResponse userInfo) {

    }

    @Override
    public void userAppointment() {

    }

    @Override
    public void userFollow() {

    }

    @Override
    public void sendMsg() {
        if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin", false)) {
            YWIMKit mIMKit = YWAPI.getIMKitInstance("mobile", "23346330");
            Intent intent = mIMKit.getChattingActivityIntent("mobile", "23346330");
            startActivity(intent);
        } else {
            showToast("请先登录");
/*            ToastView toast=new ToastView(mContext,"请先登录");
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();*/
        }
    }

    @OnClick({R.id.top_bar_back, R.id.btn_follow, R.id.btn_appointment, R.id.btn_call, R.id.btn_msg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                break;
            case R.id.btn_follow:
                break;
            case R.id.btn_appointment:
                break;
            case R.id.btn_call:
                break;
            case R.id.btn_msg:
                sendMsg();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
