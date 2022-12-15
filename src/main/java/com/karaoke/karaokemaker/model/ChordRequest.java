package com.karaoke.karaokemaker.model;

public class ChordRequest {
    String singleNote;
    String type;
    int length;
    int complexity;

    public ChordRequest(String singleNote, String type, int length, int complexity) {
        this.singleNote = singleNote;
        this.type = type;
        this.length = length;
        this.complexity = complexity;
    }

    public String getSingleNote() {
        return singleNote;
    }

    public void setSingleNote(String singleNote) {
        this.singleNote = singleNote;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLenght() {
        return length;
    }

    public void setLenght(int lenght) {
        this.length = lenght;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    @Override
    public String toString() {
        return "ChordRequest{" +
                "singleNote='" + singleNote + '\'' +
                ", type='" + type + '\'' +
                ", lenght=" + length +
                ", complexity=" + complexity +
                '}';
    }
}
