package com.orionst.mymaterialdesignapp.di;

import com.orionst.mymaterialdesignapp.di.modules.TestOnlineModule;
import com.orionst.mymaterialdesignapp.di.modules.TestResourceModule;
import com.orionst.mymaterialdesignapp.presentation.presenter.OnlinePhotosPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestOnlineModule.class, TestResourceModule.class})
public interface TestComponent {
    void inject(OnlinePhotosPresenter presenter);
}
