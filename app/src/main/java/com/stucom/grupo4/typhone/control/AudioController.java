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
    private float currMusicVolume;
    private int currentMusicResID;          // Store track currently played

    // Game music enum
    public enum Music {
        MENU(R.raw.superboy),
        GAME(R.raw.space_trip);

        int id;
        Music(int id) { this.id = id; }
    }

    private AudioController() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setVolume(1f, 1f);
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
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

    public void fadeIn() {
        // Raise current music volume
        final float increase = .001f;
        setMusicVolume(currMusicVolume + increase);

        // Recursive call
        if (currMusicVolume < 1f) {
            fadeIn();
        }
    }
    public void fadeOut() {
        // Lower current music volume
        final float decrease = .001f;
        setMusicVolume(currMusicVolume - decrease);

        // Recursive call
        if (currMusicVolume > 0) {
            fadeOut();
        } else {
            stopMusic();
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

    private void setMusicVolume(float volume) {
        if (volume > 1) {
            volume = 1;
        } else if (volume < 0) {
            volume = 0;
        }

//        Tools.log(String.valueOf(volume));

        this.currMusicVolume = volume;
        this.mediaPlayer.setVolume(volume, volume);
    }
    private void setMusicTrack(Context context, Music music, boolean loop) {
        final int musicResID = music.id;
        mediaPlayer = MediaPlayer.create(context, musicResID);
        mediaPlayer.setLooping(loop);
        currentMusicResID = musicResID;
    }
}
