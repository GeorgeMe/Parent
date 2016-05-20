package com.dmd.zsb.protocol.table;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/9.
 */
public class OrdersBean  extends SugarRecord implements Serializable {

    public String img_header;//头像
    public String name;//姓名
    public String type;//职业类型
    public String sex;//性别
    public String appointed_time;//约定时间
    public String charging;//计费
    public String curriculum;//课程
    public String address;//地址
    public String place;//地点
    public String state;//状态

    public OrdersBean() {
        super();
    }

    public OrdersBean(String img_header, String name, String type, String sex, String appointed_time, String charging, String curriculum, String address, String place, String state) {
        super();
        this.img_header = img_header;
        this.name = name;
        this.type = type;
        this.sex = sex;
        this.appointed_time = appointed_time;
        this.charging = charging;
        this.curriculum = curriculum;
        this.address = address;
        this.place = place;
        this.state = state;
    }

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
