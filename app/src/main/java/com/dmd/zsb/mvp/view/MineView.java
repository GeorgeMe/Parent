package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.usermineResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/4/3.
 */
public interface MineView {
    void setView(usermineResponse response);
    void showTip(String msg);
}
