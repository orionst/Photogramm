package com.orionst.mymaterialdesignapp.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.orionst.mymaterialdesignapp.database.PhotoDatabase;
import com.orionst.mymaterialdesignapp.database.dao.PhotoDao;
import com.orionst.mymaterialdesignapp.database.model.Photo;

import java.util.List;

public class PhotoRepository {

    private PhotoDao mPhotoDao;
    private LiveData<List<Photo>> mAllPhotos;

    public PhotoRepository(Application application) {
        PhotoDatabase db = PhotoDatabase.getDatabase(application);
        mPhotoDao = db.PhotoDao();
        mAllPhotos = mPhotoDao.getAllPhotos();
    }

    public LiveData<List<Photo>> getAllPhotos() {
        return mAllPhotos;
    }

//    protected LiveData<List<Photo>> getAllPhotos() {
//        List<Photo> photos = new ArrayList<>();
//        String[] list = PhotoListFragment.newInstance().storageDir.list(new FilenameFilter() {
//            @Override
//            public boolean accept(File dir, String name) {
//                return name.contains(".jpg");
//            }
//        });
//        for (:) {
//            Uri contentUri = Uri.parse(list)
//        }
//        return photos;
//    }

    //INSERT
    public void insert(Photo photo) {
        new insertAsyncTask(mPhotoDao).execute(photo);
    }

    private static class insertAsyncTask extends AsyncTask<Photo, Void, Void> {

        private PhotoDao mAsyncTaskDao;

        insertAsyncTask(PhotoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Photo... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    //UPDATE
    public void update(Photo photo) {
        new updateAsyncTask(mPhotoDao).execute(photo);
    }

    private static class updateAsyncTask extends AsyncTask<Photo, Void, Void> {

        private PhotoDao mAsyncTaskDao;

        updateAsyncTask(PhotoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Photo... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

}
