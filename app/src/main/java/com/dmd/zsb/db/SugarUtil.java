package com.dmd.zsb.db;

import android.content.Context;

import com.dmd.zsb.protocol.table.GradesBean;
import com.dmd.zsb.protocol.table.SubjectsBean;
import com.orm.SugarDb;

/**
 * Created by Administrator on 2016/5/20.
 */
public class SugarUtil {

    private static SugarUtil mInstance;

    public static SugarUtil getInstance(){
        if (mInstance==null){
            mInstance=new SugarUtil();
        }
        return mInstance;
    }

    public void initDB(Context context){
        SugarDb sugarDb=new SugarDb(context);
        SchemaGeneratorUtil schemaGeneratorUtil=new SchemaGeneratorUtil(context);
        sugarDb.getDB().execSQL(schemaGeneratorUtil.getTableSQL(GradesBean.class));
        sugarDb.getDB().execSQL(schemaGeneratorUtil.getTableSQL(SubjectsBean.class));
        sugarDb.close();
    }
}
