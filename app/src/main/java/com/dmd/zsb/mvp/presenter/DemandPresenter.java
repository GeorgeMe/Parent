package com.dmd.zsb.mvp.presenter;

import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/29.
 */
public interface DemandPresenter {
    void onDemand(int event_tag, JsonObject jsonObject);
}
