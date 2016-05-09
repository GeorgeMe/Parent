package com.dmd.zsb.protocol.request;

import com.activeandroid.DataBaseModel;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/4.
 */
@Table(name = "demandRequest")
public class demandRequest extends DataBaseModel{

    @Column(name = "appkey")
    public String   appkey;

    @Column(name = "version")
    public String version;

    @Column(name = "sid")
    public String   sid;

    @Column(name = "uid")
    public String   uid;

    @Column(name = "page")
    public String   page;

    @Column(name = "rows")
    public String   rows;

    @Column(name = "group_menu")
    public String   group_menu;

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
