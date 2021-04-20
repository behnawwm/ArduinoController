package com.example.arduinocontroller.DI;

import com.example.arduinocontroller.DB.Dao.ButtonWidgetDao;
import com.example.arduinocontroller.DB.Dao.DimmerWidgetDao;
import com.example.arduinocontroller.DB.Repository.ButtonWidgetRepository;
import com.example.arduinocontroller.DB.Repository.DimmerWidgetRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {

    @Singleton
    @Provides
    ButtonWidgetRepository provideButtonWidgetRepository(ButtonWidgetDao dao) {
        return new ButtonWidgetRepository(dao);
    }

    @Singleton
    @Provides
    DimmerWidgetRepository provideDimmerWidgetRepository(DimmerWidgetDao dao) {
        return new DimmerWidgetRepository(dao);
    }
}
