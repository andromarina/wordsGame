package com.words.core;

import android.view.MotionEvent;
import android.view.View;
import com.words.core.symbols.LetterHolderSymbol;
import com.words.core.symbols.LetterSymbol;
import com.words.core.symbols.WordHolderSymbol;

import java.util.ArrayList;

/**
 * Created by mara on 3/24/14.
 */
public class TouchHandler implements View.OnTouchListener {
    private Scene scene;
    private LetterSymbol letterSymbol;
    private LetterHolderSymbol letterHolderSymbol;

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
                handleOnLetterClick(X, Y);
                handleOnLetterHolderDown(X, Y);
                break;


            case MotionEvent.ACTION_MOVE:
                if (this.letterSymbol != null && this.letterSymbol.isMovable()) {
                    this.letterSymbol.move(X, Y);
                }
                break;

            case MotionEvent.ACTION_UP:
                handleLetterPlacement();

                break;
        }
        // redraw the canvas
        v.invalidate();
        return true;
    }

    private void handleLetterPlacement() {
        ArrayList<LetterHolderSymbol> holderSymbols = this.scene.getWordHolderSymbol().getLetterHolderSymbols();
        Word puzzleWord = this.scene.getWordSymbol().getWord();

        for (LetterHolderSymbol holderSymbol : holderSymbols) {
            if (this.letterSymbol != null && this.letterSymbol.intersects(holderSymbol)) {

                if (puzzleWord.placed(letterSymbol.getLetter(), holderSymbol.getPosition())) {
                    holderSymbol.attachToAnimation(letterSymbol);
                    letterSymbol.setX(holderSymbol.getX());
                    letterSymbol.setMovable(false);
                    break;
                } else {
                    this.letterSymbol.setX(letterSymbol.getSavedX());
                    this.letterSymbol.setY(letterSymbol.getSavedY());
                }
            }
        }
    }

    private void handleOnLetterClick(int X, int Y) {
        this.letterSymbol = null;
        ArrayList<LetterSymbol> symbols = this.scene.getWordSymbol().getLetterSymbols();
        for (LetterSymbol symbol : symbols) {

            if (symbol.contains(X, Y)) {
                this.letterSymbol = symbol;
                this.letterSymbol.saveCoordinates();
                break;
            }
        }
    }

    private void handleOnLetterHolderDown(int X, int Y) {
        this.letterHolderSymbol = null;
        WordHolderSymbol wordHolderSymbol = this.scene.getWordHolderSymbol();
        ArrayList<LetterHolderSymbol> symbols = wordHolderSymbol.getLetterHolderSymbols();
        for (int i = 0; i < symbols.size(); ++i) {
            if (symbols.get(i).contains(X, Y)) {
                this.letterHolderSymbol = symbols.get(i);
                LetterSymbol letterSymbol = this.letterHolderSymbol.getHintLetterSymbol();
                if (letterSymbol != null) {
                    letterSymbol.animate(this.scene);
                }
                break;
            }
        }
    }

}
