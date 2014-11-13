package com.words.core.symbols;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import com.words.core.R;

import java.util.ArrayList;

/**
 * Created by mara on 4/22/14.
 */
public class SyllabusHolderSymbol implements ISymbol {
    private BitmapDrawable bitmapDrawable;
    private Bitmap img;
    private int coordX;
    private int coordY = 30;
    private int position;
    private ISymbol attachedSymbol;
    private ArrayList<SyllabusSymbol> bindedSyllabusSymbols;

    public SyllabusHolderSymbol(int position) {
        this.position = position;
    }

    public void initialize(Context context) {
        this.img = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_letters_blue);
        this.bitmapDrawable = new BitmapDrawable(img);
    }

    public void draw(Context context, Canvas canvas) {
        this.bitmapDrawable.setBounds(getBoundingBox());
        this.bitmapDrawable.draw(canvas);
    }

    @Override
    public boolean contains(int X, int Y) {
        int centerX = this.coordX + getWidth() / 2;
        int centerY = this.coordY + getHeight() / 2;

        // calculate the radius from the touch to the center
        double radCircle = Math.sqrt((double) (((centerX - X) * (centerX - X)) + (centerY - Y) * (centerY - Y)));

        if (radCircle < getWidth() / 2) {
            return true;
        }
        return false;
    }

    public Rect getBoundingBox() {
        int height = getHeight();
        int weight = getWidth();
        int offset = 50;
        Rect rect = new Rect(coordX, coordY, coordX + weight, coordY + height);
        return rect;
    }

    public int getPosition() {
        return position;
    }

    public int getHeight() {
        return this.bitmapDrawable.getBitmap().getHeight();
    }


    public int getWidth() {
        return this.bitmapDrawable.getBitmap().getWidth();
    }


    public void setX(int newX) {
        this.coordX = newX;
        if (this.attachedSymbol != null) {
            this.attachedSymbol.setX(newX);
        }
    }

    public void setY(int newY) {
        this.coordY = newY;
        if (this.attachedSymbol != null) {
            this.attachedSymbol.setY(newY);
        }
    }

    @Override
    public void setAlpha(int value) {
        this.bitmapDrawable.setAlpha(value);
    }

    @Override
    public void setScale(float value) {
        int newWidth = (int) (getWidth() * value);
        int newHeight = (int) (getHeight() * value);
        if (newWidth <= 0 || newHeight <= 0) {
            return;
        }
        Bitmap newImg = Bitmap.createBitmap(this.img, 0, 0, newWidth, newHeight);
        this.bitmapDrawable = new BitmapDrawable(newImg);
    }

    public int getX() {
        return coordX;
    }

    public int getY() {
        return coordY;
    }

    public void attachToAnimation(SyllabusSymbol symbol) {
        this.attachedSymbol = symbol;
        symbol.setAttached();
    }

    public void bindSyllabusSymbols(ArrayList<SyllabusSymbol> syllabusSymbols) {
        this.bindedSyllabusSymbols = new ArrayList<SyllabusSymbol>();
        for (SyllabusSymbol syllabusSymbol : syllabusSymbols) {
            this.bindedSyllabusSymbols.add(syllabusSymbol);
        }
    }

    public SyllabusSymbol getHintSyllabusSymbol() {
        for (SyllabusSymbol syllabusSymbol : this.bindedSyllabusSymbols) {
            if (!syllabusSymbol.isAttached()) {
                return syllabusSymbol;
            }
        }
        return null;
    }
}
