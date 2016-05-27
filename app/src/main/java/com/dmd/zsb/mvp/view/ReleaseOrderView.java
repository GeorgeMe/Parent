package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.releaseorderResponse;

/**
 * Created by Administrator on 2016/3/28.
 */
public interface ReleaseOrderView  extends BaseView{
    void showSuccessView(releaseorderResponse response);
    void showTip(String msg);
}
