package com.dmd.zsb.mvp.view;

import com.dmd.zsb.entity.UserEntity;
import com.dmd.zsb.entity.VouchersEntity;
import com.dmd.zsb.entity.response.SeekResponse;
import com.dmd.zsb.entity.response.VouchersResponse;
import com.dmd.zsb.protocol.response.vouchersResponse;

/**
 * Created by Administrator on 2016/3/28.
 */
public interface VouchersView extends BaseView{

    void navigateToVouchersDetail(int position, VouchersEntity itemData);

    void refreshListData(vouchersResponse data);

    void addMoreListData(vouchersResponse data);
}
