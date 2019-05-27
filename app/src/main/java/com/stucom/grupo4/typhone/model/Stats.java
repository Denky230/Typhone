package com.stucom.grupo4.typhone.model;

import java.io.Serializable;

public class Stats implements Serializable {

    private int score;
    private int inputsTotal, inputsRight, inputsWrong;
    private int hiStreakWord, hiStreakLetter;
    private float ipsMax, ipsAvg;

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public int getInputsTotal() {
        return inputsTotal;
    }
    public void addInput(boolean isRight) {
        if (isRight) {
            inputsRight++;
        } else {
            inputsWrong++;
        }
        inputsTotal++;
    }

    public int getAccuracy() {
        return 100 / (inputsTotal / inputsRight);
    }

    public float getIpsMax() {
        return ipsMax;
    }
    public float getIpsAvg() {
        return ipsAvg;
    }
    public void setIpsAvg(float ipsAvg) {
        if (ipsAvg > ipsMax) ipsMax = ipsAvg;
        this.ipsAvg = ipsAvg;
    }

    public int getHiStreakWord() {
        return hiStreakWord;
    }
    public void setHiStreakWord(int hiStreakWord) {
        this.hiStreakWord = hiStreakWord;
    }

    public int getHiStreakLetter() {
        return hiStreakLetter;
    }
    public void setHiStreakLetter(int hiStreakLetter) {
        this.hiStreakLetter = hiStreakLetter;
    }
}
