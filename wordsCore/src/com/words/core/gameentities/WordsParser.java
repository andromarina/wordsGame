package com.words.core.gameentities;

import android.content.Context;
import android.content.res.AssetManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Created by mara on 5/25/14.
 */
public class WordsParser {
    private Context context;
    private JSONObject jsonObject;
    private Game game;

    public WordsParser(Context context) {
        this.context = context;
        this.jsonObject = parseStringToJSON();
        this.game = new Game();
        this.game.addLevels(getLevels());
    }

    public Game getGame() {
        return game;
    }

    private ArrayList<Level> getLevels() {
        JSONObject game = (JSONObject) jsonObject.get("game");
        JSONArray levels = (JSONArray) game.get("levels");
        ArrayList<Level> levelArrayList = new ArrayList<Level>();
        for (int i = 0; i < levels.size(); ++i) {
            JSONObject jsonLevel = (JSONObject) levels.get(i);
            long levelId = (Long) jsonLevel.get("id");
            ArrayList<PuzzleAttributes> puzzleAttributesArray = createPuzzleAttributesForLevel((int) levelId);
            Level level = new Level((int) levelId, puzzleAttributesArray);
            levelArrayList.add(level);
        }
        return levelArrayList;
    }

    private ArrayList<PuzzleAttributes> createPuzzleAttributesForLevel(int levelId) {
        JSONArray jsonArray = getPuzzlesForLevel(levelId);
        ArrayList<PuzzleAttributes> puzzleAttributesArray = new ArrayList<PuzzleAttributes>();
        for (int i = 0; i < jsonArray.size(); ++i) {
            JSONObject puzzle = (JSONObject) jsonArray.get(i);
            long id = (Long) puzzle.get("id");
            String word = (String) puzzle.get("word");
            String wordSound = (String) puzzle.get("sound");
            String pictureName = (String) puzzle.get("picture");
            PuzzleAttributes puzzleAttributes = new PuzzleAttributes((int) id, word, wordSound, pictureName);
            puzzleAttributesArray.add(puzzleAttributes);
        }
        return puzzleAttributesArray;
    }

    private JSONArray getPuzzlesForLevel(int levelId) {
        JSONObject game = (JSONObject) jsonObject.get("game");
        JSONArray levels = (JSONArray) game.get("levels");
        for (int i = 0; i < levels.size(); ++i) {
            JSONObject jsonLevel = (JSONObject) levels.get(i);
            long levelIdActual = (Long) jsonLevel.get("id");
            if (levelId == (int) levelIdActual) {
                JSONArray puzzlesArray = (JSONArray) jsonLevel.get("puzzles");
                return puzzlesArray;
            }
        }
        return null;
    }

    private String getDataFromFile() {

        String text = "";
        try {
            AssetManager assetManager = this.context.getAssets();
            InputStream input;
            input = assetManager.open("content.json");

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

    private JSONObject parseStringToJSON() {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(getDataFromFile());
            return jsonObject;

        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
