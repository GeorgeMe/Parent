package com.dmd.zsb.protocol.table;

import com.activeandroid.DataBaseModel;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/9.
 */
@Table(name = "Vouchers")
public class VouchersBean extends Model implements Serializable {
    @Column(name = "img_path")
    public String img_path;//图片地址
    @Column(name = "note")
    public String note;//说明
    @Column(name = "validity_period")
    public String validity_period;//有效期
    @Column(name = "state")
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
