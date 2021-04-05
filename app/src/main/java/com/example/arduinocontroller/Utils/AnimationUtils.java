package com.example.arduinocontroller.Utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;

import com.example.arduinocontroller.R;

public class AnimationUtils {


    public static void turnOnAnimation(Context mContext, View iv) {

        if (iv.getBackground().getConstantState().equals(mContext.getResources().getDrawable(R.drawable.switch_off).getConstantState())) {
            iv.animate()
                    .alpha(0f)
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            iv.setBackgroundResource(R.drawable.switch_on);
                            iv.animate()
                                    .alpha(1f)
                                    .setDuration(200)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                        }
                                    });
                        }

                    });
//            enable = !enable;
        } else {
            iv.animate()
                    .alpha(0f)
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            iv.setBackgroundResource(R.drawable.switch_off);
                            iv.animate()
                                    .alpha(1f)
                                    .setDuration(200)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                        }
                                    });
                        }

                    });
//            enable = !enable;
        }
    }
}
