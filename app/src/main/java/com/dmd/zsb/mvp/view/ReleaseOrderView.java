package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.releaseorderResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public interface ReleaseOrderView {
    void showSuccessView(releaseorderResponse response);
    void showTip(String msg);
}
