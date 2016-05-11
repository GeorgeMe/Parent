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
@Table(name = "confirmpayResponse")
public class confirmpayResponse extends DataBaseModel{
    @Column(name = "oid")
    public String oid;
    @Column(name = "order_sn")
    public String order_sn;
    @Column(name = "order_status")
    public String order_status;
    @Column(name = "offer_price")
    public String offer_price;

    @Column(name = "errno")
    public int errno;
    @Column(name = "msg")
    public String msg;

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.errno = jsonObject.optInt("errno");
        this.msg = jsonObject.optString("msg");

        this.oid = jsonObject.optString("oid");
        this.order_sn = jsonObject.optString("order_sn");
        this.order_status = jsonObject.optString("order_status");
        this.offer_price = jsonObject.optString("offer_price");

        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("errno", errno);
        localItemObject.put("msg", msg);

        localItemObject.put("oid", oid);
        localItemObject.put("order_sn", order_sn);
        localItemObject.put("order_status", order_status);
        localItemObject.put("offer_price", offer_price);

        return localItemObject;
    }
}
