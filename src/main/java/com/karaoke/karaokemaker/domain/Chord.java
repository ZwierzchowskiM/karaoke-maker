package com.karaoke.karaokemaker.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Chord {

    @Id
    private Long Id;
    private String sigleNote;
    private String type;
    private String root;



    public Chord() {
    }
}
