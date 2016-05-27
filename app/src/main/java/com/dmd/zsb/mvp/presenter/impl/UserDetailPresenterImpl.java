package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.interactor.impl.UserDetailInteractoterImpl;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.presenter.UserDetailPresenter;
import com.dmd.zsb.mvp.view.UserDetailView;
import com.dmd.zsb.protocol.request.userdetailsRequest;
import com.dmd.zsb.protocol.response.userdetailsResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/18.
 */
public class UserDetailPresenterImpl implements UserDetailPresenter,BaseMultiLoadedListener<userdetailsResponse> {
    private Context mContext=null;
    private UserDetailView userDetailView;
    private UserDetailInteractoterImpl userDetailInteractor;

    public UserDetailPresenterImpl(Context mContext, UserDetailView userDetailView) {
        this.mContext = mContext;
        this.userDetailView = userDetailView;
        userDetailInteractor=new UserDetailInteractoterImpl(this);
    }

    @Override
    public void onUserDetail(int event_tag,JSONObject jsonObject) {
        userDetailView.showLoading(null);
        userdetailsRequest request=new userdetailsRequest();
        try{
            request.fromJson(jsonObject);
            userDetailInteractor.getCommonListData(event_tag,request.toJson());
        }catch (JSONException j){

        }

    }

    @Override
    public void onSuccess(int event_tag, userdetailsResponse response) {
        userDetailView.hideLoading();
        if (event_tag == Constants.EVENT_REFRESH_DATA) {
            userDetailView.refreshListData(response);
        } else if (event_tag == Constants.EVENT_LOAD_MORE_DATA) {
            userDetailView.addMoreListData(response);
        }else if (event_tag==901){
            if (response.errno==0){
                userDetailView.setUserInfo(response);
            }
        }
    }

    @Override
    public void onError(String msg) {
        userDetailView.hideLoading();
    }

    @Override
    public void onException(String msg) {
        userDetailView.hideLoading();
    }
}
