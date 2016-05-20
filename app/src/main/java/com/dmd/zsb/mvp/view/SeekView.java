package com.dmd.zsb.mvp.view;


import com.dmd.zsb.protocol.response.seekResponse;
import com.dmd.zsb.protocol.table.UsersBean;

/**
 * Created by George on 2015/12/9.
 */
public interface SeekView extends BaseView {

    void navigateToUserDetail(UsersBean itemData);

    void refreshListData(seekResponse response);

    void addMoreListData(seekResponse response);

}
