package com.words.core.symbols;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import com.words.core.IPlayer;
import com.words.core.Syllabus;
import com.words.core.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by mara on 3/24/14.
 */
public class WordSymbol implements ISymbol {
    private Word word;
    private ArrayList<SyllabusSymbol> syllabusSymbols;
    private Context context;
    private String[] soundNames;


    public WordSymbol(Word word, String soundNames) {
        this.word = word;
        this.soundNames = soundNames.split("-");
    }

    public void initialize(Context context, IPlayer player) {
        this.context = context;
        this.syllabusSymbols = createSyllabusSymbols(player);
    }

    public ArrayList<SyllabusSymbol> getSyllabusSymbols() {
        return this.syllabusSymbols;
    }

    @Override
    public void draw(Context context, Canvas canvas) {
        for (SyllabusSymbol syllabusSymbol : this.syllabusSymbols) {
            syllabusSymbol.draw(context, canvas);
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

        int segmentLength = displayWidth / (syllabusSymbols.size());
        int minX = 0;
        int maxX = segmentLength;
        ArrayList<SyllabusSymbol> shuffledArray = this.syllabusSymbols;
        Collections.shuffle(shuffledArray);

        for (SyllabusSymbol syllabusSymbol : shuffledArray) {
            Point p = generateRandomPoint(syllabusSymbol, minX, maxX, displayHeight);
            syllabusSymbol.move(p.x, p.y);
            minX = maxX;
            maxX = maxX + segmentLength;
        }
    }

    public ArrayList<SyllabusSymbol> getSymbolsWithSameCharacter(SyllabusSymbol symbol) {
        ArrayList<SyllabusSymbol> repeatedSymbols = new ArrayList<SyllabusSymbol>();
        repeatedSymbols.add(symbol);
        for (int j = 1; j < syllabusSymbols.size() - 1; ++j) {
            if (symbol.getSyllabus().isEqual(syllabusSymbols.get(j).getSyllabus())) {
                repeatedSymbols.add(syllabusSymbols.get(j));
            }
        }
        return repeatedSymbols;
    }

    public void pronounceWord() {
        for (SyllabusSymbol symbol : this.syllabusSymbols) {
            symbol.play();
        }
    }

    private ArrayList<SyllabusSymbol> createSyllabusSymbols(IPlayer player) {
        ArrayList<Syllabus> syllabuses = this.word.getSyllabuses();
        ArrayList<SyllabusSymbol> syllabSymb = new ArrayList<SyllabusSymbol>();
        for (int i = 0; i < syllabuses.size(); ++i) {
            Syllabus syllabus = syllabuses.get(i);
            SyllabusSymbol syllabusSymbol = new SyllabusSymbol(syllabus, soundNames[i], player);
            syllabusSymbol.initialize(this.context);
            syllabSymb.add(syllabusSymbol);
        }
        return syllabSymb;
    }

    private Point generateRandomPoint(SyllabusSymbol symbol, int minWidth, int maxWidth, int maxHeight) {
        Random rand = new Random();
        int minX = minWidth + symbol.getWidth() / 3;
        int maxX = (int) (maxWidth * 0.2);
        int x = ((minX + maxX) / 2) + rand.nextInt(50);
        int minY = (int) (maxHeight * 0.4);
        int maxY = maxHeight - symbol.getHeight() / 2;
        int y = minY + rand.nextInt(maxY - minY + 1);
        Point point = new Point(x, y);
        return point;
    }

    private boolean isOverlap(SyllabusSymbol symbol) {
        for (int i = 0; i < this.syllabusSymbols.size(); ++i) {
            ISymbol toCheck = syllabusSymbols.get(i);
            if (toCheck == symbol) {
                continue;
            }
            if (symbol.intersects(toCheck)) {
                return true;
            }
        }
        return false;
    }


}
