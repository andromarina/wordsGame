package com.words.core;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import com.words.core.symbols.PuzzleImageSymbol;
import com.words.core.symbols.SyllabusHolderSymbol;
import com.words.core.symbols.SyllabusSymbol;
import com.words.core.symbols.WordSymbol;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by mara on 3/24/14.
 */
public class TouchHandler implements View.OnTouchListener {
    private Scene scene;
    private SyllabusSymbol syllabusSymbol;
    private long touchStartTime;
    private boolean isClick = false;
    private int Xprev;
    private int Yprev;

    public TouchHandler(Scene scene) {
        this.scene = scene;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int eventAction = event.getAction();

        int X = (int) event.getX();
        int Y = (int) event.getY();

        switch (eventAction) {

            case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on a symbol
                isClick = true;
                this.touchStartTime = Calendar.getInstance().getTimeInMillis();
                Xprev = X;
                Yprev = Y;
                handleOnSyllabusTouch(X, Y);
                break;

            case MotionEvent.ACTION_MOVE:
                isClick = false;
                if (this.syllabusSymbol != null && this.syllabusSymbol.isMovable()) {
                    Rect oldBox = this.syllabusSymbol.getBoundingBox();
                    int dX = X - Xprev;
                    int dY = Y - Yprev;
                    this.syllabusSymbol.move(dX, dY);
                    Xprev = X;
                    Yprev = Y;
                    Rect newBox = this.syllabusSymbol.getBoundingBox();
                    newBox.union(oldBox);
                    v.invalidate(newBox);
                }
                break;

            case MotionEvent.ACTION_UP:
                if (this.syllabusSymbol != null) {
                    this.syllabusSymbol.onTouchFinish();
                }

                if (isAccidentMovement()) {
                    isClick = true;
                }
                if (isClick == true) {
                    handleOnSyllabusClick(X, Y);
                    handleImageClick(X, Y);
                }
                handleSyllabusPlacement();
                v.invalidate();
                break;
        }
        // redraw the canvas
        //v.invalidate();
        return true;
    }

    private void handleImageClick(int X, int Y) {
        PuzzleImageSymbol imageSymbol = this.scene.getPuzzleImageSymbol();

        if (imageSymbol.contains(X, Y)) {
            imageSymbol.play();
        }
    }

    private void handleSyllabusPlacement() {
        ArrayList<SyllabusHolderSymbol> holderSymbols = this.scene.getWordHolderSymbol().getSyllabusHolderSymbols();
        Word puzzleWord = this.scene.getWordSymbol().getWord();

        for (SyllabusHolderSymbol holderSymbol : holderSymbols) {
            if (this.syllabusSymbol != null && this.syllabusSymbol.intersects(holderSymbol)) {

                if (puzzleWord.placed(syllabusSymbol.getSyllabus(), holderSymbol.getPosition())) {
                    holderSymbol.attachToAnimation(syllabusSymbol);
                    syllabusSymbol.setX(holderSymbol.getX());
                    syllabusSymbol.setY(holderSymbol.getY());
                    syllabusSymbol.setMovable(false);
                    break;
                } else {
                    this.syllabusSymbol.setX(syllabusSymbol.getSavedX());
                    this.syllabusSymbol.setY(syllabusSymbol.getSavedY());
                }
            }
        }
    }

    private void handleOnSyllabusClick(int X, int Y) {
        this.syllabusSymbol = null;
        WordSymbol wordSymbol = this.scene.getWordSymbol();
        ArrayList<SyllabusSymbol> syllabusSymbols = wordSymbol.getSyllabusSymbols();
        for (SyllabusSymbol symbol : syllabusSymbols) {
            if (symbol.contains(X, Y)) {
                this.syllabusSymbol = symbol;
                this.syllabusSymbol.play();
                break;
            }
        }
    }

    private void handleOnSyllabusTouch(int X, int Y) {
        this.syllabusSymbol = null;
        WordSymbol wordSymbol = this.scene.getWordSymbol();
        ArrayList<SyllabusSymbol> syllabusSymbols = wordSymbol.getSyllabusSymbols();
        for (SyllabusSymbol symbol : syllabusSymbols) {

            if (symbol.contains(X, Y)) {
                this.syllabusSymbol = symbol;
                this.syllabusSymbol.saveCoordinates();
                this.syllabusSymbol.onTouchStart();
                break;
            }
        }
    }

    private boolean isAccidentMovement() {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        int difference = (int) (currentTime - this.touchStartTime);
        if (difference < 200) {
            return true;
        }
        return false;
    }

}
