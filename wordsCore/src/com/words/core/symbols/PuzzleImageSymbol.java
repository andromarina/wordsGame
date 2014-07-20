package com.words.core.symbols;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by mara on 7/9/14.
 */
public class PuzzleImageSymbol implements ISymbol {
    private Bitmap img;
    private int coordX;
    private int coordY;
    private int offset = 15;
    private String pictureName;

    public PuzzleImageSymbol(String pictureName) {
        this.pictureName = pictureName;
    }

    @Override
    public void draw(Context context, Canvas canvas) {
        Resources res = context.getResources();
        int drawableId = res.getIdentifier("drawable/" + pictureName, "drawable", context.getPackageName());
        this.img = BitmapFactory.decodeResource(res, drawableId);
        int x = canvas.getWidth() - this.img.getWidth() - offset;
        int y = canvas.getHeight() - this.img.getHeight() - offset;
        canvas.drawBitmap(img, x, y, null);
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
        this.coordX = newX;
    }

    @Override
    public void setY(int newY) {
        this.coordY = newY;
    }

    @Override
    public boolean contains(int X, int Y) {
        return false;
    }

    @Override
    public Rect getBoundingBox() {
        return null;
    }
}
