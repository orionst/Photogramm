package com.orionst.mymaterialdesignapp.utils;

import android.support.v7.util.DiffUtil;

import com.orionst.mymaterialdesignapp.domain.model.entity.Image;

import java.util.List;

public class MyDiffImagesCallback extends DiffUtil.Callback{

    private List<Image> oldPhotos;
    private List<Image> newPhotos;

    public MyDiffImagesCallback(List<Image> oldPhotos, List<Image> newPhotos) {
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