package com.words.core;

import java.util.ArrayList;

/**
 * Created by mara on 3/23/14.
 */
public class Word {
    private ArrayList<Syllabus> etalonWord;
    private Syllabus[] word;
    private IWordListener listener;

    public Word(String puzzleWord) {

        this.etalonWord = new ArrayList<Syllabus>();
        String[] syllabuses = puzzleWord.split("-");
        for (int i = 0; i < syllabuses.length; ++i) {
            Syllabus syllabus = new Syllabus(syllabuses[i], i);
            this.etalonWord.add(syllabus);
        }
        this.word = new Syllabus[this.etalonWord.size()];

    }

    public void addListener(IWordListener listener) {
        this.listener = listener;
    }

    private void insertSyllabus(Syllabus syllabus, int position) {
        this.word[position] = syllabus;
    }

    public int getSize() {
        return this.etalonWord.size();
    }

    public ArrayList<Syllabus> getSyllabuses() {
        return this.etalonWord;
    }

    private boolean isFinished() {
        for (Syllabus syllabus : this.word) {
            if (syllabus == null) {
                return false;
            }
        }
        return true;
    }

    public boolean placed(Syllabus syllabus, int position) {
        if (canPlace(syllabus, position)) {
            insertSyllabus(syllabus, position);
            risePlaceSucceeded(syllabus);
            if (isFinished()) {
                riseFinished();
            }
            return true;
        } else {
            risePlaceFailed(syllabus);
            return false;
        }
    }

    private void risePlaceSucceeded(Syllabus syllabus) {
        if (this.listener != null) {
            this.listener.placedSucceeded(syllabus);
        }
    }

    private void risePlaceFailed(Syllabus syllabus) {
        if (this.listener != null) {
            this.listener.placedFailed(syllabus);
        }
    }

    private void riseFinished() {
        if (this.listener != null) {
            this.listener.finished();
        }
    }

    private boolean canPlace(Syllabus syllabus, int position) {
        Syllabus source = this.etalonWord.get(position);
        if (source.isEqual(syllabus)) {
            return true;
        }
        return false;
    }

}
