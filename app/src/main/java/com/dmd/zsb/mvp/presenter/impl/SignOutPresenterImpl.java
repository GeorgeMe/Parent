package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.mvp.interactor.SignOutInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.SignOutPresenter;
import com.dmd.zsb.mvp.view.SignOutView;
import com.dmd.zsb.openim.LoginHelper;
import com.dmd.zsb.protocol.request.signoutRequest;
import com.dmd.zsb.protocol.response.signoutResponse;

import org.json.JSONException;
import org.json.JSONObject;

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
    public void onSignOut(JSONObject jsonObject) {
        signOutView.showLoading(null);
        signoutRequest request=new signoutRequest();
        try {
            request.fromJson(jsonObject);
            signOutInteractor.getCommonSingleData(request.toJson());
        }catch (JSONException j){

        }

    }

    @Override
    public void onSuccess(signoutResponse response) {
        signOutView.hideLoading();
        if (response.errno==0){
            IYWLoginService mLoginService = LoginHelper.getInstance().getIMKit().getLoginService();
            mLoginService.logout(new IWxCallback() {

                @Override
                public void onSuccess(Object... arg0) {
                    XmlDB.getInstance(mContext).saveKey("sid","");
                    XmlDB.getInstance(mContext).saveKey("uid","");
                    XmlDB.getInstance(mContext).saveKey("isLogin",false);
                    signOutView.onSuccess();
                }

                @Override
                public void onProgress(int arg0) {

                }

                @Override
                public void onError(int arg0, String arg1) {
                    signOutView.showTip("退出报错");
                }
            });

        }
    }

    @Override
    public void onError(String msg) {
        signOutView.hideLoading();
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
