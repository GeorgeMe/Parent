package com.dmd.zsb.parent.activity;


import android.os.Bundle;
import android.view.View;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.zsb.parent.R;
import com.dmd.zsb.parent.activity.base.BaseActivity;
import com.dmd.zsb.utils.CheckBankCard;

public class BankCardActivity extends BaseActivity {

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_bank_card;
    }

    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        //校验银行卡号码
        if (CheckBankCard.checkBankCard("")){

        }
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }
}
