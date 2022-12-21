package com.karaoke.karaokemaker.service;


import com.karaoke.karaokemaker.model.Chord;
import com.karaoke.karaokemaker.model.Request;
import com.karaoke.karaokemaker.model.Song;
import com.karaoke.karaokemaker.model.User;
import com.karaoke.karaokemaker.repositories.SongRepository;

//import org.ehcache.Cache;
//import org.ehcache.CacheManager;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import java.util.UUID;
import java.util.stream.Collectors;


@Service

public class SongService {

    SongRepository songRepository;
    SongWriter songWriter;
    ChordService chordService;
    CacheManager cacheManager;

    public SongService(SongRepository songRepository, SongWriter songWriter, ChordService chordService, CacheManager cacheManager ) {
        this.songRepository = songRepository;
        this.songWriter = songWriter;
        this.chordService = chordService;
        this.cacheManager = cacheManager;

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



    @CachePut(cacheNames = "Songs", key = "#result.uuid")
    public Song generateSong(Request songRequest) throws UnsupportedAudioFileException, IOException, NullPointerException {

        Song newSong = new Song();

        List<Chord> songChords = Arrays.stream(songRequest.getChords())
                .map(chordService::mapChordRequestToChord)
                .collect(Collectors.toList());
        System.out.println(songChords);

        newSong.setChords(songChords);
        newSong.setName(songRequest.getName());

        String songPath = songWriter.writeSong(newSong);
        newSong.setPath(songPath);

        UUID uuid = UUID.randomUUID();
        newSong.setUuid(uuid);

        newSong.setUser(new User("adam","kowalski","email@gmail"));

        System.out.println(newSong);

        return newSong;

    }


        public void checkCash(UUID uuid ) {

        Cache songCache = cacheManager.getCache("Songs");

        if (songCache.get(uuid) == null) {
            System.out.println("does not exist in the cache");
        } else {
            System.out.println("exist in the cache");
        }

    }

    @Transactional
    @Cacheable(cacheNames = "Songs", key = "#uuid")
    public Song getFromCache(UUID uuid) {
        return null;
    }


    @Transactional
    @CachePut(cacheNames = "Songs", key = "#result.uuid")
    public Song putSongToCache() {

        String uuid ="5fc03087-d265-11e7-b8c6-83e29cd24f4c";
        Song song = new Song("testUUID", UUID.fromString(uuid));

        return song;
    }


//    public Optional<Song> replaceSong(Long songId, Song song) {
//        if (!songRepository.existsById(songId)) {
//            return Optional.empty();
//        }
//        song.setId(songId);
//        Song updatedEntity = songRepository.save(song);
//        return Optional.of(song);
//    }



}
