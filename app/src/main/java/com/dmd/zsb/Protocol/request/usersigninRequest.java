package com.dmd.zsb.protocol.request;

import com.activeandroid.DataBaseModel;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.dmd.zsb.protocol.table.LOCATION;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "usersigninRequest")
public class usersigninRequest  extends DataBaseModel
{

     @Column(name = "platform")
     public String   platform;

     @Column(name = "mobile")
     public String   mobile;

     @Column(name = "location")
     public LOCATION location;

     @Column(name = "UUID")
     public String   UUID;

     @Column(name = "ver")
     public int ver;

     @Column(name = "password")
     public String   password;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          this.platform = jsonObject.optString("platform");

          this.mobile = jsonObject.optString("mobile");
          LOCATION  location = new LOCATION();
          location.fromJson(jsonObject.optJSONObject("location"));
          this.location = location;

          this.UUID = jsonObject.optString("UUID");

          this.ver = jsonObject.optInt("ver");

          this.password = jsonObject.optString("password");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("platform", platform);
          localItemObject.put("mobile_phone", mobile);
          if(null != location)
          {
            localItemObject.put("location", location.toJson());
          }
          localItemObject.put("UUID", UUID);
          localItemObject.put("ver", ver);
          localItemObject.put("password", password);
          return localItemObject;
     }

}
