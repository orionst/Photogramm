package com.orionst.mymaterialdesignapp.di.modules;

import com.orionst.mymaterialdesignapp.domain.ResourceManager;

import org.mockito.Mockito;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class TestResourceModule {

    @Provides
    @Named("Online")
    public ResourceManager resourceManager() {
        return Mockito.mock(ResourceManager.class);
    }

}
