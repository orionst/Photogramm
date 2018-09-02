package com.orionst.mymaterialdesignapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.orionst.mymaterialdesignapp.database.dao.PhotoDao;
import com.orionst.mymaterialdesignapp.database.model.Photo;

@Database(entities = {Photo.class}, version = 1, exportSchema = false)
public abstract class PhotoDatabase extends RoomDatabase{

    public abstract PhotoDao PhotoDao();

    private static volatile PhotoDatabase DB_INSTANCE;

    public static PhotoDatabase getDatabase(final Context context) {
        if (DB_INSTANCE == null) {
            synchronized (PhotoDatabase.class) {
                if (DB_INSTANCE == null) {
                    DB_INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PhotoDatabase.class, "photogramm_db")
                            .build();
                }
            }
        }
        return DB_INSTANCE;
    }
}
