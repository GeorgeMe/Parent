package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.ProfileInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.ProfilePresenter;
import com.dmd.zsb.mvp.view.ProfileView;
import com.dmd.zsb.protocol.request.profileRequest;
import com.dmd.zsb.protocol.response.profileResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/28.
 */
public class ProfilePresenterImpl implements ProfilePresenter,BaseSingleLoadedListener<profileResponse> {
    private ProfileInteractorImpl briefInteractor;
    private Context mContext;
    private ProfileView briefView;

    public ProfilePresenterImpl(ProfileView briefView, Context mContext) {
        this.briefView = briefView;
        this.mContext = mContext;
        briefInteractor=new ProfileInteractorImpl(this);
    }

    @Override
    public void onChangeProfile(JSONObject jsonObject) {
        profileRequest request=new profileRequest();
        try {
            request.fromJson(jsonObject);
            briefInteractor.getCommonSingleData(request.toJson());
        }catch (JSONException j){

        }

    }

    @Override
    public void onSuccess(profileResponse response) {
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
