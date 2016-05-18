package com.dmd.zsb.protocol.response;

import com.activeandroid.DataBaseModel;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

@Table(name = "mineResponse")
public class mineResponse extends Model implements Serializable
{

     @Column(name = "mineHeaderImg")
     public String   mineHeaderImg;

     @Column(name = "mineName")
     public String   mineName;
     @Column(name = "mineAddress")
     public String   mineAddress;
     @Column(name = "mineGrade")
     public String   mineGrade;
     @Column(name = "mineSubjects")
     public String   mineSubjects;
     @Column(name = "uid")
     public String   uid;

     @Column(name = "succeed")
     public int succeed;

     @Column(name = "msg")
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
