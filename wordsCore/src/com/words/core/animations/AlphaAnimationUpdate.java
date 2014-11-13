package com.words.core.animations;

import android.animation.ValueAnimator;
import android.view.View;
import com.words.core.symbols.ISymbol;

/**
 * Created by mara on 5/18/14.
 */
public class AlphaAnimationUpdate implements ValueAnimator.AnimatorUpdateListener {
    private View view;
    private ISymbol symbol;

    public AlphaAnimationUpdate(View view, ISymbol symbol) {
        this.view = view;
        this.symbol = symbol;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        this.symbol.setAlpha((Integer) animation.getAnimatedValue());
        view.invalidate(this.symbol.getBoundingBox());
    }

}
