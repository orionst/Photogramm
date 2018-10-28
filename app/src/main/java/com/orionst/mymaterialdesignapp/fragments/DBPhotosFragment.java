package com.orionst.mymaterialdesignapp.fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.orionst.mymaterialdesignapp.fragments.adapters.ImageListAdapter;
import com.orionst.mymaterialdesignapp.fragments.eventbus.ReloadImagesEvent;
import com.orionst.mymaterialdesignapp.presentation.presenter.DBPhotosPresenter;
import com.orionst.mymaterialdesignapp.presentation.view.PhotoView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class DBPhotosFragment extends MvpAppCompatFragment implements PhotoView {

    private ImageListAdapter adapter;
    @BindView(R.id.photos_recyclerview) RecyclerView imagesRecyclerView;

    @InjectPresenter DBPhotosPresenter presenter;

    public static DBPhotosFragment newInstance() {
        return new DBPhotosFragment();
    }

    @ProvidePresenter
    public DBPhotosPresenter provideMainPresenter() {
        presenter = new DBPhotosPresenter(AndroidSchedulers.mainThread());
        App.getAppComponent().inject(presenter);
        return presenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_db_photos, container, false);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.hide();

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
