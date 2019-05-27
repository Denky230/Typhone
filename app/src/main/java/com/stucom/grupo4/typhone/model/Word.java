package com.stucom.grupo4.typhone.model;

public class Word {

    private String word;
    private Letter[] letters;

    public Word(String word) {
        this.word = word;
        this.letters = new Letter[word.length()];
        for (int i = 0; i < word.length(); i++) {
            this.letters[i] = new Letter(word.charAt(i));
        }
    }

    public Letter[] getLetters() {
        return this.letters;
    }

    public Letter getLetterAt(int index) {
        return letters[index];
    }

    public int getLength() {
        return this.word.length();
    }

    public String getWord() {
        return this.word;
    }
}
