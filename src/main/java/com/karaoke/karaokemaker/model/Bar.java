package com.karaoke.karaokemaker.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Bar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToMany
    private List<Chord> chords = new ArrayList<>();


    public Bar() {
    }

    public void addChord(Chord chord) {
        chords.add(chord);
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }





}
