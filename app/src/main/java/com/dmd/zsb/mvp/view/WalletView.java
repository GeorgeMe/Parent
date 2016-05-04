package com.dmd.zsb.mvp.view;

import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public interface WalletView {
    void setView(JSONObject jsonObject);
    void showTip(String msg);
}
