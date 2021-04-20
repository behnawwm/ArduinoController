package com.example.arduinocontroller.UI.Widgets.Dimmer.RV;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.DB.Model.DimmerWidgetItem;
import com.example.arduinocontroller.DB.Repository.ButtonWidgetRepository;
import com.example.arduinocontroller.DB.Repository.DimmerWidgetRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DimmerWidgetViewModel extends AndroidViewModel {

    private DimmerWidgetRepository mRepository;

    private final LiveData<List<DimmerWidgetItem>> mAllItems;

    @Inject
    public DimmerWidgetViewModel(Application application, DimmerWidgetRepository repository) {
        super(application);
        mRepository = repository;
        mAllItems = mRepository.getAllItems();
    }

    public LiveData<List<DimmerWidgetItem>> getAllItems() {
        return mAllItems;
    }

    public void insert(DimmerWidgetItem item) {
        mRepository.insert(item);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void update(DimmerWidgetItem savedItem, DimmerWidgetItem item) {
        mRepository.update(savedItem, item);
    }

    public void deleteById(int id) {
        mRepository.deleteById(id);
    }


}
