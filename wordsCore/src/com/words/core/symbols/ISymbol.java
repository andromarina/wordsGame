package com.words.core.symbols;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by mara on 3/24/14.
 */
public interface ISymbol {

    public void draw(Context context, Canvas canvas);

    public int getX();

    public int getY();

    public void setX(int newX);

    public void setY(int newY);



    public boolean contains(int X, int Y);

    public Rect getBoundingBox();
}
