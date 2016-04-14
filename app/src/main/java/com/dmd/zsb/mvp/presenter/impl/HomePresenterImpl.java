package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.parent.R;
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.entity.UserEntity;
import com.dmd.zsb.entity.response.HomeResponse;
import com.dmd.zsb.mvp.interactor.impl.HomeInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.presenter.HomePresenter;
import com.dmd.zsb.mvp.view.HomeView;
import com.google.gson.JsonObject;


/**
 * Created by Administrator on 2015/12/15.
 */
public class HomePresenterImpl implements HomePresenter,BaseMultiLoadedListener<HomeResponse> {
//keytool -v -list -keystore keystore.jks
    private Context mContext=null;
    private HomeView mHomeView=null;
    private HomeInteractorImpl mCommonListInteractor = null;

    public HomePresenterImpl(Context mContext,HomeView mHomeView){
        this.mContext=mContext;
        this.mHomeView=mHomeView;
        mCommonListInteractor=new HomeInteractorImpl(this);
    }
    @Override
    public void loadListData(JsonObject data) {
        mHomeView.hideLoading();
        if (!data.get("isSwipeRefresh").getAsBoolean()) {
            mHomeView.showLoading(mContext.getString(R.string.common_loading_message));
        }
        //提交的参数封装
        JsonObject jsonObject=new JsonObject();
        String uid=XmlDB.getInstance(mContext).getKeyString("uid","uid");
        String sid=XmlDB.getInstance(mContext).getKeyString("sid","sid");
        jsonObject.addProperty("uid",uid);
        jsonObject.addProperty("sid",sid);
        jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
        jsonObject.addProperty("page",data.get("page").getAsString());//页码
        jsonObject.addProperty("subid",data.get("subid").getAsString());//科目id
        mCommonListInteractor.getCommonListData(data.get("event").getAsInt(),jsonObject);
    }
    @Override
    public void onSuccess(int event_tag, HomeResponse data) {
        mHomeView.hideLoading();
        if (event_tag == Constants.EVENT_REFRESH_DATA) {
            mHomeView.refreshListData(data);
        } else if (event_tag == Constants.EVENT_LOAD_MORE_DATA) {
            mHomeView.addMoreListData(data);
        }
    }

    @Override
    public void onError(String msg) {
        mHomeView.hideLoading();
        mHomeView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        mHomeView.hideLoading();
        mHomeView.showError(msg);
    }

}
