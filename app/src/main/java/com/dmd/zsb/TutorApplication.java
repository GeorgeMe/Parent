package com.dmd.zsb;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;

import com.alibaba.mobileim.aop.AdviceBinder;
import com.alibaba.mobileim.aop.PointCutEnum;
import com.alibaba.wxlib.util.SysUtil;
import com.dmd.tutor.base.BaseAppManager;
import com.dmd.tutor.lbs.LocationManager;
import com.dmd.tutor.utils.TLog;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.openim.ChattingUICustom;
import com.dmd.zsb.openim.ConversationListUICustom;
import com.dmd.zsb.utils.VolleyHelper;
import com.umeng.openim.OpenIMAgent;

import java.io.File;
import java.util.UUID;

/**
 * Created by George on 2015/12/6.
 */
public class TutorApplication extends Application {
    public static TutorApplication mInstance = null;
    private static Context sContext;
    public static Context getContext(){
        return sContext;
    }
    public static TutorApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        sContext=getApplicationContext();
        //项目log文件夹
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/000/";
        File storePath = new File(path);
        storePath.mkdirs();
        //设备码
        XmlDB.getInstance(this).saveKey("uuid", getUUID());
        //定位信息
        LocationManager locationManager = new LocationManager(this);
        locationManager.refreshLocation();

        //Volley初始化
        VolleyHelper.getInstance().init(this);

        //todo Application.onCreate中，首先执行这部分代码，以下代码固定在此处，不要改动，这里return是为了退出Application.onCreate！！！
        if(mustRunFirstInsideApplicationOnCreate()){
            //todo 如果在":TCMSSevice"进程中，无需进行openIM和app业务的初始化，以节省内存
            return;
        }
        //聊天界面的自定义风格1：［图片、文字小猪气泡］风格
        AdviceBinder.bindAdvice(PointCutEnum.CHATTING_FRAGMENT_UI_POINTCUT, ChattingUICustom.class);
        //会话列表UI相关
        AdviceBinder.bindAdvice(PointCutEnum.CONVERSATION_FRAGMENT_UI_POINTCUT, ConversationListUICustom.class);
        //初始化ImSDK
        OpenIMAgent im = OpenIMAgent.getInstance(this);
        im.init();

    }
    private boolean mustRunFirstInsideApplicationOnCreate() {
        //必须的初始化
        SysUtil.setApplication(this);

        return SysUtil.isTCMSServiceProcess(mInstance.getApplicationContext());
    }
    @Override
    public void onLowMemory() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onLowMemory();
    }

    public void exitApp() {
        BaseAppManager.getInstance().clear();
        System.gc();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private String getUUID() {
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        TLog.d("UUID    ", uniqueId);
        return uniqueId;
    }

}
