package com.orionst.mymaterialdesignapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    private SharedPrefs() {}

    public static void saveCurrentTheme(Context context, int placeObjStr) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.PREFS_NAME, Activity.MODE_PRIVATE).edit();
        editor.putInt(Constants.CURRENT_THEME, placeObjStr);
        editor.apply();
    }

    public static int getCurrentTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFS_NAME, Activity.MODE_PRIVATE);
        return sharedPreferences.getInt(Constants.CURRENT_THEME, Constants.DEFAULT_THEME);
    }

}
