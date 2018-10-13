package com.orionst.mymaterialdesignapp.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.orionst.mymaterialdesignapp.database.UriDBFieldConverter;

@Entity(tableName = "photos")
public class Photo {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "uri")
    @TypeConverters({UriDBFieldConverter.class})
    private Uri photoUri;

    @NonNull
    @ColumnInfo(name = "favorite")
    private boolean favorite;

    public Photo(@NonNull Uri photoUri, @NonNull boolean favorite) {
        this.photoUri = photoUri;
        this.favorite = favorite;
    }

    @NonNull
    public Uri getPhotoUri() {
        return photoUri;
    }

    @NonNull
    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(@NonNull boolean favorite) {
        this.favorite = favorite;
    }
}
