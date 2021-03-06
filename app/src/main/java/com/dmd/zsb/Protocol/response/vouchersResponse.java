package com.dmd.zsb.protocol.response;

import com.dmd.zsb.protocol.table.VouchersBean;
import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
public class vouchersResponse extends SugarRecord implements Serializable {

    public int errno;
    public String msg;
    public int total_count;
    public List<VouchersBean> vouchers;

    public vouchersResponse() {
        super();
    }

    public vouchersResponse(int errno, String msg, int total_count, List<VouchersBean> vouchers) {
        super();
        this.errno = errno;
        this.msg = msg;
        this.total_count = total_count;
        this.vouchers = vouchers;
    }

    public void  fromJson(JSONObject jsonObject)  throws JSONException {
        if (null == jsonObject) {
            return;
        }

        JSONArray subItemArray;

        this.errno = jsonObject.optInt("errno");
        this.total_count = jsonObject.optInt("total_count");
        this.msg = jsonObject.optString("msg");
        subItemArray=jsonObject.optJSONArray("demands");
        if(null != subItemArray) {
            for (int i = 0; i < subItemArray.length(); i++) {
                JSONObject subItemObject = subItemArray.getJSONObject(i);
                VouchersBean subItem = new VouchersBean();
                subItem.fromJson(subItemObject);
                this.vouchers.add(subItem);
            }
        }
        return ;
    }
    public JSONObject  toJson() throws JSONException{
        JSONObject localItemObject = new JSONObject();
        JSONArray itemJSONArray = new JSONArray();

        localItemObject.put("errno", errno);
        localItemObject.put("total_count", total_count);
        localItemObject.put("msg", msg);
        for(int i =0; i< vouchers.size(); i++)
        {
            VouchersBean itemData =vouchers.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("vouchers", itemJSONArray);

        return localItemObject;
    }
}
