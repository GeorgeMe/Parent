package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.WithdrawalInterActorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.WithdrawalPresenter;
import com.dmd.zsb.mvp.view.WithdrawalView;
import com.dmd.zsb.protocol.response.withdrawalResponse;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/13.
 */
public class WithdrawalPresenterImpl implements WithdrawalPresenter,BaseSingleLoadedListener<withdrawalResponse> {

    private Context mContext;
    private WithdrawalView withdrawalView;
    private WithdrawalInterActorImpl withdrawalInterActor;

    public WithdrawalPresenterImpl(Context mContext, WithdrawalView withdrawalView) {
        this.mContext = mContext;
        this.withdrawalView = withdrawalView;
        withdrawalInterActor=new WithdrawalInterActorImpl(this);
    }

    @Override
    public void onSuccess(withdrawalResponse data) {
        withdrawalView.onWithdrawal(data);
    }

    @Override
    public void onError(String msg) {
        withdrawalView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }

    @Override
    public void onWithdrawal(JSONObject jsonObject) {
        withdrawalInterActor.getCommonSingleData(jsonObject);
    }
}
