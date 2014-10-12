package com.words.core;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by mara on 5/18/14.
 */
public class AnimationUpdate implements ObjectAnimator.AnimatorUpdateListener {
    private View view;
    private Rect dirty;

    public AnimationUpdate(View view, Rect dirty) {
        this.view = view;
        this.dirty = dirty;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        view.invalidate(dirty);
    }

}
