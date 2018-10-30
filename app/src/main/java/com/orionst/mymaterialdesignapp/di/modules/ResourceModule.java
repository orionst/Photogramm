package com.orionst.mymaterialdesignapp.di.modules;

import android.content.Context;

import com.orionst.mymaterialdesignapp.domain.CommonResourceManager;
import com.orionst.mymaterialdesignapp.domain.DatabaseResourceManager;
import com.orionst.mymaterialdesignapp.domain.OnlineResourceManager;
import com.orionst.mymaterialdesignapp.domain.ResourceManager;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module(includes = {AppModule.class})
public class ResourceModule {

    @Provides
    @Named("Online")
    ResourceManager provideOnlineResourceManager(Context context) {
        return new OnlineResourceManager(context);
    }

    @Provides
    @Named("DB")
    ResourceManager provideDatabaseResourceManager(Context context) {
        return new DatabaseResourceManager(context);
    }

    @Provides
    @Named("Common")
    ResourceManager provideCommonResourceManager(Context context) {
        return new CommonResourceManager(context);
    }
}
