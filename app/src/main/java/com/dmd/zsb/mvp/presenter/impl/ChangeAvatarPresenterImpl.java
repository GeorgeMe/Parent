package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.tutor.utils.OnUploadProcessListener;
import com.dmd.zsb.mvp.interactor.impl.ChangeAvatarInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.ChangeAvatarPresenter;
import com.dmd.zsb.mvp.view.ChangeAvatarView;
import com.dmd.zsb.protocol.request.changeavatarRequest;
import com.dmd.zsb.protocol.response.changeavatarResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/5.
 */
public class ChangeAvatarPresenterImpl implements ChangeAvatarPresenter,BaseSingleLoadedListener<changeavatarResponse> {
    private OnUploadProcessListener uploadProcessListener;
    private ChangeAvatarInteractorImpl changeAvatarInteractor;
    private ChangeAvatarView changeAvatarView;
    private Context mContext;

    public ChangeAvatarPresenterImpl(ChangeAvatarView changeAvatarView, Context mContext,OnUploadProcessListener uploadProcessListener) {
        this.changeAvatarView = changeAvatarView;
        this.mContext = mContext;
        changeAvatarInteractor=new ChangeAvatarInteractorImpl(this,uploadProcessListener);
    }

    @Override
    public void onChangeAvatar(JSONObject jsonObject) {
        changeavatarRequest request =new changeavatarRequest();
        try {
            request.fromJson(jsonObject);
            changeAvatarInteractor.getCommonSingleData(request.toJson());
        }catch (JSONException j){

        }
    }

    @Override
    public void onSuccess(changeavatarResponse response) {

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onException(String msg) {

    }
}
