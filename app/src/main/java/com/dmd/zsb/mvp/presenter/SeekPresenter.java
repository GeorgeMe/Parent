package com.dmd.zsb.mvp.presenter;

import com.dmd.zsb.entity.UserEntity;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2015/12/22.
 */
public interface SeekPresenter {
    //String requestTag, int event_tag, String keywords, int page, boolean isSwipeRefresh
    void loadListData(int event,JsonObject jsonObject);
}
