package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.MinekInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.presenter.MinePresenter;
import com.dmd.zsb.mvp.view.MineView;
import com.google.gson.JsonObject;

/**
 * Created by Administrator on 2016/4/3.
 */
public class MinePresenterImpl implements MinePresenter,BaseMultiLoadedListener<JsonObject> {
    private Context mContext;
    private MinekInteractorImpl minekInteractor;
    private MineView mineView;

    public MinePresenterImpl(Context mContext, MineView mineView) {
        this.mContext = mContext;
        this.mineView = mineView;
        minekInteractor=new MinekInteractorImpl(this);
    }

    @Override
    public void onSuccess(int event,JsonObject data) {
        mineView.setView(data);
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
    public void onMineInfo(int event,JsonObject jsonObject) {
        minekInteractor.getCommonListData(event,jsonObject);
    }
}
