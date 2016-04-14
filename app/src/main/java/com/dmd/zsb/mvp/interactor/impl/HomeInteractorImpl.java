package com.dmd.zsb.mvp.interactor.impl;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;

import com.dmd.zsb.entity.response.HomeResponse;
import com.dmd.zsb.mvp.listeners.CommonListInteractor;
import com.dmd.zsb.mvp.listeners.BaseMultiLoadedListener;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2015/12/15.
 */
public class HomeInteractorImpl implements CommonListInteractor {

    private BaseMultiLoadedListener<HomeResponse> loadedListener = null;
    public HomeInteractorImpl(BaseMultiLoadedListener<HomeResponse> loadedListener){
        this.loadedListener=loadedListener;
    }
    @Override
    public void getCommonListData(final int event,final JsonObject data) {
        GsonRequest<HomeResponse> gsonRequest = new GsonRequest<HomeResponse>(
                UriHelper.getInstance().home(data),
                null,
                new TypeToken<HomeResponse>() {
                }.getType(),
                new Response.Listener<HomeResponse>() {
                    @Override
                    public void onResponse(HomeResponse response) {
                        loadedListener.onSuccess(event, response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NetworkError){
                            loadedListener.onError("请求时出现网络错误。");
                        }else if (error instanceof ServerError){
                            loadedListener.onError("服务器内部错误，请稍后重试");
                        }else if (error instanceof NoConnectionError){
                            loadedListener.onError("连接的错误。");
                        }else if (error instanceof TimeoutError){
                            loadedListener.onError("连接超时");
                        }else if (error instanceof ParseError){
                            loadedListener.onError("服务器的响应不能被解析。");
                        }else if (error instanceof AuthFailureError){
                            loadedListener.onError("指示在执行请求时有一个身份验证失败的错误。");
                        }else {
                            loadedListener.onError(error.getMessage());
                        }
                    }
                }
        );

        gsonRequest.setShouldCache(true);
        gsonRequest.setTag("home");

        VolleyHelper.getInstance().getRequestQueue().add(gsonRequest);
    }
}
