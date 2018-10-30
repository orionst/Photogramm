package com.orionst.mymaterialdesignapp.domain;

import android.content.Context;

import com.orionst.mymaterialdesignapp.R;

public class CommonResourceManager implements ResourceManager {

    private Context context;

    public CommonResourceManager(Context context) {
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
        return getString(R.string.alert_photo_get_list_error);
    }

    @Override
    public String getStringNotificationOnChangeFavorite(boolean favorite) {
        return getString(favorite ? R.string.alert_photo_favorite_unset : R.string.alert_photo_favorite_set);
    }

    @Override
    public String getStringNotificationOnErrorChangeFavorite() {
        return getString(R.string.alert_photo_favorite_change_error);
    }

    @Override
    public String getStringNotificationOnDeleteImage() {
        return getString(R.string.alert_photo_deleted);
    }

    @Override
    public String getStringNotificationOnDeleteImageError() {
        return getString(R.string.alert_photo_delete_error);
    }

    @Override
    public String getStringNotificationOnNewImageError() {
        return getString(R.string.alert_photo_add_error);
    }
}
