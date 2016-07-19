package com.dmd.zsb.parent.activity;


import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.StringUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.BankCardPresenterImpl;
import com.dmd.zsb.mvp.view.BankCardView;
import com.dmd.zsb.parent.R;
import com.dmd.zsb.parent.activity.base.BaseActivity;
import com.dmd.zsb.protocol.response.bankcardResponse;
import com.dmd.zsb.utils.CheckBankCard;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

public class BankCardActivity extends BaseActivity implements BankCardView {

    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.add_bank_card)
    Button addBankCard;
    @Bind(R.id.rl_none_card)
    RelativeLayout rlNoneCard;
    @Bind(R.id.et_cardholder)
    EditText etCardholder;
    @Bind(R.id.input_bank_card)
    EditText inputBankCard;
    @Bind(R.id.save_bank_card)
    Button saveBankCard;
    @Bind(R.id.rl_add_card)
    RelativeLayout rlAddCard;
    @Bind(R.id.tv_bank_card)
    TextView boundCard;
    @Bind(R.id.change_bank_card)
    Button changeBankCard;
    @Bind(R.id.rl_change_card)
    RelativeLayout rlChangeCard;

    private String bankCard="";

    private BankCardPresenterImpl bankCardPresenter;
    @Override
    protected void getBundleExtras(Bundle extras) {
        bankCard=extras.getString("bank_card");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_bank_card;
    }

    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        topBarTitle.setText("银行卡详情");
        inputBankCard.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
        if (StringUtils.StringIsEmpty(bankCard)){
            rlNoneCard.setVisibility(View.VISIBLE);
            rlAddCard.setVisibility(View.GONE);
            rlChangeCard.setVisibility(View.GONE);
            boundCard.setText("");
        }else {
            //校验银行卡号码
            if (CheckBankCard.checkBankCard(bankCard)) {
                rlNoneCard.setVisibility(View.GONE);
                rlAddCard.setVisibility(View.GONE);
                rlChangeCard.setVisibility(View.VISIBLE);
                boundCard.setText(bankCard);
            }else {
                rlNoneCard.setVisibility(View.VISIBLE);
                rlAddCard.setVisibility(View.GONE);
                rlChangeCard.setVisibility(View.GONE);
                boundCard.setText("");
            }

        }

    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

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

    @OnClick({R.id.top_bar_back, R.id.add_bank_card, R.id.save_bank_card, R.id.change_bank_card})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.add_bank_card:
                rlNoneCard.setVisibility(View.GONE);
                rlAddCard.setVisibility(View.VISIBLE);
                rlChangeCard.setVisibility(View.GONE);

                break;
            case R.id.save_bank_card:
                if (TextUtils.isEmpty(etCardholder.getText().toString())){
                    showToast("请输入持卡人姓名");
                }else if (TextUtils.isEmpty(inputBankCard.getText().toString())){
                    showToast("请输入卡号");
                }else {
                    if (CheckBankCard.checkBankCard(inputBankCard.getText().toString())) {
                        bankCardPresenter=new BankCardPresenterImpl(this,mContext);
                        JSONObject jsonObject =new JSONObject();
                        try {
                            jsonObject.put("appkey", Constants.ZSBAPPKEY);
                            jsonObject.put("version", Constants.ZSBVERSION);
                            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid","sid"));
                            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid","uid"));

                            jsonObject.put("name", etCardholder.getText().toString());
                            jsonObject.put("bank_card", inputBankCard.getText().toString());
                            bankCardPresenter.onChangeBankCard(jsonObject);
                        }catch (JSONException j){

                        }
                    }else {
                        showToast("卡号格式错误");
                    }

                }


                break;
            case R.id.change_bank_card:
                rlNoneCard.setVisibility(View.GONE);
                rlAddCard.setVisibility(View.VISIBLE);
                rlChangeCard.setVisibility(View.GONE);

                break;
        }
    }

    @Override
    public void onBankCardView(bankcardResponse response) {
        if (response.errno==0){
            rlNoneCard.setVisibility(View.GONE);
            rlAddCard.setVisibility(View.GONE);
            rlChangeCard.setVisibility(View.VISIBLE);
        }else {
            showError(response.msg);
        }

    }
}
