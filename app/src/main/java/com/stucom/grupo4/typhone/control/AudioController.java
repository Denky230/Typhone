package com.stucom.grupo4.typhone.control;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class AudioController {

    private MediaPlayer mediaPlayer;    // Media player for background music
    private SoundPool soundPool;        // Sound pool for sound effects

    // Game sound effects IDs
    private final int[] sfx = {

    };

    private boolean sfxMuted = false;

    private AudioController() {
        // Initialize Media player
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setVolume(0.5f, 0.5f);
        mediaPlayer.setLooping(true);

        // Initialize Sound pool
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    }
    private static AudioController instance;
    public static AudioController getInstance() {
        if (instance == null)
            instance = new AudioController();
        return instance;
    }

    public void playMusic(Context context, int musicResID) {
        mediaPlayer = MediaPlayer.create(context, musicResID);
        mediaPlayer.start();
    }
    public void startMusic() {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }
    public void pauseMusic() { mediaPlayer.pause(); }
    public void stopMusic() { mediaPlayer.reset(); }

    public void playSfx(int resID) {
        final int volume = sfxMuted ? 0 : 1;
        soundPool.play(resID, volume, volume, 1, 0, 1);
    }

    public void muteMusic(boolean muted) {
        final float musicVolume = muted ? 0 : 0.5f;
        mediaPlayer.setVolume(musicVolume, musicVolume);
    }
    public void muteSfx(boolean muted) {
        this.sfxMuted = muted;
    }
}
