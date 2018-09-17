package com.orionst.mymaterialdesignapp.presentation.presenter;

public interface IEntityPresenter {
    void getPhotoList();
    void changePhotoStateFavorite(int position);
    void deletePhoto(int position);
    void openPhoto(int position);
}
