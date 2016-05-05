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
import com.android.volley.toolbox.PostUploadRequest;
import com.dmd.tutor.utils.OnUploadProcessListener;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.listeners.CommonSingleInteractor;
import com.dmd.zsb.protocol.response.changeavatarResponse;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.utils.VolleyHelper;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/5.
 */
public class ChangeAvatarInteractorImpl implements CommonSingleInteractor {

    private BaseSingleLoadedListener<changeavatarResponse> loadedListener;
    private OnUploadProcessListener uploadProcessListener;
    public ChangeAvatarInteractorImpl(BaseSingleLoadedListener<changeavatarResponse> loadedListener, OnUploadProcessListener uploadProcessListener) {
        this.loadedListener = loadedListener;
        this.uploadProcessListener = uploadProcessListener;
    }

    @Override
    public void getCommonSingleData(JSONObject json) {
        List<FormFile> fileList=new ArrayList<>();
        JSONObject file=json.optJSONObject("formFile");
        JSONObject params=json.optJSONObject("json");
        FormFile formFile=new FormFile(file.optString("fileName"), new File(file.optString("filePath")), file.optString("parameterName"), file.optString("contentType"));
        fileList.add(formFile);
        PostUploadRequest<changeavatarResponse> uploadRequest=new PostUploadRequest<changeavatarResponse>(UriHelper.getInstance().changeAvatar(params), fileList, new TypeToken<changeavatarResponse>() {
        }.getType(), new Response.Listener<changeavatarResponse>() {
            @Override
            public void onResponse(changeavatarResponse response) {
                loadedListener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
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
        });
        uploadRequest.setOnUploadProcessListener(uploadProcessListener);
        uploadRequest.setShouldCache(true);
        uploadRequest.setTag("changeAvatar");
        VolleyHelper.getInstance().getRequestQueue().add(uploadRequest);
    }
}