package com.orionst.mymaterialdesignapp.fragments;


import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.orionst.mymaterialdesignapp.R;
import com.orionst.mymaterialdesignapp.database.model.Photo;
import com.orionst.mymaterialdesignapp.presentation.presenter.OnlinePhotosPresenter;
import com.orionst.mymaterialdesignapp.presentation.view.PhotoView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class OnlinePhotosFragment extends MvpAppCompatFragment implements PhotoView {

    @InjectPresenter OnlinePhotosPresenter onlinePhotosPresenter;

    public OnlinePhotosFragment() {

    }

    public static OnlinePhotosFragment newInstance() {
        OnlinePhotosFragment fragment = new OnlinePhotosFragment();
        return fragment;
    }

    @ProvidePresenter
    public OnlinePhotosPresenter provideOnlinePhotosPresenter() {
        onlinePhotosPresenter = new OnlinePhotosPresenter(AndroidSchedulers.mainThread());
        //App.getInstance().getAppComponent().injectFragment(onlinePhotosPresenter);
        return onlinePhotosPresenter;
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

    @Override
    public void getImages(LiveData<List<Photo>> allPhotos) {

    }

    @Override
    public void getImages(List<Photo> allPhotos) {

    }

    @Override
    public void onFavoriteChanged(boolean favoriteState) {

    }

    @Override
    public void onPhotoDelete(boolean actionSuccesseful) {

    }

    @Override
    public void onPhotoView(String uriString) {

    }

    @Override
    public void onNewImageList() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showNotification(String message) {

    }

    @Override
    public void sendReloadListMessage() {

    }
}
