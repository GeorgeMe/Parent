package com.dmd.zsb.mvp.listeners;

import com.google.gson.JsonObject;

public interface CommonListInteractor {
    void getCommonListData(final int event,JsonObject gson);
}
