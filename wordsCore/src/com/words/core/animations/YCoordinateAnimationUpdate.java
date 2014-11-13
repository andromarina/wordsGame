package com.words.core.animations;

import android.animation.ValueAnimator;
import android.view.View;
import com.words.core.symbols.SyllabusSymbol;

/**
 * Created by mara on 10/23/14.
 */
public class YCoordinateAnimationUpdate implements ValueAnimator.AnimatorUpdateListener {

    private View view;
    private SyllabusSymbol symbol;

    public YCoordinateAnimationUpdate(View view, SyllabusSymbol symbol) {
        this.view = view;
        this.symbol = symbol;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        this.symbol.setY((Integer) animation.getAnimatedValue());
        view.invalidate(this.symbol.getExtendedBoundingBox());
    }
}
