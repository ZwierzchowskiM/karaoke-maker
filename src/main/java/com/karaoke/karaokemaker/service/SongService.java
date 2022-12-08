package com.karaoke.karaokemaker.service;


import com.karaoke.karaokemaker.model.*;
import com.karaoke.karaokemaker.repositories.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class SongService {

    SongRepository songRepository;
    BarService barService;

    public SongService(SongRepository songRepository, BarService barService) {

        this.songRepository = songRepository;
        this.barService = barService;
    }


    public void createSong() {
        Song song = new Song();
        song.setName("utwor testowy");
        saveSong(song);
    }

    @Transactional
    public Song saveSong(Song song) {
        Song savedSong = songRepository.save(song);
        return savedSong;
    }

    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }


    public Optional<Song> replaceSong(Long songId, Song song) {
        if (!songRepository.existsById(songId)) {
            return Optional.empty();
        }
        song.setId(songId);
        Song updatedEntity = songRepository.save(song);
        return Optional.of(song);
    }

    // na podstawie struktury dodaje znaczniki zw ref
    public void organizeSong(Song song)
    {
        addSongPartToBars(song);
        //setBarAtributes(song);
    }


    public void addSongPartToBars(Song song){

        ArrayList<Integer> songPartsBarNumbers = new ArrayList<>();
        ArrayList<String> songPartsNames =  new ArrayList<>();

        for (SongPart p: song.getStructure() ) {
            songPartsBarNumbers.add(p.getBarNumber());
            songPartsNames.add(p.getPartName());
        }

        int songPartsBarNumbersLenght = songPartsBarNumbers.size();
        int songLenght = song.getBars().size();
        int startbar=0;
        int endbar=0;

        for (int i = 0; i <songPartsBarNumbersLenght; i++) {

            startbar= songPartsBarNumbers.get(i);
            if (i== songPartsBarNumbersLenght -1 ){
                endbar=songLenght;
            } else {
                endbar= songPartsBarNumbers.get(i + 1);
            }

            for (int j = startbar; j <endbar ; j++) {

                song.getBars().get(j).setPartName(songPartsNames.get(i));
            }
        }

    }


    // ustawia dlugosc akordow i czesc utworu
    public void setBarAtributes(Song song)
    {
        List<Bar> bars = song.getBars();
        bars.forEach(barService::setRequiredChordsAtributes);
    }



    public List<Chord> readChordsFromDataBase (Song song){

        List<Bar> bars = song.getBars();
        List<Chord> allChords = bars.stream().map(barService::readChordsInBar).flatMap(List::stream).collect(Collectors.toList());
        return allChords;




//        List<Chord> allChords = new ArrayList<>();

//        for (int i = 0; i < bars.size(); i++) {
//            List<Chord> chordsInBar = barService.readChordsInBar(bars.get(i)) ;
//
//
//            for (int i1 = 0; i1 < chordsInBar.size(); i1++) {
//                    allChords.add(chordsInBar.get(i1))  ;
//            }
//        }




    }




}
