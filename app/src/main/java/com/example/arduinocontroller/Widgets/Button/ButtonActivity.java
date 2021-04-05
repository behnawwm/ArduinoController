package com.example.arduinocontroller.Widgets.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.arduinocontroller.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class ButtonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
        getSupportActionBar().setTitle("Button");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_widget_button, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_switch_add:
                setupAddBottomSheet();
                return true;
            case R.id.menu_switch_delete:
                //sth
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupAddBottomSheet() {
        ButtonWidgetBottomSheetDialog bottomSheet = new ButtonWidgetBottomSheetDialog();
        bottomSheet.show(getSupportFragmentManager(),
                "ModalBottomSheet");
    }


}