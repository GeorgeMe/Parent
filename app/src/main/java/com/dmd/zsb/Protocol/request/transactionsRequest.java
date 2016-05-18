package com.dmd.zsb.protocol.request;

import com.activeandroid.DataBaseModel;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/4.
 */
@Table(name = "transactionsRequest")
public class transactionsRequest extends Model implements Serializable {
    @Column(name = "appkey")
    public String   appkey;

    @Column(name = "version")
    public String version;

    @Column(name = "sid")
    public String   sid;

    @Column(name = "uid")
    public String   uid;

    @Column(name = "buyer_id")
    public String   buyer_id;

    @Column(name = "page")
    public int   page;

    @Column(name = "rows")
    public int   rows;

    public transactionsRequest() {
        super();
    }

    public transactionsRequest(String appkey, String version, String sid, String uid, String buyer_id, int page, int rows) {
        super();
        this.appkey = appkey;
        this.version = version;
        this.sid = sid;
        this.uid = uid;
        this.buyer_id = buyer_id;
        this.page = page;
        this.rows = rows;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.appkey = jsonObject.optString("appkey");
        this.version = jsonObject.optString("version");
        this.sid = jsonObject.optString("sid");
        this.uid = jsonObject.optString("uid");
        this.buyer_id = jsonObject.optString("buyer_id");
        this.page = jsonObject.optInt("page");
        this.rows = jsonObject.optInt("rows");


        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("appkey", appkey);
        localItemObject.put("version", version);
        localItemObject.put("sid", sid);
        localItemObject.put("uid", uid);
        localItemObject.put("buyer_id", buyer_id);
        localItemObject.put("page", page);
        localItemObject.put("rows", rows);

        return localItemObject;
    }
}
