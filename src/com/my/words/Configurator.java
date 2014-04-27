package com.my.words;


import android.content.res.Resources;
import android.util.DisplayMetrics;
import com.words.core.Scene;
import com.words.core.Word;
import com.words.core.symbols.WordHolderSymbol;
import com.words.core.symbols.WordSymbol;

/**
 * Created by mara on 3/24/14.
 */
public class Configurator {
    private Word puzzleWord;
    private WordSymbol puzzleWordSymbol;
    private WordHolderSymbol wordHolderSymbol;

    public Configurator() {
        this.puzzleWord = new Word(getPuzzleString());
        this.puzzleWordSymbol = new WordSymbol(this.puzzleWord);
        this.wordHolderSymbol = new WordHolderSymbol(this.puzzleWord.getSize());
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

}
