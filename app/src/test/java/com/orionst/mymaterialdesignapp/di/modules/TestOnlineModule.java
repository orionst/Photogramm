package com.orionst.mymaterialdesignapp.di.modules;

import com.orionst.mymaterialdesignapp.repositories.OnlineRepository;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

@Module
public class TestOnlineModule {

    @Provides
    public OnlineRepository onlineRepository() {
        return Mockito.mock(OnlineRepository.class);
    }

}
