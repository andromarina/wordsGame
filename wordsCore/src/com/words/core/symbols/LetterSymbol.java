package com.words.core.symbols;

import android.content.Context;
import android.graphics.*;
import com.words.core.Letter;
import com.words.core.R;

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

    public LetterSymbol(Letter letter) {
        this.letter = letter;
    }

    public LetterSymbol(Letter letter, int coordX, int coordY) {
        this.letter = letter;
        this.coordX = coordX;
        this.coordY = coordY;
    }

    public void initialize(Context context) {
        this.img = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_tile_blank);
    }

    @Override
    public void draw(Context context, Canvas canvas) {

        canvas.drawBitmap(img, this.coordX, this.coordY, null);
        Paint paint = new Paint();
        paint.setTextSize(getFontSize());
        String text = Character.toString(this.letter.getCharacter());
        //TODO: handle font size
        canvas.drawText(text, coordX + 50, coordY + 75, paint);
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
        return (float) 50.0;
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

        int centerX = this.coordX + getWidth()/2;
        int centerY = this.coordY + getHeight()/2;

        // calculate the radius from the touch to the center
        double radCircle  = Math.sqrt( (double) (((centerX-X)*(centerX-X)) + (centerY-Y)*(centerY-Y)));

        if (radCircle < getWidth()/2){
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
        setX(X - getWidth()/2);
        setY(Y - getHeight()/2);
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
}
