package com.stucom.grupo4.typhone.model;

public class Stats {

    int score;
    int inputsTotal, inputsRight, inputsWrong;
    int accuracy;
    float ipsMax, ipsAvg;
    int hiStreakWord, hiStreakLetter;

    public Stats(int score, int inputsTotal, int accuracy, float ipsMax, float ipsAvg, int hiStreakWord, int hiStreakLetter) {
        this.score = score;
        this.inputsTotal = inputsTotal;
        this.accuracy = accuracy;
        this.ipsMax = ipsMax;
        this.ipsAvg = ipsAvg;
        this.hiStreakWord = hiStreakWord;
        this.hiStreakLetter = hiStreakLetter;
    }
    public Stats() {}

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getInputsTotal() {
        return inputsTotal;
    }

    public void setInputsTotal(int inputsTotal) {
        this.inputsTotal = inputsTotal;
    }

    public void addInput(boolean isRight){
        if(isRight){
            inputsRight++;
        }else{
            inputsWrong++;
        }
        inputsTotal++;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public float getIpsMax() {
        return ipsMax;
    }

    public void setIpsMax(float ipsMax) {
        this.ipsMax = ipsMax;
    }

    public float getIpsAvg() {
        return ipsAvg;
    }

    public void setIpsAvg(float ipsAvg) {
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
