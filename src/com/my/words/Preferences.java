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

    public static void initialize() {
        Preferences.context = WordApplication.getContext();
    }

    public static void saveProgress(int currentWord) {

        if (context == null) {
            initialize();
        }

        sPref = context.getSharedPreferences(CURRENT_WORD, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(CURRENT_WORD, currentWord);
        ed.commit();
        Log.d(LOG_TAG, currentWord + " current word number saved to Preferences");
    }

    public static int getProgress() {

        if (context == null) {
            initialize();
        }

        sPref = context.getSharedPreferences(CURRENT_WORD, Context.MODE_PRIVATE);
        int currentWord = sPref.getInt(CURRENT_WORD, 0);
        Log.i(LOG_TAG, currentWord + " current word number loaded");
        return currentWord;
    }
}
