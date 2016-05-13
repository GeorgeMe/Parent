package com.dmd.zsb.protocol.table;

import com.activeandroid.DataBaseModel;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/13.
 */
@Table(name = "WithDrawal")
public class WithDrawalBean  extends DataBaseModel {

    @Column(name = "id")
    public String id;//图片地址
    @Column(name = "created_at")
    public String created_at;//图片地址
    @Column(name = "amount")
    public String amount;//说明
    @Column(name = "category")
    public String category;//有效期
    @Column(name = "user")
    public String user;

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.id = jsonObject.optString("id");
        this.created_at = jsonObject.optString("created_at");
        this.amount = jsonObject.optString("amount");
        this.category = jsonObject.optString("category");
        this.user = jsonObject.optString("user");


        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("id", id);
        localItemObject.put("created_at", created_at);
        localItemObject.put("amount", amount);
        localItemObject.put("category", category);
        localItemObject.put("user", user);

        return localItemObject;
    }
}
