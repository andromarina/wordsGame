package com.words.core;

import com.words.core.symbols.LetterSymbol;

/**
 * Created by mara on 4/27/14.
 */
public interface IWordListener {

    public void placedSucceeded(Letter symbol);

    public void placedFailed(Letter symbol);

    public void finished();

}
