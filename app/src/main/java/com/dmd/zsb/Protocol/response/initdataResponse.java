package com.dmd.zsb.protocol.response;

import com.activeandroid.DataBaseModel;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.dmd.zsb.protocol.table.GradesBean;
import com.dmd.zsb.protocol.table.SubjectsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
@Table(name = "bankcardResponse")
public class initdataResponse extends Model implements Serializable {
    @Column(name = "errno")
    public int errno;
    @Column(name = "msg")
    public String msg;

    @Column(name = "grades")
    public List<GradesBean> grades;

    @Column(name = "subjects")
    public List<SubjectsBean> subjects;

    public initdataResponse() {
        super();
    }

    public initdataResponse(int errno, String msg, List<GradesBean> grades, List<SubjectsBean> subjects) {
        super();
        this.errno = errno;
        this.msg = msg;
        this.grades = grades;
        this.subjects = subjects;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;
        JSONArray subItemArray2;

        this.errno = jsonObject.optInt("errno");
        this.msg = jsonObject.optString("msg");
        subItemArray=jsonObject.optJSONArray("grades");
        subItemArray2=jsonObject.optJSONArray("subjects");
        if(null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                GradesBean subItem = new GradesBean();
                subItem.fromJson(subItemObject);
                this.grades.add(subItem);
            }
        }
        if(null != subItemArray2) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray2.getJSONObject(i);
                SubjectsBean subItem = new SubjectsBean();
                subItem.fromJson(subItemObject);
                this.subjects.add(subItem);
            }
        }

        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();
        JSONArray itemJSONArray2 = new JSONArray();
        localItemObject.put("errno", errno);
        localItemObject.put("msg", msg);

        for(int i =0; i< grades.size(); i++)
        {
            GradesBean itemData =grades.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("grades", itemJSONArray);
        for(int i =0; i< subjects.size(); i++)
        {
            SubjectsBean itemData =subjects.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray2.put(itemJSONObject);
        }
        localItemObject.put("subjects", itemJSONArray2);

        return localItemObject;
    }
}
