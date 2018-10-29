package com.orionst.mymaterialdesignapp.domain;

import android.content.Context;

import com.orionst.mymaterialdesignapp.R;

public class OnlineResourceManager implements ResourceManager {
    private Context context;

    public OnlineResourceManager(Context context) {
        this.context = context;
    }

    @Override
    public String getString(int resourceId)  {
        return context.getResources().getString(resourceId);
    }

    @Override
    public int getInteger(int resourceId) {
        return context.getResources().getInteger(resourceId);
    }

    @Override
    public String getStringNotificationOnErrorGetPhotoList() {
        return getString(R.string.alert_photo_online_get_error);
    }

    @Override
    public String getStringNotificationOnChangeFavorite(boolean favorite) {
        return "";
    }

    @Override
    public String getStringNotificationOnErrorChangeFavorite() {
        return getString(R.string.alert_photo_favorite_set_error);
    }

    @Override
    public String getStringNotificationOnDeleteImage() {
        return "";
    }

    @Override
    public String getStringNotificationOnDeleteImageError() {
        return "";
    }

    @Override
    public String getStringNotificationOnNewImageError() {
        return getString(R.string.alert_photo_add_error);
    }
}
