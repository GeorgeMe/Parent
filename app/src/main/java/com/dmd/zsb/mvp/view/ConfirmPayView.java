package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.confirmpayResponse;

/**
 * Created by Administrator on 2016/5/10.
 */
public interface ConfirmPayView extends BaseView {

    void setConfirmPayView(confirmpayResponse response);

    void showTip(String msg);

}
