package com.orionst.mymaterialdesignapp.domain;

import android.content.Context;

import javax.inject.Inject;

public class AndroidResourceManager implements ResourceManager {
    @Inject Context context;

    @Inject
    public AndroidResourceManager(Context context) {
        this.context = context;
    }

    @Override
    public String getString(int resourceId)  {
        return context.getResources().getString(resourceId);
    }

    @Override
    public int getInteger(int resourceId) {
        return context.getResources().getInteger(resourceId);
    }}
