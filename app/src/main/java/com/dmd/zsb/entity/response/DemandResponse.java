package com.dmd.zsb.entity.response;

import com.dmd.zsb.entity.DemandEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/3/29.
 */
public class DemandResponse {
    private int total_page;
    private List<DemandEntity> demandEntities;

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public List<DemandEntity> getDemandEntities() {
        return demandEntities;
    }

    public void setDemandEntities(List<DemandEntity> demandEntities) {
        this.demandEntities = demandEntities;
    }
}
