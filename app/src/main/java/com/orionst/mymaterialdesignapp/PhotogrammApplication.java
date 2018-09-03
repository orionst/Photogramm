package com.orionst.mymaterialdesignapp;

import android.app.Application;
import android.content.Context;

public class PhotogrammApplication extends Application {
    private static PhotogrammApplication mApp = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
    }

    public static Context context() {
        return mApp.getApplicationContext();
    }
}
