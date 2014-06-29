package com.words.core;

import java.util.HashMap;

/**
 * Created by mara on 6/29/14.
 */
public class Transliterator {

    private static HashMap<String,String> transliterationMap = fillMap();

    private static HashMap<String,String> fillMap() {
        HashMap<String,String> transliterationMap = new HashMap<String, String>();
        transliterationMap.put("ба","ba");
        transliterationMap.put("со","so");
        transliterationMap.put("ка", "ka");
        transliterationMap.put("ку", "ku");
        transliterationMap.put("ри", "ri");
        transliterationMap.put("ца", "ca");
        transliterationMap.put("ко", "ko");
        transliterationMap.put("ро", "ro");
        transliterationMap.put("ва","va");
        return transliterationMap;
    }

    public static String convertToLatin(String cyrillicSyllabus) {
        return transliterationMap.get(cyrillicSyllabus);
    }
}
