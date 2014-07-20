package com.words.core.symbols;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by mara on 3/24/14.
 */
public class WordHolderSymbol implements ISymbol {
    private int coordX = 0;
    private int coordY = 30;
    private int offset = 30;
    private Context context;
    private int syllabusesCount;
    private ArrayList<SyllabusHolderSymbol> syllabusHolderSymbols;

    public WordHolderSymbol(int syllabusesCount) {
        this.syllabusesCount = syllabusesCount;
    }

    public void initialize(Context context) {
        this.context = context;
        this.syllabusHolderSymbols = createSyllabusHolderSymbols();
    }

    @Override
    public void draw(Context context, Canvas canvas) {

        for (SyllabusHolderSymbol syllabusSymbol : this.syllabusHolderSymbols) {
            syllabusSymbol.draw(context, canvas);
        }
    }

    private ArrayList<SyllabusHolderSymbol> createSyllabusHolderSymbols() {

        this.syllabusHolderSymbols = new ArrayList<SyllabusHolderSymbol>();
        int deltaX = this.coordX;
        for (int i = 0; i < this.syllabusesCount; ++i) {
            SyllabusHolderSymbol syllabusHolderSymbol = new SyllabusHolderSymbol(i);
            syllabusHolderSymbol.initialize(this.context);
            if (i == 0) {
                deltaX = calculateXCoordinate(syllabusHolderSymbol.getWidth());
            }
            syllabusHolderSymbol.setX(deltaX);
            deltaX = deltaX + syllabusHolderSymbol.getWidth() + this.offset;
            syllabusHolderSymbols.add(syllabusHolderSymbol);
        }
        return syllabusHolderSymbols;
    }

    public void animate(View view) {

        for (SyllabusHolderSymbol syllabusHolderSymbol : this.syllabusHolderSymbols) {
            syllabusHolderSymbol.animate(view);
        }
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

    public ArrayList<SyllabusHolderSymbol> getSyllabusHolderSymbols() {
        return syllabusHolderSymbols;
    }

    private int calculateXCoordinate(int width) {
        int wordLength = (width + this.offset) * this.syllabusesCount - this.offset;
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        int maxWidth = dm.widthPixels;
        int X = (maxWidth - wordLength) / 2;
        return X;
    }
}
