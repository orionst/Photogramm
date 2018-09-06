package com.orionst.mymaterialdesignapp.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import com.orionst.mymaterialdesignapp.PhotogrammApplication;
import com.orionst.mymaterialdesignapp.database.PhotoDatabase;
import com.orionst.mymaterialdesignapp.database.dao.PhotoDao;
import com.orionst.mymaterialdesignapp.database.model.Photo;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class PhotoRepository {

    private PhotoDao mPhotoDao;
    private LiveData<List<Photo>> mAllPhotos;
    private LiveData<List<Photo>> mFavPhotos;

    public PhotoRepository(Application application) {
        PhotoDatabase db = PhotoDatabase.getDatabase(application);
        mPhotoDao = db.PhotoDao();
        mAllPhotos = mPhotoDao.getAllPhotos();
        mFavPhotos = mPhotoDao.getAllFavoritePhotos();
    }

    public LiveData<List<Photo>> getAllPhotos() {
        return mAllPhotos;
    }

    public LiveData<List<Photo>> getFavPhotos() {
        return mFavPhotos;
    }

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

    //DELETE
    public boolean delete(Photo photo) {
        AsyncTask<Photo, Void, Boolean> task = new deleteByIdAsyncTask(mPhotoDao);
        try {
            return task.execute(photo).get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static class deleteByIdAsyncTask extends AsyncTask<Photo, Void, Boolean> {

        private PhotoDao mAsyncTaskDao;

        deleteByIdAsyncTask(PhotoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Boolean doInBackground(final Photo... params) {
            //return deleteUriFile(params[0].getPhotoUri());    // не удаляет почему-то
            String filePath = PhotogrammApplication.context().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()
                    + "/"
                    + params[0].getPhotoUri().getLastPathSegment();
            if (deletePhotoFile(filePath)) {
                mAsyncTaskDao.deleteByUri(params[0].getPhotoUri().toString());
                return true;
            } else {
                return false;
            }
        }

        private boolean deleteUriFile(Uri uri) {
            if (PhotogrammApplication.context().getContentResolver().delete(uri, null, null) >=1) {
                return true;
            }
            return false;
        }

        private boolean deletePhotoFile(String filePath) {
            File fdelete = new File(filePath);
            if (fdelete.exists()) {
                if (fdelete.delete()) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }
    }

}
