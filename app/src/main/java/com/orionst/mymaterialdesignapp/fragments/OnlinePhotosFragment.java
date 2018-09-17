package com.orionst.mymaterialdesignapp.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orionst.mymaterialdesignapp.R;

public class OnlinePhotosFragment extends Fragment {

    public OnlinePhotosFragment() {

    }

    public static OnlinePhotosFragment newInstance() {
        OnlinePhotosFragment fragment = new OnlinePhotosFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_online_photos, container, false);
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.hide();
        return layout;
    }

}
