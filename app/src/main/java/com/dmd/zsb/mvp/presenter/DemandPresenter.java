package com.dmd.zsb.mvp.presenter;


import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/29.
 */
public interface DemandPresenter {
    void onDemand(int event_tag, JSONObject jsonObject);
}
