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

    public PhotoViewModel(@NonNull Application application) {
        super(application);
        mRepository = new PhotoRepository(application);
        mAllPhotos = mRepository.getAllPhotos();
    }

    public LiveData<List<Photo>> getAllPhotos() {
        return mAllPhotos;
    }

    public void insert(Uri photoUri) {
        mRepository.insert(new Photo(photoUri, false));
    }

    public void update(Photo photo) {
        mRepository.update(photo);
    }

}
