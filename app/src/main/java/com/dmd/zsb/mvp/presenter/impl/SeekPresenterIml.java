package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.parent.R;
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.entity.UserEntity;
import com.dmd.zsb.entity.response.SeekResponse;
import com.dmd.zsb.mvp.interactor.impl.SeekInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.presenter.SeekPresenter;
import com.dmd.zsb.mvp.view.SeekView;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 2015/12/22.
 */
public class SeekPresenterIml implements SeekPresenter,BaseMultiLoadedListener<SeekResponse> {
    private Context mContext=null;
    private SeekView mSeekView=null;
    private SeekInteractorImpl mCommonListInteractor = null;

    public SeekPresenterIml(Context mContext, SeekView mSeekView) {
        this.mContext = mContext;
        this.mSeekView = mSeekView;
        mCommonListInteractor = new SeekInteractorImpl(this);
    }

    @Override
    public void loadListData(int event,JSONObject data) {
        mSeekView.hideLoading();
        if (event==Constants.EVENT_REFRESH_DATA) {
            mSeekView.showLoading(mContext.getString(R.string.common_loading_message));
        }else if (event==Constants.EVENT_LOAD_MORE_DATA) {
            mSeekView.showLoading(mContext.getString(R.string.common_loading_message));
        }
        //提交的参数封装
        JSONObject jsonObject=new JSONObject();
        String uid= XmlDB.getInstance(mContext).getKeyString("uid","uid");
        String sid=XmlDB.getInstance(mContext).getKeyString("sid","sid");
        try {
            jsonObject.put("appkey", Constants.ZSBAPPKEY);
            jsonObject.put("version", Constants.ZSBVERSION);
            jsonObject.put("uid",uid);
            jsonObject.put("sid",sid);
            jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);//每页条数
            jsonObject.put("page",data.optString("page"));//页码
            jsonObject.put("subid",data.optString("subid"));//科目id
        }catch (JSONException j){

        }


        mCommonListInteractor.getCommonListData(event,jsonObject);
    }


    @Override
    public void onSuccess(int event_tag, SeekResponse data) {
        mSeekView.hideLoading();
        if (event_tag == Constants.EVENT_REFRESH_DATA) {
            mSeekView.refreshListData(data);
        } else if (event_tag == Constants.EVENT_LOAD_MORE_DATA) {
            mSeekView.addMoreListData(data);
        }
    }

    @Override
    public void onError(String msg) {
        mSeekView.hideLoading();
        mSeekView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        mSeekView.hideLoading();
        mSeekView.showException(msg);
    }
}
