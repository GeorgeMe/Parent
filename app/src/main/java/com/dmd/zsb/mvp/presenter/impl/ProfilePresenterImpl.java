package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.ProfileInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.ProfilePresenter;
import com.dmd.zsb.mvp.view.ProfileView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public class ProfilePresenterImpl implements ProfilePresenter,BaseSingleLoadedListener<JsonObject> {
    private ProfileInteractorImpl briefInteractor;
    private Context mContext;
    private ProfileView briefView;

    public ProfilePresenterImpl(ProfileView briefView, Context mContext) {
        this.briefView = briefView;
        this.mContext = mContext;
        briefInteractor=new ProfileInteractorImpl(this);
    }

    @Override
    public void onChangeProfile(JsonObject jsonObject) {
        briefInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(JsonObject data) {
        briefView.toSettingView();
    }

    @Override
    public void onError(String msg) {
        briefView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
