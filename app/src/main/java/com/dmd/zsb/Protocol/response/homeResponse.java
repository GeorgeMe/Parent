package com.dmd.zsb.protocol.response;

import com.activeandroid.DataBaseModel;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.dmd.zsb.protocol.table.AdvertisementsBean;
import com.dmd.zsb.protocol.table.SubjectsBean;
import com.dmd.zsb.protocol.table.UsersBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
@Table(name = "homeResponse")
public class homeResponse extends DataBaseModel{

    @Column(name = "errno")
    public int errno;
    @Column(name = "msg")
    public String msg;
    @Column(name = "total_count")
    public int total_count;

    @Column(name = "users")
    public List<UsersBean> users;
    @Column(name = "subjects")
    public List<SubjectsBean> subjects;
    @Column(name = "advertisements")
    public List<AdvertisementsBean> advertisements;

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;
        JSONArray subItemArray2;
        JSONArray subItemArray3;

        this.errno = jsonObject.optInt("errno");
        this.total_count = jsonObject.optInt("total_count");
        this.msg = jsonObject.optString("msg");
        subItemArray=jsonObject.optJSONArray("users");
        if(null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                UsersBean subItem = new UsersBean();
                subItem.fromJson(subItemObject);
                this.users.add(subItem);
            }
        }
        subItemArray2=jsonObject.optJSONArray("subjects");
        if(null != subItemArray) {
            for (int i = 0; i < subItemArray2.length(); i++) {
                JSONObject subItemObject = subItemArray2.getJSONObject(i);
                SubjectsBean subItem = new SubjectsBean();
                subItem.fromJson(subItemObject);
                this.subjects.add(subItem);
            }
        }
        subItemArray3=jsonObject.optJSONArray("advertisements");
        if(null != subItemArray) {
            for (int i = 0; i < subItemArray3.length(); i++) {
                JSONObject subItemObject = subItemArray3.getJSONObject(i);
                AdvertisementsBean subItem = new AdvertisementsBean();
                subItem.fromJson(subItemObject);
                this.advertisements.add(subItem);
            }
        }
        return ;
    }


    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        JSONArray itemJSONArray2 = new JSONArray();
        JSONArray itemJSONArray3 = new JSONArray();

        localItemObject.put("errno", errno);
        localItemObject.put("total_count", total_count);
        localItemObject.put("msg", msg);

        for(int i =0; i< users.size(); i++)
        {
            UsersBean itemData =users.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("users", itemJSONArray);

        for(int i =0; i< subjects.size(); i++)
        {
            SubjectsBean itemData =subjects.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray2.put(itemJSONObject);
        }
        localItemObject.put("subjects", itemJSONArray2);

        for(int i =0; i< advertisements.size(); i++)
        {
            AdvertisementsBean itemData =advertisements.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray3.put(itemJSONObject);
        }
        localItemObject.put("advertisements", itemJSONArray3);
        return localItemObject;
    }
}
