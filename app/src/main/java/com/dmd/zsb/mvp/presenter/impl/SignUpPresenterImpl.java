package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.SignUpInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.SignUpPresenter;
import com.dmd.zsb.mvp.view.SignUpView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/14.
 */
public class SignUpPresenterImpl implements SignUpPresenter,BaseSingleLoadedListener<JsonObject> {
    private Context mContext=null;
    private SignUpView signUpView;
    private SignUpInteractorImpl signUpInteractor;

    public SignUpPresenterImpl(Context mContext, SignUpView signUpView) {
        this.mContext = mContext;
        this.signUpView = signUpView;
        signUpInteractor=new SignUpInteractorImpl(this);
    }

    @Override
    public void signUp(JsonObject data) {
        signUpInteractor.getCommonSingleData(data);
    }

    @Override
    public void onSuccess(JsonObject data) {
        signUpView.navigateToHome();
    }

    @Override
    public void onError(String msg) {
        signUpView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
