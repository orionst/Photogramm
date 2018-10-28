package com.orionst.mymaterialdesignapp.domain.model.entity;

import android.net.Uri;

public class Image {

    private Uri photoUri;
    private boolean favorite;
    private boolean remote;

    public Image(Uri photoUri, boolean favorite, boolean remote) {
        this.photoUri = photoUri;
        this.favorite = favorite;
        this.remote = remote;
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

    public boolean isRemote() {
        return remote;
    }

    public void setRemote(boolean remote) {
        this.remote = remote;
    }
}
