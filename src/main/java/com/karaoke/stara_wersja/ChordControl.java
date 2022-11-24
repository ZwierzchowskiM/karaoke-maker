package com.karaoke.stara_wersja;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChordControl {

    // plik z akordami
    // Kategoria;Typ;sciezka
    // C,CDUR,"sciezka"
    final static String fileName = "Files\\chords.txt";

    Map<String, HashMap<String, Chord>> chords = new HashMap<>();


    ChordControl() {
        readChordsFromFile();
    }


    void printSingleChords() {
        for (String s : chords.keySet()) {
            System.out.print(s);
            System.out.print(" | ");

        }
        System.out.print('\n');
    }

    void printTypeOfChords(String chordKey) {

        for (String s : chords.get(chordKey).keySet()) {
            System.out.print(s);
            System.out.print(" | ");
        }
        System.out.print('\n');

    }


    //odczyt z pliku akrodow w oraz sciezek
    void readChordsFromFile() {
        try (
                var fileReader = new FileReader(fileName);
                var reader = new BufferedReader(fileReader);
        ) {
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                String[] data = nextLine.split(";");
                String category = data[0];
                Chord chord = new Chord(data[1], (data[2]));
                insertProductIntoMap(chords, category, chord);
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    // Jezeli w mapie jest kategoria akordu: C, D, E... to wrzuca do tej kategorii
    // Jezeli nie ma takiej kategorii to tworzy nowa i tam wrzuca typ akordu CMAJ, CMOLL itd.
    private void insertProductIntoMap(Map<String, HashMap<String, Chord>> allChords, String category, Chord chord) {
        if (allChords.containsKey(category))
            allChords.get(category).put(chord.name, chord);
        else {
            HashMap<String, Chord> categorySet = new HashMap<>();
            categorySet.put(chord.name, chord);
            chords.put(category, categorySet);
        }
    }
}




