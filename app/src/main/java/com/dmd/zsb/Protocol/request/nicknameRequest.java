package com.dmd.zsb.protocol.request;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/4.
 */
public class nicknameRequest extends SugarRecord implements Serializable {
    public String   appkey;
    public String version;
    public String   sid;
    public String   uid;
    public String   nickname;

    public nicknameRequest() {
        super();
    }

    public nicknameRequest(String appkey, String version, String sid, String uid, String nickname) {
        super();
        this.appkey = appkey;
        this.version = version;
        this.sid = sid;
        this.uid = uid;
        this.nickname = nickname;
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
        this.nickname = jsonObject.optString("nickname");


        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("appkey", appkey);
        localItemObject.put("version", version);
        localItemObject.put("sid", sid);
        localItemObject.put("uid", uid);
        localItemObject.put("nickname", nickname);

        return localItemObject;
    }
}
