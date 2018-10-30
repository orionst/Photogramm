package com.orionst.mymaterialdesignapp.di;

import com.orionst.mymaterialdesignapp.di.modules.DBModule;
import com.orionst.mymaterialdesignapp.di.modules.OnlineModule;
import com.orionst.mymaterialdesignapp.di.modules.ResourceModule;
import com.orionst.mymaterialdesignapp.presentation.presenter.CommonListPresenter;
import com.orionst.mymaterialdesignapp.presentation.presenter.DBPhotosPresenter;
import com.orionst.mymaterialdesignapp.presentation.presenter.FavoritesPresenter;
import com.orionst.mymaterialdesignapp.presentation.presenter.OnlinePhotosPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {DBModule.class, OnlineModule.class, ResourceModule.class})
public interface AppComponent {

    void inject(DBPhotosPresenter presenter);
    void inject(FavoritesPresenter presenter);
    void inject(OnlinePhotosPresenter presenter);
    void inject(CommonListPresenter presenter);

}
