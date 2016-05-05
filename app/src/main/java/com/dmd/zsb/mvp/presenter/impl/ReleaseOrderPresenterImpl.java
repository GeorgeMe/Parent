package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.ReleaseOrderInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.ReleaseOrderPresenter;
import com.dmd.zsb.mvp.view.ReleaseOrderView;
import com.dmd.zsb.protocol.request.releaseorderRequest;
import com.dmd.zsb.protocol.response.releaseorderResponse;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public class ReleaseOrderPresenterImpl implements ReleaseOrderPresenter ,BaseSingleLoadedListener<releaseorderResponse>{

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
        releaseorderRequest request=new releaseorderRequest();
        try {
            releaseOrderInteractor.getCommonSingleData(request.toJson());
        }catch (JSONException j){

        }

    }

    @Override
    public void onSuccess(releaseorderResponse response) {
        releaseOrderView.showSuccessView(response);
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
