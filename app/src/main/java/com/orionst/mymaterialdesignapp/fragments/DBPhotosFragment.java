package com.orionst.mymaterialdesignapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orionst.mymaterialdesignapp.R;

public class DBPhotosFragment extends Fragment {

    private static final String TAG = "Fragment";

    public DBPhotosFragment() {
        Log.i(TAG, "Fragment DB");
    }

    public static DBPhotosFragment newInstance() {
        DBPhotosFragment fragment = new DBPhotosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "Fragment Photo List inflating "+ container);
        return inflater.inflate(R.layout.fragment_db_photos, container, false);
    }

}
