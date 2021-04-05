package com.example.arduinocontroller.Widgets.Button;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.davidmiguel.multistateswitch.MultiStateSwitch;
import com.davidmiguel.multistateswitch.State;
import com.davidmiguel.multistateswitch.StateListener;
import com.example.arduinocontroller.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ButtonWidgetBottomSheetDialog extends BottomSheetDialogFragment {
    int switchState;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_widget_button,
                container, false);

        ImageView btnClose = v.findViewById(R.id.bottom_sheet_widget_button_close);
        Button btnSave = v.findViewById(R.id.bottom_sheet_widget_button_save);
        TextInputEditText etName = v.findViewById(R.id.bottom_sheet_widget_button_name);
        TextInputEditText etOn = v.findViewById(R.id.bottom_sheet_widget_button_on);
        TextInputEditText etOff = v.findViewById(R.id.bottom_sheet_widget_button_off);
        MultiStateSwitch switchType = v.findViewById(R.id.bottom_sheet_widget_button_type);

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
                        etOff.getText().toString().equals("")) {
                    //save to DB
                    dismiss();
                } else {
                    Toasty.warning(getContext(), "Fill out all fields!").show();
                }
            }
        });

        return v;
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