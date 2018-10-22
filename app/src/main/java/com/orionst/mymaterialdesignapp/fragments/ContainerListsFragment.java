package com.orionst.mymaterialdesignapp.fragments;


import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orionst.mymaterialdesignapp.R;

public class ContainerListsFragment extends Fragment {

    public static final String TAG_DB = "db";
    public static final String TAG_INTERNET = "internet";
    public static final String TAG_COMMON = "common";

    public ContainerListsFragment() {

    }

    public static ContainerListsFragment newInstance() {
        ContainerListsFragment fragment = new ContainerListsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_container_lists, container, false);

        FragmentManager mFragmentManager = getChildFragmentManager();

        if (savedInstanceState == null) {
            mFragmentManager.beginTransaction()
                    .add(R.id.containerFrame, DBPhotosFragment.newInstance(), TAG_DB)
                    .commit();
        }

        BottomNavigationView bnv = getActivity().findViewById(R.id.bnv);
        bnv.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.action_db:
                    Fragment dbFragment = mFragmentManager.findFragmentByTag(TAG_DB);
                    if (dbFragment == null) {
                        dbFragment = DBPhotosFragment.newInstance();
                    }
                    mFragmentManager.beginTransaction()
                            .replace(R.id.containerFrame, dbFragment, TAG_DB)
                            .commit();
                    return true;
                case R.id.action_internet:
                    Fragment onlineFragment = mFragmentManager.findFragmentByTag(TAG_INTERNET);
                    if (onlineFragment == null) {
                        onlineFragment = OnlinePhotosFragment.newInstance();
                    }
                    mFragmentManager.beginTransaction()
                            .replace(R.id.containerFrame, onlineFragment, TAG_INTERNET)
                            .commit();
                    return true;
                case R.id.action_common:
                    Fragment photoListFragment = mFragmentManager.findFragmentByTag(TAG_COMMON);
                    if (photoListFragment == null) {
                        photoListFragment = PhotoListFragment.newInstance();
                    }
                    mFragmentManager.beginTransaction()
                            .replace(R.id.containerFrame, photoListFragment, TAG_COMMON)
                            .commit();
                    return true;
            }
            return false;
        });
        return layout;
    }

}
