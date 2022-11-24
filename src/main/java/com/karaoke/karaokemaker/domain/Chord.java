package com.karaoke.karaokemaker.domain;

import javax.persistence.*;

@Entity
@Table(name = "chords")
public class Chord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String singleNote;
    private String type;        // maj, moll, dominant7, maj9 itp
//    private int root;           // przewrot, im wiekszy tym wyzej
//    private int complex;        // stopien zlozonosci frazy
//    private String path;
//    private int lenght


    public Chord() {
    }

    public Chord(String singleNote,String type) {
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

    @Override
    public String toString() {
        return "Chord{" +
                "id=" + id +
                ", singleNote='" + singleNote + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
