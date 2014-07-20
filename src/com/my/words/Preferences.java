package com.my.words;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by mara on 6/2/14.
 */
public class Preferences {
    private static SharedPreferences sPref;
    private static Context context;
    private final static String LOG_TAG = "Preferences";
    private final static String CURRENT_WORD = "CurrentWord";
    private final static String CURRENT_LEVEL = "CurrentLevel";

    public static void initialize() {
        Preferences.context = WordApplication.getContext();
    }

    public static void saveProgress(int currentLevel, int currentWord) {

        if (context == null) {
            initialize();
        }

        sPref = context.getSharedPreferences(CURRENT_WORD, Context.MODE_PRIVATE);
        SharedPreferences.Editor currentWordEditor = sPref.edit();
        currentWordEditor.putInt(CURRENT_WORD, currentWord);
        currentWordEditor.commit();
        Log.d(LOG_TAG, currentWord + " current word number saved to Preferences");

        sPref = context.getSharedPreferences(CURRENT_LEVEL, Context.MODE_PRIVATE);
        SharedPreferences.Editor currentLevelEditor = sPref.edit();
        currentLevelEditor.putInt(CURRENT_LEVEL, currentLevel);
        currentLevelEditor.commit();
        Log.d(LOG_TAG, currentWord + " current level number saved to Preferences");
    }

    public static int getProgressWord() {

        if (context == null) {
            initialize();
        }

        sPref = context.getSharedPreferences(CURRENT_WORD, Context.MODE_PRIVATE);
        int currentWord = sPref.getInt(CURRENT_WORD, 1);
        Log.i(LOG_TAG, currentWord + " current word number loaded");
        return currentWord;
    }

    public static int getProgressLevel() {

        if (context == null) {
            initialize();
        }

        sPref = context.getSharedPreferences(CURRENT_LEVEL, Context.MODE_PRIVATE);
        int currentLevel = sPref.getInt(CURRENT_LEVEL, 1);
        Log.i(LOG_TAG, currentLevel + " current level number loaded");
        return currentLevel;
    }
}
