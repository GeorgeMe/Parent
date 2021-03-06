package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.interactor.impl.DemandInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.presenter.DemandPresenter;
import com.dmd.zsb.mvp.view.DemandView;
import com.dmd.zsb.protocol.request.demandRequest;
import com.dmd.zsb.protocol.response.demandResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/29.
 */
public class DemandPresenterImpl implements DemandPresenter,BaseMultiLoadedListener<demandResponse> {
    private DemandInteractorImpl demandInteractor;
    private Context mContext;
    private DemandView demandView;

    public DemandPresenterImpl(Context mContext, DemandView demandView) {
        this.mContext = mContext;
        this.demandView = demandView;
        demandInteractor=new DemandInteractorImpl(this);
    }

    @Override
    public void onDemand(int event_tag, JSONObject jsonObject) {
        demandView.showLoading(null);
        demandRequest request=new demandRequest();
        try {
            request.fromJson(jsonObject);
            demandInteractor.getCommonListData(event_tag,request.toJson());
        }catch (JSONException j){

        }

    }

    @Override
    public void onSuccess(int event_tag, demandResponse response) {
        demandView.hideLoading();
        if (event_tag== Constants.EVENT_REFRESH_DATA){
            demandView.refreshListData(response);
        }else if (event_tag==Constants.EVENT_LOAD_MORE_DATA){
            demandView.addMoreListData(response);
        }

    }

    @Override
    public void onError(String msg) {
        demandView.hideLoading();
        demandView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        demandView.showException(msg);
    }
}
