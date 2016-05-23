package com.dmd.zsb.protocol.table;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/23.
 */
public class MyServices extends SugarRecord implements Serializable {
    public String user_id;
    public String service_id;
    public String service_img;
    public String service_name;
    public String service_price;

    public MyServices() {
    }

    public MyServices(String user_id, String service_id, String service_img, String service_name, String service_price) {
        this.user_id = user_id;
        this.service_id = service_id;
        this.service_img = service_img;
        this.service_name = service_name;
        this.service_price = service_price;
    }
    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;
        this.user_id = jsonObject.optString("user_id");
        this.service_id = jsonObject.optString("service_id");
        this.service_img = jsonObject.optString("service_img");
        this.service_name = jsonObject.optString("service_name");
        this.service_price = jsonObject.optString("service_price");

    }

    public JSONObject  toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("user_id", user_id);
        localItemObject.put("service_id", service_id);
        localItemObject.put("service_img", service_img);
        localItemObject.put("service_name", service_name);
        localItemObject.put("service_price", service_price);

        return localItemObject;
    }
}
