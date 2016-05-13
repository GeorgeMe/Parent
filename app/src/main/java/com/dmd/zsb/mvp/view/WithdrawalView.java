package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.withdrawalResponse;

/**
 * Created by Administrator on 2016/5/13.
 */
public interface WithdrawalView extends BaseView {
    void onWithdrawal(withdrawalResponse response);
}
