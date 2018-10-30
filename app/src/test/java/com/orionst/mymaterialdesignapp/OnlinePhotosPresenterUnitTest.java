package com.orionst.mymaterialdesignapp;

import com.orionst.mymaterialdesignapp.di.DaggerTestComponent;
import com.orionst.mymaterialdesignapp.di.TestComponent;
import com.orionst.mymaterialdesignapp.di.modules.TestOnlineModule;
import com.orionst.mymaterialdesignapp.presentation.presenter.OnlinePhotosPresenter;
import com.orionst.mymaterialdesignapp.presentation.view.PhotoView;
import com.orionst.mymaterialdesignapp.repositories.OnlineRepository;
import com.orionst.mymaterialdesignapp.utils.Const;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;

public class OnlinePhotosPresenterUnitTest {

    @Mock
    PhotoView photoView;

    private TestScheduler testScheduler;
    private OnlinePhotosPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        testScheduler = new TestScheduler();
        presenter = Mockito.spy(new OnlinePhotosPresenter(testScheduler));
    }

    @Test
    public void getPhotosListTest() {
        TestComponent component = DaggerTestComponent.builder()
                .testOnlineModule(new TestOnlineModule() {
                    @Override
                    public OnlineRepository onlineRepository() {
                        OnlineRepository repository = super.onlineRepository();
                        Mockito.when(repository.getQueryResult(Const.API_KEY)).thenReturn(Observable.just(new ArrayList<>()));
                        return repository;
                    }
                })
                .build();
        presenter.attachView(photoView);
        Mockito.verify(presenter).getPhotoList();
        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS);

        Mockito.verify(photoView).onNewImageList();
    }

}
