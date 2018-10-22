package com.orionst.mymaterialdesignapp.presentation.presenter;

import android.annotation.SuppressLint;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.orionst.mymaterialdesignapp.database.model.Photo;
import com.orionst.mymaterialdesignapp.presentation.view.PhotoView;
import com.orionst.mymaterialdesignapp.repositories.RealmRepository;
import com.orionst.mymaterialdesignapp.viewmodels.PhotoViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class PhotoPresenter extends MvpPresenter<PhotoView> implements IPresenter {

    private PhotoViewModel mPhotoViewModel;
    RealmRepository dbRepo;

    public PhotoPresenter(PhotoViewModel viewModel) {
        this.mPhotoViewModel = viewModel;
        dbRepo = new RealmRepository();
    }

    @Override
    public void getPhotoList() {
//        getViewState().getImages(mPhotoViewModel.getAllPhotos());
    }

 //   @Override
//    public void changePhotoStateFavorite(int position) {
//        Photo item = mPhotoViewModel.getAllPhotos().getValue().get(position);
//        mPhotoViewModel.update(item);
//        getViewState().onFavoriteChanged(item.isFavorite());
//    }

    @Override
    public void deletePhoto(int position) {
        Photo item = mPhotoViewModel.getAllPhotos().getValue().get(position);
        boolean successeful = mPhotoViewModel.delete(item);
        getViewState().onPhotoDelete(successeful);
    }

    @Override
    public void openPhoto(int position) {
//        String photoUriString = mPhotoViewModel.getAllPhotos().getValue().get(position).getPhotoUri().toString();
//        getViewState().onPhotoView(photoUriString);
    }

    @Override
    public void onMessageEvent() {
        getPhotoList();
    }

    @SuppressLint("CheckResult")
    public void addImage(String uriString) {
        dbRepo.saveImageToDB(uriString, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                            getViewState().onNewImageList();
                        },
                        throwable -> {
                            getViewState().showError("Error saving");
                        }
                );
    }
}
