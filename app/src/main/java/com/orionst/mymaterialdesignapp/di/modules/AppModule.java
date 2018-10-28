package com.orionst.mymaterialdesignapp.di.modules;

import android.content.Context;

import com.orionst.mymaterialdesignapp.domain.AndroidResourceManager;
import com.orionst.mymaterialdesignapp.domain.ResourceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Context appContext;

    public AppModule(Context context){
        this.appContext = context;
    }

    @Provides
    @Singleton
    Context getContext(){
        return appContext;
    }

    @Singleton
    @Provides
    ResourceManager provideResourceManager(AndroidResourceManager resourceManager) {
        return resourceManager;
    }

}