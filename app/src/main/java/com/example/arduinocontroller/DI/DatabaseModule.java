package com.example.arduinocontroller.DI;

import android.content.Context;

import androidx.room.Room;

import com.example.arduinocontroller.DB.ButtonWidgetDatabase;
import com.example.arduinocontroller.DB.Dao.ButtonWidgetDao;
import com.example.arduinocontroller.DB.Dao.DimmerWidgetDao;
import com.example.arduinocontroller.DB.DimmerWidgetDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Singleton
    @Provides
    public ButtonWidgetDatabase provideButtonWidgetDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                ButtonWidgetDatabase.class, "button_database")
                .build();
    }

    @Singleton
    @Provides
    ButtonWidgetDao provideButtonWidgetDAO(ButtonWidgetDatabase db) {
        return db.buttonWidgetDao();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    @Singleton
    @Provides
    public DimmerWidgetDatabase provideDimmerWidgetDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                DimmerWidgetDatabase.class, "dimmer_database")
                .fallbackToDestructiveMigration() //todo check
                .build();
    }

    @Singleton
    @Provides
    DimmerWidgetDao provideDimmerWidgetDAO(DimmerWidgetDatabase db) {
        return db.dimmerWidgetDao();
    }
}
