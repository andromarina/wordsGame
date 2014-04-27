package com.words.core;

/**
 * Created by mara on 3/23/14.
 */
public class Letter {
    private char character;

    public Letter(char character) {
        this.character = character;
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

}
