package com.words.core;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by mara on 5/25/14.
 */
public class WordsParser {
    private Context context;

    public WordsParser(Context context) {
        this.context = context;
    }

    private String getStringFromFile() {

        String text = "";
        try {
            AssetManager assetManager = this.context.getAssets();
            InputStream input;
            input = assetManager.open("level_1.txt");

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            // byte buffer into a string
            text = new String(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    public String[] getPuzzleStrings() {
        String puzzle = null;

        puzzle = getStringFromFile();

        String[] splitted = puzzle.split(" ");
        return splitted;
    }
}
