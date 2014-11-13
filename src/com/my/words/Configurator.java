package com.my.words;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.Toast;
import com.words.core.*;
import com.words.core.animations.DisappearAnimation;
import com.words.core.animations.OnClickAnimation;
import com.words.core.gameentities.Game;
import com.words.core.gameentities.Level;
import com.words.core.gameentities.WordsParser;
import com.words.core.symbols.*;

import java.util.ArrayList;

/**
 * Created by mara on 3/24/14.
 */
public class Configurator implements IWordListener, AnimatorSet.AnimatorListener {
    private Word puzzleWord;
    private WordSymbol puzzleWordSymbol;
    private WordHolderSymbol wordHolderSymbol;
    private PuzzleImageSymbol puzzleImageSymbol;
    private static int solvedWordsCounter = 1;
    private static int levelsCounter = 1;
    private Level level;
    private Game game;
    private WordPlayer player;
    private ObjectPlayer objectPlayer;
    private Scene scene;

    public void initialize() {
        WordsParser wordsParser = new WordsParser(WordApplication.getContext());
        this.game = wordsParser.getGame();
        this.level = this.game.getLevelById(levelsCounter);
        this.scene = WordApplication.getScene();

        if (this.player == null) {
            this.player = new WordPlayer(WordApplication.getContext());
        }

        if (this.objectPlayer == null) {
            this.objectPlayer = new ObjectPlayer(WordApplication.getContext());
        }
        createWord();
        TouchHandler touchHandler = new TouchHandler(this.scene);
        this.scene.setOnTouchListener(touchHandler);
        addSymbolsToScene();
        initializeWord();
    }

    public void deinitialize() {
        scene.removeAllSymbols();
        this.puzzleWord = null;
        this.puzzleWordSymbol = null;
        this.wordHolderSymbol = null;
        this.player = null;
        this.scene.setOnTouchListener(null);
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

        DisappearAnimation animation = new DisappearAnimation(this.scene, this.puzzleWordSymbol, this.wordHolderSymbol);
        animation.setListener(this);
        animation.play();

        this.player.addToPlaylist("TaDa", null);
    }

    private void createWord() {
        String str = this.level.getPuzzleAttributesById(solvedWordsCounter).getWord();
        this.puzzleWord = new Word(str);
        this.puzzleWord.addListener(this);
        String soundNamesString = this.level.getPuzzleAttributesById(solvedWordsCounter).getWordSounds();
        this.puzzleWordSymbol = new WordSymbol(this.puzzleWord, soundNamesString);
        this.wordHolderSymbol = new WordHolderSymbol(this.puzzleWord.getSize());
        String pictureName = this.level.getPuzzleAttributesById(solvedWordsCounter).getPictureName();
        String objectSoundString = this.level.getPuzzleAttributesById(solvedWordsCounter).getObjectSound();
        this.puzzleImageSymbol = new PuzzleImageSymbol(pictureName, WordApplication.getContext(), this.objectPlayer, objectSoundString);
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
        this.puzzleWordSymbol.initialize(WordApplication.getContext(), this.player, this.scene);
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        this.wordHolderSymbol.initialize(WordApplication.getContext());
        bindSyllabusSymbolsToWordHolderSymbol();
        this.puzzleWordSymbol.presentWord();
        this.puzzleWordSymbol.shuffle(dm.widthPixels, dm.heightPixels);
        setOnClickListenersToSymbols();
    }

    private void setOnClickListenersToSymbols() {
        ArrayList<SyllabusSymbol> symbols = this.puzzleWordSymbol.getSyllabusSymbols();

        for (SyllabusSymbol symbol : symbols) {
            OnClickAnimation listener = new OnClickAnimation(this.scene, symbol);
            symbol.setListener(listener);
        }
    }

    private void addSymbolsToScene() {
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

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        this.scene.invalidate();
        if (this.level.isCompleted(solvedWordsCounter)) {
            finishLevel();
            return;
        }

        ++solvedWordsCounter;
        saveProgress();
        scene.removeAllSymbols();
        createWord();
        addSymbolsToScene();
        initializeWord();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
