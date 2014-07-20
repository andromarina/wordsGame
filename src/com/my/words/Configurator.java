package com.my.words;


import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.Toast;
import com.words.core.*;
import com.words.core.gameentities.Game;
import com.words.core.gameentities.Level;
import com.words.core.gameentities.WordsParser;
import com.words.core.symbols.*;

import java.util.ArrayList;

/**
 * Created by mara on 3/24/14.
 */
public class Configurator implements IWordListener{
    private Word puzzleWord;
    private WordSymbol puzzleWordSymbol;
    private WordHolderSymbol wordHolderSymbol;
    private PuzzleImageSymbol puzzleImageSymbol;
    private Player playerWordCompleted;
    private static int solvedWordsCounter = 1;
    private static int levelsCounter = 1;
    private Level level;
    private Game game;

    public void initialize() {
        WordsParser wordsParser = new WordsParser(WordApplication.getContext());
        this.game = wordsParser.getGame();
        this.level = this.game.getLevelById(levelsCounter);
        createWord();
        createPlayers();
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
        this.playerWordCompleted = null;
    }

    @Override
    public void placedSucceeded(Syllabus symbol) {
    }

    @Override
    public void placedFailed(Syllabus symbol) {
    }

    public void saveProgress() {
        Preferences.saveProgress(levelsCounter, solvedWordsCounter);
    }

    public void restoreProgress() {
        solvedWordsCounter = Preferences.getProgressWord();
        levelsCounter = Preferences.getProgressLevel();
    }

    @Override
    public void finished() {
        this.playerWordCompleted.playSound();
        if (this.level.isCompleted(solvedWordsCounter)) {
            finishLevel();
            return;
        }
        ++solvedWordsCounter;
        saveProgress();
        Scene scene = WordApplication.getScene();
        scene.removeAllSymbols();
        createWord();
        addSymbolsToScene();
        initializeWord();
        runAnimations();
    }

    private void createWord() {
        String str = this.level.getPuzzleAttributesById(solvedWordsCounter).getWord();
        this.puzzleWord = new Word(str);
        this.puzzleWord.addListener(this);
        String soundNamesString = this.level.getPuzzleAttributesById(solvedWordsCounter).getWordSounds();
        this.puzzleWordSymbol = new WordSymbol(this.puzzleWord, soundNamesString);
        this.wordHolderSymbol = new WordHolderSymbol(this.puzzleWord.getSize());
        String pictureName = this.level.getPuzzleAttributesById(solvedWordsCounter).getPictureName();
        this.puzzleImageSymbol = new PuzzleImageSymbol(pictureName);
    }

    private void createPlayers() {
        this.playerWordCompleted = new Player(WordApplication.getContext(), "TaDa.mp3");
    }

    private void bindSyllabusSymbolsToWordHolderSymbol() {
        ArrayList<SyllabusHolderSymbol> syllabusHolderSymbols = this.wordHolderSymbol.getSyllabusHolderSymbols();
        for (int i = 0; i < syllabusHolderSymbols.size(); ++i) {
            SyllabusHolderSymbol syllabusHolderSymbol = syllabusHolderSymbols.get(i);
            SyllabusSymbol puzzleSyllabusSymbol = this.puzzleWordSymbol.getSyllabusSymbols().get(i);
            syllabusHolderSymbol.bindSyllabusSymbols(this.puzzleWordSymbol.getSymbolsWithSameCharacter(puzzleSyllabusSymbol));
        }
    }

    private void initializeWord() {
        this.puzzleWordSymbol.initialize(WordApplication.getContext());
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        this.wordHolderSymbol.initialize(WordApplication.getContext());
        bindSyllabusSymbolsToWordHolderSymbol();
        this.puzzleWordSymbol.shuffle(dm.widthPixels, dm.heightPixels);
    }

    private void runAnimations() {
        // this.wordHolderSymbol.animate(WordApplication.getScene());
    }

    private void addSymbolsToScene() {
        Scene scene = WordApplication.getScene();
        scene.addSymbol(this.wordHolderSymbol);
        scene.addSymbol(this.puzzleWordSymbol);
        scene.addSymbol(this.puzzleImageSymbol);
    }

    private void finishLevel() {
        Toast levelFinished = Toast.makeText(WordApplication.getContext(), R.string.level_finished, 3);
        levelFinished.show();
        solvedWordsCounter = 1;
        if (this.game.isFinished(levelsCounter)) {
            Toast gameFinished = Toast.makeText(WordApplication.getContext(), R.string.game_finished, 3);
            gameFinished.show();
            levelsCounter = 1;
            saveProgress();
            deinitialize();
            return;
        }
        ++levelsCounter;
        saveProgress();
        this.level = this.game.getLevelById(levelsCounter);
    }
}
