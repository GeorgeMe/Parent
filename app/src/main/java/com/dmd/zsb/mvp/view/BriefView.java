package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.briefResponse;

/**
 * Created by Administrator on 2016/3/28.
 */
public interface BriefView extends BaseView{
    void toSettingView(briefResponse response);
    void showTip(String msg);
}
