package com.dmd.zsb.mvp.view;

import com.dmd.zsb.entity.DemandEntity;
import com.dmd.zsb.entity.response.DemandResponse;

/**
 * Created by Administrator on 2016/3/29.
 */
public interface DemandView extends BaseView{
    void navigateToDemandDetail(int position, DemandEntity itemData);

    void refreshListData(DemandResponse data);

    void addMoreListData(DemandResponse data);
}
