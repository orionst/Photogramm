package com.orionst.mymaterialdesignapp.presentation.presenter;

public interface IPresenter {
    void getPhotoList();
    void deletePhoto(int position);
    void openPhoto(int position);
    void onMessageEvent();
}
