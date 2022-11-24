package com.karaoke.stara_wersja;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;


public class App {

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException {

        Song song = new Song();
        song.createSong();



    }
}


