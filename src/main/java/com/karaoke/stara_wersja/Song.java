package com.karaoke.stara_wersja;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Song {

    private static final int ADD_CHORD = 1;
    private static final int CHANGE_CHORD = 2;
    private static final int DELETE_CHORD = 3;
    private static final int PRINT_STRUCTURE = 4;
    private static final int EXIT = 0;

    int option = -1;

    String finalSong = "Files\\finalSong.wav";
    ChordControl chordUtil = new ChordControl();

    AudioInputStream sound1;
    AudioInputStream sound2;
    AudioInputStream appendedFiles;

    ArrayList<Chord> song = new ArrayList<>();
    Scanner sc = new Scanner(System.in);


    void createSong() throws UnsupportedAudioFileException, IOException {

        createStructure();
        writeSong();

    }


    void createStructure() {

        while (option != EXIT) {
            printOptions();
            option = readOption();

            switch (option) {
                case ADD_CHORD -> addChord();
                case CHANGE_CHORD -> changeChord();
                case DELETE_CHORD -> deleteChord();
                case PRINT_STRUCTURE -> printSongStructure();
                
                case EXIT -> {return;}
                
                default -> throw new IllegalStateException("Unexpected value: " + option);
            }
        }
    }

    private int readOption() {
        int option = sc.nextInt();
        sc.nextLine();
        return option;
    }

    private void printSongStructure() {
        System.out.println(song);

    }


    private void printOptions() {
        System.out.println("Dostepne opcje: ");
        System.out.println(ADD_CHORD + " - Dodanie nowego akordu");
//        System.out.println(CHANGE_CHORD + " - Zmiana akordu");
//        System.out.println(DELETE_CHORD + " - Usuniecie akordu");
        System.out.println(PRINT_STRUCTURE + " - Wyswietlenie struktury utworu");
        System.out.println(EXIT + " - Zapis utworu ");

    }

    private void addChord() {
        System.out.println("Dostepne akordy: ");
        chordUtil.printSingleChords();
        System.out.println("Wybierz akord: ");
        String singleChord = sc.nextLine();
        System.out.println("Dostepne typy akordu: ");
        chordUtil.printTypeOfChords(singleChord);
        System.out.println("Wybierz typ akordu: ");
        String chordType = sc.nextLine();
        Chord newChord = chordUtil.chords.get(singleChord).get(chordType);
        song.add(newChord);

    }
    private void deleteChord() {
    }

    private void changeChord() {
    }


    private void writeSong() throws UnsupportedAudioFileException, IOException {

        int songLenght =  song.size();

        for (int i = 1; i < songLenght ; i++) {

            if (i == 1) {
                sound1 = pickChord(song.get(0));
            } else {
                sound1 = pickTempFile(i-1);
            }

            sound2 = pickChord(song.get(i));

            appendedFiles = new AudioInputStream(new SequenceInputStream(sound1, sound2), sound1.getFormat(), sound1.getFrameLength() + sound2.getFrameLength());

            if (i < songLenght - 1) {
                writeTempFile(i);

            } else {
                writeFinalSong();
            }

            sound1.close();
            sound2.close();

            deleteTempFile(i);

        }
    }

    private void writeFinalSong() throws IOException {
        AudioSystem.write(appendedFiles, AudioFileFormat.Type.WAVE, new File(finalSong));
    }

    private void writeTempFile(int i) throws IOException {
        AudioSystem.write(appendedFiles, AudioFileFormat.Type.WAVE, new File("Files\\tmpAudio" + i + ".wav"));
    }

    AudioInputStream pickChord(Chord chord) throws UnsupportedAudioFileException, IOException {
        return AudioSystem.getAudioInputStream(new File(chord.getPath()));
    }

    AudioInputStream pickTempFile (int i) throws UnsupportedAudioFileException, IOException {
        return AudioSystem.getAudioInputStream(new File("Files\\tmpAudio" + (i) + ".wav"));
    }

    private void deleteTempFile(int i) {
        File f= new File("Files\\tmpAudio" + (i -1) + ".wav");
        f.delete();
    }

}
