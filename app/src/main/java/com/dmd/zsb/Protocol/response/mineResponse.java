package com.dmd.zsb.protocol.response;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class mineResponse extends SugarRecord implements Serializable
{
     public String   mineHeaderImg;
     public String   mineName;
     public String   mineAddress;
     public String   mineGrade;
     public String   mineSubjects;
     public String   uid;
     public int succeed;
     public String msg;

     public mineResponse() {
          super();
     }

     public mineResponse(String mineHeaderImg, String mineName, String mineAddress, String mineGrade, String mineSubjects, String uid, int succeed, String msg) {
          super();
          this.mineHeaderImg = mineHeaderImg;
          this.mineName = mineName;
          this.mineAddress = mineAddress;
          this.mineGrade = mineGrade;
          this.mineSubjects = mineSubjects;
          this.uid = uid;
          this.succeed = succeed;
          this.msg = msg;
     }

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          this.mineHeaderImg = jsonObject.optString("mineHeaderImg");
          this.mineName = jsonObject.optString("mineName");
          this.mineAddress = jsonObject.optString("mineAddress");
          this.mineGrade = jsonObject.optString("mineGrade");
          this.mineSubjects = jsonObject.optString("mineSubjects");
          this.succeed = jsonObject.optInt("succeed");
          this.msg = jsonObject.optString("msg");

          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("mineHeaderImg", mineHeaderImg);
          localItemObject.put("mineName", mineName);
          localItemObject.put("mineAddress", mineAddress);
          localItemObject.put("mineGrade", mineGrade);
          localItemObject.put("mineSubjects", mineSubjects);
          localItemObject.put("succeed", succeed);
          localItemObject.put("msg", msg);
          return localItemObject;
     }

}
