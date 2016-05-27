package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.demandResponse;
import com.dmd.zsb.protocol.table.DemandsBean;

/**
 * Created by Administrator on 2016/3/29.
 */
public interface DemandView extends BaseView{

    void navigateToDemandDetail(int position, DemandsBean itemData);

    void refreshListData(demandResponse response);

    void addMoreListData(demandResponse response);
}
