package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.mvp.interactor.impl.SignInInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.SignInPresenter;
import com.dmd.zsb.mvp.view.SignInView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/14.
 */
public class SignInPresenterImpl implements SignInPresenter,BaseSingleLoadedListener<JsonObject>{
    private Context mContext=null;
    private SignInView signInView;
    private SignInInteractorImpl signInInteractor;

    public SignInPresenterImpl(Context mContext, SignInView signInView) {
        this.mContext = mContext;
        this.signInView = signInView;
        signInInteractor=new SignInInteractorImpl(this);
    }

    @Override
    public void onSuccess(JsonObject data) {
        if (data.get("msg").getAsString().equals("fail")){
            onError("登录失败");
        }else {
            XmlDB.getInstance(mContext).saveKey("uid",data.get("id").getAsString());
            XmlDB.getInstance(mContext).saveKey("sid",data.get("sid").getAsString());
            XmlDB.getInstance(mContext).saveKey("isLogin", true);
            signInView.navigateToHome();
        }
    }

    @Override
    public void onError(String msg) {
        XmlDB.getInstance(mContext).saveKey("isLogin", false);
        signInView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }

    @Override
    public void signIn(JsonObject jsonObject) {
        signInInteractor.getCommonSingleData(jsonObject);
    }
}
