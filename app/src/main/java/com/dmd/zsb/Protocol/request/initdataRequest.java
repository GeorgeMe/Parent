package com.dmd.zsb.protocol.request;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/4.
 */
public class initdataRequest extends SugarRecord {

    public String   appkey;
    public String version;
    public int  db_version;

    public initdataRequest() {
        super();
    }

    public initdataRequest(int db_version,String appkey, String version) {
        super();
        this.appkey = appkey;
        this.version = version;
        this.db_version = db_version;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.appkey = jsonObject.optString("appkey");
        this.version = jsonObject.optString("version");
        this.db_version = jsonObject.optInt("db_version");

        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("appkey", appkey);
        localItemObject.put("version", version);
        localItemObject.put("db_version", db_version);

        return localItemObject;
    }
}
