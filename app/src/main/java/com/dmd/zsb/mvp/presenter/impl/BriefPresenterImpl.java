package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.BriefInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.BriefPresenter;
import com.dmd.zsb.mvp.view.BriefView;
import com.dmd.zsb.protocol.request.briefRequest;
import com.dmd.zsb.protocol.response.briefResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public class BriefPresenterImpl implements BriefPresenter,BaseSingleLoadedListener<briefResponse> {
    private BriefInteractorImpl briefInteractor;
    private Context mContext;
    private BriefView briefView;

    public BriefPresenterImpl(BriefView briefView, Context mContext) {
        this.briefView = briefView;
        this.mContext = mContext;
        briefInteractor=new BriefInteractorImpl(this);
    }

    @Override
    public void onChangeBrief(JSONObject jsonObject) {
        briefView.showLoading(null);
        briefRequest request=new briefRequest();
        try {
            request.fromJson(jsonObject);
            briefInteractor.getCommonSingleData(request.toJson());
        }catch (JSONException j){

        }

    }

    @Override
    public void onSuccess(briefResponse response) {
        briefView.hideLoading();
        briefView.toSettingView(response);
    }

    @Override
    public void onError(String msg) {
        briefView.hideLoading();
        briefView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
