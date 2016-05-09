package com.dmd.zsb.protocol.table;

import com.activeandroid.DataBaseModel;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/5/9.
 */
@Table(name = "Users")
public class UsersBean  extends DataBaseModel {

    @Column(name = "curriculum_id")
    public String curriculum_id;
    @Column(name = "user_id")
    public String user_id;
    @Column(name = "avatar")
    public String avatar;
    @Column(name = "gender")
    public String gender;
    @Column(name = "comment_count")
    public int comment_count;
    @Column(name = "comment_goodrate")
    public int comment_goodrate;
    @Column(name = "location")
    public String location;
    @Column(name = "lat")
    public String lat;
    @Column(name = "lon")
    public String lon;
    @Column(name = "mobile")
    public String mobile;
    @Column(name = "total_hours")
    public int total_hours;
    @Column(name = "role")
    public String role;
    @Column(name = "grade_name")
    public String grade_name;
    @Column(name = "subject_name")
    public String subject_name;

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
        localItemObject.put("mobile", mobile);
        localItemObject.put("role", role);
        localItemObject.put("mobile", mobile);
        localItemObject.put("grade_name", grade_name);
        localItemObject.put("subject_name", subject_name);

        return localItemObject;
    }


}
