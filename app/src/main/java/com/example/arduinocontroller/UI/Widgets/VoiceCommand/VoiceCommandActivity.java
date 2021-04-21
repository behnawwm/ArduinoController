package com.example.arduinocontroller.UI.Widgets.VoiceCommand;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.arduinocontroller.R;
import com.google.android.material.textfield.TextInputEditText;

import dagger.hilt.android.AndroidEntryPoint;
import es.dmoral.toasty.Toasty;

@AndroidEntryPoint
public class VoiceCommandActivity extends AppCompatActivity {

    private static final int RECORD_AUDIO_REQUEST_CODE = 1;

    TextInputEditText editText;
    TextInputEditText editTextResult;
    Button btn;
    Button btnSend;
    SpeechRecognizer mSpeechRecognizer;
    Intent mSpeechRecognizerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_command);

        editText = findViewById(R.id.et_input_widget_voice);
        editTextResult = findViewById(R.id.et_result_widget_voice);
        btn = findViewById(R.id.btn_record_widget_voice);
        btnSend = findViewById(R.id.btn_send_widget_voice);

        checkPermission();
        VoiceCommandViewModel mViewModel = new ViewModelProvider(this).get(VoiceCommandViewModel.class);

        ///////////////////////////////
        mViewModel.resultData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null)
                    editText.setText(s);
            }
        });
        mSpeechRecognizer = mViewModel.getSpeechRecognizer();
        mSpeechRecognizerIntent = mViewModel.getSpeechRecognitionIntent();
        //////////////////////////

        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();
                        editText.setHint("You will see input here");
                        break;

                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        editText.setText("");
                        editText.setHint("Listening...");
                        break;
                }
                return false;
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextResult.append("> " + editText.getText().toString() + "\n");
            }
        });

    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO_REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == RECORD_AUDIO_REQUEST_CODE && resultCode == RESULT_OK)
                Toasty.success(this, "Microphone Permission granted!", Toast.LENGTH_SHORT).show();
            else {
                //todo
                Toasty.error(this, "Grant microphone permission to access this tool!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception ex) {
            Toast.makeText(VoiceCommandActivity.this, ex.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}