package com.sqlitedatabase;

public class Model {
    private int id;
    private String word;

    public Model(int id, String word) {
        this.id = id;
        this.word = word;
    }

    public Model(String word) {
        this.word = word;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
