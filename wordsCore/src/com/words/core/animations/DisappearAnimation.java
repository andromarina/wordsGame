package com.words.core.animations;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import com.words.core.symbols.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by mara on 10/26/14.
 */
public class DisappearAnimation {
    private AnimatorSet animatorSet;
    private ArrayList<Animator> alphaAnimators;
    private ArrayList<Animator> scaleAnimators;
    private final int duration = 1000;

    public DisappearAnimation(View view, WordSymbol wordSymbol, WordHolderSymbol wordHolderSymbol) {
        this.alphaAnimators = createAlphaAnimators(view, wordSymbol, wordHolderSymbol);
        this.scaleAnimators = createScaleAnimators(view, wordSymbol, wordHolderSymbol);
        this.animatorSet = new AnimatorSet();
    }

    public void play() {
        Collection<Animator> mergedAnimatorsCollection = this.alphaAnimators;
        for (Animator animator : this.scaleAnimators) {
            mergedAnimatorsCollection.add(animator);
        }
        this.animatorSet.playTogether(mergedAnimatorsCollection);
        this.animatorSet.start();
    }

    public void setListener(AnimatorSet.AnimatorListener listener) {
        this.animatorSet.addListener(listener);
    }

    private ArrayList<Animator> createAlphaAnimators(View view, WordSymbol wordSymbol, WordHolderSymbol wordHolderSymbol) {
        ArrayList<Animator> animators = new ArrayList<Animator>();

        for (SyllabusSymbol syllabusSymbol : wordSymbol.getSyllabusSymbols()) {
            Animator wordSymbolAnimator = prepareAlphaAnimator(view, syllabusSymbol);
            animators.add(wordSymbolAnimator);
        }

        for (SyllabusHolderSymbol syllabusHolderSymbol : wordHolderSymbol.getSyllabusHolderSymbols()) {
            Animator wordSymbolAnimator = prepareAlphaAnimator(view, syllabusHolderSymbol);
            animators.add(wordSymbolAnimator);
        }

        return animators;
    }

    private Animator prepareAlphaAnimator(View view, ISymbol symbol) {
        ValueAnimator animation = ValueAnimator.ofInt(255, 0);
        animation.setDuration(this.duration);
        AlphaAnimationUpdate listener = new AlphaAnimationUpdate(view, symbol);
        animation.addUpdateListener(listener);
        AccelerateInterpolator interpolator = new AccelerateInterpolator();
        animation.setInterpolator(interpolator);
        return animation;
    }


    private ArrayList<Animator> createScaleAnimators(View view, WordSymbol wordSymbol, WordHolderSymbol wordHolderSymbol) {
        ArrayList<Animator> animators = new ArrayList<Animator>();

        for (SyllabusSymbol syllabusSymbol : wordSymbol.getSyllabusSymbols()) {
            Animator animator = prepareScaleAnimator(view, syllabusSymbol);
            animators.add(animator);
        }

        for (SyllabusHolderSymbol syllabusHolderSymbol : wordHolderSymbol.getSyllabusHolderSymbols()) {
            Animator animator = prepareScaleAnimator(view, syllabusHolderSymbol);
            animators.add(animator);
        }
        return animators;
    }

    private Animator prepareScaleAnimator(View view, ISymbol symbol) {
        ValueAnimator animation = ValueAnimator.ofFloat(1.0f, 0.8f);
        animation.setDuration(this.duration);
        ScaleAnimationUpdate listener = new ScaleAnimationUpdate(view, symbol);
        animation.addUpdateListener(listener);
        AccelerateInterpolator interpolator = new AccelerateInterpolator();
        animation.setInterpolator(interpolator);
        return animation;
    }
}
