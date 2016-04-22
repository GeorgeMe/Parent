package com.dmd.zsb.parent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.entity.UserEntity;
import com.dmd.zsb.parent.R;
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.entity.response.UserDetailResponse;
import com.dmd.zsb.mvp.view.UserDetailView;
import com.dmd.zsb.parent.activity.base.BaseActivity;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import butterknife.Bind;

public class UserDetailActivity extends BaseActivity implements UserDetailView,View.OnClickListener {

    @Bind(R.id.user_img_header)
    ImageView userImgHeader;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.user_gender)
    TextView userGender;
    @Bind(R.id.user_praise)
    TextView userPraise;
    @Bind(R.id.user_audition)
    TextView userAudition;
    @Bind(R.id.user_appointment)
    TextView userAppointment;
    @Bind(R.id.user_follow)
    ImageView userFollow;
    @Bind(R.id.teacher_identity)
    TextView teacherIdentity;
    @Bind(R.id.teacher_price)
    TextView teacherPrice;
    @Bind(R.id.appointment_time)
    TextView appointmentTime;
    @Bind(R.id.good_subjects)
    TextView goodSubjects;
    @Bind(R.id.teaching_place)
    TextView teachingPlace;
    @Bind(R.id.teaching_methods)
    TextView teachingMethods;
    @Bind(R.id.self_evaluation)
    TextView selfEvaluation;
    @Bind(R.id.good_subjects2)
    TextView goodSubjects2;
    @Bind(R.id.professional_accreditation)
    TextView professionalAccreditation;
    @Bind(R.id.send_msg)
    Button sendMsg;
    @Bind(R.id.user_loading_view)
    FrameLayout userLoadingView;

    private UserEntity data;


    @Override
    protected void getBundleExtras(Bundle extras) {
        data=(UserEntity)extras.getSerializable("data");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_user_detail;
    }

    @Subscribe
    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return userLoadingView;
    }

    @Override
    protected void initViewsAndEvents() {
        Picasso.with(mContext).load(ApiConstants.Urls.API_IMG_BASE_URLS+data.getAvatar()).into(userImgHeader);
        userName.setText(data.getUsername());
        userGender.setText(data.getGender());
        userPraise.setText(data.getGender());
        teacherIdentity.setText(data.getSubject_name());
        teacherPrice.setText(data.getRole());
        appointmentTime.setText(data.getSignature());
        goodSubjects.setText(data.getCurriculum_id());
        teachingPlace.setText(data.getLocation());
        teachingMethods.setText(data.getCurriculum_id());
        selfEvaluation.setText(""+data.getComment_count());
        goodSubjects2.setText(data.getSubject_name());
        professionalAccreditation.setText(data.getSubject_name());

        userAudition.setOnClickListener(this);
        userAppointment.setOnClickListener(this);
        userFollow.setOnClickListener(this);
        sendMsg.setOnClickListener(this);
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
        if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin",false)){
            Bundle bundle=new Bundle();
            bundle.putString("default_receiver_id",data.getUser_id());
            readyGo(ReleaseOrderActivity.class,bundle);
        }else {
            showToast("请登录后进行约课");
        }
    }

    @Override
    public void userFollow() {
        showToast("关注");
    }

    @Override
    public void sendMsg() {
        if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin",false)){
            YWIMKit mIMKit = YWAPI.getIMKitInstance(data.getMobile(),"23346330");
            Intent intent =mIMKit.getChattingActivityIntent(data.getMobile(),"23346330");
            startActivity(intent);
        }
    }
    @Override
    public void onClick(View v) {
        if (v==userAppointment){
            userAppointment();
        }else if(v==sendMsg){
            sendMsg();
        }else if(v==userFollow){
            userFollow();
        }
    }
}
