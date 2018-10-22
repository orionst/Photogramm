package com.orionst.mymaterialdesignapp.fragments;


import android.arch.lifecycle.LiveData;
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
import com.orionst.mymaterialdesignapp.App;
import com.orionst.mymaterialdesignapp.R;
import com.orionst.mymaterialdesignapp.ViewerActivity;
import com.orionst.mymaterialdesignapp.database.model.Photo;
import com.orionst.mymaterialdesignapp.fragments.adapters.ImageListAdapter;
import com.orionst.mymaterialdesignapp.fragments.eventbus.ReloadImagesEvent;
import com.orionst.mymaterialdesignapp.presentation.presenter.FavoritesPresenter;
import com.orionst.mymaterialdesignapp.presentation.view.PhotoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class FavoritesListFragment extends MvpAppCompatFragment implements PhotoView {

    private ImageListAdapter adapter;
    @BindView(R.id.photos_recyclerview) RecyclerView imagesRecyclerView;

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
        presenter = new FavoritesPresenter(AndroidSchedulers.mainThread());
        App.getAppComponent().inject(presenter);
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_favorites_list, container, false);
        ButterKnife.bind(this, layout);

        adapter = new ImageListAdapter(presenter.getImageListPresenter(), layout.getContext());
        imagesRecyclerView.setHasFixedSize(true);
        imagesRecyclerView.setAdapter(adapter);
        imagesRecyclerView.setLayoutManager(new GridLayoutManager(layout.getContext(),
                (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ? 3 : 2));

        return layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    //TODO: to delete
    @Override
    public void getImages(LiveData<List<Photo>> allPhotos) {
    }

    //TODO: to delete
    @Override
    public void getImages(List<Photo> allPhotos) {

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

    @Override
    public void onNewImageList() {
        adapter.updateList();
    }

    @Override
    public void showError(String message) {
        Snackbar.make(this.getView(), message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    @Override
    public void showNotification(String message) {
        Snackbar.make(this.getView(), message, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    @Override
    public void sendReloadListMessage() {
        EventBus.getDefault().post(new ReloadImagesEvent(true));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ReloadImagesEvent event) {
        presenter.onMessageEvent();
    }

}
