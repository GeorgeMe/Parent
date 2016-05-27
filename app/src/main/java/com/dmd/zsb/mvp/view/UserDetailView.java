package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.userdetailsResponse;

/**
 * Created by Administrator on 2016/3/18.
 */
public interface UserDetailView  extends BaseView{
    void refreshListData(userdetailsResponse response);

    void addMoreListData(userdetailsResponse response);

    void setUserInfo(userdetailsResponse userInfo);
    void userAppointment();
    void userFollow();
    void sendMsg();
    void showError(String msg);

}
