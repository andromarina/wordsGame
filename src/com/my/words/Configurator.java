package com.my.words;


import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import com.words.core.*;
import com.words.core.symbols.WordHolderSymbol;
import com.words.core.symbols.WordSymbol;

import java.io.IOException;

/**
 * Created by mara on 3/24/14.
 */
public class Configurator implements IWordListener{
    private Word puzzleWord;
    private WordSymbol puzzleWordSymbol;
    private WordHolderSymbol wordHolderSymbol;
    private Player player;

    public Configurator() {
        this.puzzleWord = new Word(getPuzzleString());
        this.puzzleWord.addListener(this);
        this.puzzleWordSymbol = new WordSymbol(this.puzzleWord);
        this.wordHolderSymbol = new WordHolderSymbol(this.puzzleWord.getSize());
        this.player = new Player();
        addSymbolsToScene();
    }

    public void initialize() {
        this.puzzleWordSymbol.initialize(WordApplication.getContext());
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        this.puzzleWordSymbol.shuffle(dm.widthPixels, dm.heightPixels);
        this.wordHolderSymbol.initialize(WordApplication.getContext());

    }

    private String getPuzzleString() {
        String puzzle = "КОРОВА";
        return puzzle;
    }


    private void addSymbolsToScene() {
        Scene scene = WordApplication.getScene();
        scene.addSymbol(this.wordHolderSymbol);
        scene.addSymbol(this.puzzleWordSymbol);
    }

    @Override
    public void placedSucceeded(Letter symbol) {

    }

    @Override
    public void placedFailed(Letter symbol) {

    }

    @Override
    public void finished() {
        this.player.playWordCompleted(WordApplication.getContext());
    }
}
