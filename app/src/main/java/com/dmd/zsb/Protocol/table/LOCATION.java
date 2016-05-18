
package com.dmd.zsb.protocol.table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.DataBaseModel;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

@Table(name = "LOCATION")
public class LOCATION  extends Model implements Serializable
{

     @Column(name = "lon")
     public double   lon;

     @Column(name = "name")
     public String   name;

     @Column(name = "lat")
     public double   lat;

     public LOCATION() {
          super();
     }

     public LOCATION(double lon, String name, double lat) {
          super();
          this.lon = lon;
          this.name = name;
          this.lat = lat;
     }

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;
          this.lon = jsonObject.optDouble("lon");

          this.name = jsonObject.optString("name");

          this.lat = jsonObject.optDouble("lat");
          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("lon", lon);
          localItemObject.put("name", name);
          localItemObject.put("lat", lat);
          return localItemObject;
     }

}
