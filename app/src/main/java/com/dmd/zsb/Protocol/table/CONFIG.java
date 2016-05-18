package com.dmd.zsb.protocol.table;

import com.activeandroid.DataBaseModel;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

@Table(name = "CONFIG")
public class CONFIG  extends Model implements Serializable
{

     @Column(name = "push")
     public int push;

     public CONFIG() {
          super();
     }

     public CONFIG(int push) {
          super();
          this.push = push;
     }

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }
          JSONArray subItemArray;

          this.push = jsonObject.optInt("push");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("push", push);
          return localItemObject;
     }

}
