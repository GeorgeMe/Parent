package com.dmd.zsb.mvp.view;

import com.dmd.zsb.entity.OrderEntity;
import com.dmd.zsb.entity.response.OrderResponse;
import com.dmd.zsb.protocol.response.orderResponse;

/**
 * Created by Administrator on 2016/3/29.
 */
public interface OrderView extends BaseView{

    void navigateToOrderDetail(OrderEntity data);

    void refreshListData(orderResponse response);

    void addMoreListData(orderResponse response);
}
