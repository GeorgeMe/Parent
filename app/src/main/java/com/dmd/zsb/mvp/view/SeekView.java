package com.dmd.zsb.mvp.view;


import com.dmd.zsb.entity.UserEntity;
import com.dmd.zsb.entity.response.SeekResponse;

/**
 * Created by George on 2015/12/9.
 */
public interface SeekView extends BaseView {

    void navigateToUserDetail(UserEntity itemData);

    void refreshListData(SeekResponse data);

    void addMoreListData(SeekResponse data);

}
