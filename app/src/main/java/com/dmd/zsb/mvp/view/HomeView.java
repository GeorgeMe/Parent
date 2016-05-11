package com.dmd.zsb.mvp.view;

import com.dmd.zsb.entity.UserEntity;
import com.dmd.zsb.entity.response.HomeResponse;
import com.dmd.zsb.protocol.response.homeResponse;
import com.dmd.zsb.protocol.table.UsersBean;
import com.google.gson.JsonObject;

/**
 * Created by George on 2015/12/6.
 */
public interface HomeView extends BaseView{

    void navigateToUserDetail(UsersBean data);

    void refreshListData(homeResponse response);

    void addMoreListData(homeResponse response);

}
