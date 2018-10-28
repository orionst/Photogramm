package com.orionst.mymaterialdesignapp.di.modules;

import com.orionst.mymaterialdesignapp.domain.AndroidResourceManager;
import com.orionst.mymaterialdesignapp.domain.ResourceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {AppModule.class})
public class ResourceModule {

    @Singleton
    @Provides
    ResourceManager provideResourceManager(AndroidResourceManager resourceManager) {
        return resourceManager;
    }

}
