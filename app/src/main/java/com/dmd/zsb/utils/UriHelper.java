package com.dmd.zsb.utils;


import android.util.Log;

import com.dmd.tutor.utils.TLog;
import com.dmd.zsb.api.ApiConstants;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UriHelper {

    private static volatile UriHelper instance = null;
    private static boolean development=true;
    /**
     * 20 datas per page
     */
    public static final int PAGE_LIMIT = 20;


    private UriHelper() {
    }

    public static UriHelper getInstance() {
        if (null == instance) {
            synchronized (UriHelper.class) {
                if (null == instance) {
                    instance = new UriHelper();
                }
            }
        }
        return instance;
    }
    //页码处理
    public int calculateTotalPages(int totalNumber) {
        if (totalNumber > 0) {
            return totalNumber % PAGE_LIMIT != 0 ? (totalNumber / PAGE_LIMIT + 1) : totalNumber / PAGE_LIMIT;
        } else {
            return 0;
        }
    }

    private String urlToString(boolean flag,String action,JsonObject json){
        StringBuffer stringbuffer = new StringBuffer();
        if (development){
            stringbuffer.append(ApiConstants.Urls.API_DEVELOPMENT_URLS);
        }else {
            stringbuffer.append(ApiConstants.Urls.API_BASE_URLS);
        }
        stringbuffer.append(action);
        if (flag){
            stringbuffer.append("json=");
            stringbuffer.append(json);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒SSS");
        String time = sdf.format(new Date(System.currentTimeMillis()));
        if (development){
            Log.e("UriHelper",time+" 调试请求 ："+stringbuffer.toString().trim());
        }else {
            Log.e("UriHelper",time+" 正式请求 ："+stringbuffer.toString().trim());
        }

        return stringbuffer.toString().trim();
    }

    //初始化数据
    public String InitData(JsonObject json){
        return urlToString(false,ApiConstants.Urls.API_USER_INITDATA,json);
    }
    //注册
    public String signUp(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_SIGNUP,json);
    }
    //登陆
    public String signIn(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_SIGNIN,json);
    }
    //退出
    public String signOut(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_SIGNOUT,json);
    }


    //首页
    public String home(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_HOME,json);
    }
    //找老师
    public String findteacher(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_FINDTEACHER,json);
    }
    //我的
    public String mine(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MINE,json);
    }
    //用户详情
    public String userDetail(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_USERDETAIL,json);
    }


    //我的钱包
    public String mywallet(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYWALLET,json);
    }
    //我的订单
    public String myorder(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYORDER,json);
    }
    //我的评价
    public String myevaluation(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYEVALUATION,json);
    }
    //我的需求
    public String mydemand(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYDEMAND,json);
    }
    //我的代金券
    public String myvouchers(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYVOUCHERS,json);
    }
    //交易记录（钱包里面）
    public String billdetail(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_BILLDETAIL,json);
    }


    //充值
    public String recharge(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_RECHARGE,json);
    }
    //提现
    public String withdrawals(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_WITHDRAWALS,json);
    }
    //银行卡
    public String bankcard(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_BANKCARD,json);
    }
    //代金券
    public String vouchers(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_VOUCHERS,json);
    }


    //我的订单详情
    public String myorderdetail(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYORDERDETAIL,json);
    }
    //我的评价详情
    public String myevaluationdetail(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYEVALUATIONDETAIL,json);
    }
    //我的需求详情
    public String mydemanddetail(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYDEMANDDETAIL,json);
    }
    //我的代金券详情
    public String myvouchersdetail(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_MYVOUCHERSDETAIL,json);
    }

    //修改用户简介
    public String changeprofile(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CHANGEPROFILE,json);
    }
    //修改头像
    public String changeAvatar(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CHANGEAVATAR,json);
    }
    //修改密码
    public String changePassword(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CHANGEPASSWORD,json);
    }
    //修改签名
    public String changesignature(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CHANGESIGNATURE,json);
    }
    //修改昵称
    public String changenickname(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CHANGENICKNAME,json);
    }

    //上传日志
    public String logFile(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_LOGFILE,json);
    }
    //反馈
    public String seedFeedback(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_FEEDBACK,json);
    }
    //认证
    public String certify(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_USER_CERTIFY,json);
    }


    //保存订单信息
    public String saveOrder(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_SAVEORDER,json);
    }
    //取消订单
    public String cancelOrder(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_CANCEL,json);
    }
    //支付订单
    public String payOrder(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_PAY,json);
    }
    //确认支付
    public String confirmpay(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_CONFIRMPAY,json);
    }
    //修改订单状态
    public String updateOrderStatus(JsonObject json){
        return urlToString(true,ApiConstants.Urls.API_ORDER_UPDATEORDERSTATUS,json);
    }

}
