package com.orionst.mymaterialdesignapp.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.orionst.mymaterialdesignapp.domain.model.entity.Image;
import com.orionst.mymaterialdesignapp.presentation.view.PhotoView;
import com.orionst.mymaterialdesignapp.repositories.InstagramRepository;

import java.util.List;

import io.reactivex.Scheduler;

@InjectViewState
public class OnlinePhotosPresenter extends MvpPresenter<PhotoView> {

    InstagramRepository repository;
    private Scheduler scheduler;
    private String token;

    private List<Image> imageList;
    private List<Image> imageListNew;

    public OnlinePhotosPresenter(Scheduler scheduler) {
        this.scheduler = scheduler;

    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        //getPhotoList();
    }

    void getPhotoList() {
        repository.getToken("test")
                .observeOn(scheduler)
                .subscribe(token -> {
                    this.token = token;
                    repository.getImages(token)
                            .observeOn(scheduler)
                            .subscribe(images -> {
                                this.imageListNew = images;
                                getViewState().onNewImageList();
                            },
                                    throwable -> getViewState().showError(throwable.getMessage()));
                }, throwable -> getViewState().showError(throwable.getMessage()));

    }
}
