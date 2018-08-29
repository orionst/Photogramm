package com.orionst.mymaterialdesignapp.fragments;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orionst.mymaterialdesignapp.MainActivity;
import com.orionst.mymaterialdesignapp.R;
import com.orionst.mymaterialdesignapp.database.model.Photo;
import com.orionst.mymaterialdesignapp.utils.OnActivityResultListener;
import com.orionst.mymaterialdesignapp.viewmodels.PhotoViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PhotoListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhotoListFragment extends Fragment implements PhotoListAdapter.EntitiesListener, OnActivityResultListener {

    private final int REQUEST_CODE_PHOTO = 1;

    private OnFragmentInteractionListener mListener;

    private PhotoViewModel mPhotoViewModel;

    public PhotoListFragment() {
    }

    public static PhotoListFragment newInstance() {
        PhotoListFragment fragment = new PhotoListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_photo_list, container, false);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(view -> ((MainActivity) getActivity()).makePicture());

        RecyclerView recyclerView = layout.findViewById(R.id.photos_recyclerview);
        final PhotoListAdapter adapter = new PhotoListAdapter(layout.getContext(), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(layout.getContext(), 2));

        mPhotoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);
        mPhotoViewModel.getAllWords().observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(@Nullable final List<Photo> words) {
                adapter.setPhotos(words);
            }
        });

        return layout;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onEntityChange(Photo item) {
        item.setFavorite(!item.isFavorite());
        mPhotoViewModel.update(item);
        Snackbar.make(this.getView(), (item.isFavorite()) ? getString(R.string.alert_photo_set_favorite) : getString(R.string.alert_photo_unset_favorite), Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    @Override
    public void onActivityResultTest(int requestCode, int resultCode, Uri photoURI) {
        if (requestCode == REQUEST_CODE_PHOTO && resultCode == Activity.RESULT_OK) {
            mPhotoViewModel.insert(photoURI);
            Snackbar.make(this.getView(), R.string.alert_photo_added, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }

    }
}
