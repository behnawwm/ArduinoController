package com.example.arduinocontroller.UI.Widgets.Button.BottomSheet;

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
import com.example.arduinocontroller.R;
import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.UI.Widgets.Button.RV.ButtonWidgetViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ButtonWidgetBottomSheetDialog extends BottomSheetDialogFragment {
    private ButtonWidgetViewModel mButtonWidgetViewModel;

    int switchState;

    TextInputEditText etName;
    TextInputEditText etOn;
    TextInputEditText etOff;
    MultiStateSwitch switchType;

    ButtonWidgetItem savedItem;

    public ButtonWidgetBottomSheetDialog() {

    }

    public ButtonWidgetBottomSheetDialog(ButtonWidgetItem item) {
        this.savedItem = item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_widget_button,
                container, false);

        mButtonWidgetViewModel = new ViewModelProvider(this).get(ButtonWidgetViewModel.class);

        ImageView btnClose = v.findViewById(R.id.bottom_sheet_widget_button_close);
        Button btnSave = v.findViewById(R.id.bottom_sheet_widget_button_save);
        etName = v.findViewById(R.id.bottom_sheet_widget_button_name);
        etOn = v.findViewById(R.id.bottom_sheet_widget_button_on);
        etOff = v.findViewById(R.id.bottom_sheet_widget_button_off);
        switchType = v.findViewById(R.id.bottom_sheet_widget_button_type);

        setUpSavedStats(savedItem);
        setUpMultiStateSwitch(switchType);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText().toString().equals("") ||
                        etOn.getText().toString().equals("") ||
                        etOff.getText().toString().equals(""))
                    Toasty.warning(getContext(), "Fill out all fields!").show();
                else {
                    //save to DB
                    ButtonWidgetItem item = new ButtonWidgetItem(etName.getText().toString(),
                            switchState,
                            etOn.getText().toString(),
                            etOff.getText().toString());

                    if (savedItem == null)
                        mButtonWidgetViewModel.insert(item);
                    else
                        mButtonWidgetViewModel.update(savedItem, item);

                    dismiss();
                }
            }
        });

        return v;
    }

    private void setUpSavedStats(ButtonWidgetItem saved) {
        if (saved != null) {
            etName.setText(saved.getText());
            etOn.setText(saved.getOnCommand());
            etOff.setText(saved.getOffCommand());
            switchType.selectState(saved.getType());
        }
    }

    private void setUpMultiStateSwitch(MultiStateSwitch switchType) {
        List<String> switchStates = new ArrayList<>();
        switchStates.add("Push");
        switchStates.add("Toggle");
        switchType.addStatesFromStrings(switchStates);
        switchType.addStateListener(new StateListener() {
            @Override
            public void onStateSelected(int i, State state) {
                switchState = i;
            }
        });
    }

}
