package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.commentsendResponse;

/**
 * Created by Administrator on 2016/6/16.
 */
public interface CommentSendView extends BaseView {
    void onCommentSendView(commentsendResponse response);
}
