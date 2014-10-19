package com.words.core.symbols;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import com.words.core.IPlayer;
import com.words.core.IPlayerListener;
import com.words.core.R;
import com.words.core.Syllabus;

/**
 * Created by mara on 3/24/14.
 */
public class SyllabusSymbol implements ISymbol, IPlayerListener {

    private BitmapDrawable bitmapDrawable;
    private Bitmap img;
    private int coordX = 0;
    private int coordY = 0;
    private int savedX;
    private int savedY;
    private Syllabus syllabus;
    private boolean isMovable = true;
    private boolean isAttached = false;
    private boolean isTouched = false;
    private IPlayer player;
    private String soundName;
    private Paint textPaint;
    private ValueAnimator animator;

    public SyllabusSymbol(Syllabus syllabus, String soundName, IPlayer player) {
        this.syllabus = syllabus;
        this.soundName = soundName;
        this.player = player;
    }

    public void initialize(Context context, ValueAnimator animator) {
        //selected size in Android assets Studio 75px
        this.img = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_letters_yellow);
        this.bitmapDrawable = new BitmapDrawable(context.getResources(), img);
        createPaintText();
        setAlpha(0);
        this.textPaint.setAlpha(0);
        this.animator = animator;
    }

    public void onTouchStart() {
        this.isTouched = true;
    }

    public void onTouchFinish() {
        this.isTouched = false;
    }

    @Override
    public void draw(Context context, Canvas canvas) {
//        if (isTouched == true) {
//            this.img = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_letters_yellow_pressed);
//        } else {
//            this.img = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_letters_yellow);
//        }
        this.bitmapDrawable.setBounds(getBoundingBox());
        this.bitmapDrawable.draw(canvas);
        // canvas.drawBitmap(img, this.coordX, this.coordY, null);
        Log.d("DRAW", "draw called");

        String text = this.syllabus.getString();
        //TODO: handle font size
        int testWidth = (int) this.textPaint.measureText(text);
        int textHeight = (int) this.textPaint.getTextSize();
        int x = getWidth() / 2 - testWidth / 2;
        int y = getHeight() / 2 + textHeight / 4;
        canvas.drawText(text, coordX + x, coordY + y, this.textPaint);
    }

    @Override
    public int getX() {
        return this.coordX;
    }

    @Override
    public int getY() {
        return this.coordY;
    }

    public void setAlpha(int value) {
        Log.d("Words", "Alpha is " + value);
        this.bitmapDrawable.setAlpha(value);
        this.textPaint.setAlpha(value);
    }

    public void animate() {
        this.animator.start();
    }

    public void saveCoordinates() {
        this.savedX = this.coordX;
        this.savedY = this.coordY;
    }

    public int getSavedX() {
        return savedX;
    }

    public int getSavedY() {
        return savedY;
    }

    public void setX(int newX) {
        this.coordX = newX;
    }

    public void setY(int newY) {
        this.coordY = newY;
    }

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

    public void setMovable(boolean value) {
        this.isMovable = value;
    }

    public boolean isMovable() {
        return isMovable;
    }

    public void move(int dX, int dY) {
        setX(dX + coordX);
        setY(dY + coordY);
    }

    public Rect getBoundingBox() {
        int height = getHeight();
        int weight = getWidth();
        Rect rect = new Rect(coordX, coordY, coordX + weight, coordY + height);
        return rect;
    }

    public int getHeight() {
        return this.bitmapDrawable.getBitmap().getHeight();
    }

    public int getWidth() {
        return this.bitmapDrawable.getBitmap().getWidth();
    }

    public Syllabus getSyllabus() {
        return this.syllabus;
    }

    public boolean intersects(ISymbol symbol) {
        Rect letterBox = this.getBoundingBox();
        Rect symbolBox = symbol.getBoundingBox();
        boolean collision = Rect.intersects(letterBox, symbolBox);
        return collision;
    }

    public void setAttached() {
        this.isAttached = true;
    }

    public void play() {
        this.player.addToPlaylist(this.soundName, this);
    }

    public boolean isAttached() {
        return isAttached;
    }


    private float getFontSize() {
        return (float) 70.0;
    }

    private void createPaintText() {
        this.textPaint = new Paint();
        this.textPaint.setTextSize(getFontSize());
        this.textPaint.setStyle(Paint.Style.FILL);
        this.textPaint.setAntiAlias(true);
        this.textPaint.setColor(Color.BLACK);
    }

    @Override
    public void onSoundStart() {
        animate();
    }
}
