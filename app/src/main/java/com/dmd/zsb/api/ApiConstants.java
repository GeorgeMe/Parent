package com.dmd.zsb.api;

public class ApiConstants {

    public static final class Urls {

        public static final String API_DEVELOPMENT_URLS = "http://192.168.1.105:8080/TutorClient/v1.0/";
        public static final String API_BASE_URLS = "http://www.cqdmd.com/v1.0/";
        public static final String API_IMG_BASE_URLS = "http://www.cqdmd.com/";

        public static final String API_USER_INITDATA = "p_user_initdata.action";//初始化
        public static final String API_USER_SIGNUP = "p_user_signup.action?";//注册
        public static final String API_USER_SIGNIN = "p_user_signin.action?";//登陆
        public static final String API_USER_SIGNOUT = "p_user_signout.action?";//退出

        public static final String API_USER_HOME = "p_user_home.action?";//主页
        public static final String API_USER_FINDTEACHER = "p_user_findteacher.action?";//找老师
        public static final String API_USER_MINE = "p_user_mine.action?";//我的
        public static final String API_USER_USERDETAIL = "p_user_userdetail.action?";//老师详情

        public static final String API_USER_MYWALLET= "p_user_mywallet.action?";//我的钱包
        public static final String API_USER_MYORDER = "p_user_myorder.action?";//我的订单
        public static final String API_USER_MYEVALUATION = "p_user_myevaluation.action?";//我的评价
        public static final String API_USER_MYDEMAND = "p_user_mydemand.action?";//我的需求
        public static final String API_USER_MYVOUCHERS = "p_user_myvouchers.action?";//我的代金券
        public static final String API_USER_BILLDETAIL = "p_user_billdetail.action?";//交易记录（钱包里面）

        public static final String API_USER_MYORDERDETAIL = "p_user_myorderdetail.action?";//我的订单详情
        public static final String API_USER_MYEVALUATIONDETAIL = "p_user_myevaluationdetail.action?";//我的评价详情
        public static final String API_USER_MYDEMANDDETAIL = "p_user_mydemanddetail.action?";//我的需求详情
        public static final String API_USER_MYVOUCHERSDETAIL = "p_user_myvouchersdetail.action?";//我的代金券详情


        public static final String API_USER_RECHARGE = "p_user_recharge.action?";//充值
        public static final String API_USER_WITHDRAWALS = "p_user_withdrawals.action?";//提现
        public static final String API_USER_BANKCARD = "p_user_bankcard.action?";//银行卡
        public static final String API_USER_VOUCHERS = "p_user_vouchers.action?";//代金券

        public static final String API_USER_CHANGEAVATAR = "p_user_changeavatar.action?";//修改头像
        public static final String API_USER_CHANGEPROFILE = "p_user_changeprofile.action?";//修改信息简介
        public static final String API_USER_CHANGEPASSWORD = "p_user_changepassword.action?";//修改密码
        public static final String API_USER_CHANGESIGNATURE= "p_user_changesignature.action?";//修改签名
        public static final String API_USER_CHANGENICKNAME = "p_user_changenickname.action?";//修改昵称

        public static final String API_USER_CERTIFY = "p_user_certify.action?";//认证
        public static final String API_USER_LOGFILE = "p_user_logFile.action?";//上传日志
        public static final String API_USER_FEEDBACK = "p_user_feedback.action?";//反馈

        public static final String API_ORDER_SAVEORDER = "p_order_saveOrder.action?";//保存订单
        public static final String API_ORDER_CANCEL = "p_order_cancel.action?";//取消订单
        public static final String API_ORDER_PAY = "p_order_pay.action?";//支付订单
        public static final String API_ORDER_CONFIRMPAY = "p_order_confirmpay.action?";//确认支付
        public static final String API_ORDER_UPDATEORDERSTATUS = "p_order_updateOrderStatus.action?";//修改订单状态

    }

    public static final class Integers {
        public static final int PAGE_LAZY_LOAD_DELAY_TIME_MS = 200;
        public static final int PAGE_LIMIT = 20;
    }
}