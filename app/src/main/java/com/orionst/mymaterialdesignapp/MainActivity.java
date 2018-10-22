package com.orionst.mymaterialdesignapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.orionst.mymaterialdesignapp.fragments.PhotoListFragment;
import com.orionst.mymaterialdesignapp.fragments.adapters.CustomFragmentPagerAdapter;
import com.orionst.mymaterialdesignapp.presentation.view.MainView;
import com.orionst.mymaterialdesignapp.utils.SharedPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnPageChange;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, MainView {

    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.navigation_view) NavigationView navigationView;
    @BindView(R.id.bnv) BottomNavigationView bnv;
    @BindView(R.id.page_container) ViewPager mViewPager;
    @BindView(R.id.tabs) TabLayout tabLayout;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initUI();
    }

    @Override
    public void initUI() {
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        CustomFragmentPagerAdapter customFragmentPagerAdapter
                = new CustomFragmentPagerAdapter(getSupportFragmentManager(), this);

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(customFragmentPagerAdapter);

        tabLayout.setupWithViewPager(mViewPager);
    }

    @OnPageChange(value=R.id.page_container, callback = OnPageChange.Callback.PAGE_SELECTED)
    void onPageSelected(int i) {
        if (i == 0) {
            bnv.setVisibility(View.VISIBLE);
            if (bnv.getSelectedItemId() == R.id.action_common) {
                fab.show();
            } else {
                fab.hide();
            }
        } else {
            fab.hide();
            bnv.setVisibility(View.GONE);
        }
    }

    @Override
    public Resources.Theme getTheme() {
        Resources.Theme theme = super.getTheme();
        theme.applyStyle(SharedPrefs.getCurrentTheme(this), true);
        return theme;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_item_change_theme:
//                Intent intent = new Intent(this, ThemeChoosingActivity.class);
//                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.menu_item_main:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, PhotoListFragment.newInstance())
                        .commit();
                break;
            case R.id.menu_item_change_theme:
                Intent intent = new Intent(this, ThemeChoosingActivity.class);
                startActivity(intent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
