package com.karaoke.karaokemaker.model;

import javax.persistence.*;

@Entity
@Table(name = "chords")
public class Chord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String singleNote;
    private String type;        // maj, moll, dominant7, maj9 itp
    private int length;
    private String path;
    private int complexity;



    public Chord() {
    }

    public Chord(String singleNote, String type, int lenght, String path, int complexity) {
        this.singleNote = singleNote;
        this.type = type;
        this.length = lenght;
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    @Override
    public String toString() {
        return "Chord{" +
                ", singleNote='" + singleNote + '\'' +
                ", type='" + type + '\'' +
                ", lenght=" + length +
                ", path='" + path + '\'' +
                ", complexity=" + complexity +
                '}';
    }
}
