package com.dmd.zsb.entity.response;

import com.dmd.zsb.entity.AdvertisementEntity;
import com.dmd.zsb.entity.SubjectEntity;
import com.dmd.zsb.entity.UserEntity;

import java.util.List;

/**
 * Created by Administrator on 2016/3/14.
 */
public class HomeResponse {
    private int total_page;
    private List<UserEntity> users;

    private List<SubjectEntity> subjects;

    private List<AdvertisementEntity> advertisements;

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public void setSubjects(List<SubjectEntity> subjects) {
        this.subjects = subjects;
    }

    public void setAdvertisements(List<AdvertisementEntity> advertisements) {
        this.advertisements = advertisements;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public List<SubjectEntity> getSubjects() {
        return subjects;
    }

    public List<AdvertisementEntity> getAdvertisements() {
        return advertisements;
    }

}
