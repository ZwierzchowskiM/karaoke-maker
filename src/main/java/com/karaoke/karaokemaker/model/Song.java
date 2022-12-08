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
    private List<Bar> bars = new ArrayList<>();

    @OneToMany (cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "song_id")
    private List<SongPart> structure = new ArrayList<>();



    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Bar> getBars() {
        return bars;
    }

    public void setBars(List<Bar> bars) {
        this.bars = bars;
    }

    public List<SongPart> getStructure() {
        return structure;
    }

    public void setStructure(List<SongPart> structure) {
        this.structure = structure;
    }

    public Song() {
    }

    public Song(String name) {

        this.name = name;

    }

    @Override
    public String toString() {
        return "Song{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                '}';
    }

    public void addBar(Bar bar) {
        this.bars.add(bar);
    }

    public void addPart(SongPart songPart) {
        this.structure.add(songPart);
    }


}
