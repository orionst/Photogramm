package com.orionst.mymaterialdesignapp.utils;

import android.support.v7.util.DiffUtil;

import com.orionst.mymaterialdesignapp.database.model.Photo;

import java.util.List;

public class MyDiffCallback extends DiffUtil.Callback{

    private List<Photo> oldPhotos;
    private List<Photo> newPhotos;

    public MyDiffCallback(List<Photo> oldPhotos, List<Photo> newPhotos) {
        this.oldPhotos = oldPhotos;
        this.newPhotos = newPhotos;
    }

    @Override
    public int getOldListSize() {
        return oldPhotos.size();
    }

    @Override
    public int getNewListSize() {
        return newPhotos.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPhotos.get(oldItemPosition).getPhotoUri().equals(newPhotos.get(newItemPosition).getPhotoUri());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        //return oldPhotos.get(oldItemPosition).equals(newPhotos.get(newItemPosition));
        return oldPhotos.get(oldItemPosition).getPhotoUri().equals(newPhotos.get(newItemPosition).getPhotoUri())
                && oldPhotos.get(oldItemPosition).isFavorite() == newPhotos.get(newItemPosition).isFavorite();
    }
}