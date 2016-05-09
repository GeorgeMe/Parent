package com.dmd.zsb.protocol.response;

import com.activeandroid.DataBaseModel;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/4.
 */
@Table(name = "walletResponse")
public class walletResponse extends DataBaseModel{

    @Column(name = "errno")
    public int errno;
    @Column(name = "msg")
    public String msg;

    @Column(name = "balance")
    public String   balance;

    @Column(name = "total_hours")
    public String total_hours;

    @Column(name = "total_amount")
    public String   total_amount;


    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.errno = jsonObject.optInt("errno");
        this.msg = jsonObject.optString("msg");
        this.balance = jsonObject.optString("balance");
        this.total_hours = jsonObject.optString("total_hours");
        this.total_amount = jsonObject.optString("total_amount");


        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("errno", errno);
        localItemObject.put("msg", msg);
        localItemObject.put("balance", balance);
        localItemObject.put("total_hours", total_hours);
        localItemObject.put("total_amount", total_amount);

        return localItemObject;
    }
}
