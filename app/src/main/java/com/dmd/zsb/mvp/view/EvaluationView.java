package com.dmd.zsb.mvp.view;

import com.dmd.zsb.entity.response.EvaluationResponse;
import com.dmd.zsb.entity.EvaluationEntity;
import com.dmd.zsb.protocol.response.evaluationResponse;

/**
 * Created by Administrator on 2016/3/28.
 */
public interface EvaluationView extends BaseView {

    void navigateToEvaluationDetail(EvaluationEntity data);

    void refreshListData(evaluationResponse response);

    void addMoreListData(evaluationResponse response);
}
