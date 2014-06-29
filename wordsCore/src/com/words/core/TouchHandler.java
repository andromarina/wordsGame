package com.words.core;

import android.view.MotionEvent;
import android.view.View;
import com.words.core.symbols.SyllabusHolderSymbol;
import com.words.core.symbols.SyllabusSymbol;
import com.words.core.symbols.WordHolderSymbol;

import java.util.ArrayList;

/**
 * Created by mara on 3/24/14.
 */
public class TouchHandler implements View.OnTouchListener {
    private Scene scene;
    private SyllabusSymbol syllabusSymbol;
    private SyllabusHolderSymbol syllabusHolderSymbol;

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
                handleOnSyllabusClick(X, Y);
                handleOnSyllabusHolderDown(X, Y);
                break;


            case MotionEvent.ACTION_MOVE:
                if (this.syllabusSymbol != null && this.syllabusSymbol.isMovable()) {
                    this.syllabusSymbol.move(X, Y);
                }
                break;

            case MotionEvent.ACTION_UP:
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
                this.syllabusSymbol.saveCoordinates();
                this.syllabusSymbol.onClick();
                break;
            }
        }
    }

    private void handleOnSyllabusHolderDown(int X, int Y) {
        this.syllabusHolderSymbol = null;
        WordHolderSymbol wordHolderSymbol = this.scene.getWordHolderSymbol();
        ArrayList<SyllabusHolderSymbol> symbols = wordHolderSymbol.getSyllabusHolderSymbols();
        for (int i = 0; i < symbols.size(); ++i) {
            if (symbols.get(i).contains(X, Y)) {
                this.syllabusHolderSymbol = symbols.get(i);
                SyllabusSymbol syllabusSymbol = this.syllabusHolderSymbol.getHintSyllabusSymbol();
                if (syllabusSymbol != null) {
                    syllabusSymbol.animate(this.scene);
                }
                break;
            }
        }
    }

}
