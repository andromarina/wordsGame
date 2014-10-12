package com.words.core.symbols;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.util.Log;
import android.view.View;
import com.words.core.AnimationUpdate;
import com.words.core.IPlayer;
import com.words.core.R;
import com.words.core.Syllabus;

/**
 * Created by mara on 3/24/14.
 */
public class SyllabusSymbol implements ISymbol {
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

    public SyllabusSymbol(Syllabus syllabus, String soundName, IPlayer player) {
        this.syllabus = syllabus;
        this.soundName = soundName;
        this.player = player;

    }

    public void initialize(Context context) {
        //selected size in Android assets Studio 75px
        this.img = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_letters_yellow);
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
        canvas.drawBitmap(img, this.coordX, this.coordY, null);
        Log.d("DRAW", "draw called");
        Paint paint = new Paint();
        paint.setTextSize(getFontSize());
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);

        String text = this.syllabus.getString();
        //TODO: handle font size
        int testWidth = (int) paint.measureText(text);
        int textHeight = (int) paint.getTextSize();
        int x = getWidth() / 2 - testWidth / 2;
        int y = getHeight() / 2 + textHeight / 4;
        canvas.drawText(text, coordX + x, coordY + y, paint);
    }

    @Override
    public int getX() {
        return this.coordX;
    }

    @Override
    public int getY() {
        return this.coordY;
    }

    private float getFontSize() {
        return (float) 70.0;
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
        return this.img.getHeight();
    }

    public int getWidth() {
        return this.img.getWidth();
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

    public void animate(View view) {
        ObjectAnimator anim = scaleAnimation(view);
        anim.start();
    }

    public void setAttached() {
        this.isAttached = true;
    }

    public void play() {
        this.player.addToPlaylist(this.soundName);
    }

    public boolean isAttached() {
        return isAttached;
    }

    private ObjectAnimator scaleAnimation(View view) {

        int savedY = coordY;
        this.coordY = coordY + 30;
        ObjectAnimator anim = ObjectAnimator.ofInt(this, "y", coordY, savedY);
        anim.setDuration(5000);
        anim.setRepeatCount(25);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        AnimationUpdate listener = new AnimationUpdate(view, getBoundingBox());
        anim.addUpdateListener(listener);
        return anim;
    }
}
