package com.dmd.zsb.protocol.request;

import com.activeandroid.DataBaseModel;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.dmd.zsb.protocol.table.LOCATION;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

@Table(name = "signinRequest")
public class signinRequest extends Model implements Serializable
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

     public signinRequest() {
          super();
     }

     public signinRequest(String appkey, String client_type, String mobile, String role, double lat, double lon, String location, String version, String password) {
          super();
          this.appkey = appkey;
          this.client_type = client_type;
          this.mobile = mobile;
          this.role = role;
          this.lat = lat;
          this.lon = lon;
          this.location = location;
          this.version = version;
          this.password = password;
     }

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
