package com.words.core;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by mara on 4/27/14.
 */
public class Player implements MediaPlayer.OnCompletionListener, IPlayer {
    private MediaPlayer mp;
    private Queue<String> playlist = new LinkedBlockingQueue<String>();
    private Context context;
    private boolean idle = true;

    public Player(Context context) {
        this.mp = new MediaPlayer();
        this.mp.setOnCompletionListener(this);
        this.context = context;
    }

    @Override
    public void addToPlaylist(String soundName) {
        if (this.idle) {
            play(soundName);
        } else {
            this.playlist.add(soundName);
        }
    }

    private void play(String soundName) {
        preparePlayer(soundName);
        mp.start();
        this.idle = false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.stop();
        mp.reset();
        this.idle = true;
        if (this.playlist.isEmpty()) {
            return;
        }
        String next = this.playlist.peek();
        this.playlist.poll();
        play(next);
    }

    private void preparePlayer(String fileName) {
        try {
            AssetFileDescriptor afd;
            afd = context.getAssets().openFd(fileName + ".mp3");
            mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mp.prepare();

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
