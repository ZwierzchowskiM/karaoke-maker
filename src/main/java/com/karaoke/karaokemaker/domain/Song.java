package com.karaoke.karaokemaker.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Song {

    @Id
    private Long Id;
    private String name;
    private String userId;
    private int bpm;
    private String time;

    public Song() {
    }
}
