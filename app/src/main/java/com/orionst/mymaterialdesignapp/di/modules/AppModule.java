package com.orionst.mymaterialdesignapp.di.modules;

import android.content.Context;

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

//    @Singleton
//    @Provides
//    ResourceManager provideResourceManager(ResourceManager resourceManager) {
//        return resourceManager;
//    }

}