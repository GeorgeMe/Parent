package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.ConfirmPayInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.ConfirmPayPresenter;
import com.dmd.zsb.mvp.view.ConfirmPayView;
import com.dmd.zsb.protocol.response.confirmpayResponse;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/10.
 */
public class ConfirmPayPresenterImpl implements ConfirmPayPresenter,BaseSingleLoadedListener<confirmpayResponse> {
    private Context mContext;
    private ConfirmPayView payView;
    private ConfirmPayInteractorImpl confirmPayInteractor;

    public ConfirmPayPresenterImpl(Context mContext, ConfirmPayView payView) {
        this.mContext = mContext;
        this.payView = payView;
        confirmPayInteractor=new ConfirmPayInteractorImpl(this);
    }

    @Override
    public void onConfirmPay(JSONObject jsonObject) {
        confirmPayInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(confirmpayResponse data) {
        payView.setConfirmPayView(data);
    }

    @Override
    public void onError(String msg) {
        payView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
