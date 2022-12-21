package com.karaoke.karaokemaker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
//@RedisHash("Song")
public class Song implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;

    @JsonIgnore //działa, ale nie wczytuje chords
//
    @Fetch(FetchMode.JOIN)
    @OneToMany (cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "song_id")
    private List<Chord> chords = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    String path;
    UUID uuid;


    public Song() {
    }

    public Song(String name, List<Chord> chords, User user, String path, UUID uuid) {
        this.name = name;
        this.chords = chords;
        this.user = user;
        this.path = path;
        this.uuid = uuid;
    }

    public Song(String name) {
        this.name = name;
    }

    public Song(String name, List<Chord> chords) {
        this.name = name;
        this.chords = chords;
    }


    public Song(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public Song(String name, List<Chord> chords, User user) {
        this.name = name;
        this.chords = chords;
        this.user = user;
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



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void addBar(Chord chord) {
        this.chords.add(chord);
    }


    @Override
    public String toString() {
        return "Song{" +
                "Id=" + Id + "\n" +
                ", name='" + name + "\n" +
                ", chords=" + chords+ "\n" +
                ", user=" + user + "\n" +
                ", path='" + path + "\n" +
                ", uuid=" + uuid+ "\n"  +
                '}';
    }
}
