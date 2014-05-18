package com.words.core.symbols;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.test.UiThreadTest;
import android.util.DisplayMetrics;
import android.view.View;
import com.words.core.AnimationUpdate;
import com.words.core.Scene;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mara on 3/24/14.
 */
public class WordHolderSymbol implements ISymbol {
    private int coordX = 0;
    private int coordY = 10;
    private int offset = 15;
    private Context context;
    private int lettersCount;
    private ArrayList<LetterHolderSymbol> letterSymbols;
    private ArrayList<LetterHolderSymbol> letterHolderSymbols;

    public WordHolderSymbol(int lettersCount) {
        this.lettersCount = lettersCount;
    }

    public void initialize(Context context) {
        this.context = context;
        this.letterSymbols = createLetterSymbols();
    }

    @Override
    public void draw(Context context, Canvas canvas) {

        for(LetterHolderSymbol letterSymbol:this.letterSymbols) {
            letterSymbol.draw(context, canvas);
        }
    }

    private ArrayList<LetterHolderSymbol> createLetterSymbols() {

        this.letterHolderSymbols = new ArrayList<LetterHolderSymbol>();
        int deltaX = this.coordX;
        for(int i = 0; i < this.lettersCount; ++i) {
            LetterHolderSymbol letterHolderSymbol = new LetterHolderSymbol(i);
            letterHolderSymbol.initialize(this.context);
            if (i == 0) {
                deltaX = calculateXCoordinate(letterHolderSymbol.getWidth());
            }
            letterHolderSymbol.setX(deltaX);
            deltaX = deltaX + letterHolderSymbol.getWidth() + this.offset;
            letterHolderSymbols.add(letterHolderSymbol);
        }
        return letterHolderSymbols;
    }

    public void animate(View view) {

        for (LetterHolderSymbol letterHolderSymbol : this.letterHolderSymbols) {
           letterHolderSymbol.animate(view);
        }
    }

    private ArrayList<ObjectAnimator> createAnimators(View view) {
        ArrayList<ObjectAnimator> animators = new ArrayList<ObjectAnimator>();

        for (LetterHolderSymbol letterHolderSymbol : this.letterHolderSymbols) {
            Random rand = new Random();
            this.coordY = rand.nextInt(60 - this.offset) + this.offset;
            ObjectAnimator anim = ObjectAnimator.ofInt(letterHolderSymbol, "y", this.offset, coordY);
            anim.setDuration(2000);
            anim.setRepeatCount(ValueAnimator.INFINITE);
            anim.setRepeatMode(ValueAnimator.REVERSE);
            AnimationUpdate listener = new AnimationUpdate(view);
            anim.addUpdateListener(listener);
            animators.add(anim);
        }
        return animators;
    }

    @Override
    public int getX() {
        return this.coordX;
    }

    @Override
    public int getY() {
        return this.coordY;
    }

    @Override
    public void setX(int newX) {

    }

    @Override
    public void setY(int newY) {

    }

    @Override
    public boolean contains(int X, int Y) {
        return false;
    }

    @Override
    public Rect getBoundingBox() {
        return null;
    }

    public ArrayList<LetterHolderSymbol> getLetterSymbols() {
        return letterSymbols;
    }

    private int calculateXCoordinate(int width) {
        int wordLength = (width + this.offset) * this.lettersCount - this.offset;
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        int maxWidth = dm.widthPixels;
        int X = (maxWidth - wordLength)/2;
        return X;
    }
}
