package com.orionst.mymaterialdesignapp.fragments;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orionst.mymaterialdesignapp.R;
import com.orionst.mymaterialdesignapp.database.model.Photo;
import com.orionst.mymaterialdesignapp.viewmodels.PhotoViewModel;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PhotoListFragment extends Fragment implements PhotoListAdapter.EntitiesListener {

    private final int REQUEST_CODE_PHOTO = 1;

    private PhotoViewModel mPhotoViewModel;

    private Uri photoURI;

    public PhotoListFragment() {
    }

    public static PhotoListFragment newInstance() {
        PhotoListFragment fragment = new PhotoListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            photoURI= savedInstanceState.getParcelable("outputFileUri");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_photo_list, container, false);

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(view -> dispatchTakePictureIntent());

        RecyclerView recyclerView = layout.findViewById(R.id.photos_recyclerview);
        final PhotoListAdapter adapter = new PhotoListAdapter(layout.getContext(), this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(layout.getContext(),
                (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)? 3 : 2));

        mPhotoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);
        mPhotoViewModel.getAllPhotos().observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(@Nullable List<Photo> photos) {
                adapter.setPhotos(photos);
            }
        });

        return layout;
    }

    @Override
    public void onEntityChange(Photo item) {
        mPhotoViewModel.update(item);
        Snackbar.make(this.getView(), (item.isFavorite()) ? getString(R.string.alert_photo_set_favorite) : getString(R.string.alert_photo_unset_favorite), Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }

    @Override
    public boolean onEntityDelete(Photo item) {
        if (mPhotoViewModel.delete(item)) {
            Snackbar.make(this.getView(), "Photo has been deleted", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return true;
        } else {
            Snackbar.make(this.getView(), "Something has wrong", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return false;
        }
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
            mPhotoViewModel.insert(photoURI);
            Snackbar.make(this.getView(), R.string.alert_photo_added, Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
        }
    }

    private void dispatchTakePictureIntent() {
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
        return image;
    }

}
