package com.words.core.symbols;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import com.words.core.ObjectPlayer;

/**
 * Created by mara on 7/9/14.
 */
public class PuzzleImageSymbol implements ISymbol {
    private Bitmap img;
    private int coordX;
    private int coordY;
    private int offset = 15;
    private String pictureName;
    private String soundString;
    private ObjectPlayer player;

    public PuzzleImageSymbol(String pictureName, Context context, ObjectPlayer player, String soundString) {
        this.pictureName = pictureName;
        initializeImage(context);
        this.player = player;
        this.soundString = soundString;
    }

    @Override
    public void draw(Context context, Canvas canvas) {
        this.coordX = canvas.getWidth() - this.img.getWidth() - offset;
        this.coordY = canvas.getHeight() - this.img.getHeight() - offset;
        canvas.drawBitmap(img, coordX, coordY, null);
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
    public void setAlpha(int value) {

    }

    @Override
    public void setScale(float value) {

    }

    @Override
    public boolean contains(int X, int Y) {
        int centerX = this.coordX + this.img.getWidth() / 2;
        int centerY = this.coordY + this.img.getHeight() / 2;

        // calculate the radius from the touch to the center
        double radCircle = Math.sqrt((double) (((centerX - X) * (centerX - X)) + (centerY - Y) * (centerY - Y)));

        if (radCircle < this.img.getWidth() / 2) {
            return true;
        }
        return false;
    }

    @Override
    public Rect getBoundingBox() {
        return null;
    }

    public void play() {
        player.play(soundString);
    }

    private void initializeImage(Context context) {
        Resources res = context.getResources();
        int drawableId = res.getIdentifier("drawable/" + pictureName, "drawable", context.getPackageName());
        this.img = BitmapFactory.decodeResource(res, drawableId);
    }
}
