package com.dmd.zsb.protocol.response;

import com.dmd.zsb.protocol.table.MyComments;
import com.dmd.zsb.protocol.table.MyServices;
import com.dmd.zsb.protocol.table.UserDetails;
import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/23.
 */
public class userdetailsResponse extends SugarRecord implements Serializable {

    public int errno;
    public String msg;
    public int total_count;

    public UserDetails details;
    public List<MyComments> comments;
    public List<MyServices> services;

    public userdetailsResponse() {
        super();
    }

    public userdetailsResponse(int errno, String msg, int total_count, List<MyComments> comments, List<MyServices> services) {
        this.errno = errno;
        this.msg = msg;
        this.total_count = total_count;
        this.comments = comments;
        this.services = services;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;
        JSONArray subItemArray2;

        this.errno = jsonObject.optInt("errno");
        this.total_count = jsonObject.optInt("total_count");
        this.msg = jsonObject.optString("msg");
        UserDetails details=new UserDetails();
        details.fromJson(jsonObject.optJSONObject("details"));
        this.details =details;
        subItemArray=jsonObject.optJSONArray("comments");
        if(null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                MyComments subItem = new MyComments();
                subItem.fromJson(subItemObject);
                this.comments.add(subItem);
            }
        }
        subItemArray2=jsonObject.optJSONArray("services");
        if(null != subItemArray2) {
            for (int i = 0; i < subItemArray2.length(); i++) {
                JSONObject subItemObject = subItemArray2.getJSONObject(i);
                MyServices subItem = new MyServices();
                subItem.fromJson(subItemObject);
                this.services.add(subItem);
            }
        }


        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        JSONArray itemJSONArray2 = new JSONArray();

        localItemObject.put("errno", errno);
        localItemObject.put("total_count", total_count);
        localItemObject.put("msg", msg);
        if (details!=null){
            localItemObject.put("details",details.toJson());
        }
        for(int i =0; i< comments.size(); i++)
        {
            MyComments itemData =comments.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("transactions", itemJSONArray);

        for(int i =0; i< services.size(); i++)
        {
            MyServices itemData =services.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray2.put(itemJSONObject);
        }
        localItemObject.put("services", itemJSONArray2);

        return localItemObject;
    }
}
