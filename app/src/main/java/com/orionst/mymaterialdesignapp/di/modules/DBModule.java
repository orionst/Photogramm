package com.orionst.mymaterialdesignapp.di.modules;

import com.orionst.mymaterialdesignapp.repositories.RealmRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class DBModule {

    @Provides
    public RealmRepository  realmRepository() {
        return new RealmRepository();
    }

}
