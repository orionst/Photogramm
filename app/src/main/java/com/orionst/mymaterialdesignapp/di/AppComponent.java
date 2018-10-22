package com.orionst.mymaterialdesignapp.di;

import com.orionst.mymaterialdesignapp.di.modules.DBModule;
import com.orionst.mymaterialdesignapp.presentation.presenter.DBPhotosPresenter;
import com.orionst.mymaterialdesignapp.presentation.presenter.FavoritesPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {DBModule.class})
@Singleton
public interface AppComponent {

    void inject(DBPhotosPresenter presenter);
    void inject(FavoritesPresenter presenter);

}
