package com.example.arduinocontroller.Widgets.Button.RV;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.DB.Repository.ButtonWidgetRepository;

import java.util.List;

public class ButtonWidgetViewModel extends AndroidViewModel {

    private ButtonWidgetRepository mRepository;

    private final LiveData<List<ButtonWidgetItem>> mAllItems;

    public ButtonWidgetViewModel(Application application) {
        super(application);
        mRepository = new ButtonWidgetRepository(application);
        mAllItems = mRepository.getAllItems();
    }

    public LiveData<List<ButtonWidgetItem>> getAllItems() {
        return mAllItems;
    }

    public void insert(ButtonWidgetItem item) {
        mRepository.insert(item);
    }

}
