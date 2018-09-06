package com.orionst.mymaterialdesignapp.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orionst.mymaterialdesignapp.R;
import com.orionst.mymaterialdesignapp.ViewerActivity;
import com.orionst.mymaterialdesignapp.database.model.Photo;
import com.orionst.mymaterialdesignapp.fragments.adapters.PhotoListAdapter;
import com.orionst.mymaterialdesignapp.viewmodels.PhotoViewModel;

public class FavoritesListFragment extends Fragment implements PhotoListAdapter.EntitiesListener {

    private PhotoViewModel mPhotoViewModel;
    private static final String TAG = "Fragment";

    public FavoritesListFragment() {
        Log.i(TAG, "Fragment Favotites");
    }

    public static FavoritesListFragment newInstance() {
        FavoritesListFragment fragment = new FavoritesListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_favorites_list, container, false);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);

        RecyclerView recyclerView = layout.findViewById(R.id.photos_recyclerview);
        PhotoListAdapter adapter = new PhotoListAdapter(layout.getContext(), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(layout.getContext(),
                (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ? 3 : 2));

        mPhotoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);
        mPhotoViewModel.getAllFavPhotos().observe(this, photos -> adapter.setPhotos(photos));

        return layout;
    }

    @Override
    public void onEntityChange(int position) {
        Photo item = mPhotoViewModel.getAllFavPhotos().getValue().get(position);
        mPhotoViewModel.update(item);
        Snackbar.make(this.getView(), (item.isFavorite()) ? getString(R.string.alert_photo_unset_favorite) : getString(R.string.alert_photo_set_favorite), Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    @Override
    public void onEntityDelete(int position) {
        if (mPhotoViewModel.delete(mPhotoViewModel.getAllFavPhotos().getValue().get(position))) {
            Snackbar.make(this.getView(), "Photo has been deleted", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        } else {
            Snackbar.make(this.getView(), "Something has wrong", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }
    }

    @Override
    public void onEntityOpen(int position) {
        String photoUriString = mPhotoViewModel.getAllFavPhotos().getValue().get(position).getPhotoUri().toString();
        Intent intent = new Intent(this.getActivity(), ViewerActivity.class);
        intent.putExtra("photoUriString", photoUriString);
        startActivity(intent);
    }
}
