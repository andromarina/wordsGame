package com.words.core.gameentities;

import java.util.ArrayList;

/**
 * Created by mara on 7/13/14.
 */
public class Level {
    private int id;
    ArrayList<PuzzleAttributes> puzzleAttributesArray;

    public Level(int id, ArrayList<PuzzleAttributes> puzzleAttributesArray) {
        this.id = id;
        this.puzzleAttributesArray = puzzleAttributesArray;
    }

    public int getId() {
        return id;
    }

    public ArrayList<PuzzleAttributes> getPuzzleAttributesArray() {
        return puzzleAttributesArray;
    }

    public PuzzleAttributes getPuzzleAttributesById(int id) {
        for (PuzzleAttributes attributes : this.puzzleAttributesArray) {
            if (attributes.getId() == id) {
                return attributes;
            }
        }
        return null;
    }

    public boolean isCompleted(int currentWordCounter) {
        if (currentWordCounter < this.puzzleAttributesArray.size()) {
            return false;
        }
        return true;
    }
}
