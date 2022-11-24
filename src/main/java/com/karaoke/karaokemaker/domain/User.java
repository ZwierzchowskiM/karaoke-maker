package com.karaoke.karaokemaker.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private String lastName;
    private String email;
//    @OneToMany (mappedBy = "user")
    @OneToMany
    //@JoinColumn(name = "song_id")
    private List<Song> songs = new ArrayList<>();



    public User() {
    }

    public User(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    public void addSong(Song song) {

        songs.add(song);
    }
}
