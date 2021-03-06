package com.dmd.zsb.parent.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.dmd.dialog.DialogAction;
import com.dmd.dialog.MaterialDialog;
import com.dmd.tutor.adapter.ListViewDataAdapter;
import com.dmd.tutor.adapter.ViewHolderBase;
import com.dmd.tutor.adapter.ViewHolderCreator;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.StringUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.tutor.widgets.XSwipeRefreshLayout;
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.UserDetailPresenterImpl;
import com.dmd.zsb.mvp.view.UserDetailView;
import com.dmd.zsb.parent.R;
import com.dmd.zsb.parent.activity.base.BaseActivity;
import com.dmd.zsb.protocol.response.userdetailsResponse;
import com.dmd.zsb.protocol.table.MyComments;
import com.dmd.zsb.protocol.table.MyServices;
import com.dmd.zsb.protocol.table.UserDetails;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

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


    //XSwipeRefreshLayout service_list_swipe_layout;

    private View mHeaderView;

    LoadMoreListView serviceListView;
    private UserDetails details;

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
    private String user="";
    private UserDetailPresenterImpl userDetailPresenter;
    @Override
    protected void getBundleExtras(Bundle extras) {
        user=extras.getString("user");
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


        serviceListView= ButterKnife.findById(mHeaderView, R.id.service_list_view);
       // service_list_swipe_layout= ButterKnife.findById(mHeaderView, R.id.service_list_swipe_layout);


        userDetailPresenter = new UserDetailPresenterImpl(mContext, this);
        if (NetUtils.isNetworkConnected(mContext)) {
            if (null != evaluationListSwipeLayout) {
                evaluationListSwipeLayout.postDelayed(new Runnable() {
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
                            jsonObject.put("user_id", user);//页码
                            jsonObject.put("flag", "detail");//页码
                        } catch (JSONException j) {

                        }

                        userDetailPresenter.onUserDetail(Constants.EVENT_REFRESH_DATA, jsonObject);
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
                        jsonObject.put("user_id", user);//页码
                        jsonObject.put("flag", "detail");//页码
                    } catch (JSONException j) {

                    }

                    userDetailPresenter.onUserDetail(Constants.EVENT_REFRESH_DATA, jsonObject);
                }
            });
        }

        servicesListViewDataAdapter = new ListViewDataAdapter<>(new ViewHolderCreator<MyServices>() {
            @Override
            public ViewHolderBase<MyServices> createViewHolder(int position) {
                return new ViewHolderBase<MyServices>() {
                    ImageView service_img;
                    TextView name, price;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View view = layoutInflater.inflate(R.layout.teacher_sevice_list_item, null);
                        service_img = ButterKnife.findById(view, R.id.service_img);
                        name = ButterKnife.findById(view, R.id.name);
                        price = ButterKnife.findById(view, R.id.price);
                        return view;
                    }

                    @Override
                    public void showData(int position, MyServices itemData) {
                        Picasso.with(mContext).load(itemData.service_img).into(service_img);
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
                        Picasso.with(mContext).load(itemData.user_avatar).into(user_avatar);
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
    private void hight(){

        if (servicesListViewDataAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < servicesListViewDataAdapter.getCount(); i++) {
            View listItem = servicesListViewDataAdapter.getView(i, null, serviceListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = serviceListView.getLayoutParams();
        params.height = totalHeight + (serviceListView.getDividerHeight() * (servicesListViewDataAdapter.getCount()-1));
        ((ViewGroup.MarginLayoutParams)params).setMargins(10, 10, 10, 10);
        serviceListView.setLayoutParams(params);
    }
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
            jsonObject.put("user_id", user);//页码
            jsonObject.put("flag", "detail");//页码
        } catch (JSONException j) {

        }

        userDetailPresenter.onUserDetail(Constants.EVENT_LOAD_MORE_DATA, jsonObject);
    }

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
            jsonObject.put("user_id", user);//页码
            jsonObject.put("flag", "detail");//页码
        } catch (JSONException j) {

        }
        userDetailPresenter.onUserDetail(Constants.EVENT_REFRESH_DATA, jsonObject);
    }

    @Override
    public void refreshListData(userdetailsResponse response) {
        if (evaluationListSwipeLayout!=null){
            evaluationListSwipeLayout.setRefreshing(false);
        }
        if (response!=null){
            if (response.follow){
                btnFollow.setText("已关注");
                btnFollow.setClickable(false);
            }
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
                    hight();
                    servicesListViewDataAdapter.notifyDataSetChanged();
                }
            }
            if (response.details!=null){
                details=response.details;
                Picasso.with(mContext).load(details.user_avatar).into(userAvatar);

                tvName.setText(details.user_name);
                tvSeniority.setText(details.user_seniority);
                appointmentTime.setText(details.appointment_time);
                goodSubjects.setText(details.good_subjects);
                teachingPlace.setText(details.teaching_place);
                teachingMethods.setText(details.teaching_methods);
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
    public void setUserInfo(userdetailsResponse userInfo) {

        if (userInfo.errno==0){
            if (userInfo.follow){
                btnFollow.setText("已关注");
                btnFollow.setClickable(false);
                showToast(userInfo.msg);
            }
        }else if (userInfo.errno==1){
            showToast(userInfo.msg);
        }else {
            showToast(userInfo.msg);
        }
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
            if (details!=null){
                YWIMKit mIMKit = YWAPI.getIMKitInstance(details.mobile, "23346330");
                Intent intent = mIMKit.getChattingActivityIntent(details.mobile, "23346330");
                startActivity(intent);
            }else {
                showToast("未获取到教师信息");
            }
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
                finish();
                break;
            case R.id.btn_follow:
                //提交的参数封装
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("appkey", Constants.ZSBAPPKEY);
                    jsonObject.put("version", Constants.ZSBVERSION);
                    jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                    jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                    jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
                    jsonObject.put("page", 1);//页码
                    jsonObject.put("user_id", user);//页码
                    jsonObject.put("flag", "follow");//页码

                } catch (JSONException j) {

                }
                userDetailPresenter.onUserDetail(901, jsonObject);
                break;
            case R.id.btn_appointment:
                if (!StringUtils.StringIsEmpty(user)){
                    Bundle bundle=new Bundle();
                    bundle.putString("default_receiver_id",user);
                    readyGo(ReleaseOrderActivity.class,bundle);
                }else {
                    showToast("发生错误，请重试");
                }
                break;
            case R.id.btn_call:
                if (details!=null){
                    new MaterialDialog.Builder(mContext).title("拨打电话").content("确定拨打"+details.mobile+"吗？").positiveText("确定").negativeText("取消").onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    }).onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            call();
                            dialog.dismiss();
                        }
                    }).show();
                }else {
                    showToast("未获取到教师信息");
                }

                break;
            case R.id.btn_msg:
                sendMsg();
                break;
        }

    }

    private void  call(){
        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+details.mobile));
            startActivity(intent);
        }catch (SecurityException s){
            Log.e("msg",s.getMessage());
        }

    }
}
