package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.impl.BankCardInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.BankCardPresenter;
import com.dmd.zsb.mvp.view.BankCardView;
import com.dmd.zsb.protocol.request.bankcardRequest;
import com.dmd.zsb.protocol.response.bankcardResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/6/13.
 */
public class BankCardPresenterImpl implements BankCardPresenter,BaseSingleLoadedListener<bankcardResponse> {
    private BankCardView bankCardView;
    private Context mContext;
    private BankCardInteractorImpl bankCardInteractor;

    public BankCardPresenterImpl(BankCardView bankCardView, Context mContext) {
        this.bankCardView = bankCardView;
        this.mContext = mContext;
        bankCardInteractor=new BankCardInteractorImpl(this);
    }

    @Override
    public void onChangeBankCard(JSONObject jsonObject) {
        bankCardView.showLoading(null);
        try {
            bankcardRequest request=new bankcardRequest();
            request.fromJson(jsonObject);
            bankCardInteractor.getCommonSingleData(request.toJson());
        }catch (JSONException j){

        }

    }

    @Override
    public void onSuccess(bankcardResponse data) {
        bankCardView.hideLoading();
        bankCardView.onBankCardView(data);
    }

    @Override
    public void onError(String msg) {
        bankCardView.hideLoading();
        bankCardView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
