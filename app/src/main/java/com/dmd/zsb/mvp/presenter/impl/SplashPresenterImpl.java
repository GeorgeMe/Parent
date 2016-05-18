package com.dmd.zsb.mvp.presenter.impl;

import android.content.Context;
import android.util.Log;
import android.view.animation.Animation;

import com.dmd.tutor.utils.XmlDB;
import com.dmd.zsb.common.Constants;
import com.dmd.zsb.db.ZSBDataBase;
import com.dmd.zsb.db.dao.GradeDao;
import com.dmd.zsb.db.dao.SubjectDao;
import com.dmd.zsb.entity.response.SplashResponse;
import com.dmd.zsb.mvp.interactor.SplashInteractor;
import com.dmd.zsb.mvp.interactor.impl.SplashInteractorImpl;
import com.dmd.zsb.mvp.listeners.BaseSingleLoadedListener;
import com.dmd.zsb.mvp.presenter.Presenter;
import com.dmd.zsb.mvp.presenter.SplashPresenter;
import com.dmd.zsb.mvp.view.SplashView;
import com.dmd.zsb.protocol.request.initdataRequest;
import com.dmd.zsb.protocol.response.initdataResponse;

import org.json.JSONException;


public class SplashPresenterImpl implements SplashPresenter, Presenter, BaseSingleLoadedListener<initdataResponse> {

    private Context mContext = null;
    private SplashView mSplashView = null;
    private SplashInteractor mSplashInteractor = null;
    private GradeDao gradeDao;
    private SubjectDao subjectDao;

    public SplashPresenterImpl(Context context, SplashView splashView) {
        if (null == splashView) {
            throw new IllegalArgumentException("Constructor's parameters must not be Null");
        }
        gradeDao = new GradeDao(ZSBDataBase.getInstance(context));
        subjectDao = new SubjectDao(ZSBDataBase.getInstance(context));
        mContext = context;
        mSplashView = splashView;
        mSplashInteractor = new SplashInteractorImpl(this);
    }

    @Override
    public void initialized() {
        mSplashView.initializeUmengConfig();
        mSplashView.initializeViews(mSplashInteractor.getVersionName(mContext),
                mSplashInteractor.getCopyright(mContext),
                mSplashInteractor.getBackgroundImageResID());
        if (XmlDB.getInstance(mContext).getKeyBooleanValue("isFirstRunLead", true)) {
            mSplashView.navigateToLead();
        } else {
            Animation animation = mSplashInteractor.getBackgroundImageAnimation(mContext);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    loadingInitData();
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    //计时 5秒后进入主页
                  //  mSplashView.navigateToHomePage();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mSplashView.animateBackgroundImage(animation);
        }

    }

    @Override
    public void loadingInitData() {
        try{
            initdataRequest request=new initdataRequest();
            request.appkey= Constants.ZSBAPPKEY;
            request.version=Constants.ZSBVERSION;
            mSplashInteractor.loadingInitData(request.toJson());
        }catch (JSONException j){

        }
    }

    @Override
    public void onSuccess(initdataResponse data) {

        if (data.errno == 0) {
            data.save();
            Log.e("0",data.grades.size()+"  0");
            Log.e("0",data.subjects.size()+"  1");
        }
        mSplashView.navigateToHomePage();

    }

    @Override
    public void onError(String msg) {
      // mSplashView.navigateToHomePage();
        Log.e("mSplashView",msg);
        //mSplashView.showError(msg);
    }

    @Override
    public void onException(String msg) {
        onError(msg);
    }
}
