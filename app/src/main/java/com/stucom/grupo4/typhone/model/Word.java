package com.stucom.grupo4.typhone.model;

public class Word {

    private String word;
    private Letter[] letters;
    private int scaleX;

    public Word(String word) {
        // Make sure Word is ALWAYS upper cased
        this.word = word.toUpperCase();
        this.letters = new Letter[word.length()];
        for (int i = 0; i < this.word.length(); i++) {
            this.letters[i] = new Letter(this.word.charAt(i));
        }
        this.scaleX = 1;
    }

    public String getWord() {
        return this.word;
    }

    public Letter[] getLetters() {
        return this.letters;
    }
    public Letter getLetterAt(int index) {
        return letters[index];
    }

    public int getScaleX() {
        return this.scaleX;
    }
    public void setScaleX(int scaleX) {
        // Make sure scaleX is always 1 / -1
        this.scaleX = scaleX / Math.abs(scaleX);
    }

    public int getLength() {
        return this.word.length();
    }
}
