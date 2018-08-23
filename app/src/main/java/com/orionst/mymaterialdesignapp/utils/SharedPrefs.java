package com.orionst.mymaterialdesignapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.orionst.mymaterialdesignapp.R;

public final class SharedPrefs {

    public static final String PREFS_NAME = "MyMaterialDesign";

    public static final String CURRENT_THEME = "b97517e4-7653-4f08-b813-232bce45e08a";
    public static final int DEFAULT_THEME = R.style.AppThemeWarm;

    private SharedPrefs() {}

    public static void saveCurrentTheme(Context context, int placeObjStr) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(CURRENT_THEME, placeObjStr);
        editor.apply();
    }

    public static int getCurrentTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(CURRENT_THEME, DEFAULT_THEME);
    }

}
