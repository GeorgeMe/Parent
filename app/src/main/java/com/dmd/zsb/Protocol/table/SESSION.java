package com.dmd.zsb.protocol.table;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

@Table(name = "SESSION")
public class SESSION  extends Model implements Serializable
{
    @Column(name = "appkey")
    public String   appkey;

    @Column(name = "version")
    public String version;

     @Column(name = "uid")
     public int uid ;

     @Column(name = "sid")
     public String sid;

    public SESSION() {
        super();
    }

    public SESSION(String appkey, String version, int uid, String sid) {
        super();
        this.appkey = appkey;
        this.version = version;
        this.uid = uid;
        this.sid = sid;
    }

    public static SESSION instance;
     public static SESSION getInstance() {
         if (instance == null) {
             instance = new SESSION();
         }
         return instance;
     }

}
