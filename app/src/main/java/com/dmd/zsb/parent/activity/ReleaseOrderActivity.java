package com.dmd.zsb.parent.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dmd.dialog.AlertDialogWrapper;
import com.dmd.tutor.eventbus.EventCenter;
import com.dmd.tutor.netstatus.NetUtils;
import com.dmd.tutor.timepicker.ScreenInfo;
import com.dmd.tutor.timepicker.WheelMain;
import com.dmd.tutor.utils.TLog;
import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.db.ZSBDataBase;
import com.dmd.zsb.db.dao.GradeDao;
import com.dmd.zsb.db.dao.SubjectDao;
import com.dmd.zsb.entity.GradeEntity;
import com.dmd.zsb.entity.SubjectEntity;
import com.dmd.zsb.mvp.presenter.impl.ReleaseOrderPresenterImpl;
import com.dmd.zsb.mvp.view.ReleaseOrderView;
import com.dmd.zsb.parent.R;
import com.dmd.zsb.parent.activity.base.BaseActivity;
import com.dmd.zsb.parent.adapter.SeekGradeAdapter;
import com.dmd.zsb.parent.adapter.SeekSubjectAdapter;
import com.dmd.zsb.widgets.ToastView;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReleaseOrderActivity extends BaseActivity implements ReleaseOrderView {


    @Bind(R.id.publish_order_price)
    EditText publishOrderPrice;
    @Bind(R.id.publish_order_time)
    TextView publishOrderTime;
    @Bind(R.id.publish_order_location)
    EditText publishOrderLocation;
    @Bind(R.id.publish_order_text)
    EditText publishOrderText;
    @Bind(R.id.publish_order_publish)
    Button publishOrderPublish;
    @Bind(R.id.top_bar_back)
    TextView topBarBack;
    @Bind(R.id.top_bar_title)
    TextView topBarTitle;
    @Bind(R.id.publish_order_subject)
    EditText publishOrderSubject;
    private SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private WheelMain mWheelMain;
    private ListView seek_list_view_grade, seek_list_view_subject;
    private SeekGradeAdapter seekGradeAdapter;
    private SeekSubjectAdapter seekSubjectAdapter;
    private ReleaseOrderPresenterImpl releaseOrderPresenter;
    private String default_receiver_id = null;
    private String subid = "";
    private int screenWidth;
    private int screenHeight;
    @Override
    protected void getBundleExtras(Bundle extras) {
        default_receiver_id = extras.getString("default_receiver_id", "");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_release_order;
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
        initScreenWidth();
        topBarTitle.setText(getResources().getText(R.string.home_bar_demand));
        releaseOrderPresenter = new ReleaseOrderPresenterImpl(mContext, this);
        Date date = new Date();
        publishOrderTime.setText(mFormat.format(date));
        publishOrderLocation.setText(XmlDB.getInstance(mContext).getKeyString("addr", ""));
        //publishOrderText.setVisibility(View.GONE);
        //设置光标靠右
        CharSequence charSequencePirce = publishOrderPrice.getText();
        if (charSequencePirce instanceof Spannable) {
            Spannable spanText = (Spannable) charSequencePirce;
            Selection.setSelection(spanText, charSequencePirce.length());
        }
        CharSequence charSequenceLocation = publishOrderLocation.getText();
        if (charSequenceLocation instanceof Spannable) {
            Spannable spanText = (Spannable) charSequenceLocation;
            Selection.setSelection(spanText, charSequenceLocation.length());
        }
        publishOrderPrice.clearFocus();
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

    @OnClick({R.id.top_bar_back, R.id.publish_order_time, R.id.publish_order_publish,R.id.publish_order_subject})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top_bar_back:
                finish();
                break;
            case R.id.publish_order_subject:
                appointmentSubject();
                break;
            case R.id.publish_order_time:
                appointmentTime();
                break;
            case R.id.publish_order_publish:

                int num = 0;
                try {    // 判断预约时间是否大于当前时间
                    Date date = new Date();
                    Date date1 = mFormat.parse(mFormat.format(date));
                    Date date2 = mFormat.parse(publishOrderTime.getText().toString());
                    num = date2.compareTo(date1);

                    if (num < 0) {
                        long diff = date1.getTime() - date2.getTime();
                        long mins = diff / (1000 * 60);
                        if (mins < 3) {
                            num = 1;
                        }
                    }
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (publishOrderPrice.getText().toString().equals("")) {
                    showToast(getString(R.string.price_range));
                } else if (publishOrderPrice.getText().toString().equals("0.")) {
                    showToast(getString(R.string.right_price));
                } else if (publishOrderTime.getText().toString().equals("")) {
                    showToast(getString(R.string.appoint_time));
                } else if (num < 0) {
                    showToast(getString(R.string.wrong_appoint_time_hint));
                } else if (publishOrderLocation.getText().toString().equals("")) {
                    showToast(getString(R.string.appoint_location_hint));
                } else {
                    publishOrderPublish.setClickable(false);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("appkey", Constants.ZSBAPPKEY);
                    jsonObject.addProperty("version", Constants.ZSBVERSION);
                    jsonObject.addProperty("sid", XmlDB.getInstance(mContext).getKeyString("sid", "sid"));
                    jsonObject.addProperty("uid", XmlDB.getInstance(mContext).getKeyString("uid", "uid"));
                    jsonObject.addProperty("lon", XmlDB.getInstance(mContext).getKeyFloatValue("longitude", 0));
                    jsonObject.addProperty("lat", XmlDB.getInstance(mContext).getKeyFloatValue("latitude", 0));
                    jsonObject.addProperty("location", publishOrderLocation.getText().toString());
                    jsonObject.addProperty("offer_price", publishOrderPrice.getText().toString());
                    jsonObject.addProperty("appointment_time", publishOrderTime.getText().toString());
                    jsonObject.addProperty("text", publishOrderText.getText().toString());
                    jsonObject.addProperty("subid", subid);
                    jsonObject.addProperty("default_receiver_id", default_receiver_id);
                    releaseOrderPresenter.onReleaseOrder(jsonObject);
                }
                break;
        }
    }


    @Override
    public void showSuccessView(JsonObject data) {
        publishOrderPublish.setClickable(true);
        if (data.get("errno").getAsInt()==0){
            ToastView toast = new ToastView(mContext, "发布成功");
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public void showTip(String msg) {
        publishOrderPublish.setClickable(true);
        showToast(msg);
    }

    // 关闭键盘
    public void closeKeyBoard() {
        publishOrderPrice.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(publishOrderPrice.getWindowToken(), 0);
    }
    private void appointmentSubject(){
        onCreateCoursePopWindow(publishOrderSubject);
    }
    private void appointmentTime() {
        closeKeyBoard();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        final View timepickerview = inflater.inflate(R.layout.timepicker, null);
        ScreenInfo screenInfo = new ScreenInfo(this);
        mWheelMain = new WheelMain(timepickerview, true);
        mWheelMain.screenheight = screenInfo.getHeight();
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(mFormat.parse(publishOrderTime.getText().toString()));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        mWheelMain.initDateTimePicker(year, month, day, hour, min);
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.choose_time))
                .setView(timepickerview)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        publishOrderTime.setText(mWheelMain.getTime());
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
    //课程
    public void onCreateCoursePopWindow(View view) {
        final PopupWindow popupWindow = new PopupWindow(mContext);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.seek_menu_class_popupwindow, null);
        seek_list_view_grade = (ListView) contentView.findViewById(R.id.seek_list_view_grade);
        seek_list_view_subject = (ListView) contentView.findViewById(R.id.seek_list_view_subject);

        if (seek_list_view_grade.getVisibility() == View.INVISIBLE) {
            seek_list_view_grade.setVisibility(View.VISIBLE);
        }
        seekGradeAdapter = new SeekGradeAdapter(getGrades(), mContext);
        seek_list_view_grade.setAdapter(seekGradeAdapter);
        seek_list_view_grade.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (parent.getAdapter() instanceof SeekGradeAdapter) {

                    if (seek_list_view_subject.getVisibility() == View.INVISIBLE) {
                        seek_list_view_subject.setVisibility(View.VISIBLE);
                    }

                    if (!getSubjects(getGrades().get(position).getGrade_id()).isEmpty()) {
                        seekSubjectAdapter = new SeekSubjectAdapter(getSubjects(getGrades().get(position).getGrade_id()), mContext);
                        seek_list_view_subject.setAdapter(seekSubjectAdapter);
                        seekSubjectAdapter.notifyDataSetChanged();
                        seek_list_view_subject.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                //请求数据
                                publishOrderSubject.setText(((SubjectEntity) parent.getAdapter().getItem(position)).getSub_name());
                                subid=((SubjectEntity) parent.getAdapter().getItem(position)).getSub_id();
                                popupWindow.dismiss();
                            }
                        });
                    }
                }
            }
        });


        popupWindow.setWidth(screenWidth);
        popupWindow.setHeight(screenHeight * 4);
        popupWindow.setContentView(contentView);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new PaintDrawable());
        popupWindow.showAsDropDown(view);
    }
    private void initScreenWidth() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenHeight = dm.heightPixels / 10;
        screenWidth = dm.widthPixels;
        TLog.v("屏幕宽高", "宽度" + screenWidth + "高度" + screenHeight);
    }

    private List<GradeEntity> getGrades() {
        GradeDao gradeDao = new GradeDao(ZSBDataBase.getInstance(mContext));
        return gradeDao.getGrades();
    }

    private List<SubjectEntity> getSubjects(String grade_id) {
        SubjectDao subjectDao = new SubjectDao(ZSBDataBase.getInstance(mContext));
        return subjectDao.getGrades(grade_id);
    }

}
