package com.dmd.zsb.mvp.interactor.impl;

import com.android.volley.AuthFailureError;
import com.android.volley.FormFile;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;
import com.android.volley.toolbox.PostUploadRequest;
import com.dmd.tutor.utils.OnUploadProcessListener;
import com.dmd.tutor.utils.TLog;
import com.dmd.zsb.mvp.listeners.CommonListInteractor;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/25.
 */
public class SettingInteracterImpl implements CommonListInteractor {
    private BaseMultiLoadedListener<JSONObject> loadedListener;
    private OnUploadProcessListener uploadProcessListener;

    public SettingInteracterImpl(BaseMultiLoadedListener<JSONObject> loadedListener, OnUploadProcessListener uploadProcessListener) {
        this.loadedListener = loadedListener;
        this.uploadProcessListener = uploadProcessListener;
    }

    @Override
    public void getCommonListData(final int event, JSONObject json) {
        //event  事件标记   changeAvatar修改头像  signOut退出登录

    }

    public void onSignOut(final int event,JSONObject json) {
        GsonRequest<JSONObject> gsonRequest=new GsonRequest<JSONObject>(UriHelper.getInstance().signOut(json),null,new TypeToken<JSONObject>(){}.getType(), new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                loadedListener.onSuccess(event,response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                loadedListener.onError(error.getMessage());
            }
        });
        gsonRequest.setShouldCache(true);
        gsonRequest.setTag("signOut");
        VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
    }
}
