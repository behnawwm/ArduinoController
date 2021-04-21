package com.example.arduinocontroller.UI.Widgets.VoiceCommand;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import es.dmoral.toasty.Toasty;

@HiltViewModel
public class VoiceCommandViewModel extends AndroidViewModel {

    MutableLiveData<String> resultData = new MutableLiveData<>();

    @Inject
    public VoiceCommandViewModel(Application application) {
        super(application);
    }

    public SpeechRecognizer getSpeechRecognizer() {
        SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getApplication());

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {
            }

            @Override
            public void onBeginningOfSpeech() {
            }

            @Override
            public void onRmsChanged(float v) {
            }

            @Override
            public void onBufferReceived(byte[] bytes) {
            }

            @Override
            public void onEndOfSpeech() {
            }

            @Override
            public void onError(int i) {
                Toasty.info(getApplication(), "Couln't hear that!", Toasty.LENGTH_SHORT).show();
            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null)
                    for (String match : matches)
                        resultData.postValue(match);
//                        editText.append(match);
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        return mSpeechRecognizer;
    }

    public Intent getSpeechRecognitionIntent() {
        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());
        return mSpeechRecognizerIntent;
    }
}
