package com.words.core;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.view.View;
import com.words.core.symbols.ISymbol;
import com.words.core.symbols.PuzzleImageSymbol;
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

    public void setBackground(String pictureName) {
        Resources res = context.getResources();
        int drawableId = res.getIdentifier("drawable/" + pictureName, "drawable", context.getPackageName());
        setBackgroundResource(drawableId);
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

    public PuzzleImageSymbol getPuzzleImageSymbol() {
        for (ISymbol symbol : this.symbols) {
            if (symbol instanceof PuzzleImageSymbol) {
                return (PuzzleImageSymbol) symbol;
            }
        }
        return null;
    }

    public void removeAllSymbols() {
        this.symbols.clear();
    }

}
