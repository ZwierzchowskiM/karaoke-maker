package com.karaoke.stara_wersja;

public class Chord {

    String name;
    String path;


    public Chord(String name, String path) {
        this.name = name;
        this.path = path;
    }

    @Override
    public String toString() {
        return  name ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
