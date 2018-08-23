package com.orionst.mymaterialdesignapp.models;

public class Photo {

    private int pictureId;
    private boolean favorite;

    public Photo(int pictureId, boolean favorite) {
        this.pictureId = pictureId;
        this.favorite = favorite;
    }

    public int getPictureId() {
        return pictureId;
    }

    public boolean isFavorite() {
        return favorite;
    }

}
