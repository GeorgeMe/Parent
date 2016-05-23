package com.dmd.zsb.mvp.view;

import com.dmd.zsb.entity.response.UserDetailResponse;
import com.dmd.zsb.protocol.response.userdetailsResponse;

/**
 * Created by Administrator on 2016/3/18.
 */
public interface UserDetailView {
    void refreshListData(userdetailsResponse response);

    void addMoreListData(userdetailsResponse response);

    void setUserInfo(UserDetailResponse userInfo);
    void userAppointment();
    void userFollow();
    void sendMsg();
    void showError(String msg);

}
