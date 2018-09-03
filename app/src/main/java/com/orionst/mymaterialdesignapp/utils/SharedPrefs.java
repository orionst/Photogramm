package com.orionst.mymaterialdesignapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.orionst.mymaterialdesignapp.R;

public final class SharedPrefs {

    private static final String PREFS_NAME = "Photogramm";

    private static final String CURRENT_THEME = "b97517e4-7653-4f08-b813-232bce45e08a";

    private static final String THEME_WARM = "warm";
    private static final String THEME_COLD = "cold";
    private static final String THEME_STANDARD = "standard";

    private SharedPrefs() {}

    public static void saveCurrentTheme(Context context, int placeObjStr) {

        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        switch (placeObjStr) {
            case R.style.AppThemeWarm:
                editor.putString(CURRENT_THEME, THEME_WARM);
                break;
            case R.style.AppThemeCold:
                editor.putString(CURRENT_THEME, THEME_COLD);
                break;
            default:
                editor.putString(CURRENT_THEME, THEME_STANDARD);
        }
        editor.apply();
    }

    public static int getCurrentTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String storedTheme = sharedPreferences.getString(CURRENT_THEME, THEME_STANDARD);
        switch (storedTheme) {
            case THEME_WARM:
                return R.style.AppThemeWarm;
            case THEME_COLD:
                return R.style.AppThemeCold;
            default:
                return R.style.AppTheme_Base;
        }
    }

}
