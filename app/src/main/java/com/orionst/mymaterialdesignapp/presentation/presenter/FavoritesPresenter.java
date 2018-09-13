package com.orionst.mymaterialdesignapp.presentation.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.orionst.mymaterialdesignapp.database.model.Photo;
import com.orionst.mymaterialdesignapp.presentation.view.PhotoView;
import com.orionst.mymaterialdesignapp.viewmodels.PhotoViewModel;

@InjectViewState
public class FavoritesPresenter extends MvpPresenter<PhotoView> implements IEntityPresenter {

    private PhotoViewModel mPhotoViewModel;

    public FavoritesPresenter(PhotoViewModel viewModel) {
        this.mPhotoViewModel = viewModel;
    }

    @Override
    public void getPhotoList() {
        getViewState().getImages(mPhotoViewModel.getAllFavPhotos());
    }

    @Override
    public void changePhotoStateFavorite(int position) {
        Photo item = mPhotoViewModel.getAllFavPhotos().getValue().get(position);
        mPhotoViewModel.update(item);
        getViewState().onFavoriteChanged(item.isFavorite());
    }

    @Override
    public void deletePhoto(int position) {
        Photo item = mPhotoViewModel.getAllFavPhotos().getValue().get(position);
        boolean successeful = mPhotoViewModel.delete(item);
        getViewState().onPhotoDelete(successeful);
    }

    @Override
    public void openPhoto(int position) {
        String photoUriString = mPhotoViewModel.getAllFavPhotos().getValue().get(position).getPhotoUri().toString();
        getViewState().onPhotoView(photoUriString);
    }
}
