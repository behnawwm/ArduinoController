package com.example.arduinocontroller.DB.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.arduinocontroller.DB.ButtonWidgetDatabase;
import com.example.arduinocontroller.DB.Dao.ButtonWidgetDao;
import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;

import java.util.List;

public class ButtonWidgetRepository {
    private ButtonWidgetDao mButtonWidgetDao;
    private LiveData<List<ButtonWidgetItem>> mAllItems;

    public ButtonWidgetRepository(Application application) {
        ButtonWidgetDatabase db = ButtonWidgetDatabase.getDatabase(application);
        mButtonWidgetDao = db.buttonWidgetDao();
        mAllItems = mButtonWidgetDao.getAll();
    }

    public LiveData<List<ButtonWidgetItem>> getAllItems() {
        return mAllItems;
    }

    public void insert(ButtonWidgetItem item) {
        ButtonWidgetDatabase.databaseWriteExecutor.execute(() -> {
            mButtonWidgetDao.insert(item);
        });
    }

    public void deleteAll() {
        ButtonWidgetDatabase.databaseWriteExecutor.execute(() -> {
            mButtonWidgetDao.deleteAll();
        });
    }
}
