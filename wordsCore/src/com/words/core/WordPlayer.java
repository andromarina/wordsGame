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
public class WordPlayer implements MediaPlayer.OnCompletionListener, IPlayer {
    private MediaPlayer mp;
    private Queue<SoundListenerPair> playlist = new LinkedBlockingQueue<SoundListenerPair>();
    private Context context;
    private boolean idle = true;

    public WordPlayer(Context context) {
        this.mp = new MediaPlayer();
        this.mp.setOnCompletionListener(this);
        this.context = context;
    }

    private class SoundListenerPair {
        public String soundName;
        public IPlayerListener listener;

        public SoundListenerPair(String soundName, IPlayerListener listener) {
            this.soundName = soundName;
            this.listener = listener;
        }
    }

    @Override
    public void addToPlaylist(String soundName, IPlayerListener listener) {
        if (this.idle) {
            play(soundName, listener);
        } else {
            this.playlist.add(new SoundListenerPair(soundName, listener));
        }
    }

    private void play(String soundName, IPlayerListener listener) {
        preparePlayer(soundName);
        if (listener != null) {
            listener.onSoundStart();
        }
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
        SoundListenerPair next = this.playlist.peek();
        this.playlist.poll();
        play(next.soundName, next.listener);
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
