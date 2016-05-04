package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.mvp.interactor.impl.SignUpInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.SignUpPresenter;
import com.dmd.zsb.mvp.view.SignUpView;
import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/14.
 */
public class SignUpPresenterImpl implements SignUpPresenter,BaseSingleLoadedListener<JSONObject> {
    private Context mContext=null;
    private SignUpView signUpView;
    private SignUpInteractorImpl signUpInteractor;

    public SignUpPresenterImpl(Context mContext, SignUpView signUpView) {
        this.mContext = mContext;
        this.signUpView = signUpView;
        signUpInteractor=new SignUpInteractorImpl(this);
    }

    @Override
    public void signUp(JSONObject data) {
        signUpInteractor.getCommonSingleData(data);
    }

    @Override
    public void onSuccess(JSONObject data) {
        if (data.optString("msg").equals("fail")){
            onError("登录失败");
        }else {
            XmlDB.getInstance(mContext).saveKey("uid",data.optString("id"));
            XmlDB.getInstance(mContext).saveKey("sid",data.optString("sid"));
            XmlDB.getInstance(mContext).saveKey("isLogin", true);
            signUpView.navigateToHome();
        }
    }

    @Override
    public void onError(String msg) {
        signUpView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
