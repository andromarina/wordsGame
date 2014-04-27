package com.words.core;

import java.util.ArrayList;

/**
 * Created by mara on 3/23/14.
 */
public class Word {
    private ArrayList<Letter> etalonWord;
    private Letter[] word;
    private IWordListener listener;

    public Word(String puzzleWord) {

        this.etalonWord = new ArrayList<Letter>();
        char[] charArray = puzzleWord.toCharArray();
        for(int i = 0; i < charArray.length; ++i){
            Letter letter = new Letter(charArray[i], i);
            this.etalonWord.add(letter);
        }
        this.word = new Letter[this.etalonWord.size()];

    }

    public void addListener(IWordListener listener) {
        this.listener = listener;
    }

    private void insertLetter(Letter letter, int position) {
        this.word[position] = letter;
    }

    private void removeLetter(int position) {
        this.word[position] = null;
    }

    public boolean isEqual(Word otherWord) {
        ArrayList<Letter> otherLetters = otherWord.getLetters();
        if (this.etalonWord.size() != otherLetters.size()) {
            return false;
        }
        for (int i = 0; i < this.etalonWord.size(); ++i) {
            if (this.etalonWord.get(i) != otherLetters.get(i)) {
                return false;
            }
        }
        return true;
    }

    public int getSize() {
       return this.etalonWord.size();
    }

    public ArrayList<Letter> getLetters() {
        return this.etalonWord;
    }

    private boolean isFinished() {
        for(Letter letter: this.word) {
            if(letter == null) {
                return false;
            }
        }
        return true;
    }

    public boolean placed(Letter letter, int position) {
        if(canPlace(letter, position)) {
            insertLetter(letter, position);
            risePlaceSucceeded(letter);
            if (isFinished()) {
                riseFinished();
            }
            return true;
        }
        else {
            risePlaceFailed(letter);
            return false;
        }
    }

    private void risePlaceSucceeded(Letter letter) {
        if(this.listener != null) {
            this.listener.placedSucceeded(letter);
        }
    }

    private void risePlaceFailed(Letter letter) {
        if(this.listener != null) {
            this.listener.placedFailed(letter);
        }
    }

    private void riseFinished() {
        if(this.listener != null) {
            this.listener.finished();
        }
    }

    private boolean canPlace(Letter letter, int position) {
        Letter source = this.etalonWord.get(position);
        if (source.isEqual(letter)) {
            return true;
        }
        return false;
    }

}
