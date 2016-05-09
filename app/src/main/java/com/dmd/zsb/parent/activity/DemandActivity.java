package com.dmd.zsb.parent.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.dmd.tutor.adapter.ListViewDataAdapter;
import com.dmd.tutor.adapter.ViewHolderBase;
import com.dmd.tutor.adapter.ViewHolderCreator;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.tutor.widgets.XSwipeRefreshLayout;
import com.dmd.zsb.parent.R;
import com.dmd.zsb.api.ApiConstants;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.entity.DemandEntity;
import com.dmd.zsb.entity.response.DemandResponse;
import com.dmd.zsb.mvp.presenter.impl.DemandPresenterImpl;
import com.dmd.zsb.mvp.view.DemandView;
import com.dmd.zsb.parent.activity.base.BaseActivity;
import com.dmd.zsb.protocol.response.demandResponse;
import com.dmd.zsb.protocol.table.DemandsBean;
import com.dmd.zsb.utils.UriHelper;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.google.gson.JsonObject;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

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
    @Bind(R.id.demand_has_been_completed)
    RadioButton demandHasBeenCompleted;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.top_bar_back)
    TextView topBarBack;

    private DemandPresenterImpl demandPresenter;
    private ListViewDataAdapter<DemandsBean> mListViewAdapter;
    private int page = 1;

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
                            if (demandLevyConcentration.isChecked()) {
                                jsonObject.put("group_menu", "demandLevyConcentration");
                            } else if (demandToBeCompleted.isChecked()) {
                                jsonObject.put("group_menu", "demandToBeCompleted");
                            } else if (demandHasBeenCompleted.isChecked()) {
                                jsonObject.put("group_menu", "demandHasBeenCompleted");
                            } else {
                                jsonObject.put("group_menu", "demandLevyConcentration");
                            }
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
                        if (demandLevyConcentration.isChecked()) {
                            jsonObject.put("group_menu", "demandLevyConcentration");
                        } else if (demandToBeCompleted.isChecked()) {
                            jsonObject.put("group_menu", "demandToBeCompleted");
                        } else if (demandHasBeenCompleted.isChecked()) {
                            jsonObject.put("group_menu", "demandHasBeenCompleted");
                        }
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

                    ImageView img_header;
                    TextView tv_zjz, tv_name, tv_type, tv_sex, tv_sex2, tv_type2, tv_appointed_time, tv_charging, tv_curriculum, tv_address, tv_place, tv_state, tv_mode, tv_praise;
                    LinearLayout l_zjz, l_zjh, l_zjz_p, l_zjh_m, l_zjh_s;

                    @Override
                    public View createView(LayoutInflater layoutInflater) {
                        View view = layoutInflater.inflate(R.layout.demand_list_item, null);
                        tv_zjz = ButterKnife.findById(view, R.id.tv_zjz);
                        l_zjz = ButterKnife.findById(view, R.id.l_zjz);
                        l_zjz_p = ButterKnife.findById(view, R.id.l_zjz_p);
                        l_zjh_m = ButterKnife.findById(view, R.id.l_zjh_m);
                        l_zjh_s = ButterKnife.findById(view, R.id.l_zjh_s);
                        l_zjh = ButterKnife.findById(view, R.id.l_zjh);
                        tv_praise = ButterKnife.findById(view, R.id.tv_praise);
                        img_header = ButterKnife.findById(view, R.id.img_header);
                        tv_name = ButterKnife.findById(view, R.id.tv_name);
                        tv_mode = ButterKnife.findById(view, R.id.tv_mode);
                        tv_state = ButterKnife.findById(view, R.id.tv_state);
                        tv_type = ButterKnife.findById(view, R.id.tv_type);
                        tv_sex = ButterKnife.findById(view, R.id.tv_sex);
                        tv_type2 = ButterKnife.findById(view, R.id.tv_type2);
                        tv_sex2 = ButterKnife.findById(view, R.id.tv_sex2);
                        tv_appointed_time = ButterKnife.findById(view, R.id.tv_appointed_time);
                        tv_charging = ButterKnife.findById(view, R.id.tv_charging);
                        tv_curriculum = ButterKnife.findById(view, R.id.tv_curriculum);
                        tv_address = ButterKnife.findById(view, R.id.tv_address);
                        tv_place = ButterKnife.findById(view, R.id.tv_place);
                        return view;
                    }

                    @Override
                    public void showData(int position, DemandsBean itemData) {
                        if (demandLevyConcentration.isChecked()) {
                            tv_zjz.setVisibility(View.VISIBLE);
                            img_header.setVisibility(View.GONE);
                            l_zjh.setVisibility(View.GONE);
                            l_zjz_p.setVisibility(View.VISIBLE);
                            l_zjh_m.setVisibility(View.GONE);
                            l_zjh_s.setVisibility(View.GONE);
                            l_zjz.setVisibility(View.VISIBLE);

                            tv_type.setText(itemData.type);
                            tv_sex.setText(itemData.sex);
                            tv_praise.setText(itemData.praise);

                        } else if (demandToBeCompleted.isChecked()) {
                            tv_zjz.setVisibility(View.GONE);
                            img_header.setVisibility(View.VISIBLE);
                            l_zjh.setVisibility(View.VISIBLE);
                            l_zjz.setVisibility(View.GONE);
                            l_zjz_p.setVisibility(View.GONE);
                            l_zjh_m.setVisibility(View.VISIBLE);
                            l_zjh_s.setVisibility(View.GONE);

                            Picasso.with(mContext).load(ApiConstants.Urls.API_IMG_BASE_URLS + itemData.img_header).into(img_header);
                            tv_type2.setText(itemData.type);
                            tv_sex2.setText(itemData.sex);
                            tv_name.setText(itemData.name);
                            if (itemData.mode.equals("1")) {
                                tv_mode.setText("一对一");
                            } else if (itemData.mode.equals("2")) {
                                tv_mode.setText("一对多");
                            }

                        } else if (demandHasBeenCompleted.isChecked()) {
                            tv_zjz.setVisibility(View.GONE);
                            img_header.setVisibility(View.VISIBLE);
                            l_zjh.setVisibility(View.VISIBLE);
                            l_zjz.setVisibility(View.GONE);
                            l_zjz_p.setVisibility(View.GONE);
                            l_zjh_m.setVisibility(View.GONE);
                            l_zjh_s.setVisibility(View.VISIBLE);

                            Picasso.with(mContext).load(ApiConstants.Urls.API_IMG_BASE_URLS + itemData.img_header).into(img_header);
                            tv_type2.setText(itemData.type);
                            tv_sex2.setText(itemData.sex);
                            tv_name.setText(itemData.name);
                            if (itemData.state.equals("2")) {
                                tv_state.setText("已完成");
                            } else if (itemData.state.equals("3")) {
                                tv_state.setText("已付款");
                            }

                        }
                        tv_appointed_time.setText(itemData.appointed_time);
                        tv_charging.setText(itemData.charging);
                        tv_curriculum.setText(itemData.curriculum);
                        tv_address.setText(itemData.address);
                        tv_place.setText(itemData.place);

                    }
                };
            }
        });
        fragmentDemandListView.setAdapter(mListViewAdapter);
        fragmentDemandListView.setOnLoadMoreListener(this);
        fragmentDemandListView.setOnItemClickListener(this);

        fragmentDemandListSwipeLayout.setColorSchemeColors(
                getResources().getColor(R.color.gplus_color_1),
                getResources().getColor(R.color.gplus_color_2),
                getResources().getColor(R.color.gplus_color_3),
                getResources().getColor(R.color.gplus_color_4));
        fragmentDemandListSwipeLayout.setOnRefreshListener(this);

        demandLevyConcentration.setChecked(true);
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    public void navigateToDemandDetail(int position, DemandEntity itemData) {
        showToast("我们");
    }

    @Override
    public void refreshListData(demandResponse response) {
        if (fragmentDemandListSwipeLayout != null)
            fragmentDemandListSwipeLayout.setRefreshing(false);
        if (response != null) {
            if (response.demands.size() >= 2) {
                if (mListViewAdapter != null) {
                    mListViewAdapter.getDataList().clear();
                    mListViewAdapter.getDataList().addAll(response.demands);
                    mListViewAdapter.notifyDataSetChanged();
                }
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DemandEntity demandEntity = (DemandEntity) parent.getItemAtPosition(position);
        navigateToDemandDetail(position, demandEntity);
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
            if (demandLevyConcentration.isChecked()) {
                jsonObject.put("group_menu", "demandLevyConcentration");
            } else if (demandToBeCompleted.isChecked()) {
                jsonObject.put("group_menu", "demandToBeCompleted");
            } else if (demandHasBeenCompleted.isChecked()) {
                jsonObject.put("group_menu", "demandHasBeenCompleted");
            }
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
            if (demandLevyConcentration.isChecked()) {
                jsonObject.put("group_menu", "demandLevyConcentration");
            } else if (demandToBeCompleted.isChecked()) {
                jsonObject.put("group_menu", "demandToBeCompleted");
            } else if (demandHasBeenCompleted.isChecked()) {
                jsonObject.put("group_menu", "demandHasBeenCompleted");
            }
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

    @OnClick({R.id.top_bar_back ,R.id.demand_levy_concentration, R.id.demand_to_be_completed, R.id.demand_has_been_completed})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.demand_levy_concentration:
                JSONObject demand_levy_concentration=new JSONObject();
                try {
                    demand_levy_concentration.put("appkey", Constants.ZSBAPPKEY);
                    demand_levy_concentration.put("version", Constants.ZSBVERSION);
                    demand_levy_concentration.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                    demand_levy_concentration.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                    demand_levy_concentration.put("page", 1);
                    demand_levy_concentration.put("rows", ApiConstants.Integers.PAGE_LIMIT);
                    demand_levy_concentration.put("group_menu", "demandLevyConcentration");
                }catch (JSONException j){

                }

                demandPresenter.onDemand(Constants.EVENT_REFRESH_DATA, demand_levy_concentration);
                break;
            case R.id.demand_to_be_completed:
                JSONObject demand_to_be_completed=new JSONObject();
                try {
                    demand_to_be_completed.put("appkey", Constants.ZSBAPPKEY);
                    demand_to_be_completed.put("version", Constants.ZSBVERSION);
                    demand_to_be_completed.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                    demand_to_be_completed.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                    demand_to_be_completed.put("page", 1);
                    demand_to_be_completed.put("rows", ApiConstants.Integers.PAGE_LIMIT);
                    demand_to_be_completed.put("group_menu", "demandToBeCompleted");
                }catch (JSONException j){

                }

                demandPresenter.onDemand(Constants.EVENT_REFRESH_DATA, demand_to_be_completed);
                break;
            case R.id.demand_has_been_completed:
                JSONObject demand_has_been_completed=new JSONObject();
                try {
                    demand_has_been_completed.put("appkey", Constants.ZSBAPPKEY);
                    demand_has_been_completed.put("version", Constants.ZSBVERSION);
                    demand_has_been_completed.put("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                    demand_has_been_completed.put("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                    demand_has_been_completed.put("page", 1);
                    demand_has_been_completed.put("rows", ApiConstants.Integers.PAGE_LIMIT);
                    demand_has_been_completed.put("group_menu", "demandHasBeenCompleted");
                }catch (JSONException j){

                }

                demandPresenter.onDemand(Constants.EVENT_REFRESH_DATA, demand_has_been_completed);
                break;
        }
    }

}
