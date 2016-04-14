package com.dmd.zsb.mvp.view;

import com.dmd.zsb.entity.UserEntity;
import com.dmd.zsb.entity.response.HomeResponse;
import com.google.gson.JsonObject;

/**
 * Created by George on 2015/12/6.
 */
public interface HomeView extends BaseView{

    void navigateToUserDetail(UserEntity data);

    void refreshListData(HomeResponse data);

    void addMoreListData(HomeResponse data);

}
