package com.dmd.zsb.protocol.response;

import com.activeandroid.DataBaseModel;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.dmd.zsb.protocol.table.CONFIG;
import com.dmd.zsb.protocol.table.USER;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "signinResponse")
public class signinResponse extends DataBaseModel
{

     @Column(name = "sid")
     public String   sid;

     @Column(name = "uid")
     public String   uid;

     @Column(name = "succeed")
     public int succeed;

     @Column(name = "msg")
     public String msg;

     public void  fromJson(JSONObject jsonObject)  throws JSONException
     {
          if(null == jsonObject){
            return ;
           }

          JSONArray subItemArray;

          this.sid = jsonObject.optString("sid");
          this.uid = jsonObject.optString("uid");
          this.succeed = jsonObject.optInt("succeed");
          this.msg = jsonObject.optString("msg");

          return ;
     }

     public JSONObject  toJson() throws JSONException 
     {
          JSONObject localItemObject = new JSONObject();
          JSONArray itemJSONArray = new JSONArray();
          localItemObject.put("sid", sid);
          localItemObject.put("uid", uid);
          localItemObject.put("succeed", succeed);
          localItemObject.put("msg", msg);
          return localItemObject;
     }

}
