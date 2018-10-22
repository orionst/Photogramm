package com.orionst.mymaterialdesignapp.di.modules;

import com.orionst.mymaterialdesignapp.repositories.RealmRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DBModule {

    @Provides
    @Singleton
    public RealmRepository  realmRepository() {
        return new RealmRepository();
    }

}
