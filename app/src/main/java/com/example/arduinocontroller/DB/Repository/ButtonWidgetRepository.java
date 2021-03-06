package com.example.arduinocontroller.DB.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.arduinocontroller.DB.ButtonWidgetDatabase;
import com.example.arduinocontroller.DB.Dao.ButtonWidgetDao;
import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

public class ButtonWidgetRepository {
    private ButtonWidgetDao mButtonWidgetDao;
    private LiveData<List<ButtonWidgetItem>> mAllItems;


    public ButtonWidgetRepository(ButtonWidgetDao dao) {
//        ButtonWidgetDatabase db = ButtonWidgetDatabase.getDatabase(application);
        mButtonWidgetDao = dao;
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

    public void update(ButtonWidgetItem savedItem, ButtonWidgetItem item) {
        ButtonWidgetDatabase.databaseWriteExecutor.execute(() -> {
            ButtonWidgetItem foundItem = mButtonWidgetDao.findByID(savedItem.getId());
            item.setId(foundItem.getId());
            mButtonWidgetDao.insert(item);
        });
    }

    public void deleteById(int id) {
        ButtonWidgetDatabase.databaseWriteExecutor.execute(() -> {
            ButtonWidgetItem foundItem = mButtonWidgetDao.findByID(id);
            mButtonWidgetDao.delete(foundItem);
        });
    }
}
