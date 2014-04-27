package com.words.core;

/**
 * Created by mara on 3/23/14.
 */
public class Letter {
    private char character;
    private int position;

    public Letter(char character, int position) {
        this.character = character;
        this.position = position;
    }

    public boolean isEqual(Letter otherLetter) {
        char otherCharacter = otherLetter.getCharacter();
        if (this.character == otherCharacter) {
            return true;
        }
    return false;
    }

    public char getCharacter() {
        return this.character;
    }

    public int getPosition() {
        return position;
    }
}
