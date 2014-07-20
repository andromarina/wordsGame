package com.words.core;

import android.view.MotionEvent;
import android.view.View;
import com.words.core.symbols.SyllabusHolderSymbol;
import com.words.core.symbols.SyllabusSymbol;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by mara on 3/24/14.
 */
public class TouchHandler implements View.OnTouchListener {
    private Scene scene;
    private SyllabusSymbol syllabusSymbol;
    private SyllabusHolderSymbol syllabusHolderSymbol;
    private long touchStartTime;
    private boolean isClick = false;

    public TouchHandler(Scene scene) {
       this.scene = scene;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int eventAction = event.getAction();

        int X = (int)event.getX();
        int Y = (int)event.getY();

        switch (eventAction) {

            case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on a symbol
                isClick = true;
                this.touchStartTime = Calendar.getInstance().getTimeInMillis();
                handleOnSyllabusTouch(X, Y);
                break;

            case MotionEvent.ACTION_MOVE:
                isClick = false;
                if (this.syllabusSymbol != null && this.syllabusSymbol.isMovable()) {
                    this.syllabusSymbol.move(X, Y);
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
                }
                handleSyllabusPlacement();
                break;
        }
        // redraw the canvas
        v.invalidate();
        return true;
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
        ArrayList<SyllabusSymbol> symbols = this.scene.getWordSymbol().getSyllabusSymbols();
        for (SyllabusSymbol symbol : symbols) {
            if (symbol.contains(X, Y)) {
                this.syllabusSymbol = symbol;
                this.syllabusSymbol.onClick();
                break;
            }
        }
    }

    private void handleOnSyllabusTouch(int X, int Y) {
        this.syllabusSymbol = null;
        ArrayList<SyllabusSymbol> symbols = this.scene.getWordSymbol().getSyllabusSymbols();
        for (SyllabusSymbol symbol : symbols) {

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
        if (difference < 500) {
            return true;
        }
        return false;
    }

}
