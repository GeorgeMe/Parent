package com.dmd.zsb.protocol.table;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/9.
 */

public class VouchersBean extends SugarRecord implements Serializable {

    public String img_path;//图片地址
    public String note;//说明
    public String validity_period;//有效期
    public String state;

    public VouchersBean() {
        super();
    }

    public VouchersBean(String img_path, String note, String validity_period, String state) {
        super();
        this.img_path = img_path;
        this.note = note;
        this.validity_period = validity_period;
        this.state = state;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.img_path = jsonObject.optString("img_path");
        this.note = jsonObject.optString("note");
        this.validity_period = jsonObject.optString("validity_period");
        this.state = jsonObject.optString("state");


        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("img_path", img_path);
        localItemObject.put("note", note);
        localItemObject.put("validity_period", validity_period);
        localItemObject.put("state", state);

        return localItemObject;
    }
}
