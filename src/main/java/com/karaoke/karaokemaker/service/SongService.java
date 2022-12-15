package com.karaoke.karaokemaker.service;


import com.karaoke.karaokemaker.model.Chord;
import com.karaoke.karaokemaker.model.ChordRequest;
import com.karaoke.karaokemaker.model.Song;
import com.karaoke.karaokemaker.repositories.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SongService {

    SongRepository songRepository;
    SongWriter songWriter;
    ChordService chordService;

    public SongService(SongRepository songRepository, SongWriter songWriter, ChordService chordService) {
        this.songRepository = songRepository;
        this.songWriter = songWriter;
        this.chordService = chordService;
    }



    @Transactional
    public Song saveSong(Song song) {
        Song savedSong = songRepository.save(song);
        return savedSong;
    }

    @Transactional
    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }


    public Song generateSong(ChordRequest[] chords) throws UnsupportedAudioFileException, IOException,NullPointerException {
        List<Chord> songChords = Arrays.stream(chords).map(chordService::mapChordRequestToChord).collect(Collectors.toList());
        System.out.println(songChords);
        Song newSong = new Song("new",songChords);
        songWriter.writeSong(newSong);
        return newSong;

    }


//    public Optional<Song> replaceSong(Long songId, Song song) {
//        if (!songRepository.existsById(songId)) {
//            return Optional.empty();
//        }
//        song.setId(songId);
//        Song updatedEntity = songRepository.save(song);
//        return Optional.of(song);
//    }

//
//    public List<Chord> readChordsFromDataBase (Song song){
//
//        List<Bar> bars = song.getBars();
//        List<Chord> allChords = bars.stream().map(barService::readChordsInBar).flatMap(List::stream).collect(Collectors.toList());
//        return allChords;
//
//
//    }




}
