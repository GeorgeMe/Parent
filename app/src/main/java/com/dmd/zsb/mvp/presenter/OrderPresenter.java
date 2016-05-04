package com.dmd.zsb.mvp.presenter;

import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/29.
 */
public interface OrderPresenter {
    void onOrder(int event_tag, JSONObject jsonObject);
}
