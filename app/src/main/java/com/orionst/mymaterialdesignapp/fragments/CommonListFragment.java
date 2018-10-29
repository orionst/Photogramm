package com.orionst.mymaterialdesignapp.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
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
import com.orionst.mymaterialdesignapp.presentation.presenter.CommonListPresenter;
import com.orionst.mymaterialdesignapp.presentation.view.PhotoView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class CommonListFragment extends MvpAppCompatFragment implements PhotoView {

    private final int REQUEST_CODE_PHOTO = 1;
    private static final int PERMISSION_REQUEST_CODE = 10;

    private Uri photoURI;
    private String mCurrentPhotoPath;
    private ImageListAdapter adapter;

    @BindView(R.id.photos_recyclerview) RecyclerView imagesRecyclerView;

    @InjectPresenter CommonListPresenter presenter;

    public CommonListFragment() {

    }

    public static CommonListFragment newInstance() {
        CommonListFragment fragment = new CommonListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            photoURI = savedInstanceState.getParcelable("outputFileUri");
        }
    }

    @ProvidePresenter
    public CommonListPresenter provideMainPresenter() {
        presenter = new CommonListPresenter(AndroidSchedulers.mainThread());
        App.getAppComponent().inject(presenter);
        return presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_photo_list, container, false);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.show();
        fab.setOnClickListener(view -> dispatchTakePictureIntent());

        ButterKnife.bind(this, layout);

        adapter = new ImageListAdapter(presenter.getImageListPresenter(), layout.getContext());
        imagesRecyclerView.setHasFixedSize(true);
        imagesRecyclerView.setAdapter(adapter);
        imagesRecyclerView.setLayoutManager(new GridLayoutManager(layout.getContext(),
                (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) ? 3 : 2));

        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("outputFileUri", photoURI);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PHOTO && resultCode == Activity.RESULT_OK) {
            //TODO addImage with mCurrentPhotoPath in param
            presenter.addImage(photoURI.toString());
            Snackbar.make(this.getView(), R.string.alert_photo_added, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }
    }

    private void getNewPhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getContext(),
                        getString(R.string.authority_name),
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CODE_PHOTO);
            }
        }
    }

    private void dispatchTakePictureIntent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSION_REQUEST_CODE
                );
            } else {
                getNewPhoto();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getNewPhoto();
                }
                return;
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
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

    }
}
