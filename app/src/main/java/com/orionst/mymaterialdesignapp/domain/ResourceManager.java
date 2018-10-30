package com.orionst.mymaterialdesignapp.domain;

public interface ResourceManager {

    String getString(int resourceId);
    int getInteger(int resourceId);
    String getStringNotificationOnErrorGetPhotoList();
    String getStringNotificationOnChangeFavorite(boolean favorite);
    String getStringNotificationOnErrorChangeFavorite();
    String getStringNotificationOnDeleteImage();
    String getStringNotificationOnDeleteImageError();
    String getStringNotificationOnNewImageError();
}
