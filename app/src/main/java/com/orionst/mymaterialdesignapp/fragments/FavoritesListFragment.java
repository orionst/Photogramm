package com.orionst.mymaterialdesignapp.fragments;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.orionst.mymaterialdesignapp.R;
import com.orionst.mymaterialdesignapp.ViewerActivity;
import com.orionst.mymaterialdesignapp.database.model.Photo;
import com.orionst.mymaterialdesignapp.fragments.adapters.PhotoListAdapter;
import com.orionst.mymaterialdesignapp.presentation.presenter.FavoritesPresenter;
import com.orionst.mymaterialdesignapp.presentation.view.PhotoView;
import com.orionst.mymaterialdesignapp.viewmodels.PhotoViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class FavoritesListFragment extends MvpAppCompatFragment implements PhotoView {

    private PhotoViewModel mPhotoViewModel;
    private PhotoListAdapter adapter;

    @InjectPresenter
    FavoritesPresenter presenter;

    public FavoritesListFragment() {
    }

    public static FavoritesListFragment newInstance() {
        FavoritesListFragment fragment = new FavoritesListFragment();
        return fragment;
    }

    @ProvidePresenter
    public FavoritesPresenter provideMainPresenter() {
        mPhotoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);
        presenter = new FavoritesPresenter(mPhotoViewModel, AndroidSchedulers.mainThread());
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_favorites_list, container, false);

        RecyclerView recyclerView = layout.findViewById(R.id.photos_recyclerview);
        adapter = new PhotoListAdapter(layout.getContext(), presenter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(layout.getContext(),
                (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ? 3 : 2));

        presenter.getPhotoList();

        return layout;
    }

    @Override
    public void getImages(LiveData<List<Photo>> allPhotos) {
        allPhotos.observe(this, photos -> adapter.setPhotos(photos));
    }

    @Override
    public void onFavoriteChanged(boolean favoriteState) {
        Snackbar.make(this.getView(), (favoriteState) ? getString(R.string.alert_photo_unset_favorite) : getString(R.string.alert_photo_set_favorite), Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    @Override
    public void onPhotoDelete(boolean actionSuccesseful) {
        Snackbar.make(this.getView(),
                (actionSuccesseful ? getString(R.string.alert_photo_deleted) : getString(R.string.alert_photo_delete_error)),
                Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    @Override
    public void onPhotoView(String uriString) {
        Intent intent = new Intent(this.getActivity(), ViewerActivity.class);
        intent.putExtra("photoUriString", uriString);
        startActivity(intent);
    }
}
