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
import com.dmd.zsb.widgets.LoadMoreListView;
import com.google.gson.JsonObject;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

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
    private ListViewDataAdapter<DemandEntity> mListViewAdapter;
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
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("appkey", Constants.ZSBAPPKEY);
                        jsonObject.addProperty("version", Constants.ZSBVERSION);
                        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                        jsonObject.addProperty("page", page);
                        jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
                        if (demandLevyConcentration.isChecked()) {
                            jsonObject.addProperty("group_menu", "demandLevyConcentration");
                        } else if (demandToBeCompleted.isChecked()) {
                            jsonObject.addProperty("group_menu", "demandToBeCompleted");
                        } else if (demandHasBeenCompleted.isChecked()) {
                            jsonObject.addProperty("group_menu", "demandHasBeenCompleted");
                        } else {
                            jsonObject.addProperty("group_menu", "demandLevyConcentration");
                        }
                        demandPresenter.onDemand(Constants.EVENT_REFRESH_DATA, jsonObject);
                    }
                }, ApiConstants.Integers.PAGE_LAZY_LOAD_DELAY_TIME_MS);
            }
        } else {
            toggleNetworkError(true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("appkey", Constants.ZSBAPPKEY);
                    jsonObject.addProperty("version", Constants.ZSBVERSION);
                    jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                    jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                    jsonObject.addProperty("page", page);
                    jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
                    if (demandLevyConcentration.isChecked()) {
                        jsonObject.addProperty("group_menu", "demandLevyConcentration");
                    } else if (demandToBeCompleted.isChecked()) {
                        jsonObject.addProperty("group_menu", "demandToBeCompleted");
                    } else if (demandHasBeenCompleted.isChecked()) {
                        jsonObject.addProperty("group_menu", "demandHasBeenCompleted");
                    }
                    demandPresenter.onDemand(Constants.EVENT_REFRESH_DATA, jsonObject);
                }
            });
        }

        mListViewAdapter = new ListViewDataAdapter<DemandEntity>(new ViewHolderCreator<DemandEntity>() {
            @Override
            public ViewHolderBase<DemandEntity> createViewHolder(int position) {
                return new ViewHolderBase<DemandEntity>() {

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
                    public void showData(int position, DemandEntity itemData) {
                        if (demandLevyConcentration.isChecked()) {
                            tv_zjz.setVisibility(View.VISIBLE);
                            img_header.setVisibility(View.GONE);
                            l_zjh.setVisibility(View.GONE);
                            l_zjz_p.setVisibility(View.VISIBLE);
                            l_zjh_m.setVisibility(View.GONE);
                            l_zjh_s.setVisibility(View.GONE);
                            l_zjz.setVisibility(View.VISIBLE);

                            tv_type.setText(itemData.getType());
                            tv_sex.setText(itemData.getSex());
                            tv_praise.setText(itemData.getPraise());

                        } else if (demandToBeCompleted.isChecked()) {
                            tv_zjz.setVisibility(View.GONE);
                            img_header.setVisibility(View.VISIBLE);
                            l_zjh.setVisibility(View.VISIBLE);
                            l_zjz.setVisibility(View.GONE);
                            l_zjz_p.setVisibility(View.GONE);
                            l_zjh_m.setVisibility(View.VISIBLE);
                            l_zjh_s.setVisibility(View.GONE);

                            Picasso.with(mContext).load(ApiConstants.Urls.API_IMG_BASE_URLS + itemData.getImg_header()).into(img_header);
                            tv_type2.setText(itemData.getType());
                            tv_sex2.setText(itemData.getSex());
                            tv_name.setText(itemData.getName());
                            if (itemData.getMode().equals("1")) {
                                tv_mode.setText("一对一");
                            } else if (itemData.getMode().equals("2")) {
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

                            Picasso.with(mContext).load(ApiConstants.Urls.API_IMG_BASE_URLS + itemData.getImg_header()).into(img_header);
                            tv_type2.setText(itemData.getType());
                            tv_sex2.setText(itemData.getSex());
                            tv_name.setText(itemData.getName());
                            if (itemData.getState().equals("2")) {
                                tv_state.setText("已完成");
                            } else if (itemData.getState().equals("3")) {
                                tv_state.setText("已付款");
                            }

                        }
                        tv_appointed_time.setText(itemData.getAppointed_time());
                        tv_charging.setText(itemData.getCharging());
                        tv_curriculum.setText(itemData.getCurriculum());
                        tv_address.setText(itemData.getAddress());
                        tv_place.setText(itemData.getPlace());

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
    public void refreshListData(DemandResponse data) {
        if (fragmentDemandListSwipeLayout != null)
            fragmentDemandListSwipeLayout.setRefreshing(false);
        if (data != null) {
            if (data.getDemandEntities().size() >= 2) {
                if (mListViewAdapter != null) {
                    mListViewAdapter.getDataList().clear();
                    mListViewAdapter.getDataList().addAll(data.getDemandEntities());
                    mListViewAdapter.notifyDataSetChanged();
                }
            }
            if (data.getTotal_page() > page)
                fragmentDemandListView.setCanLoadMore(true);
            else
                fragmentDemandListView.setCanLoadMore(false);
        }
    }

    @Override
    public void addMoreListData(DemandResponse data) {
        if (fragmentDemandListView != null)
            fragmentDemandListView.onLoadMoreComplete();
        if (data != null) {
            if (mListViewAdapter != null) {
                mListViewAdapter.getDataList().addAll(data.getDemandEntities());
                mListViewAdapter.notifyDataSetChanged();
            }
            if (data.getTotal_page() > page)
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
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("appkey", Constants.ZSBAPPKEY);
        jsonObject.addProperty("version", Constants.ZSBVERSION);
        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        jsonObject.addProperty("page", page);
        jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
        if (demandLevyConcentration.isChecked()) {
            jsonObject.addProperty("group_menu", "demandLevyConcentration");
        } else if (demandToBeCompleted.isChecked()) {
            jsonObject.addProperty("group_menu", "demandToBeCompleted");
        } else if (demandHasBeenCompleted.isChecked()) {
            jsonObject.addProperty("group_menu", "demandHasBeenCompleted");
        }
        demandPresenter.onDemand(Constants.EVENT_LOAD_MORE_DATA, jsonObject);
    }

    @Override
    public void onRefresh() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("appkey", Constants.ZSBAPPKEY);
        jsonObject.addProperty("version", Constants.ZSBVERSION);
        jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
        jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
        jsonObject.addProperty("page", 1);
        jsonObject.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
        if (demandLevyConcentration.isChecked()) {
            jsonObject.addProperty("group_menu", "demandLevyConcentration");
        } else if (demandToBeCompleted.isChecked()) {
            jsonObject.addProperty("group_menu", "demandToBeCompleted");
        } else if (demandHasBeenCompleted.isChecked()) {
            jsonObject.addProperty("group_menu", "demandHasBeenCompleted");
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
                JsonObject demand_levy_concentration = new JsonObject();
                demand_levy_concentration.addProperty("appkey", Constants.ZSBAPPKEY);
                demand_levy_concentration.addProperty("version", Constants.ZSBVERSION);
                demand_levy_concentration.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                demand_levy_concentration.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                demand_levy_concentration.addProperty("page", 1);
                demand_levy_concentration.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
                demand_levy_concentration.addProperty("group_menu", "demandLevyConcentration");
                demandPresenter.onDemand(Constants.EVENT_REFRESH_DATA, demand_levy_concentration);
                break;
            case R.id.demand_to_be_completed:
                JsonObject demand_to_be_completed = new JsonObject();
                demand_to_be_completed.addProperty("appkey", Constants.ZSBAPPKEY);
                demand_to_be_completed.addProperty("version", Constants.ZSBVERSION);
                demand_to_be_completed.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                demand_to_be_completed.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                demand_to_be_completed.addProperty("page", 1);
                demand_to_be_completed.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
                demand_to_be_completed.addProperty("group_menu", "demandToBeCompleted");
                demandPresenter.onDemand(Constants.EVENT_REFRESH_DATA, demand_to_be_completed);
                break;
            case R.id.demand_has_been_completed:
                JsonObject demand_has_been_completed = new JsonObject();
                demand_has_been_completed.addProperty("appkey", Constants.ZSBAPPKEY);
                demand_has_been_completed.addProperty("version", Constants.ZSBVERSION);
                demand_has_been_completed.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                demand_has_been_completed.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                demand_has_been_completed.addProperty("page", 1);
                demand_has_been_completed.addProperty("rows", ApiConstants.Integers.PAGE_LIMIT);
                demand_has_been_completed.addProperty("group_menu", "demandHasBeenCompleted");
                demandPresenter.onDemand(Constants.EVENT_REFRESH_DATA, demand_has_been_completed);
                break;
        }
    }

}
