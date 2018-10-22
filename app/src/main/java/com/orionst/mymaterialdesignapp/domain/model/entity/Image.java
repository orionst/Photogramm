package com.orionst.mymaterialdesignapp.domain.model.entity;

import android.net.Uri;

public class Image {

    private Uri photoUri;
    private boolean favorite;

    public Image(Uri photoUri, boolean favorite) {
        this.photoUri = photoUri;
        this.favorite = favorite;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
