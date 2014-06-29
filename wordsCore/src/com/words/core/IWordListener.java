package com.words.core;

/**
 * Created by mara on 4/27/14.
 */
public interface IWordListener {

    public void placedSucceeded(Syllabus symbol);

    public void placedFailed(Syllabus symbol);

    public void finished();

}
