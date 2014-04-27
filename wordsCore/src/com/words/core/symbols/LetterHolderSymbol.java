package com.words.core.symbols;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import com.words.core.Letter;
import com.words.core.R;

/**
 * Created by mara on 4/22/14.
 */
public class LetterHolderSymbol implements ISymbol {
    private Bitmap img;
    private int coordX;
    private int coordY;

    public LetterHolderSymbol() {}

    public void initialize(Context context) {
        this.img =  BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_cloud);
    }


    public void draw(Context context, Canvas canvas) {
        canvas.drawBitmap(img, this.coordX, this.coordY, null);
    }

    @Override
    public boolean contains(int X, int Y) {
        return false;
    }

    @Override
    public Rect getBoundingBox() {
        int height = getHeight();
        int weight = getWidth();
        Rect rect = new Rect(coordX, coordY, coordX + weight, coordY + height);
        return rect;
    }


    public int getHeight() {
        return this.img.getHeight();
    }


    public int getWidth() {
        return this.img.getWidth();
    }


    public void setX(int newX) {
        this.coordX = newX;
    }

    public void setY(int newY) {
        this.coordY = newY;
    }



    public int getX() {
        return coordX;
    }

    public int getY() {
        return coordY;
    }
}
