package com.karaoke.karaokemaker.domain;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Username {

    @Id
    private Long Id;
    private String name;
    private String lastName;
    private String email;
//    List<Song> userSongList;


    public Username() {
    }
}
