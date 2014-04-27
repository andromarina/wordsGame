package com.words.core.symbols;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;

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

        ArrayList<LetterHolderSymbol> lS = new ArrayList<LetterHolderSymbol>();
        int deltaX = this.coordX;
        for(int i = 0; i < this.lettersCount; ++i) {
            LetterHolderSymbol letterSymbol = new LetterHolderSymbol(i);
            letterSymbol.initialize(this.context);
            if (i == 0) {
                deltaX = calculateXCoordinate(letterSymbol.getWidth());
            }
            letterSymbol.setX(deltaX);
            Random rand = new Random();
            this.coordY = rand.nextInt(60 - this.offset) + this.offset;
            letterSymbol.setY(this.coordY);
            deltaX = deltaX + letterSymbol.getWidth() + this.offset;
            lS.add(letterSymbol);
        }

        return lS;
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
