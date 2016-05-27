package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.interactor.impl.SeekInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.mvp.presenter.SeekPresenter;
import com.dmd.zsb.mvp.view.SeekView;
import com.dmd.zsb.protocol.request.seekRequest;
import com.dmd.zsb.protocol.response.seekResponse;
import com.dmd.zsb.utils.UriHelper;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 2015/12/22.
 */
public class SeekPresenterIml implements SeekPresenter,BaseMultiLoadedListener<seekResponse> {
    private Context mContext=null;
    private SeekView mSeekView=null;
    private SeekInteractorImpl mCommonListInteractor = null;

    public SeekPresenterIml(Context mContext, SeekView mSeekView) {
        this.mContext = mContext;
        this.mSeekView = mSeekView;
        mCommonListInteractor = new SeekInteractorImpl(this);
    }

    @Override
    public void loadListData(int event,JSONObject jsonObject) {
        mSeekView.showLoading(null);
        seekRequest request=new seekRequest();
        try {
            //request.fromJson(jsonObject);
            request.appkey=Constants.ZSBAPPKEY;
            request.version=Constants.ZSBVERSION;
            request.uid= XmlDB.getInstance(mContext).getKeyString("uid","uid");
            request.sid=XmlDB.getInstance(mContext).getKeyString("sid","sid");
            request.page=jsonObject.optInt("page");
            request.sort=jsonObject.optInt("sort");
            request.rows= UriHelper.PAGE_LIMIT;
            request.subid=jsonObject.optString("subid");
            mCommonListInteractor.getCommonListData(event,request.toJson());
        }catch (JSONException j){

        }
    }


    @Override
    public void onSuccess(int event_tag, seekResponse response) {
        mSeekView.hideLoading();
        if (event_tag == Constants.EVENT_REFRESH_DATA) {
            mSeekView.refreshListData(response);
        } else if (event_tag == Constants.EVENT_LOAD_MORE_DATA) {
            mSeekView.addMoreListData(response);
        }
    }

    @Override
    public void onError(String msg) {
        mSeekView.hideLoading();
        mSeekView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
