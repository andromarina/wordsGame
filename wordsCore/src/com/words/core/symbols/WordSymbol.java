package com.words.core.symbols;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import com.words.core.Letter;
import com.words.core.Word;

import java.util.ArrayList;
import java.util.Collections;
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

    public void shuffle(int displayWidth, int displayHeight) {

        int segmentLength = displayWidth / (letterSymbols.size());
        int minX = 0;
        int maxX = segmentLength;
        ArrayList<LetterSymbol> shuffledArray = this.letterSymbols;
        Collections.shuffle(shuffledArray);

        for (LetterSymbol letterSymbol : shuffledArray) {
            Point p = generateRandomPoint(letterSymbol, minX, maxX, displayHeight);
            letterSymbol.move(p.x, p.y);
            minX = maxX;
            maxX = maxX + segmentLength;
        }
    }

    public ArrayList<LetterSymbol> getSymbolsWithSameCharacter(LetterSymbol symbol) {
        ArrayList<LetterSymbol> repeatedSymbols = new ArrayList<LetterSymbol>();
        repeatedSymbols.add(symbol);
        for (int j = 1; j < letterSymbols.size() - 1; ++j) {
            if (symbol.getLetter().isEqual(letterSymbols.get(j).getLetter())) {
                repeatedSymbols.add(letterSymbols.get(j));
            }
        }
        return repeatedSymbols;
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

    private Point generateRandomPoint(LetterSymbol symbol, int minWidth, int maxWidth, int maxHeight) {
        Random rand = new Random();
        int minX = minWidth + symbol.getWidth() / 2;
        int maxX = maxWidth - symbol.getWidth() / 2;
        int x = ((minX + maxX) / 2) + rand.nextInt(100);
        int minY = (int) (maxHeight * 0.7);
        int maxY = maxHeight - symbol.getHeight() / 2;
        int y = minY + rand.nextInt(maxY - minY + 1);
        Point point = new Point(x,y);
        return point;
    }

    private boolean isOverlap(LetterSymbol symbol) {
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
