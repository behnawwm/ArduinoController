package com.example.arduinocontroller.Widgets.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.arduinocontroller.DB.Model.ButtonWidgetItem;
import com.example.arduinocontroller.R;
import com.example.arduinocontroller.Utils.AnimationUtils;
import com.example.arduinocontroller.Widgets.Button.BottomSheet.ButtonWidgetBottomSheetDialog;
import com.example.arduinocontroller.Widgets.Button.RV.ButtonWidgetViewModel;

public class ButtonActivity extends AppCompatActivity {
    private ButtonWidgetViewModel mButtonWidgetViewModel;
    RecyclerView recyclerView;
    ButtonWidgetAdapter adapter;

    boolean enable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
        getSupportActionBar().setTitle("Button");

        /////////
        recyclerView = findViewById(R.id.rv_widget_button);
        adapter = new ButtonWidgetAdapter(new ButtonWidgetAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mButtonWidgetViewModel = new ViewModelProvider(this).get(ButtonWidgetViewModel.class);
        mButtonWidgetViewModel.getAllItems().observe(this, items -> {
            adapter.submitList(items);
        });
        ////////animation
        ImageView iv = findViewById(R.id.tessst);
        Button btn = findViewById(R.id.btttn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimationUtils.turnOnAnimation(getBaseContext(), iv);
            }

        });

//        TransitionDrawable transition = (TransitionDrawable) iv.getBackground();
//        transition.startTransition(2000);
        ////////
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
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
                ////
                ButtonWidgetItem mamad = new ButtonWidgetItem("qq", 1, "qqqq", "qqqqq");
                mButtonWidgetViewModel.insert(mamad);
                ///
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