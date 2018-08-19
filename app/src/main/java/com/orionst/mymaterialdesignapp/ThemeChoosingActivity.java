package com.orionst.mymaterialdesignapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.orionst.mymaterialdesignapp.utils.SharedPrefs;

public class ThemeChoosingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(SharedPrefs.getCurrentTheme(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_choosing);
    }

    public void setTheme(View view) {
        switch(view.getId()){
            case R.id.btn_theme_warm :
                SharedPrefs.saveCurrentTheme(this, R.style.AppThemeWarm);
                break;
            case R.id.btn_theme_cold :
                SharedPrefs.saveCurrentTheme(this, R.style.AppThemeCold);
                break;
        }
        recreate();
    }
}
