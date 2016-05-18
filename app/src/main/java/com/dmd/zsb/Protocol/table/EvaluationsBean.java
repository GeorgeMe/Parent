package com.dmd.zsb.protocol.table;

import com.activeandroid.DataBaseModel;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/5/9.
 */
@Table(name = "Evaluations")
public class EvaluationsBean  extends Model implements Serializable {

    @Column(name = "img_header")
    public String img_header;//头像
    @Column(name = "name")
    public String name;//姓名
    @Column(name = "type")
    public String type;//职业类型
    @Column(name = "sex")
    public String sex;//性别
    @Column(name = "appointed_time")
    public String appointed_time;//约定时间
    @Column(name = "charging")
    public String charging;//计费
    @Column(name = "curriculum")
    public String curriculum;//课程
    @Column(name = "note")
    public String note;//评论内容
    @Column(name = "comment_level")
    public String comment_level;//评论等级

    public EvaluationsBean() {
        super();
    }

    public EvaluationsBean(String img_header, String name, String type, String sex, String appointed_time, String charging, String curriculum, String note, String comment_level) {
        super();
        this.img_header = img_header;
        this.name = name;
        this.type = type;
        this.sex = sex;
        this.appointed_time = appointed_time;
        this.charging = charging;
        this.curriculum = curriculum;
        this.note = note;
        this.comment_level = comment_level;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException
    {
        if(null == jsonObject){
            return ;
        }

        JSONArray subItemArray;

        this.img_header = jsonObject.optString("img_header");
        this.name = jsonObject.optString("name");
        this.type = jsonObject.optString("type");
        this.sex = jsonObject.optString("sex");
        this.appointed_time = jsonObject.optString("appointed_time");
        this.charging = jsonObject.optString("charging");
        this.curriculum = jsonObject.optString("curriculum");
        this.note = jsonObject.optString("note");
        this.comment_level = jsonObject.optString("comment_level");
        return ;
    }

    public JSONObject  toJson() throws JSONException
    {
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        localItemObject.put("img_header", img_header);
        localItemObject.put("name", name);
        localItemObject.put("sex", sex);
        localItemObject.put("appointed_time", appointed_time);
        localItemObject.put("charging", charging);
        localItemObject.put("curriculum", curriculum);
        localItemObject.put("note", note);
        localItemObject.put("comment_level", comment_level);
        return localItemObject;
    }
}
