package com.example.arduinocontroller.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.arduinocontroller.DB.Dao.ButtonWidgetDao;
import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ButtonWidgetItem.class}, version = 1, exportSchema = false)
public abstract class ButtonWidgetDatabase extends RoomDatabase {

    public abstract ButtonWidgetDao buttonWidgetDao();

    private static volatile ButtonWidgetDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

//    public static ButtonWidgetDatabase getDatabase(final Context context) {
//        if (INSTANCE == null) {
//            synchronized (ButtonWidgetDatabase.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            ButtonWidgetDatabase.class, "button_database")
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
    //todo add sample button configs
}
