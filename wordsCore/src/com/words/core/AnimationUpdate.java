package com.words.core;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;

/**
 * Created by mara on 5/18/14.
 */
public class AnimationUpdate implements ObjectAnimator.AnimatorUpdateListener {
    private View view;

    public AnimationUpdate(View view) {
        this.view = view;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        view.invalidate();
    }
}
