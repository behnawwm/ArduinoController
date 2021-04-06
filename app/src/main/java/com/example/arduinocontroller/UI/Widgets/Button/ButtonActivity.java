package com.example.arduinocontroller.UI.Widgets.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.R;
import com.example.arduinocontroller.UI.Widgets.Button.BottomSheet.ButtonWidgetBottomSheetDialog;
import com.example.arduinocontroller.UI.Widgets.Button.RV.ButtonWidgetAdapter;
import com.example.arduinocontroller.UI.Widgets.Button.RV.ButtonWidgetViewHolder;
import com.example.arduinocontroller.UI.Widgets.Button.RV.ButtonWidgetViewModel;
import com.example.arduinocontroller.Utils.AnimationUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikepenz.itemanimators.AlphaInAnimator;

import java.util.ArrayList;

public class ButtonActivity extends AppCompatActivity implements ButtonWidgetViewHolder.OnWidgetButtonListener {
    private ButtonWidgetViewModel mButtonWidgetViewModel;
    RecyclerView recyclerView;
    ButtonWidgetAdapter adapter;
    FloatingActionButton fab;
    ArrayList<ButtonWidgetItem> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
        getSupportActionBar().setTitle("Button");

        recyclerView = findViewById(R.id.rv_widget_button);
        fab = findViewById(R.id.fab_activity_widget_button);

        setUpRecyclerView();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupAddBottomSheet();
            }
        });
    }

    private void setUpRecyclerView() {
        adapter = new ButtonWidgetAdapter(getBaseContext(), mList, this);
        mButtonWidgetViewModel = new ViewModelProvider(this).get(ButtonWidgetViewModel.class);
        mButtonWidgetViewModel.getAllItems().observe(this, items -> {
            adapter.updateButtonWidgetListItems(items);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new AlphaInAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
            case R.id.menu_switch_delete:
                mButtonWidgetViewModel.deleteAll();
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

    @Override
    public void onItemClick(int position) {
//        Toast.makeText(this, "item click", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOptionsClick(int position) {
        Toast.makeText(this, "options click", Toast.LENGTH_SHORT).show();

    }


}