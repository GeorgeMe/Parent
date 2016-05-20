package com.dmd.zsb.protocol.table;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/9.
 */
public class GradesBean extends SugarRecord implements Serializable {

    public String grade_id;
    public String grade_name;

    public GradesBean() {
        super();
    }

    public GradesBean(String grade_id, String grade_name) {
        super();
        this.grade_id = grade_id;
        this.grade_name = grade_name;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;

        this.grade_id = jsonObject.optString("grade_id");
        this.grade_name = jsonObject.optString("grade_name");
        return ;
    }
    public JSONObject toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("grade_id", grade_id);
        localItemObject.put("grade_name", grade_name);
        return localItemObject;
    }
}
