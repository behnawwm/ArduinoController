package com.example.arduinocontroller.DB.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.arduinocontroller.DB.ButtonWidgetDatabase;
import com.example.arduinocontroller.DB.Dao.DimmerWidgetDao;
import com.example.arduinocontroller.DB.DimmerWidgetDatabase;
import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.DB.Model.DimmerWidgetItem;

import java.util.List;

import javax.inject.Inject;

public class DimmerWidgetRepository {
    private DimmerWidgetDao mDimmerWidgetDao;
    private LiveData<List<DimmerWidgetItem>> mAllItems;


    public DimmerWidgetRepository(DimmerWidgetDao dao) {
//        DimmerWidgetDatabase db = DimmerWidgetDatabase.getDatabase(application);
        mDimmerWidgetDao = dao;
        mAllItems = mDimmerWidgetDao.getAll();
    }

    public LiveData<List<DimmerWidgetItem>> getAllItems() {
        return mAllItems;
    }

    public void insert(DimmerWidgetItem item) {
        ButtonWidgetDatabase.databaseWriteExecutor.execute(() -> {
            mDimmerWidgetDao.insert(item);
        });
    }

    public void deleteAll() {
        ButtonWidgetDatabase.databaseWriteExecutor.execute(() -> {
            mDimmerWidgetDao.deleteAll();
        });
    }

    public void update(DimmerWidgetItem savedItem, DimmerWidgetItem item) {
        ButtonWidgetDatabase.databaseWriteExecutor.execute(() -> {
            DimmerWidgetItem foundItem = mDimmerWidgetDao.findByID(savedItem.getId());
            item.setId(foundItem.getId());
            mDimmerWidgetDao.insert(item);
        });
    }

    public void deleteById(int id) {
        DimmerWidgetDatabase.databaseWriteExecutor.execute(() -> {
            DimmerWidgetItem foundItem = mDimmerWidgetDao.findByID(id);
            mDimmerWidgetDao.delete(foundItem);
        });
    }
}
