package com.dmd.zsb.protocol.table;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/23.
 */
public class MyComments extends SugarRecord implements Serializable {

    public String user_id;
    public String user_avatar;
    public String user_nickname;
    public String rank;
    public String createtime;
    public String comment_content;

    public MyComments() {
    }

    public MyComments(String user_id, String user_avatar, String user_nickname, String rank, String createtime, String comment_content) {
        this.user_id = user_id;
        this.user_avatar = user_avatar;
        this.user_nickname = user_nickname;
        this.rank = rank;
        this.createtime = createtime;
        this.comment_content = comment_content;
    }
    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;
        this.user_id = jsonObject.optString("user_id");
        this.user_avatar = jsonObject.optString("user_avatar");
        this.user_nickname = jsonObject.optString("user_nickname");
        this.rank = jsonObject.optString("rank");
        this.createtime = jsonObject.optString("createtime");
        this.comment_content = jsonObject.optString("comment_content");

    }

    public JSONObject  toJson() throws JSONException {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("user_id", user_id);
        localItemObject.put("user_avatar", user_avatar);
        localItemObject.put("user_nickname", user_nickname);
        localItemObject.put("rank", rank);
        localItemObject.put("createtime", createtime);
        localItemObject.put("comment_content", comment_content);

        return localItemObject;
    }

}
