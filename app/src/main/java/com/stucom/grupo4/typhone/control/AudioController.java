package com.stucom.grupo4.typhone.control;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.stucom.grupo4.typhone.R;

public class AudioController {

    private MediaPlayer mediaPlayer;        // Media player for background music
    private final SoundPool soundPool;      // Sound pool for sound effects

    private boolean musicMuted, sfxMuted;
    private int currentMusicResID;          // Store track currently played

    // Game music enum
    public enum Music {
        MENU(R.raw.superboy),
        GAME(R.raw.space_trip);

        int id;
        Music(int id) { this.id = id; }
    }

    private AudioController() {
        this.mediaPlayer = new MediaPlayer();
        this.mediaPlayer.setVolume(1f, 1f);
        this.soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    }
    private static AudioController instance;
    public static AudioController getInstance() {
        if (instance == null)
            instance = new AudioController();
        return instance;
    }

    public void setMusic(Context context, Music music, boolean loop) {
        // Make sure track is not being reassigned
        if (music.id == currentMusicResID) return;

        // Stop previous track
        mediaPlayer.release();
        // Set new track
        setMusicTrack(context, music, loop);
    }
    private void setMusicTrack(Context context, Music music, boolean loop) {
        final int musicResID = music.id;
        mediaPlayer = MediaPlayer.create(context, musicResID);
        mediaPlayer.setLooping(loop);
        currentMusicResID = musicResID;
    }

    public void startMusic() {
        if (!musicMuted && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }
    public void pauseMusic() { mediaPlayer.pause(); }
    private void stopMusic() {
        currentMusicResID = -1;
        mediaPlayer.release();
    }

    public void playSfx(int resID) {
        if (!sfxMuted) {
            soundPool.play(resID, 1, 1, 1, 0, 1);
        }
    }

    public boolean isMusicMuted() {
        return this.musicMuted;
    }
    public boolean isSfxMuted() {
        return this.sfxMuted;
    }
    public void muteMusic(boolean muted) {
        this.musicMuted = muted;

        if (muted) pauseMusic();
        else startMusic();
    }
    public void muteSfx(boolean muted) {
        this.sfxMuted = muted;
    }
}
