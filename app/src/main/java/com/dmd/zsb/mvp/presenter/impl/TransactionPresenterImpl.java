package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.interactor.TransactionInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.presenter.TransactionPresenter;
import com.dmd.zsb.mvp.view.TransactionView;
import com.dmd.zsb.protocol.request.transactionsRequest;
import com.dmd.zsb.protocol.response.transactionsResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/13.
 */
public class TransactionPresenterImpl implements TransactionPresenter,BaseMultiLoadedListener<transactionsResponse> {

    private Context mContext;
    private TransactionView transactionView;
    private TransactionInteractorImpl transactionInteractor;

    public TransactionPresenterImpl(Context mContext, TransactionView transactionView) {
        this.mContext = mContext;
        this.transactionView = transactionView;
        transactionInteractor=new TransactionInteractorImpl(this);
    }

    @Override
    public void onTransaction(int event_tag, JSONObject jsonObject) {
        transactionView.showLoading(null);
        transactionsRequest request=new transactionsRequest();
        try {
            request.fromJson(jsonObject);
            transactionInteractor.getCommonListData(event_tag,request.toJson());
        }catch (JSONException j){

        }
    }

    @Override
    public void onSuccess(int event_tag, transactionsResponse data) {
        transactionView.hideLoading();
        if (event_tag== Constants.EVENT_LOAD_MORE_DATA){
            transactionView.addMoreListData(data);
        }else if (event_tag==Constants.EVENT_REFRESH_DATA){
            transactionView.refreshListData(data);
        }
    }

    @Override
    public void onError(String msg) {
        transactionView.hideLoading();
        transactionView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        transactionView.hideLoading();
        transactionView.showError(msg);
    }
}
