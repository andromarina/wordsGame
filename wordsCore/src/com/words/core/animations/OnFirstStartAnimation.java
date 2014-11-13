package com.words.core.animations;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import com.words.core.IPlayerListener;
import com.words.core.symbols.SyllabusSymbol;

/**
 * Created by mara on 10/22/14.
 */
public class OnFirstStartAnimation implements IPlayerListener

{
    private ValueAnimator animator;

    public OnFirstStartAnimation(View scene, SyllabusSymbol syllabusSymbol) {
        this.animator = createSymbolAnimator(scene, syllabusSymbol);
    }

    @Override
    public void onSoundStart() {
        this.animator.start();
    }

    private ValueAnimator createSymbolAnimator(View view, SyllabusSymbol syllabusSymbol) {
        ValueAnimator animation = ValueAnimator.ofInt(0, 255);
        animation.setDuration(1000);
        //   animation.setStartDelay(500);
        AlphaAnimationUpdate listener = new AlphaAnimationUpdate(view, syllabusSymbol);
        animation.addUpdateListener(listener);
        AccelerateInterpolator interpolator = new AccelerateInterpolator();
        animation.setInterpolator(interpolator);
        return animation;
    }
}
