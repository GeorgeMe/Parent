package com.dmd.zsb.mvp.interactor;

import android.content.Context;
import android.view.animation.Animation;


import org.json.JSONObject;

public interface SplashInteractor {

    int getBackgroundImageResID();

    String getCopyright(Context context);

    String getVersionName(Context context);

    Animation getBackgroundImageAnimation(Context context);

    void loadingInitData(JSONObject json);
}
