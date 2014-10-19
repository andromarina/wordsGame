package com.words.core.gameentities;

/**
 * Created by mara on 7/14/14.
 */
public class PuzzleAttributes {
    private int id;
    private String word;
    private String wordSounds;
    private String pictureName;
    private String objectSound;

    public PuzzleAttributes(int id, String word, String wordSounds, String pictureName, String objectSound) {
        this.id = id;
        this.word = word;
        this.wordSounds = wordSounds;
        this.pictureName = pictureName;
        this.objectSound = objectSound;
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

    public String getObjectSound() {
        return objectSound;
    }
}
