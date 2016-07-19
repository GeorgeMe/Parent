package com.dmd.zsb.parent.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dmd.dialog.DialogAction;
import com.dmd.dialog.MaterialDialog;
import com.dmd.tutor.adapter.ListViewDataAdapter;
import com.dmd.tutor.adapter.ViewHolderBase;
import com.dmd.tutor.adapter.ViewHolderCreator;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.tutor.widgets.XSwipeRefreshLayout;
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.mvp.presenter.impl.DemandPresenterImpl;
import com.dmd.zsb.mvp.presenter.impl.WorkDonePresenterImpl;
import com.dmd.zsb.mvp.view.DemandView;
import com.dmd.zsb.protocol.response.demandResponse;
import com.dmd.zsb.protocol.response.workdoneResponse;
import com.dmd.zsb.protocol.table.DemandsBean;
import com.dmd.zsb.parent.R;
import com.dmd.zsb.parent.activity.base.BaseActivity;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.squareup.otto.Subscribe;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DemandActivity extends BaseActivity implements DemandView, LoadMoreListView.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {


    @Bind(R.id.demand_menu_group)
    RadioGroup demandMenuGroup;
    @Bind(R.id.fragment_demand_list_view)
    LoadMoreListView fragmentDemandListView;
    @Bind(R.id.fragment_demand_list_swipe_layout)
    XSwipeRefreshLayout fragmentDemandListSwipeLayout;
    @Bind(R.id.demand_levy_concentration)
    RadioButton demandLevyConcentration;
    @Bind(R.id.demand_to_be_completed)
    RadioButton demandToBeCompleted;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.top_bar_back)
    TextView topBarBack;

    private DemandPresenterImpl demandPresenter;
    private WorkDonePresenterImpl workDonePresenter;
    private ListViewDataAdapter<DemandsBean> mListViewAdapter;
    private int page = 1;
    private int order_status = 0;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_demand;
    }

    @Subscribe
    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    protected View getLoadingTargetView() {
        return fragmentDemandListSwipeLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        topBarTitle.setText("我的需求");
        demandLevyConcentration.setChecked(true);
        demandPresenter = new DemandPresenterImpl(mContext, this);

        if (NetUtils.isNetworkConnected(mContext)) {
            if (null != fragmentDemandListSwipeLayout) {
                fragmentDemandListSwipeLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject=new JSONObject();
                        try {
                            jsonObject.put("appkey", Constants.ZSBAPPKEY);
                            jsonObject.put("version", Constants.ZSBVERSION);
                            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                            jsonObject.put("page", page);
                            jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);
                            jsonObject.put("sort", 0);
                            jsonObject.put("order_status", order_status);
                        }catch (JSONException j){

                        }

                        demandPresenter.onDemand(Constants.EVENT_REFRESH_DATA, jsonObject);
                    }
                }, ApiConstants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
            }
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject jsonObject=new JSONObject();
                    try {
                        jsonObject.put("appkey", Constants.ZSBAPPKEY);
                        jsonObject.put("version", Constants.ZSBVERSION);
                        jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                        jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                        jsonObject.put("page", page);
                        jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);
                        jsonObject.put("sort", 0);
                        jsonObject.put("order_status", order_status);
                    }catch (JSONException j){

                    }

                    demandPresenter.onDemand(Constants.EVENT_REFRESH_DATA, jsonObject);
                }
            });
        }

        mListViewAdapter = new ListViewDataAdapter<DemandsBean>(new ViewHolderCreator<DemandsBean>() {

            @Override
            public ViewHolderBase<DemandsBean> createViewHolder(int position) {
                return new ViewHolderBase<DemandsBean>() {
                    TextView tv_oid,
                            tv_pid,
                            tv_location,
                            tv_offer_price,
                            tv_appointment_time,
                            tv_text,
                            tv_order_status,
                            tv_subid;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View view = layoutInflater.inflate(R.layout.tutor_demand_list_item, null);
                        tv_oid = ButterKnife.findById(view, R.id.tv_oid);
                        tv_pid = ButterKnife.findById(view, R.id.tv_pid);
                        tv_location = ButterKnife.findById(view, R.id.tv_location);
                        tv_offer_price = ButterKnife.findById(view, R.id.tv_offer_price);
                        tv_appointment_time = ButterKnife.findById(view, R.id.tv_appointment_time);
                        tv_text = ButterKnife.findById(view, R.id.tv_text);
                        tv_order_status = ButterKnife.findById(view, R.id.tv_order_status);
                        tv_subid = ButterKnife.findById(view, R.id.tv_subid);
                        return view;
                    }

                    @Override
                    public void showData(int position, DemandsBean itemData) {
                        tv_oid.setText(itemData.oid);
                        tv_pid.setText(itemData.pid);
                        tv_location.setText(itemData.location);
                        tv_offer_price.setText(itemData.offer_price);
                        tv_appointment_time.setText(itemData.appointment_time);
                        tv_text.setText(itemData.text);

                        if (itemData.order_status==0){
                            tv_order_status.setText("征集中");
                        }else if (itemData.order_status==1){
                            tv_order_status.setText("已接单");
                        }else if (itemData.order_status==2){
                            tv_order_status.setText("");
                        }else if (itemData.order_status==3){
                            tv_order_status.setText("");
                        }else if (itemData.order_status==4){
                            tv_order_status.setText("");
                        }else if (itemData.order_status==5){
                            tv_order_status.setText("");
                        }else if (itemData.order_status==6){
                            tv_order_status.setText("");
                        }
                        tv_subid.setText(itemData.subid);
                        //teacher_distance.setText(LocationManager.getDistance(Double.parseDouble(itemData.lat), Double.parseDouble(itemData.lon)));
                    }
                };
            }
        });
        demandLevyConcentration.setChecked(true);
        fragmentDemandListView.setAdapter(mListViewAdapter);
        fragmentDemandListView.setOnLoadMoreListener(this);
        fragmentDemandListView.setOnItemClickListener(this);

        fragmentDemandListSwipeLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        fragmentDemandListSwipeLayout.setOnRefreshListener(this);


    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    public void navigateToDemandDetail(int position, DemandsBean itemData) {
        if (itemData != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("demand", itemData);
            readyGo(DemandDetailActivity.class, bundle);
        }
    }
    private void workdonepush(DemandsBean demandsBean) {
        JSONObject workdone = new JSONObject();
        try {
            workdone.put("appkey", Constants.ZSBAPPKEY);
            workdone.put("version", Constants.ZSBVERSION);
            workdone.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
            workdone.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
            workdone.put("oid", demandsBean.oid);
            workdone.put("order_status", demandsBean.order_status);
        } catch (JSONException j) {

        }
        workDonePresenter=new WorkDonePresenterImpl(mContext,this);
        workDonePresenter.onWorkDone(workdone);
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final DemandsBean demandsBean = (DemandsBean) parent.getAdapter().getItem(position);
        if (demandToBeCompleted.isChecked()){
            if (demandsBean.order_status==9){//1
                new MaterialDialog.Builder(mContext).content("确定授课完成了吗？").positiveText("确定").negativeText("取消").onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //提交的参数封装
                        workdonepush(demandsBean);
                        dialog.dismiss();
                    }


                }).show();
            }else{
                //showToast("数据错误");
            }

        }else if (demandLevyConcentration.isChecked()){
            if (demandsBean.order_status==9){//0
                navigateToDemandDetail(position,demandsBean);
            }else{
                //showToast("数据错误");
            }
        }

    }

    @Override
    public void refreshListData(demandResponse response) {
        if (fragmentDemandListSwipeLayout != null)
            fragmentDemandListSwipeLayout.setRefreshing(false);
        if (response != null) {
            if (response.demands.size() >= 1) {
                if (mListViewAdapter != null) {
                    mListViewAdapter.getDataList().clear();
                    mListViewAdapter.getDataList().addAll(response.demands);
                    mListViewAdapter.notifyDataSetChanged();
                }
            }else {
                mListViewAdapter.getDataList().clear();
                mListViewAdapter.notifyDataSetChanged();
            }
            if (UriHelper.getInstance().calculateTotalPages(response.total_count) > page)
                fragmentDemandListView.setCanLoadMore(true);
            else
                fragmentDemandListView.setCanLoadMore(false);
        }
    }

    @Override
    public void addMoreListData(demandResponse response) {
        if (fragmentDemandListView != null)
            fragmentDemandListView.onLoadMoreComplete();
        if (response != null) {
            if (mListViewAdapter != null) {
                mListViewAdapter.getDataList().addAll(response.demands);
                mListViewAdapter.notifyDataSetChanged();
            }
            if (UriHelper.getInstance().calculateTotalPages(response.total_count) > page)
                fragmentDemandListView.setCanLoadMore(true);
            else
                fragmentDemandListView.setCanLoadMore(false);
        }
    }

    @Override
    public void onLoadMore() {
        page = page + 1;
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("appkey", Constants.ZSBAPPKEY);
            jsonObject.put("version", Constants.ZSBVERSION);
            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
            jsonObject.put("page", page);
            jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);
            jsonObject.put("sort", 0);
            jsonObject.put("order_status", order_status);
        }catch (JSONException j){

        }

        demandPresenter.onDemand(Constants.EVENT_LOAD_MORE_DATA, jsonObject);
    }

    @Override
    public void onRefresh() {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("appkey", Constants.ZSBAPPKEY);
            jsonObject.put("version", Constants.ZSBVERSION);
            jsonObject.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
            jsonObject.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
            jsonObject.put("page", 1);
            jsonObject.put("rows", ApiConstants.Integers.PAGE_LIMIT);
            jsonObject.put("sort", 0);
            jsonObject.put("order_status", order_status);
        }catch (JSONException j){

        }

        demandPresenter.onDemand(Constants.EVENT_REFRESH_DATA, jsonObject);
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

    @OnClick({R.id.top_bar_back ,R.id.demand_levy_concentration, R.id.demand_to_be_completed})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.demand_levy_concentration:
                mListViewAdapter.getDataList().clear();
                mListViewAdapter.notifyDataSetChanged();
                JSONObject demand_levy_concentration=new JSONObject();
                try {
                    order_status=0;
                    demand_levy_concentration.put("appkey", Constants.ZSBAPPKEY);
                    demand_levy_concentration.put("version", Constants.ZSBVERSION);
                    demand_levy_concentration.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                    demand_levy_concentration.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                    demand_levy_concentration.put("page", 1);
                    demand_levy_concentration.put("rows", ApiConstants.Integers.PAGE_LIMIT);
                    demand_levy_concentration.put("sort", 0);
                    demand_levy_concentration.put("order_status", order_status);
                }catch (JSONException j){

                }

                demandPresenter.onDemand(Constants.EVENT_REFRESH_DATA, demand_levy_concentration);
                break;
            case R.id.demand_to_be_completed:
                mListViewAdapter.getDataList().clear();
                mListViewAdapter.notifyDataSetChanged();
                JSONObject demand_to_be_completed=new JSONObject();
                try {
                    order_status=1;
                    demand_to_be_completed.put("appkey", Constants.ZSBAPPKEY);
                    demand_to_be_completed.put("version", Constants.ZSBVERSION);
                    demand_to_be_completed.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                    demand_to_be_completed.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                    demand_to_be_completed.put("page", 1);
                    demand_to_be_completed.put("rows", ApiConstants.Integers.PAGE_LIMIT);
                    demand_to_be_completed.put("sort", 0);
                    demand_to_be_completed.put("order_status", order_status);
                }catch (JSONException j){

                }

                demandPresenter.onDemand(Constants.EVENT_REFRESH_DATA, demand_to_be_completed);
                break;
        }
    }

    @Override
    public void workdone(workdoneResponse response) {
        if (response.errno==0){
            this.onRefresh();
        }else {
            showToast(response.msg);
        }
    }
}
