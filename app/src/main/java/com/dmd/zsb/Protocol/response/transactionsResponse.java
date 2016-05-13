package com.dmd.zsb.protocol.response;

import com.activeandroid.DataBaseModel;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.dmd.zsb.protocol.table.TransactionsBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2016/5/4.
 */
@Table(name = "transactionsResponse")
public class transactionsResponse extends DataBaseModel{
    @Column(name = "errno")
    public int errno;
    @Column(name = "msg")
    public String msg;
    @Column(name = "total_count")
    public int total_count;

    @Column(name = "vouchers")
    public List<TransactionsBean> transactions;

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
                TransactionsBean subItem = new TransactionsBean();
                subItem.fromJson(subItemObject);
                this.transactions.add(subItem);
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
        for(int i =0; i< transactions.size(); i++)
        {
            TransactionsBean itemData =transactions.get(i);
            JSONObject itemJSONObject = itemData.toJson();
            itemJSONArray.put(itemJSONObject);
        }
        localItemObject.put("transactions", itemJSONArray);

        return localItemObject;
    }
}
