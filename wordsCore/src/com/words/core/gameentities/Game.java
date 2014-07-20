package com.words.core.gameentities;

import java.util.ArrayList;

/**
 * Created by mara on 7/13/14.
 */
public class Game {

    public ArrayList<Level> levels;

    public Game() {
        this.levels = new ArrayList<Level>();
    }

    public ArrayList<Level> getLevels() {
        return this.levels;
    }

    public void addLevels(ArrayList<Level> levels) {
        this.levels = levels;
    }

    public Level getLevelById(int id) {
        for (Level level : this.levels) {
            if (level.getId() == id) {
                return level;
            }
        }
        return null;
    }

    public boolean isFinished(int currentLevelCounter) {
        if (currentLevelCounter < this.levels.size()) {
            return false;
        }
        return true;
    }
}
