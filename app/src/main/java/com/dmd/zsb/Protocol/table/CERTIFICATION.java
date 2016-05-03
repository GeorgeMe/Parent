package com.dmd.zsb.protocol.table;

import com.activeandroid.DataBaseModel;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "CERTIFICATION")
public class CERTIFICATION  extends DataBaseModel
{

     @Column(name = "CERTIFICATION_id")
     public int id;

     @Column(name = "name")
     public String   name;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          this.id = jsonObject.optInt("id");

          this.name = jsonObject.optString("name");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("id", id);
          localItemObject.put("name", name);
          return localItemObject;
     }

}
