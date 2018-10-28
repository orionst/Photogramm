package com.orionst.mymaterialdesignapp;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.facebook.stetho.Stetho;
import com.orionst.mymaterialdesignapp.di.AppComponent;
import com.orionst.mymaterialdesignapp.di.DaggerAppComponent;
import com.orionst.mymaterialdesignapp.di.modules.AppModule;
import com.orionst.mymaterialdesignapp.di.modules.DBModule;

import java.io.File;

import io.realm.Realm;

public class App extends Application {
    private static App instance;
    private static AppComponent appComponent;
    private File storageDir;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Realm.init(this);

        storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        appComponent = DaggerAppComponent.builder()
                .dBModule(new DBModule())
                .appModule(new AppModule(this))
                .build();

        Stetho.initializeWithDefaults(this);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static App getInstance() {
        return instance;
    }

    public static Context context() {
        return instance.getApplicationContext();
    }

    public File getStorageDir() {
        return storageDir;
    }
}
