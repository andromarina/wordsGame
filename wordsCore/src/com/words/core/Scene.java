package com.words.core;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import com.words.core.symbols.ISymbol;
import com.words.core.symbols.WordHolderSymbol;
import com.words.core.symbols.WordSymbol;

import java.util.ArrayList;

/**
 * Created by mara on 3/24/14.
 */
public class Scene extends View {

    private ArrayList<ISymbol> symbols;
    private Context context;

    public Scene(Context context) {
        super(context);
        this.context = context;
        this.symbols = new ArrayList<ISymbol>();
        setBackgroundResource(R.drawable.backgr);
    }

    public void addSymbol(ISymbol symbol) {
        this.symbols.add(symbol);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (ISymbol symbol : symbols) {
            symbol.draw(this.context, canvas);
        }
    }

    public WordSymbol getWordSymbol() {
        for (ISymbol symbol : this.symbols) {
            if (symbol instanceof WordSymbol) {
                return (WordSymbol) symbol;
            }
        }
        return null;
    }

    public WordHolderSymbol getWordHolderSymbol() {
        for (ISymbol symbol : this.symbols) {
            if (symbol instanceof WordHolderSymbol) {
                return (WordHolderSymbol) symbol;
            }
        }
        return null;
    }

    public void removeAllSymbols() {
        this.symbols.clear();
    }

}
