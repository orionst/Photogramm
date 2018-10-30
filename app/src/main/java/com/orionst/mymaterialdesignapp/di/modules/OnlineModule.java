package com.orionst.mymaterialdesignapp.di.modules;

import com.orionst.mymaterialdesignapp.domain.model.api.ApiService;
import com.orionst.mymaterialdesignapp.repositories.OnlineRepository;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiModule.class})
public class OnlineModule {

    @Provides
    OnlineRepository onlineRepository(ApiService api) {
        return new OnlineRepository(api);
    }
}
