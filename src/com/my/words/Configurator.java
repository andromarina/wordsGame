package com.my.words;


import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.Toast;
import com.words.core.*;
import com.words.core.symbols.LetterHolderSymbol;
import com.words.core.symbols.LetterSymbol;
import com.words.core.symbols.WordHolderSymbol;
import com.words.core.symbols.WordSymbol;

import java.util.ArrayList;

/**
 * Created by mara on 3/24/14.
 */
public class Configurator implements IWordListener{
    private Word puzzleWord;
    private WordSymbol puzzleWordSymbol;
    private WordHolderSymbol wordHolderSymbol;
    private Player player;
    private static int solvedWordsCounter = 0;


    public void initialize() {
        this.player = new Player(WordApplication.getContext());
        createWord();
        addSymbolsToScene();
        initializeWord();
        runAnimations();
    }

    public void deinitialize() {
        Scene scene = WordApplication.getScene();
        scene.removeAllSymbols();
        this.puzzleWord = null;
        this.puzzleWordSymbol = null;
        this.wordHolderSymbol = null;
        this.player = null;
    }

    @Override
    public void placedSucceeded(Letter symbol) {
    }

    @Override
    public void placedFailed(Letter symbol) {
    }

    public void saveProgress() {
        Preferences.saveProgress(solvedWordsCounter);
    }

    public void restoreProgress() {
        solvedWordsCounter = Preferences.getProgress();
    }

    @Override
    public void finished() {
        this.player.playWordCompleted();
        ++solvedWordsCounter;
        saveProgress();
        if (levelFinished()) {
            Toast levelFinished = Toast.makeText(WordApplication.getContext(), R.string.level_finished, 3);
            levelFinished.show();
            return;
        }
        Scene scene = WordApplication.getScene();
        scene.removeAllSymbols();
        createWord();
        addSymbolsToScene();
        initializeWord();
        runAnimations();
    }

    private void createWord() {
        this.puzzleWord = getPuzzleWordsForLevel().get(solvedWordsCounter);
        this.puzzleWord.addListener(this);
        this.puzzleWordSymbol = new WordSymbol(this.puzzleWord);
        this.wordHolderSymbol = new WordHolderSymbol(this.puzzleWord.getSize());
    }

    private void bindLetterSymbolsToWordHolderSymbol() {
        ArrayList<LetterHolderSymbol> letterHolderSymbols = this.wordHolderSymbol.getLetterHolderSymbols();
        for (int i = 0; i < letterHolderSymbols.size(); ++i) {
            LetterHolderSymbol letterHolderSymbol = letterHolderSymbols.get(i);
            LetterSymbol puzzleLetterSymbol = this.puzzleWordSymbol.getLetterSymbols().get(i);
            letterHolderSymbol.bindLetterSymbols(this.puzzleWordSymbol.getSymbolsWithSameCharacter(puzzleLetterSymbol));
        }
    }

    private void initializeWord() {
        this.puzzleWordSymbol.initialize(WordApplication.getContext());
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        this.wordHolderSymbol.initialize(WordApplication.getContext());
        bindLetterSymbolsToWordHolderSymbol();
        this.puzzleWordSymbol.shuffle(dm.widthPixels, dm.heightPixels);

    }

    private void runAnimations() {
        // this.wordHolderSymbol.animate(WordApplication.getScene());
    }

    private ArrayList<Word> getPuzzleWordsForLevel() {
        WordsParser wordsParser = new WordsParser(WordApplication.getContext());
        String[] puzzleStrings = wordsParser.getPuzzleStrings();
        ArrayList<Word> puzzleWords = new ArrayList<Word>();
        for (String str : puzzleStrings) {
            Word word = new Word(str);
            puzzleWords.add(word);
        }
        return puzzleWords;
    }

    private void addSymbolsToScene() {
        Scene scene = WordApplication.getScene();
        scene.addSymbol(this.wordHolderSymbol);
        scene.addSymbol(this.puzzleWordSymbol);
    }

    private boolean levelFinished() {
        if (solvedWordsCounter < getPuzzleWordsForLevel().size()) {
            return false;
        }
        solvedWordsCounter = 0;
        saveProgress();
        return true;
    }


}
