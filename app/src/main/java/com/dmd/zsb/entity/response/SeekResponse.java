package com.dmd.zsb.entity.response;

import com.dmd.zsb.entity.UserEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/3/16.
 */
public class SeekResponse {
    private List<UserEntity> users;
    private int total_page;
    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }
}
