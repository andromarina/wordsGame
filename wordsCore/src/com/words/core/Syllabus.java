package com.words.core;

/**
 * Created by mara on 3/23/14.
 */
public class Syllabus {
    private String syllabusString;
    private int position;


    public Syllabus(String syllabus, int position) {
        this.syllabusString = syllabus;
        this.position = position;
    }

    public boolean isEqual(Syllabus otherSyllabus) {

        if (!otherSyllabus.getString().equals(this.syllabusString)) {
            return false;
        }
        return true;
    }

    public String getString() {
        return this.syllabusString;
    }

}
