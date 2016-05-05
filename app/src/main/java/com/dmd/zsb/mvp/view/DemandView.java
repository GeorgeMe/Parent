package com.dmd.zsb.mvp.view;

import com.dmd.zsb.entity.DemandEntity;
import com.dmd.zsb.entity.response.DemandResponse;
import com.dmd.zsb.protocol.response.demandResponse;

/**
 * Created by Administrator on 2016/3/29.
 */
public interface DemandView extends BaseView{

    void navigateToDemandDetail(int position, DemandEntity itemData);

    void refreshListData(demandResponse response);

    void addMoreListData(demandResponse response);
}
