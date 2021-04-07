package com.example.arduinocontroller.DB.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;

import java.util.List;

@Dao
public interface ButtonWidgetDao {
    @Query("SELECT * FROM buttonwidgetitem")
    LiveData<List<ButtonWidgetItem>> getAll();

    @Query("SELECT * FROM buttonwidgetitem WHERE id is :id")
    ButtonWidgetItem findByID(int id);

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    void insertAll(ButtonWidgetItem... items);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ButtonWidgetItem item);

    @Delete
    void delete(ButtonWidgetItem item);

    @Query("DELETE FROM buttonwidgetitem")
    void deleteAll();

    @Update()
    void update(ButtonWidgetItem item);

}
