package com.words.core;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by mara on 4/27/14.
 */
public class ObjectPlayer implements IPlayer, MediaPlayer.OnCompletionListener {
    private MediaPlayer mp;
    private Context context;

    public ObjectPlayer(Context context) {
        this.mp = new MediaPlayer();
        this.mp.setOnCompletionListener(this);
        this.context = context;
    }

    public void play(String soundName) {
        preparePlayer(soundName);
        mp.start();
    }

    private void preparePlayer(String fileName) {
        try {
            AssetFileDescriptor afd;
            afd = context.getAssets().openFd(fileName);
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.prepare();

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addToPlaylist(String soundName, IPlayerListener listener) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.stop();
        mp.reset();
    }
}
