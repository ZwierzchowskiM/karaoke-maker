package com.karaoke.karaokemaker.service;


import com.karaoke.karaokemaker.dto.SongRequestDto;
import com.karaoke.karaokemaker.model.*;
import com.karaoke.karaokemaker.repositories.SongRepository;

//import org.ehcache.Cache;
//import org.ehcache.CacheManager;

import org.springframework.cache.CacheManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

import java.util.Arrays;
import java.util.List;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class SongService {

    SongRepository songRepository;
    SongWriter songWriter;
    ChordService chordService;
    CacheManager cacheManager;
    ChordDtoMapper chordDtoMapper;

    public SongService(SongRepository songRepository, SongWriter songWriter, ChordService chordService, CacheManager cacheManager, ChordDtoMapper chordDtoMapper) {
        this.songRepository = songRepository;
        this.songWriter = songWriter;
        this.chordService = chordService;
        this.cacheManager = cacheManager;
        this.chordDtoMapper = chordDtoMapper;
    }

    @Transactional
    public Song saveSong(Song song) {
        Song savedSong = songRepository.save(song);
        return savedSong;
    }


    //    @CachePut(cacheNames = "Songs", key = "#result.uuid")
    public Song generateSong(SongRequestDto songRequest) throws UnsupportedAudioFileException, IOException, NullPointerException {

        Song newSong = new Song();

//        List<Chord> songChords = Arrays.stream(songRequest.getChords())
//                .map(chordService::mapToChord)
//                .collect(Collectors.toList());
        List<Chord> songChords = chordDtoMapper.mapToChords(List.of(songRequest.getChords()));
        System.out.println(songChords);

        newSong.setChords(songChords);
        newSong.setName(songRequest.getName());

        String songPath = songWriter.writeSong(newSong);
        newSong.setPath(songPath);

        UUID uuid = UUID.randomUUID();
        newSong.setUuid(uuid);

//        newSong.setUser(new User("adam","kowalski","email@gmail","password"));

        System.out.println(newSong);

        return newSong;

    }


    public Song getSingleSong(Long id) {
        return songRepository.findById(id).orElseThrow();
    }


    @Transactional
    public Optional<Song> replaceSong(Long songId, Song song) {
        if (!songRepository.existsById(songId)) {
            return Optional.empty();
        }
        song.setId(songId);
        Song updatedEntity = songRepository.save(song);
        return Optional.of(updatedEntity);
    }


    @Transactional
    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }


    public List<Song> getSongs() {
        return songRepository.findAll();
    }

//        public void checkCash(UUID uuid ) {
//
//        Cache songCache = cacheManager.getCache("Songs");
//
//        if (songCache.get(uuid) == null) {
//            System.out.println("does not exist in the cache");
//        } else {
//            System.out.println("exist in the cache");
//        }
//
//    }
//
//    @Transactional
//    @Cacheable(cacheNames = "Songs", key = "#uuid")
//    public Song getFromCache(UUID uuid) {
//        return null;
//    }
//
//
//    @Transactional
//    @CachePut(cacheNames = "Songs", key = "#result.uuid")
//    public Song putSongToCache() {
//
//        String uuid ="5fc03087-d265-11e7-b8c6-83e29cd24f4c";
//        Song song = new Song("testUUID", UUID.fromString(uuid));
//
//        return song;
//    }


}
