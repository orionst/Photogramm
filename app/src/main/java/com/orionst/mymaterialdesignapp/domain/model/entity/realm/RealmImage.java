package com.orionst.mymaterialdesignapp.domain.model.entity.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmImage extends RealmObject {

    @PrimaryKey
    public String uriString;
    public boolean favorite;

    public String getUriString() {
        return uriString;
    }

    public void setUriString(String uri) {
        this.uriString = uri;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
