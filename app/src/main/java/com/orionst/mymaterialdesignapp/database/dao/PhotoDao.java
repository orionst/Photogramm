package com.orionst.mymaterialdesignapp.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.orionst.mymaterialdesignapp.database.model.Photo;

import java.util.List;

@Dao
public interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Photo photo);

    @Update
    void update(Photo photo);

    @Query("SELECT * from photos ORDER BY uri ASC")
    LiveData<List<Photo>> getAllPhotos();

    @Query("DELETE FROM photos WHERE uri = :path")
    void deleteByUri(String path);

}
