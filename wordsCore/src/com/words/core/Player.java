package com.words.core;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by mara on 4/27/14.
 */
public class Player {
    private MediaPlayer mp;

    public Player() {
        this.mp = new MediaPlayer();
    }

    public void playWordCompleted(Context context) {
        try {
            AssetFileDescriptor afd;
            afd = context.getAssets().openFd("TaDa.mp3");
            mp.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mp.prepare();
            mp.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
