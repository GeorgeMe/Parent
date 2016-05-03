package com.dmd.zsb.protocol.response;

import com.activeandroid.DataBaseModel;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.dmd.zsb.protocol.table.CONFIG;
import com.dmd.zsb.protocol.table.USER;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "usersigninResponse")
public class usersigninResponse  extends DataBaseModel
{

     @Column(name = "sid")
     public String   sid;

     @Column(name = "succeed")
     public int succeed;

     @Column(name = "config")
     public CONFIG config;

     @Column(name = "error_code")
     public int error_code;

     @Column(name = "user")
     public USER user;

     @Column(name = "error_desc")
     public String   error_desc;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          this.sid = jsonObject.optString("sid");

          this.succeed = jsonObject.optInt("succeed");
          CONFIG  config = new CONFIG();
          config.fromJson(jsonObject.optJSONObject("config"));
          this.config = config;

          this.error_code = jsonObject.optInt("error_code");
          USER  user = new USER();
          user.fromJson(jsonObject.optJSONObject("user"));
          this.user = user;

          this.error_desc = jsonObject.optString("error_desc");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("sid", sid);
          localItemObject.put("succeed", succeed);
          if(null != config)
          {
            localItemObject.put("config", config.toJson());
          }
          localItemObject.put("error_code", error_code);
          if(null != user)
          {
            localItemObject.put("user", user.toJson());
          }
          localItemObject.put("error_desc", error_desc);
          return localItemObject;
     }

}
