package com.gudigudigudi.mdtemplate;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/**
 * Created by gu on 10/3/17.
 */

@Database(entities = {Book.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BookDao bookDao();
}
