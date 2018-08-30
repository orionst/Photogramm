package com.orionst.mymaterialdesignapp.database;

import android.arch.persistence.room.TypeConverter;
import android.net.Uri;
import android.support.annotation.NonNull;

public class UriDBFieldConverter {

    @TypeConverter
    public Uri storedStringToLexicalClass(@NonNull String value) {
        return Uri.parse(value);
    }

    @TypeConverter
    public String lexicalClassToStoredString(Uri value) {
        return value.toString();
    }
}
