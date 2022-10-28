package com.karaoke.karaokemaker.domain;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Bar {

    @Id
    private Long Id;
    private String time;


    public Bar() {
    }
}
