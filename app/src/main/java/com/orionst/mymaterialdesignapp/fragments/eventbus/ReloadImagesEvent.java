package com.orionst.mymaterialdesignapp.fragments.eventbus;

import android.support.v4.app.Fragment;

public class ReloadImagesEvent {

    private final Fragment fragment;

    public ReloadImagesEvent(Fragment fragment) {
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }
}