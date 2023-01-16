package com.karaoke.karaokemaker.model;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "chords")
@Getter
@Setter
public class Chord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String singleNote;
    private String type;
    private int length;
    private String path;
    private int complexity;



    public Chord() {
    }

    public Chord(String singleNote, String type, int length, String path, int complexity) {
        this.singleNote = singleNote;
        this.type = type;
        this.length = length;
        this.path = path;
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
