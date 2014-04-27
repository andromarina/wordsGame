package com.words.core.symbols;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import com.words.core.Letter;
import com.words.core.Word;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mara on 3/24/14.
 */
public class WordSymbol implements ISymbol {
    private Word word;
    private ArrayList<LetterSymbol> letterSymbols;
    private Context context;


    public WordSymbol(Word word) {
        this.word = word;
    }

    public void initialize(Context context) {
        this.context = context;
        this.letterSymbols = createLetterSymbols();
    }

    public ArrayList<LetterSymbol> getLetterSymbols() {
        return this.letterSymbols;
    }

    @Override
    public void draw(Context context, Canvas canvas) {
        for(LetterSymbol letterSymbol:this.letterSymbols) {
            letterSymbol.draw(context, canvas);
        }
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public void setX(int newX) {

    }

    @Override
    public void setY(int newY) {

    }

    @Override
    public boolean contains(int X, int Y) {
        return false;
    }

    @Override
    public Rect getBoundingBox() {
        return null;
    }

    public Word getWord() {
        return word;
    }

    public void shuffle(int maxX, int maxY) {

       for(LetterSymbol letterSymbol: this.letterSymbols) {
           while(intersection(letterSymbol)== true) {
               Point p = generateRandomPoint(letterSymbol, maxX, maxY);
               letterSymbol.move(p.x, p.y);
           }
       }
    }

    private ArrayList<LetterSymbol> createLetterSymbols() {
        ArrayList<Letter> letters = this.word.getLetters();
        ArrayList<LetterSymbol> lS = new ArrayList<LetterSymbol>();
        for(Letter letter:letters) {
            LetterSymbol letterSymbol = new LetterSymbol(letter);
            letterSymbol.initialize(this.context);
            lS.add(letterSymbol);
        }
        return lS;
    }

    private Point generateRandomPoint(LetterSymbol symbol, int maxWidth, int maxHeight) {
        Random rand = new Random();
        int x = rand.nextInt((maxWidth - symbol.getWidth()) - symbol.getWidth()) + symbol.getWidth();
        int y = rand.nextInt((maxHeight - symbol.getHeight()) - symbol.getHeight()) + symbol.getHeight();
        Point point = new Point(x,y);
        return point;
    }

    private boolean intersection(LetterSymbol symbol) {
        for (int i=0; i<this.letterSymbols.size(); ++i) {
            ISymbol toCheck = letterSymbols.get(i);
            if(toCheck == symbol) {
                continue;
            }
            if(symbol.intersects(toCheck)) {
                return true;
            }
        }
        return false;
    }


}
