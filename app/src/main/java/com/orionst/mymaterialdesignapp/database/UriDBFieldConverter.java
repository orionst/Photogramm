package com.orionst.mymaterialdesignapp.database;

import android.arch.persistence.room.TypeConverter;
import android.net.Uri;
import android.support.annotation.NonNull;

public class UriDBFieldConverter {

    @TypeConverter
    public Uri storedStringToUri(@NonNull String value) {
        return Uri.parse(value);
    }

    @TypeConverter
    public String uriToStoredString(Uri value) {
        return value.toString();
    }
}
