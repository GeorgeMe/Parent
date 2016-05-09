package com.dmd.zsb.protocol.table;

import com.activeandroid.DataBaseModel;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/9.
 */
@Table(name = "Orders")
public class OrdersBean  extends DataBaseModel {
    @Column(name = "img_header")
    public String img_header;//头像
    @Column(name = "name")
    public String name;//姓名
    @Column(name = "type")
    public String type;//职业类型
    @Column(name = "sex")
    public String sex;//性别
    @Column(name = "appointed_time")
    public String appointed_time;//约定时间
    @Column(name = "charging")
    public String charging;//计费
    @Column(name = "curriculum")
    public String curriculum;//课程
    @Column(name = "address")
    public String address;//地址
    @Column(name = "place")
    public String place;//地点
    @Column(name = "state")
    public String state;//状态

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.img_header = jsonObject.optString("img_header");
        this.name = jsonObject.optString("name");
        this.type = jsonObject.optString("type");
        this.sex = jsonObject.optString("sex");
        this.appointed_time = jsonObject.optString("appointed_time");
        this.charging = jsonObject.optString("charging");
        this.curriculum = jsonObject.optString("curriculum");
        this.address = jsonObject.optString("address");
        this.place = jsonObject.optString("place");
        this.state = jsonObject.optString("state");


        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("img_header", img_header);
        localItemObject.put("name", name);
        localItemObject.put("type", type);
        localItemObject.put("sex", sex);
        localItemObject.put("appointed_time", appointed_time);
        localItemObject.put("charging", charging);
        localItemObject.put("curriculum", curriculum);
        localItemObject.put("address", address);
        localItemObject.put("place", place);
        localItemObject.put("state", state);

        return localItemObject;
    }

}
