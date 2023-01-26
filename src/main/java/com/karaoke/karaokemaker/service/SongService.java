package com.karaoke.karaokemaker.service;


import com.karaoke.karaokemaker.dto.SongRequestDto;
import com.karaoke.karaokemaker.model.*;
import com.karaoke.karaokemaker.repositories.SongRepository;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

import java.util.*;


@Service
public class SongService {

    SongRepository songRepository;
    SongWriter songWriter;
    ChordService chordService;
    CacheManager cacheManager;
    ChordDtoMapper chordDtoMapper;
    UserService userService;


    public SongService(SongRepository songRepository, SongWriter songWriter, ChordService chordService,
                       CacheManager cacheManager, ChordDtoMapper chordDtoMapper, UserService userService) {
        this.songRepository = songRepository;
        this.songWriter = songWriter;
        this.chordService = chordService;
        this.cacheManager = cacheManager;
        this.chordDtoMapper = chordDtoMapper;
        this.userService = userService;
    }

    @Transactional
    public Song saveSong(Song song) {
        Song savedSong = songRepository.save(song);
        return savedSong;
    }

    @Transactional
    @CachePut(cacheNames = "Songs", key = "#result.uuid")
    public Song generateSong(SongRequestDto songRequest) throws UnsupportedAudioFileException, IOException {

        Song newSong = new Song();

        List<Chord> songChords = chordDtoMapper.mapToChords(List.of(songRequest.getChordDtos()));
        newSong.setChords(songChords);
        newSong.setName(songRequest.getSongName());
        String songPath = "Files\\" + userService.currentUserName() + "\\" + newSong.getName() + ".wav";
        newSong.setPath(songPath);
        newSong.setUuid(UUID.randomUUID());
        newSong.setUserId(userService.currentUserId());


        songWriter.writeSong(newSong);


        return newSong;

    }

    public Optional<Song> getSingleSong(Long id) {
        Long userId = userService.currentUserId();
        Optional<Song> song = songRepository.findById(id);
        return song;
    }

    public List<Song> getUserSongs() {
        Long userId = userService.currentUserId();
        List<Song> filteredSongs = songRepository.findAll().stream().filter(song -> Objects.equals(song.getUserId(), userId)).toList();
        return filteredSongs;
    }


    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }




    @Transactional
    public Optional<Song> replaceSong(Long songId, SongRequestDto songDto) throws UnsupportedAudioFileException, IOException {
        if (!songRepository.existsById(songId)) {
            return Optional.empty();
        }
        Song replacedSong = new Song();
        List<Chord> songChords = chordDtoMapper.mapToChords(List.of(songDto.getChordDtos()));
        replacedSong.setChords(songChords);
        replacedSong.setName(songDto.getSongName());
        String songPath = "Files\\" + userService.currentUserName() + "\\" + replacedSong.getName() + ".wav";
        replacedSong.setPath(songPath);
        replacedSong.setUuid(UUID.randomUUID());
        replacedSong.setUserId(userService.currentUserId());
        songWriter.writeSong(replacedSong);
        replacedSong.setId(songId);

        Song updatedEntity = songRepository.save(replacedSong);
        return Optional.of(updatedEntity);
    }


    @Transactional
    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }





//
    @Transactional
    @Cacheable(cacheNames = "Songs", key = "#uuid")
    public Song getFromCache(String uuid) {

        UUID songUuid = UUID.fromString(uuid);

        Cache songCache = cacheManager.getCache("Songs");
        if (songCache.get(songUuid) == null) {
            System.out.println("does not exist in the cache");
            return null;
        } else {
            System.out.println("exist in the cache");
            Song song = songCache.get(songUuid,Song.class);

            return song;

        }
    }



    public String getSongPath(@PathVariable Long id) {

        Song songToDownload = songRepository.findById(id).orElseThrow();
        String songPath = songToDownload.getPath();

        return songPath;
    }


}
