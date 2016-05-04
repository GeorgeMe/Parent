package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.ReleaseOrderInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.ReleaseOrderPresenter;
import com.dmd.zsb.mvp.view.ReleaseOrderView;
import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public class ReleaseOrderPresenterImpl implements ReleaseOrderPresenter ,BaseSingleLoadedListener<JSONObject>{

    private ReleaseOrderInteractorImpl releaseOrderInteractor;
    private Context mContext;
    private ReleaseOrderView releaseOrderView;

    public ReleaseOrderPresenterImpl(Context mContext, ReleaseOrderView releaseOrderView) {
        this.mContext = mContext;
        this.releaseOrderView = releaseOrderView;
        releaseOrderInteractor=new ReleaseOrderInteractorImpl(this);
    }

    @Override
    public void onReleaseOrder(JSONObject jsonObject) {
        releaseOrderInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JSONObject data) {
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
