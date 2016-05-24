package com.dmd.zsb.protocol.table;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/23.
 */
public class UserDetails extends SugarRecord implements Serializable {

    public String user_id;
    public String user_avatar;
    public String  user_name;
    public String  user_seniority;
    public String  appointment_time;
    public String  good_subjects;
    public String  teaching_place;
    public String  teaching_methods;
    public String  mobile;

    public UserDetails() {
    }

    public UserDetails(String user_id,String user_avatar, String user_name, String user_seniority, String appointment_time, String good_subjects, String teaching_place, String teaching_methods,String  mobile) {
        this.user_id = user_id;
        this.user_avatar = user_avatar;
        this.user_name = user_name;
        this.user_seniority = user_seniority;
        this.appointment_time = appointment_time;
        this.good_subjects = good_subjects;
        this.teaching_place = teaching_place;
        this.teaching_methods = teaching_methods;
        this.mobile = mobile;
    }


    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;

        this.user_id = jsonObject.optString("user_id");
        this.user_avatar = jsonObject.optString("user_avatar");
        this.user_name = jsonObject.optString("user_name");
        this.user_seniority = jsonObject.optString("user_seniority");
        this.appointment_time = jsonObject.optString("appointment_time");
        this.good_subjects = jsonObject.optString("good_subjects");
        this.teaching_place = jsonObject.optString("teaching_place");
        this.teaching_methods = jsonObject.optString("teaching_methods");
        this.mobile = jsonObject.optString("mobile");

        return ;
    }
    public JSONObject toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();

        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("user_id", user_id);
        localItemObject.put("user_avatar", user_avatar);
        localItemObject.put("user_name", user_name);
        localItemObject.put("user_seniority", user_seniority);
        localItemObject.put("appointment_time", appointment_time);
        localItemObject.put("good_subjects", good_subjects);
        localItemObject.put("teaching_place", teaching_place);
        localItemObject.put("teaching_methods", teaching_methods);
        localItemObject.put("mobile", mobile);
        return localItemObject;
    }

}
