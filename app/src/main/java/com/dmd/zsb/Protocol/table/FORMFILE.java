package com.dmd.zsb.protocol.table;

import com.activeandroid.DataBaseModel;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/5.
 */
@Table(name = "FORMFILE")
public class FORMFILE  extends DataBaseModel {

    @Column(name = "fileName")
    public String fileName;
    @Column(name = "filePath")
    public String filePath;
    @Column(name = "parameterName")
    public String parameterName;
    @Column(name = "contentType")
    public String contentType;
    public void  fromJson(JSONObject jsonObject)  throws JSONException{
        if(null == jsonObject){
            return ;
        }
        JSONArray subItemArray;

        this.fileName = jsonObject.optString("fileName");
        this.filePath = jsonObject.optString("filePath");
        this.parameterName = jsonObject.optString("parameterName");
        this.contentType = jsonObject.optString("contentType");
        return ;
    }


    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("fileName", fileName);
        localItemObject.put("filePath", filePath);
        localItemObject.put("parameterName", parameterName);
        localItemObject.put("contentType", contentType);
        return localItemObject;
    }
}
