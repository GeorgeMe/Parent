package com.dmd.zsb.mvp.view;

import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public interface ReleaseOrderView {
    void showSuccessView(JsonObject data);
    void showTip(String msg);
}
