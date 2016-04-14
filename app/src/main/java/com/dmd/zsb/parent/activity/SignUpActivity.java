package com.dmd.zsb.parent.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.parent.R;
import com.dmd.zsb.mvp.presenter.impl.SignUpPresenterImpl;
import com.dmd.zsb.mvp.view.SignUpView;
import com.dmd.zsb.parent.activity.base.BaseActivity;
import com.dmd.zsb.widgets.ToastView;
import com.google.gson.JsonObject;
import com.squareup.otto.Subscribe;

import butterknife.Bind;

public class SignUpActivity extends BaseActivity implements SignUpView, View.OnClickListener {

    @Bind(R.id.et_nickname)
    EditText etNickname;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.et_password_again)
    EditText etPasswordAgain;
    @Bind(R.id.btn_signup_complete)
    Button btnSignupComplete;
    @Bind(R.id.signup_frame)
    FrameLayout signupFrame;

    private SignUpPresenterImpl signUpPresenter;
    private String mobile;

    @Override
    protected void getBundleExtras(Bundle extras) {
        mobile = extras.getString("mobile");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_sign_up;
    }
    @Subscribe
    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return signupFrame;
    }

    @Override
    protected void initViewsAndEvents() {
        etNickname.setOnClickListener(this);
        etPassword.setOnClickListener(this);
        etPasswordAgain.setOnClickListener(this);
        btnSignupComplete.setOnClickListener(this);
        signUpPresenter = new SignUpPresenterImpl(mContext, this);
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    /**
     * 应用透明
     */
    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void onClick(View v) {
        String nickname = etNickname.getText().toString().trim();
        String password = etPassword.getText().toString();
        String password_again = etPasswordAgain.getText().toString();
        switch (v.getId()) {
            case R.id.btn_signup_complete:
                if ("".equals(nickname)) {
                    ToastView toast = new ToastView(mContext, getString(R.string.please_input_nickname));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etNickname.setText("");
                    etNickname.requestFocus();
                } else if (nickname.length() < 1 || nickname.length() > 16) {
                    ToastView toast = new ToastView(mContext, getString(R.string.nickname_wrong_format_hint));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etNickname.requestFocus();
                } else if ("".equals(password)) {
                    ToastView toast = new ToastView(mContext, getString(R.string.please_input_password));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etPassword.requestFocus();
                } else if (password.length() < 6 || password.length() > 20) {
                    ToastView toast = new ToastView(mContext, getString(R.string.password_wrong_format_hint));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etPassword.requestFocus();
                } else if (!password.equals(password_again)) {
                    ToastView toast = new ToastView(mContext, getString(R.string.two_passwords_differ_hint));
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    etPasswordAgain.requestFocus();
                } else {
                    CloseKeyBoard();
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("appkey", Constants.ZSBAPPKEY);
                    jsonObject.addProperty("version", Constants.ZSBVERSION);
                    jsonObject.addProperty("client_type", Constants.PLATFORM);
                    jsonObject.addProperty("role", Constants.USER_ROLE);
                    jsonObject.addProperty("nickname", nickname);
                    jsonObject.addProperty("mobile", mobile);
                    jsonObject.addProperty("password", password);
                    signUpPresenter.signUp(jsonObject);
                }
                break;
        }
    }

    @Override
    public void navigateToHome() {
        readyGoThenKill(MainActivity.class);
    }

    // 关闭键盘
    private void CloseKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etNickname.getWindowToken(), 0);
    }

}
