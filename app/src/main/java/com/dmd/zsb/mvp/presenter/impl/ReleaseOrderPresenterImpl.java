package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.ReleaseOrderInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.ReleaseOrderPresenter;
import com.dmd.zsb.mvp.view.ReleaseOrderView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public class ReleaseOrderPresenterImpl implements ReleaseOrderPresenter ,BaseSingleLoadedListener<JsonObject>{

    private ReleaseOrderInteractorImpl releaseOrderInteractor;
    private Context mContext;
    private ReleaseOrderView releaseOrderView;

    public ReleaseOrderPresenterImpl(Context mContext, ReleaseOrderView releaseOrderView) {
        this.mContext = mContext;
        this.releaseOrderView = releaseOrderView;
        releaseOrderInteractor=new ReleaseOrderInteractorImpl(this);
    }

    @Override
    public void onReleaseOrder(JsonObject jsonObject) {
        releaseOrderInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {
        releaseOrderView.showSuccessView(data);
    }

    @Override
    public void onError(String msg) {
        releaseOrderView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
