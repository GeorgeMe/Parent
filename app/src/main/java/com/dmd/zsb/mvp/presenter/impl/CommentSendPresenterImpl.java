package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;

import com.dmd.zsb.mvp.interactor.CommentSendInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.CommentSendPresenter;
import com.dmd.zsb.mvp.view.CommentSendView;
import com.dmd.zsb.protocol.request.commentsendRequest;
import com.dmd.zsb.protocol.response.commentsendResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/6/16.
 */
public class CommentSendPresenterImpl implements CommentSendPresenter,BaseSingleLoadedListener<commentsendResponse> {
    private Context mContext;
    private CommentSendView commentSendView;
    private CommentSendInteractorImpl commentSendInteractor;

    public CommentSendPresenterImpl(Context mContext, CommentSendView commentSendView) {
        this.mContext = mContext;
        this.commentSendView = commentSendView;
        commentSendInteractor=new CommentSendInteractorImpl(this);
    }

    @Override
    public void onSuccess(commentsendResponse data) {
        commentSendView.hideLoading();
        commentSendView.onCommentSendView(data);
    }

    @Override
    public void onError(String msg) {
        commentSendView.hideLoading();
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }

    @Override
    public void onCommentSend(JSONObject jsonObject) {
        commentSendView.showLoading(null);
        try {
            commentsendRequest request=new commentsendRequest();
            request.fromJson(jsonObject);
            commentSendInteractor.getCommonSingleData(request.toJson());
        }catch (JSONException j){
            commentSendView.hideLoading();
        }
    }
}
