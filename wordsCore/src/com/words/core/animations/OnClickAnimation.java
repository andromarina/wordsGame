package com.words.core.animations;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.BounceInterpolator;
import com.words.core.IPlayerListener;
import com.words.core.Scene;
import com.words.core.symbols.SyllabusSymbol;

/**
 * Created by mara on 10/23/14.
 */
public class OnClickAnimation implements IPlayerListener {
    private Scene scene;
    private SyllabusSymbol syllabusSymbol;

    public OnClickAnimation(Scene scene, SyllabusSymbol syllabusSymbol) {
        this.scene = scene;
        this.syllabusSymbol = syllabusSymbol;
    }

    @Override
    public void onSoundStart() {
        ValueAnimator animator = createSymbolAnimator(scene, syllabusSymbol);
        animator.start();
    }

    private ValueAnimator createSymbolAnimator(View view, SyllabusSymbol syllabusSymbol) {
        int y = syllabusSymbol.getY();
        ValueAnimator animation = ValueAnimator.ofInt(y, y + 30, y);
        animation.setDuration(600);
        YCoordinateAnimationUpdate listener = new YCoordinateAnimationUpdate(view, syllabusSymbol);
        animation.addUpdateListener(listener);
        BounceInterpolator interpolator = new BounceInterpolator();
        animation.setInterpolator(interpolator);
        return animation;
    }
}
