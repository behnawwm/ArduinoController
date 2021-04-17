package com.example.arduinocontroller.DB.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.DB.Model.DimmerWidgetItem;

import java.util.List;

@Dao
public interface DimmerWidgetDao {
    @Query("SELECT * FROM dimmerwidgetitem")
    LiveData<List<DimmerWidgetItem>> getAll();

    @Query("SELECT * FROM dimmerwidgetitem WHERE id is :id")
    DimmerWidgetItem findByID(int id);

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    void insertAll(ButtonWidgetItem... items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DimmerWidgetItem item);

    @Delete
    void delete(DimmerWidgetItem item);

    @Query("DELETE FROM dimmerwidgetitem")
    void deleteAll();

    @Update()
    void update(DimmerWidgetItem item);

}
