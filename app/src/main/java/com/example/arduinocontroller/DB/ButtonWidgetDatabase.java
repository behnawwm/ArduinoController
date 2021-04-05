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

    public static ButtonWidgetDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ButtonWidgetDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ButtonWidgetDatabase.class, "word_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
//                ButtonWidgetDao dao = INSTANCE.buttonWidgetDao();
//                dao.deleteAll();

//                ButtonWidgetItem word = new ButtonWidgetItem("1 or 0", 1, "1", "0");
//                dao.insertAll(word);
            });
        }
    };
}
