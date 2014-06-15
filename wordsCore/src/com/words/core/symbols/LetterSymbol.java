package com.words.core.symbols;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.util.Log;
import android.view.View;
import com.words.core.AnimationUpdate;
import com.words.core.Letter;
import com.words.core.R;

import java.util.Random;

/**
 * Created by mara on 3/24/14.
 */
public class LetterSymbol implements ISymbol {
    private Bitmap img;
    private int coordX = 0;
    private int coordY = 0;
    private int savedX;
    private int savedY;
    private Letter letter;
    private boolean isMovable = true;
    private boolean isAttached = false;

    public LetterSymbol(Letter letter) {
        this.letter = letter;
    }

    public LetterSymbol(Letter letter, int coordX, int coordY) {
        this.letter = letter;
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public void initialize(Context context) {
        //selected size in Android assets Studio 70px
        Random rand = new Random();
        int number = rand.nextInt(7);
        switch (number) {
            case (0):
                this.img = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_owl_red);
                break;
            case (1):
                this.img = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_owl_blue);
                break;
            case (2):
                this.img = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_owl_green);
                break;
            case (3):
                this.img = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_owl_light_blue);
                break;
            case (4):
                this.img = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_owl_orange);
                break;
            case (5):
                this.img = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_owl_pink);
                break;
            case (6):
                this.img = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_owl_yellow);
                break;
            default:
                break;
        }

    }

    @Override
    public void draw(Context context, Canvas canvas) {

        canvas.drawBitmap(img, this.coordX, this.coordY, null);
        Log.d("DRAW", "draw called");
        Paint paint = new Paint();
        paint.setTextSize(getFontSize());
        paint.setStrokeWidth(7);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        String text = Character.toString(this.letter.getCharacter());
        //TODO: handle font size
        canvas.drawText(text, coordX + 55, coordY + 120, paint);
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
        return (float) 57.0;
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

    public void move(int X, int Y) {
        setX(X - getWidth() / 2);
        setY(Y - getHeight() / 2);
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

    public Letter getLetter() {
        return letter;
    }

    public boolean intersects(ISymbol symbol) {
        Rect letterBox = this.getBoundingBox();
        Rect symbolBox = symbol.getBoundingBox();
        boolean collision = Rect.intersects(letterBox, symbolBox);
        return collision;
    }

    public void animate(View view) {
        ObjectAnimator anim = createAnimator(view);
        anim.start();
    }

    public void setAttached() {
        this.isAttached = true;
    }

    public boolean isAttached() {
        return isAttached;
    }

    private ObjectAnimator createAnimator(View view) {

        int savedY = coordY;
        this.coordY = coordY + 30;
        ObjectAnimator anim = ObjectAnimator.ofInt(this, "y", coordY, savedY);
        anim.setDuration(200);
        anim.setRepeatCount(3);
        anim.setRepeatMode(ValueAnimator.REVERSE);
        AnimationUpdate listener = new AnimationUpdate(view);
        anim.addUpdateListener(listener);
        return anim;
    }
}
