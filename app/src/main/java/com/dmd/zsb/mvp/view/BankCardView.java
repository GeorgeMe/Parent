package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.bankcardResponse;

/**
 * Created by Administrator on 2016/6/13.
 */
public interface BankCardView  extends BaseView{
    void onBankCardView(bankcardResponse response );
}
