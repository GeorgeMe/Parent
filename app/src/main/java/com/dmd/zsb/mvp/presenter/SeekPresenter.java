package com.dmd.zsb.mvp.presenter;

import org.json.JSONObject;

/**
 * Created by Administrator on 2015/12/22.
 */
public interface SeekPresenter {
    //String requestTag, int event_tag, String keywords, int page, boolean isSwipeRefresh
    void loadListData(int event,JSONObject jsonObject);
}
