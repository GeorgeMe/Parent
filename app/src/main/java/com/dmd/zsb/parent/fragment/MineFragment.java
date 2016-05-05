package com.dmd.zsb.parent.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmd.dialog.MaterialDialog;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.parent.R;
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.mvp.presenter.impl.MinePresenterImpl;
import com.dmd.zsb.mvp.view.MineView;
import com.dmd.zsb.parent.activity.AboutUsActivity;
import com.dmd.zsb.parent.activity.DemandActivity;
import com.dmd.zsb.parent.activity.EvaluationActivity;
import com.dmd.zsb.parent.activity.OrderActivity;
import com.dmd.zsb.parent.activity.SettingActivity;
import com.dmd.zsb.parent.activity.SignInActivity;
import com.dmd.zsb.parent.activity.VouchersActivity;
import com.dmd.zsb.parent.activity.WalletActivity;
import com.dmd.zsb.parent.activity.base.BaseFragment;
import com.dmd.zsb.protocol.response.mineResponse;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.OnClick;


public class MineFragment extends BaseFragment implements MineView {

    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.mine_sign_in)
    TextView mineSignIn;
    @Bind(R.id.mine_sign_out_header)
    LinearLayout mineSignOutHeader;
    @Bind(R.id.mine_header_img)
    ImageView mineHeaderImg;
    @Bind(R.id.mine_name)
    TextView mineName;
    @Bind(R.id.mine_address)
    TextView mineAddress;
    @Bind(R.id.mine_grade)
    TextView mineGrade;
    @Bind(R.id.mine_subjects)
    TextView mineSubjects;
    @Bind(R.id.mine_modify_data)
    TextView mineModifyData;
    @Bind(R.id.mine_logout_header)
    LinearLayout mineLogoutHeader;
    @Bind(R.id.mine_wallet)
    LinearLayout mineWallet;
    @Bind(R.id.mine_order)
    LinearLayout mineOrder;
    @Bind(R.id.mine_evaluation)
    TextView mineEvaluation;
    @Bind(R.id.mine_demand)
    TextView mineDemand;
    @Bind(R.id.mine_vouchers)
    TextView mineVouchers;
    @Bind(R.id.mine_about_us)
    TextView mineAboutUs;
    @Bind(R.id.mine_switch_account)
    TextView mineSwitchAccount;
    @Bind(R.id.mine_sign_out)
    TextView mineSignOut;

    private MinePresenterImpl minePresenter;
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        topBarBack.setVisibility(View.GONE);
        topBarTitle.setText("我的");
        if (XmlDB.getInstance(mContext).getKeyBooleanValue("isLogin", false)){
            mineSignOutHeader.setVisibility(View.GONE);
            mineLogoutHeader.setVisibility(View.VISIBLE);
            minePresenter=new MinePresenterImpl(mContext,this);
            minePresenter.onMineInfo();
        }else {
            mineSignOutHeader.setVisibility(View.VISIBLE);
            mineLogoutHeader.setVisibility(View.GONE);
        }
    }

    @Override
    public void setView(mineResponse response) {
        Picasso.with(mContext).load(ApiConstants.Urls.API_IMG_BASE_URLS+response.mineHeaderImg).into(mineHeaderImg);
        mineName.setText(response.mineName);
        mineAddress.setText(response.mineAddress);
        mineGrade.setText(response.mineGrade);
        mineSubjects.setText(response.mineSubjects);

    }

    @Override
    public void showTip(String msg) {
        showToast(msg);
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Subscribe
    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @OnClick({R.id.mine_sign_in, R.id.mine_modify_data, R.id.mine_wallet, R.id.mine_order, R.id.mine_evaluation, R.id.mine_demand, R.id.mine_vouchers, R.id.mine_about_us, R.id.mine_switch_account, R.id.mine_sign_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_sign_in:
                readyGo(SignInActivity.class);
                break;
            case R.id.mine_modify_data:
                readyGo(SettingActivity.class);
                break;
            case R.id.mine_wallet:
                readyGo(WalletActivity.class);
                break;
            case R.id.mine_order:
                readyGo(OrderActivity.class);
                break;
            case R.id.mine_evaluation:
                readyGo(EvaluationActivity.class);
                break;
            case R.id.mine_demand:
                readyGo(DemandActivity.class);
                break;
            case R.id.mine_vouchers:
                readyGo(VouchersActivity.class);
                break;
            case R.id.mine_about_us:
                readyGo(AboutUsActivity.class);
                break;
            case R.id.mine_switch_account:
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.title)
                        .content("切换账户")
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .show();
                break;
            case R.id.mine_sign_out:
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.title)
                        .content("退出登录")
                        .positiveText(R.string.agree)
                        .negativeText(R.string.disagree)
                        .show();
                break;
        }
    }
}
