package com.karaoke.karaokemaker.chord.model;

import javax.persistence.*;

@Entity
@Table(name = "chords")
public class Chord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String singleNote;
    private String type;        // maj, moll, dominant7, maj9 itp
    private int lenght;
    private String path;
    private String partName;



    public Chord() {
    }

    public Chord(String singleNote, String type, int lenght, String path, String partName) {
        this.singleNote = singleNote;
        this.type = type;
        this.lenght = lenght;
        this.path = path;
        this.partName = partName;
    }

    public Chord(String singleNote, String type) {
        this.singleNote = singleNote;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        id = id;
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
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    @Override
    public String toString() {
        return "Chord{" +
                "singleNote='" + singleNote + '\'' +
                ", type='" + type + '\'' +
                ", lenght=" + lenght +
                ", path='" + path + '\'' +
                ", partName='" + partName +
                '}' + "\n" ;
    }
}
