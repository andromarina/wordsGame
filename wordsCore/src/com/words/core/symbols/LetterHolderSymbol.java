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
    private ArrayList<LetterSymbol> bindedLetterSymbols;

    public LetterHolderSymbol(int position) {
        this.position = position;
    }

    public void initialize(Context context) {
        this.img = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_cloud);
    }

    public void draw(Context context, Canvas canvas) {
        canvas.drawBitmap(img, this.coordX, this.coordY, null);
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

    @Override
    public Rect getBoundingBox() {
        int height = getHeight();
        int weight = getWidth();
        int offset = 50;
        Rect rect = new Rect(coordX - offset, coordY - offset, coordX - offset + weight, coordY - offset + height);
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
            this.attachedSymbol.setY(newY - 30);
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

    public void attachToAnimation(LetterSymbol symbol) {
        this.attachedSymbol = symbol;
        symbol.setAttached();
    }

    public void bindLetterSymbols(ArrayList<LetterSymbol> letterSymbols) {
        this.bindedLetterSymbols = new ArrayList<LetterSymbol>();
        for (LetterSymbol letterSymbol : letterSymbols) {
            this.bindedLetterSymbols.add(letterSymbol);
        }
    }

    public LetterSymbol getHintLetterSymbol() {
        for (LetterSymbol letterSymbol : this.bindedLetterSymbols) {
            if (!letterSymbol.isAttached()) {
                return letterSymbol;
            }
        }
        return null;
    }

    private ObjectAnimator createAnimator(View view) {

        int offset = 50;
        Random rand = new Random();
        this.coordY = rand.nextInt(100 - offset) + offset;
        ObjectAnimator anim = ObjectAnimator.ofInt(this, "y", offset, coordY);
        anim.setDuration(2000);
        anim.setRepeatCount(ValueAnimator.INFINITE);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        int delay = rand.nextInt(500 - 100) + 100;
        // anim.setStartDelay(delay);
        AnimationUpdate listener = new AnimationUpdate(view);
        anim.addUpdateListener(listener);
        return anim;
    }
}
