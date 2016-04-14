package com.dmd.zsb.mvp.view;

import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/3.
 */
public interface MineView {
    void setView(JsonObject data);
    void showTip(String msg);
}
