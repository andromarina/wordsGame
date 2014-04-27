package com.words.core;

import android.view.MotionEvent;
import android.view.View;
import com.words.core.symbols.LetterHolderSymbol;
import com.words.core.symbols.LetterSymbol;

import java.util.ArrayList;

/**
 * Created by mara on 3/24/14.
 */
public class TouchHandler implements View.OnTouchListener {
    private Scene scene;
    private LetterSymbol letterSymbol;

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
                this.letterSymbol = null;
                ArrayList<LetterSymbol> symbols = this.scene.getWordSymbol().getLetterSymbols();
                for (LetterSymbol symbol: symbols) {

                    if (symbol.contains(X,Y)){
                        this.letterSymbol = symbol;
                        break;
                    }
                }
                break;


            case MotionEvent.ACTION_MOVE:
                if (this.letterSymbol != null) {
                    LetterSymbol symb = this.letterSymbol;
                    symb.move(X,Y);
                }

                break;

            case MotionEvent.ACTION_UP:
                ArrayList<LetterHolderSymbol> holderSymbols = this.scene.getWordHolderSymbol().getLetterSymbols();
                Word puzzleWord = this.scene.getWordSymbol().getWord();
                for(LetterHolderSymbol symbol:holderSymbols) {
                    if (this.letterSymbol != null && this.letterSymbol.intersects(symbol)){
                        if(puzzleWord.placed(letterSymbol.getLetter(), symbol.getPosition())) {
                            this.letterSymbol.setX(symbol.getX());
                            this.letterSymbol.setY(symbol.getY());
                            break;
                        }
                    }
                }

                break;
        }
        // redraw the canvas
        v.invalidate();
        return true;
    }

}
