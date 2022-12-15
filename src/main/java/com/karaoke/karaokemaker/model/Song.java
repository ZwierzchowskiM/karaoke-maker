package com.karaoke.karaokemaker.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;

    @OneToMany (cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "song_id")
    private List<Chord> chords = new ArrayList<>();

    public Song() {
    }

    public Song(String name) {
        this.name = name;
    }

    public Song(String name, List<Chord> chords) {
        this.name = name;
        this.chords = chords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Chord> getChords() {
        return chords;
    }

    public void setChords(List<Chord> chords) {
        this.chords = chords;
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public void addBar(Chord chord) {
        this.chords.add(chord);
    }




}
