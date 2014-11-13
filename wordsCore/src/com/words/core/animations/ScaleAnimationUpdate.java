package com.words.core.animations;

import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;
import com.words.core.symbols.ISymbol;

/**
 * Created by mara on 10/28/14.
 */
public class ScaleAnimationUpdate implements ValueAnimator.AnimatorUpdateListener {
    private View view;
    private ISymbol symbol;

    public ScaleAnimationUpdate(View view, ISymbol symbol) {
        this.view = view;
        this.symbol = symbol;
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        Log.d("Animation", "ScaleAnimationUpdate listener onAnimationUpdate called");
        this.symbol.setScale((Float) animation.getAnimatedValue());
        view.invalidate(this.symbol.getBoundingBox());
    }
}
