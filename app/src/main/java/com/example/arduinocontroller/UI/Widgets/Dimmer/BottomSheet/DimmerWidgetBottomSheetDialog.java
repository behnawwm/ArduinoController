package com.example.arduinocontroller.UI.Widgets.Dimmer.BottomSheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.davidmiguel.multistateswitch.MultiStateSwitch;
import com.davidmiguel.multistateswitch.State;
import com.davidmiguel.multistateswitch.StateListener;
import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.DB.Model.DimmerWidgetItem;
import com.example.arduinocontroller.R;
import com.example.arduinocontroller.UI.Widgets.Dimmer.RV.DimmerWidgetViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class DimmerWidgetBottomSheetDialog extends BottomSheetDialogFragment {
    private DimmerWidgetViewModel mDimmerWidgetViewModel;

    TextInputEditText etName;

    DimmerWidgetItem savedItem;

    public DimmerWidgetBottomSheetDialog() {

    }

    public DimmerWidgetBottomSheetDialog(DimmerWidgetItem item) {
        this.savedItem = item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_widget_dimmer,
                container, false);

        mDimmerWidgetViewModel = new ViewModelProvider(this).get(DimmerWidgetViewModel.class);

        ImageView btnClose = v.findViewById(R.id.bottom_sheet_widget_dimmer_close);
        Button btnSave = v.findViewById(R.id.bottom_sheet_widget_dimmer_save);
        etName = v.findViewById(R.id.bottom_sheet_widget_dimmer_name);

        setUpSavedStats(savedItem);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText().toString().equals(""))
                    Toasty.warning(getContext(), "Fill out all fields!").show();
                else {
                    //save to DB
                    DimmerWidgetItem item = new DimmerWidgetItem(etName.getText().toString(),
                            0
                            , "dimmer_" + etName.getText().toString());  //todo make pattern for new made items

                    if (savedItem == null)
                        mDimmerWidgetViewModel.insert(item);
                    else
                        mDimmerWidgetViewModel.update(savedItem, item);

                    dismiss();
                }
            }
        });

        return v;
    }

    private void setUpSavedStats(DimmerWidgetItem saved) {
        if (saved != null) {
            etName.setText(saved.getText());
        }
    }

}
