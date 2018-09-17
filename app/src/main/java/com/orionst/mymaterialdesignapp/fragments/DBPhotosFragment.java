package com.orionst.mymaterialdesignapp.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orionst.mymaterialdesignapp.R;

public class DBPhotosFragment extends Fragment {

    public DBPhotosFragment() {

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
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.hide();
        View layout = inflater.inflate(R.layout.fragment_db_photos, container, false);
        return layout;
    }

}
