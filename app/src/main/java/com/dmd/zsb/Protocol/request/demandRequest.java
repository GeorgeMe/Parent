package com.dmd.zsb.protocol.request;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/4.
 */
public class demandRequest extends SugarRecord implements Serializable {

    public String   appkey;
    public String version;
    public String   sid;
    public String   uid;
    public String   page;
    public String   rows;
    public String   group_menu;

    public demandRequest() {
        super();
    }

    public demandRequest(String appkey, String version, String sid, String uid, String page, String rows, String group_menu) {
        this.appkey = appkey;
        this.version = version;
        this.sid = sid;
        this.uid = uid;
        this.page = page;
        this.rows = rows;
        this.group_menu = group_menu;
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
        this.page = jsonObject.optString("page");
        this.rows = jsonObject.optString("rows");
        this.group_menu = jsonObject.optString("group_menu");


        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("appkey", appkey);
        localItemObject.put("version", version);
        localItemObject.put("sid", sid);
        localItemObject.put("uid", uid);
        localItemObject.put("page", page);
        localItemObject.put("rows", rows);
        localItemObject.put("group_menu", group_menu);

        return localItemObject;
    }
}
