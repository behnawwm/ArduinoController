package com.example.arduinocontroller.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.arduinocontroller.DB.Dao.ButtonWidgetDao;
import com.example.arduinocontroller.DB.Dao.DimmerWidgetDao;
import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.DB.Model.DimmerWidgetItem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {DimmerWidgetItem.class}, version = 1, exportSchema = false)
public abstract class DimmerWidgetDatabase extends RoomDatabase {

    public abstract DimmerWidgetDao dimmerWidgetDao();

    private static volatile DimmerWidgetDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

//    public static DimmerWidgetDatabase getDatabase(final Context context) {
//        if (INSTANCE == null) {
//            synchronized (DimmerWidgetDatabase.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            DimmerWidgetDatabase.class, "dimmer_database")
//                            .fallbackToDestructiveMigration() //todo check
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
    //todo add sample dimmer configs

}
