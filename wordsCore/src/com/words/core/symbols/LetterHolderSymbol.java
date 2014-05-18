package com.words.core.symbols;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import com.words.core.AnimationUpdate;
import com.words.core.Letter;
import com.words.core.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mara on 4/22/14.
 */
public class LetterHolderSymbol implements ISymbol {
    private Bitmap img;
    private int coordX;
    private int coordY;
    private int position;
    private ISymbol attachedSymbol;

    public LetterHolderSymbol(int position) {
        this.position = position;
    }

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
        int offset = 50;
        Rect rect = new Rect(coordX - offset, coordY - offset, coordX -offset + weight, coordY -offset + height);
        return rect;
    }

    public int getPosition() {
        return position;
    }

    public int getHeight() {
        return this.img.getHeight();
    }


    public int getWidth() {
        return this.img.getWidth();
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

    public int getX() {
        return coordX;
    }

    public int getY() {
        return coordY;
    }

    public void animate(View view) {
        ObjectAnimator anim = createAnimator(view);
        anim.start();
    }

    public void attach(ISymbol symbol) {
        this.attachedSymbol = symbol;
    }

    private ObjectAnimator createAnimator(View view) {

            int offset = 10;
            Random rand = new Random();
            this.coordY = rand.nextInt(60 - offset) + offset;
            ObjectAnimator anim = ObjectAnimator.ofInt(this, "y", offset, coordY);
            anim.setDuration(1500);
            anim.setRepeatCount(ValueAnimator.INFINITE);
            anim.setRepeatMode(ValueAnimator.REVERSE);
            AnimationUpdate listener = new AnimationUpdate(view);
            anim.addUpdateListener(listener);
        return anim;
    }
}
