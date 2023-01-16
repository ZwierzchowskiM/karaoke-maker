package com.karaoke.karaokemaker.dto;


import java.util.Arrays;

public class SongRequestDto {

    String songname;
    ChordDto[] chords;

    public SongRequestDto(String songname, ChordDto[] chords) {
        this.songname = songname;
        this.chords = chords;
    }

    public String getName() {
        return songname;
    }

    public void setName(String name) {
        this.songname = name;
    }

    public ChordDto[] getChords() {
        return chords;
    }

    public void setChords(ChordDto[] chords) {
        this.chords = chords;
    }

    @Override
    public String toString() {
        return "Request{" +
                "songname='" + songname + '\'' +
                ", chords=" + Arrays.stream(chords).toList().toString() +
                '}';
    }
}
