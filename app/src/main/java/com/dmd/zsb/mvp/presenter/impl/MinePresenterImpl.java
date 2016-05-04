package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;
import android.util.Log;

import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.interactor.impl.MinekInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.MinePresenter;
import com.dmd.zsb.mvp.view.MineView;
import com.dmd.zsb.protocol.request.usermineRequest;
import com.dmd.zsb.protocol.response.usermineResponse;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/4/3.
 */
public class MinePresenterImpl implements MinePresenter,BaseSingleLoadedListener<usermineResponse> {
    private Context mContext;
    private MinekInteractorImpl minekInteractor;
    private MineView mineView;

    public MinePresenterImpl(Context mContext, MineView mineView) {
        this.mContext = mContext;
        this.mineView = mineView;
        minekInteractor=new MinekInteractorImpl(this);
    }

    @Override
    public void onSuccess(usermineResponse response) {
        if (response.succeed==0){
            mineView.setView(response);
        }

    }

    @Override
    public void onError(String msg) {
        mineView.showTip(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }

    @Override
    public void onMineInfo() {
        usermineRequest request=new usermineRequest();
        request.appkey = Constants.ZSBAPPKEY;
        request.version = Constants.ZSBVERSION;
        request.uid = XmlDB.getInstance(mContext).getKeyString("uid","uid");
        request.sid = XmlDB.getInstance(mContext).getKeyString("sid","sid");
        try {
            minekInteractor.getCommonSingleData(request.toJson());
        }catch (JSONException j){

        }
    }
}
