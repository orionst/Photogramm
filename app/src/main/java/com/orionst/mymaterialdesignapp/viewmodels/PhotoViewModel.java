package com.orionst.mymaterialdesignapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.orionst.mymaterialdesignapp.database.model.Photo;
import com.orionst.mymaterialdesignapp.repositories.PhotoRepository;

import java.util.List;

public class PhotoViewModel extends AndroidViewModel {

    private PhotoRepository mRepository;

    private LiveData<List<Photo>> mAllPhotos;
    private LiveData<List<Photo>> mFavPhotos;

    public PhotoViewModel(@NonNull Application application) {
        super(application);
        mRepository = new PhotoRepository(application);
        mAllPhotos = mRepository.getAllPhotos();
        mFavPhotos = mRepository.getFavPhotos();
    }

    public LiveData<List<Photo>> getAllPhotos() {
        return mAllPhotos;
    }

    public LiveData<List<Photo>> getAllFavPhotos() {
        return mFavPhotos;
    }

    public void insert(Uri photoUri) {
        mRepository.insert(new Photo(photoUri, false));
    }

    public void update(Photo photo) {
        Photo newPhoto = new Photo(photo.getPhotoUri(), !photo.isFavorite());
        mRepository.update(newPhoto);
    }

    public boolean delete(Photo photo) {
        return mRepository.delete(photo);
    }


}
