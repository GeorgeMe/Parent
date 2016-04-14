package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.common.Constants;
import com.dmd.zsb.entity.response.DemandResponse;
import com.dmd.zsb.mvp.interactor.impl.DemandInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.presenter.DemandPresenter;
import com.dmd.zsb.mvp.view.DemandView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/29.
 */
public class DemandPresenterImpl implements DemandPresenter,BaseMultiLoadedListener<DemandResponse> {
    private DemandInteractorImpl demandInteractor;
    private Context mContext;
    private DemandView demandView;

    public DemandPresenterImpl(Context mContext, DemandView demandView) {
        this.mContext = mContext;
        this.demandView = demandView;
        demandInteractor=new DemandInteractorImpl(this);
    }

    @Override
    public void onDemand(int event_tag, JsonObject jsonObject) {
        demandInteractor.getCommonListData(event_tag,jsonObject);
    }

    @Override
    public void onSuccess(int event_tag, DemandResponse data) {
        if (event_tag== Constants.EVENT_REFRESH_DATA){
            demandView.refreshListData(data);
        }else if (event_tag==Constants.EVENT_LOAD_MORE_DATA){
            demandView.addMoreListData(data);
        }

    }

    @Override
    public void onError(String msg) {
        demandView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        demandView.showException(msg);
    }
}
