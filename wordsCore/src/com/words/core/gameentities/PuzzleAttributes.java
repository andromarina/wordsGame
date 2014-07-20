package com.words.core.gameentities;

/**
 * Created by mara on 7/14/14.
 */
public class PuzzleAttributes {
    private int id;
    private String word;
    private String wordSounds;
    private String pictureName;

    public PuzzleAttributes(int id, String word, String wordSounds, String pictureName) {
        this.id = id;
        this.word = word;
        this.wordSounds = wordSounds;
        this.pictureName = pictureName;
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public String getWordSounds() {
        return wordSounds;
    }

    public String getPictureName() {
        return pictureName;
    }
}
