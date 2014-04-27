package com.words.core;

import java.util.ArrayList;

/**
 * Created by mara on 3/23/14.
 */
public class Word {
    ArrayList<Letter> word;

    public Word(String puzzleWord) {
        this.word = new ArrayList<Letter>();
        char[] charArray = puzzleWord.toCharArray();
        for(char character:charArray){
            Letter letter = new Letter(character);
            this.word.add(letter);
        }
    }

    public void insertLetter(int position, Letter letter) {
        this.word.add(position, letter);
    }

    public void removeLetter(int position) {
        this.word.set(position, null);
    }

    public boolean isEqual(Word otherWord) {
        ArrayList<Letter> otherLetters = otherWord.getLetters();
        if (this.word.size() != otherLetters.size()) {
            return false;
        }
        for (int i = 0; i < this.word.size(); ++i) {
            if (this.word.get(i) != otherLetters.get(i)) {
                return false;
            }
        }
        return true;
    }

    public int getSize() {
       return this.word.size();
    }

    public ArrayList<Letter> getLetters() {
        return this.word;
    }

    private boolean canPlace(Letter letter, int index) {

    }

}
