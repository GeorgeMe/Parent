package com.activeandroid;

import android.database.Cursor;
import android.util.Log;

import com.activeandroid.annotation.Column;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class DataBaseModel extends Model implements Serializable
{
    @Column(name = "jsonString")
    private String jsonString = null;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        this.jsonString = jsonObject.toString();
    }

    public JSONObject toJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject)
        {
            jsonObject = new JSONObject();
        }
        return jsonObject;
    }

    @Override
    public void loadFromCursor(Class<? extends Model> type, Cursor cursor)
    {
        final int columnIndex = cursor.getColumnIndex("jsonString");
        if (columnIndex < 0)
        {
            super.loadFromCursor(type,cursor);
            return;
        }

        boolean columnIsNull = cursor.isNull(columnIndex);

        if(!columnIsNull)
        {
            jsonString = cursor.getString(columnIndex);

            try
            {
                JSONObject jsonObject = new JSONObject(jsonString);
                fromJson(jsonObject);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}
