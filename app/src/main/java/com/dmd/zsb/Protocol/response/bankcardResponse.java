package com.dmd.zsb.protocol.response;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/4.
 */
public class bankcardResponse extends SugarRecord implements Serializable {
    public int errno;
    public String msg;
    public String   bank_card;
    public bankcardResponse() {
        super();
    }

    public bankcardResponse(int errno, String msg,String bank_card) {
        super();
        this.errno = errno;
        this.msg = msg;
        this.bank_card = bank_card;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.errno = jsonObject.optInt("errno");
        this.msg = jsonObject.optString("msg");
        this.bank_card = jsonObject.optString("bank_card");


        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("errno", errno);
        localItemObject.put("msg", msg);
        localItemObject.put("bank_card", bank_card);

        return localItemObject;
    }
}
