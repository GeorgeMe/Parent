package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.entity.response.UserDetailResponse;
import com.dmd.zsb.mvp.interactor.impl.UserDetailInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.UserDetailPresenter;
import com.dmd.zsb.mvp.view.UserDetailView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/3/18.
 */
public class UserDetailPresenterImpl implements UserDetailPresenter,BaseSingleLoadedListener<UserDetailResponse> {
    private Context mContext=null;
    private UserDetailView userDetailView;
    private UserDetailInteractorImpl userDetailInteractor;

    public UserDetailPresenterImpl(Context mContext, UserDetailView userDetailView) {
        this.mContext = mContext;
        this.userDetailView = userDetailView;
        userDetailInteractor=new UserDetailInteractorImpl(this);
    }

    @Override
    public void getUserDetail(JsonObject jsonObject) {
        String uid= XmlDB.getInstance(mContext).getKeyString("uid","uid");
        String sid=XmlDB.getInstance(mContext).getKeyString("sid","sid");
        jsonObject.addProperty("uid",uid);
        jsonObject.addProperty("sid",sid);

        userDetailInteractor.getCommonSingleData(jsonObject);
    }

    @Override
    public void onSuccess(UserDetailResponse data) {
        userDetailView.setUserInfo(data);
    }

    @Override
    public void onError(String msg) {
        userDetailView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
