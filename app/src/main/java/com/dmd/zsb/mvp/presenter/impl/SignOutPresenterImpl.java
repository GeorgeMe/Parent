package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.SignOutInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.SignOutPresenter;
import com.dmd.zsb.mvp.view.SignOutView;
import com.dmd.zsb.protocol.request.signoutRequest;
import com.dmd.zsb.protocol.response.signoutResponse;

import org.json.JSONException;

/**
 * Created by Administrator on 2016/5/5.
 */
public class SignOutPresenterImpl implements SignOutPresenter,BaseSingleLoadedListener<signoutResponse> {
    private Context mContext=null;
    private SignOutView signOutView;
    private SignOutInteractorImpl signOutInteractor;

    public SignOutPresenterImpl(SignOutView signOutView, Context mContext) {
        this.signOutView = signOutView;
        this.mContext = mContext;
        signOutInteractor=new SignOutInteractorImpl(this);
    }

    @Override
    public void onSignOut() {
        signoutRequest request=new signoutRequest();
        try {
            signOutInteractor.getCommonSingleData(request.toJson());
        }catch (JSONException j){

        }

    }

    @Override
    public void onSuccess(signoutResponse response) {

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onException(String msg) {

    }
}
