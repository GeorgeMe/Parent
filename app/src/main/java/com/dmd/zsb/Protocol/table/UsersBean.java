package com.dmd.zsb.protocol.table;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/9.
 */
public class UsersBean  extends SugarRecord implements Serializable {

    public String curriculum_id;
    public String user_id;
    public String avatar;
    public String gender;
    public int comment_count;
    public int comment_goodrate;
    public String location;
    public String lat;
    public String lon;
    public String mobile;
    public int total_hours;
    public String role;
    public String grade_name;
    public String subject_name;

    public UsersBean() {
        super();
    }

    public UsersBean(String curriculum_id, String user_id, String avatar, String gender, int comment_count, int comment_goodrate, String location, String lat, String lon, String mobile, int total_hours, String role, String grade_name, String subject_name) {
        super();
        this.curriculum_id = curriculum_id;
        this.user_id = user_id;
        this.avatar = avatar;
        this.gender = gender;
        this.comment_count = comment_count;
        this.comment_goodrate = comment_goodrate;
        this.location = location;
        this.lat = lat;
        this.lon = lon;
        this.mobile = mobile;
        this.total_hours = total_hours;
        this.role = role;
        this.grade_name = grade_name;
        this.subject_name = subject_name;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;

        this.comment_count = jsonObject.optInt("comment_count");
        this.comment_goodrate = jsonObject.optInt("comment_goodrate");
        this.total_hours = jsonObject.optInt("total_hours");

        this.curriculum_id = jsonObject.optString("curriculum_id");
        this.user_id = jsonObject.optString("user_id");
        this.avatar = jsonObject.optString("avatar");
        this.gender = jsonObject.optString("gender");
        this.location = jsonObject.optString("location");
        this.lat = jsonObject.optString("lon");
        this.lat = jsonObject.optString("lat");
        this.mobile = jsonObject.optString("mobile");
        this.role = jsonObject.optString("role");
        this.grade_name = jsonObject.optString("grade_name");
        this.subject_name = jsonObject.optString("subject_name");

        return ;
    }
    public JSONObject toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("comment_count", comment_count);
        localItemObject.put("comment_goodrate", comment_goodrate);
        localItemObject.put("total_hours", total_hours);

        localItemObject.put("curriculum_id", curriculum_id);
        localItemObject.put("user_id", user_id);
        localItemObject.put("avatar", avatar);
        localItemObject.put("gender", gender);
        localItemObject.put("location", location);
        localItemObject.put("lon", lon);
        localItemObject.put("lat", lat);
        localItemObject.put("mobile", mobile);
        localItemObject.put("role", role);
        localItemObject.put("grade_name", grade_name);
        localItemObject.put("subject_name", subject_name);

        return localItemObject;
    }


}
