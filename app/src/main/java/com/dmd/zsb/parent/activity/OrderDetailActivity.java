package com.dmd.zsb.parent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dmd.pay.AliPayActivity;
import com.dmd.pay.entity.PayInfo;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.lbs.LocationManager;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.TLog;
import com.dmd.zsb.mvp.view.ConfirmPayView;
import com.dmd.zsb.parent.R;
import com.dmd.zsb.parent.activity.base.BaseActivity;
import com.dmd.zsb.protocol.response.confirmpayResponse;
import com.dmd.zsb.protocol.table.OrdersBean;

import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity implements ConfirmPayView {
    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.btn_confirm_pay)
    Button btnConfirmPay;

    @Bind(R.id.tv_oid)
    TextView tvOid;
    @Bind(R.id.tv_created_at)
    TextView tvCreatedAt;
    @Bind(R.id.tv_appointment_time)
    TextView tvAppointmentTime;
    @Bind(R.id.tv_subject)
    TextView tvSubject;
    @Bind(R.id.tv_text)
    TextView tvText;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.tv_offer_price)
    TextView tvOfferPrice;
    @Bind(R.id.tv_receiver_id)
    TextView tvReceiverId;
    @Bind(R.id.tv_distance)
    TextView tvDistance;
    @Bind(R.id.tv_order_status)
    TextView tvOrderStatus;
    @Bind(R.id.show_order_status)
    TextView showOrderStatus;


    private OrdersBean data;
    //private ConfirmPayPresenterImpl confirmPayPresenter;

    @Override
    protected void getBundleExtras(Bundle extras) {
        data = (OrdersBean) extras.getSerializable("data");
        if (data != null) {
            try {
                TLog.enableLog();
                TLog.e(TAG_LOG, data.toJson().toString());
                TLog.disableLog();
            } catch (JSONException j) {

            }

        }


    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_order_detail;
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
        // confirmPayPresenter = new ConfirmPayPresenterImpl(mContext, this);
        tvOid.setText(data.oid);
        tvCreatedAt.setText(data.created_at);
        tvAppointmentTime.setText(data.appointment_time);
        tvSubject.setText(data.subject);
        tvText.setText(data.text);
        tvLocation.setText(data.location);
        tvOfferPrice.setText(data.offer_price);
        tvReceiverId.setText(data.receiver_id);
        tvDistance.setText(LocationManager.getDistance(Double.parseDouble(data.lat), Double.parseDouble(data.lon)));
        if (data.order_status == 2) {
            tvOrderStatus.setText("未付款");
        } else if (data.order_status == 3) {
            tvOrderStatus.setText("已付款");
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


    @OnClick({R.id.top_bar_back, R.id.btn_confirm_pay})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.btn_confirm_pay:

                PayInfo payInfo = new PayInfo();
                payInfo.setName(data.subject);
                payInfo.setDesc(data.text);
                payInfo.setOrder_sn(data.order_sn);
                payInfo.setPrice(Double.parseDouble(data.offer_price));
                payInfo.setRate(1.0);
                Bundle bundle = new Bundle();
                bundle.putSerializable("payInfo", payInfo);
                readyGoForResult(AliPayActivity.class, 110, bundle);
                break;
        }
    }

    @Override
    public void setConfirmPayView(confirmpayResponse response) {
        if (response.errno == 0) {

        } else {
            showTip(response.msg);
        }
    }

    @Override
    public void showTip(String msg) {
        showToast(msg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 110) {
            if (resultCode == 111) {
                setResult(10001);
                hideLoading();
                finish();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.show_order_status)
    public void onClick() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("order_status", data.order_status);
        readyGo(OrderStepActivity.class);
    }
}
