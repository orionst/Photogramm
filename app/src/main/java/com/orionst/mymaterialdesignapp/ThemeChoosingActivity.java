package com.orionst.mymaterialdesignapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.orionst.mymaterialdesignapp.utils.SharedPrefs;

public class ThemeChoosingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(SharedPrefs.getCurrentTheme(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_choosing);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button btnSetStandard= findViewById(R.id.btn_theme_standard);
        Button btnSetWarm = findViewById(R.id.btn_theme_warm);
        Button btnSetCold = findViewById(R.id.btn_theme_cold);
        btnSetStandard.setOnClickListener(view -> {
            SharedPrefs.saveCurrentTheme(this, R.style.AppTheme_Base);
            applyTheme();
        });
        btnSetWarm.setOnClickListener(view -> {
            SharedPrefs.saveCurrentTheme(this, R.style.AppThemeWarm);
            applyTheme();
        });
        btnSetCold.setOnClickListener(view -> {
            SharedPrefs.saveCurrentTheme(this, R.style.AppThemeCold);
            applyTheme();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void applyTheme() {
        TaskStackBuilder.create(this)
                .addNextIntent(new Intent(this, MainActivity.class))
                .addNextIntent(getIntent())
                .startActivities();
        finish();
    }

}