package com.dmd.zsb.parent.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.CommentSendPresenterImpl;
import com.dmd.zsb.mvp.view.CommentSendView;
import com.dmd.zsb.parent.R;
import com.dmd.zsb.parent.activity.base.BaseActivity;
import com.dmd.zsb.protocol.response.commentsendResponse;
import com.dmd.zsb.protocol.table.EvaluationsBean;
import com.dmd.zsb.widgets.ToastView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

public class EvaluationDetailActivity extends BaseActivity implements CommentSendView {

    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.tv_teacher)
    TextView tvTeacher;
    @Bind(R.id.tv_parent)
    TextView tvParent;
    @Bind(R.id.tv_appointed_time)
    TextView tvAppointedTime;
    @Bind(R.id.tv_offer_price)
    TextView tvOfferPrice;
    @Bind(R.id.tv_subject)
    TextView tvSubject;
    @Bind(R.id.tv_text)
    TextView tvText;
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_rank)
    TextView tvRank;
    @Bind(R.id.d12_rank_layout)
    LinearLayout d12RankLayout;
    @Bind(R.id.e12_comment_content)
    EditText e12CommentContent;
    @Bind(R.id.d12_publish_button)
    TextView d12PublishButton;
    @Bind(R.id.d12_star_one)
    ImageView d12StarOne;
    @Bind(R.id.d12_star_two)
    ImageView d12StarTwo;
    @Bind(R.id.d12_star_three)
    ImageView d12StarThree;
    @Bind(R.id.d12_star_four)
    ImageView d12StarFour;
    @Bind(R.id.d12_star_five)
    ImageView d12StarFive;

    private int mCommentRank = 0;
    private EvaluationsBean evaluations;
    private CommentSendPresenterImpl commentSendPresenter;

    @Override
    protected void getBundleExtras(Bundle extras) {
        evaluations = (EvaluationsBean) extras.getSerializable("evaluations");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_evaluation_detail;
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
        topBarTitle.setText("评价订单");
        tvContent.setVisibility(View.GONE);
        tvRank.setVisibility(View.GONE);
        if (evaluations != null) {
            tvTeacher.setText(evaluations.teacher);
            tvParent.setText(evaluations.parent);
            tvAppointedTime.setText(evaluations.appointed_time);
            tvOfferPrice.setText(evaluations.offer_price);
            tvSubject.setText(evaluations.subject);
            tvText.setText(evaluations.text);
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

    @OnClick({R.id.top_bar_back, R.id.d12_publish_button,R.id.d12_star_one, R.id.d12_star_two, R.id.d12_star_three, R.id.d12_star_four, R.id.d12_star_five})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.d12_star_one:
                selectRank(1);
                break;
            case R.id.d12_star_two:
                selectRank(2);
                break;
            case R.id.d12_star_three:
                selectRank(3);
                break;
            case R.id.d12_star_four:
                selectRank(4);
                break;
            case R.id.d12_star_five:
                selectRank(5);
                break;
            case R.id.d12_publish_button:
                if (mCommentRank == 0)
                {
                    ToastView toast = new ToastView(this, "评论星级");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }else {
                    try {
                        commentSendPresenter =new CommentSendPresenterImpl(mContext,this);
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put("appkey", Constants.ZSBAPPKEY);
                        jsonObject.put("version", Constants.ZSBVERSION);
                        jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                        jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                        jsonObject.put("cid",evaluations.cid);
                        jsonObject.put("content",e12CommentContent.getText().toString());
                        jsonObject.put("rank",mCommentRank);
                        commentSendPresenter.onCommentSend(jsonObject);
                    }catch (JSONException j){

                    }
                }
                break;
        }
    }

    private void selectRank(int rank) {
        mCommentRank = rank;
        d12StarOne.setImageResource(R.drawable.b7_star_off);
        d12StarTwo.setImageResource(R.drawable.b7_star_off);
        d12StarThree.setImageResource(R.drawable.b7_star_off);
        d12StarFour.setImageResource(R.drawable.b7_star_off);
        d12StarFive.setImageResource(R.drawable.b7_star_off);
        if (rank > 0) {
            d12StarOne.setImageResource(R.drawable.b7_star_on);
        }
        if (rank > 1) {
            d12StarTwo.setImageResource(R.drawable.b7_star_on);
        }
        if (rank > 2) {
            d12StarThree.setImageResource(R.drawable.b7_star_on);
        }
        if (rank > 3) {
            d12StarFour.setImageResource(R.drawable.b7_star_on);
        }
        if (rank > 4) {
            d12StarFive.setImageResource(R.drawable.b7_star_on);
        }
    }

    @Override
    public void onCommentSendView(commentsendResponse response) {
        if (response.errno==0){
           // showToast("评价成功");
            setResult(10003);
            finish();
        }else {
            showToast("评价失败");
        }

    }
}
