package com.words.core;

import android.animation.ValueAnimator;
import android.view.View;
import com.words.core.symbols.SyllabusSymbol;

/**
 * Created by mara on 5/18/14.
 */
public class AnimationUpdate implements ValueAnimator.AnimatorUpdateListener {
    private View view;
    private SyllabusSymbol symbol;

    public AnimationUpdate(View view, SyllabusSymbol symbol) {
        this.view = view;
        this.symbol = symbol;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        this.symbol.setAlpha((Integer) animation.getAnimatedValue());
        view.invalidate(this.symbol.getBoundingBox());
    }

}
