package com.karaoke.karaokemaker.model;


//zrobić jako klase wewnętrzną????
public class Request {

    String songname;
    ChordRequest[] chords;

    public Request(String songname, ChordRequest[] chords) {
        this.songname = songname;
        this.chords = chords;
    }

    public String getName() {
        return songname;
    }

    public void setName(String name) {
        this.songname = name;
    }

    public ChordRequest[] getChords() {
        return chords;
    }

    public void setChords(ChordRequest[] chords) {
        this.chords = chords;
    }
}
