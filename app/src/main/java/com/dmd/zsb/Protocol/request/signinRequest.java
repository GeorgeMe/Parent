package com.dmd.zsb.protocol.request;

import com.activeandroid.DataBaseModel;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.dmd.zsb.protocol.table.LOCATION;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "signinRequest")
public class signinRequest extends DataBaseModel
{

     @Column(name = "appkey")
     public String   appkey;

     @Column(name = "client_type")
     public String   client_type;

     @Column(name = "mobile")
     public String   mobile;

     @Column(name = "role")
     public String   role;

     @Column(name = "lat")
     public double   lat;

     @Column(name = "lon")
     public double   lon;

     @Column(name = "location")
     public String location;

     @Column(name = "version")
     public String version;

     @Column(name = "password")
     public String   password;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;
          this.appkey = jsonObject.optString("appkey");
          this.client_type = jsonObject.optString("client_type");
          this.mobile = jsonObject.optString("mobile");
          this.role = jsonObject.optString("role");
          this.lon = jsonObject.optDouble("lon");
          this.lat = jsonObject.optDouble("lat");
          this.location = jsonObject.optString("location");
          this.version = jsonObject.optString("version");
          this.password = jsonObject.optString("password");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("appkey", appkey);
          localItemObject.put("client_type", client_type);
          localItemObject.put("mobile", mobile);
          localItemObject.put("role", role);
          localItemObject.put("lat", lat);
          localItemObject.put("lon", lon);
          localItemObject.put("location", location);
          localItemObject.put("version", version);
          localItemObject.put("password", password);
          return localItemObject;
     }

}
