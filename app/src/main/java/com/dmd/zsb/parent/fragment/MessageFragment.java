package com.dmd.zsb.parent.fragment;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.widgets.XSwipeRefreshLayout;
import com.dmd.zsb.parent.R;
import com.dmd.zsb.parent.activity.base.BaseFragment;
import com.dmd.zsb.widgets.LoadMoreListView;
import com.squareup.otto.Subscribe;

import butterknife.Bind;

public class MessageFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.message_group_menu_message)
    RadioButton messageGroupMenuMessage;
    @Bind(R.id.message_group_menu_recent_contacts)
    RadioButton messageGroupMenuRecentContacts;
    @Bind(R.id.message_group_menu_attention)
    RadioButton messageGroupMenuAttention;
    @Bind(R.id.message_menu_group)
    RadioGroup messageMenuGroup;
    @Bind(R.id.fragment_message_list_list_view)
    LoadMoreListView fragmentMessageListListView;
    @Bind(R.id.fragment_message_list_swipe_layout)
    XSwipeRefreshLayout fragmentMessageListSwipeLayout;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_message;
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
        return fragmentMessageListSwipeLayout;
    }

    @Override
    protected void initViewsAndEvents() {
        messageGroupMenuMessage.setChecked(true);
        messageGroupMenuMessage.setOnClickListener(this);
        messageGroupMenuRecentContacts.setOnClickListener(this);
        messageGroupMenuAttention.setOnClickListener(this);
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Subscribe
    @Override
    public void onEventComming(EventCenter eventCenter) {

    }

    @Override
    public void onClick(View v) {
        if (messageGroupMenuMessage==v){

        }else if (messageGroupMenuRecentContacts==v){

        }else if (messageGroupMenuAttention==v){

        }
    }
}
