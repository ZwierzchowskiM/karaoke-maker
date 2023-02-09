package com.karaoke.karaokemaker.service;


import com.karaoke.karaokemaker.dto.SongRequestDto;
import com.karaoke.karaokemaker.exceptions.AudioFileNotFoundException;
import com.karaoke.karaokemaker.model.*;
import com.karaoke.karaokemaker.repositories.SongRepository;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


@Service
public class SongService {

    SongRepository songRepository;
    ChordService chordService;
    CacheManager cacheManager;
    ChordDtoMapper chordDtoMapper;
    UserService userService;


    public SongService(SongRepository songRepository, ChordService chordService,
                       CacheManager cacheManager, ChordDtoMapper chordDtoMapper, UserService userService) {
        this.songRepository = songRepository;

        this.chordService = chordService;
        this.cacheManager = cacheManager;
        this.chordDtoMapper = chordDtoMapper;
        this.userService = userService;
    }

    @Transactional
    public Song saveSong(Song song) {
        return songRepository.save(song);
    }

    @Transactional
    @CachePut(cacheNames = "Songs", key = "#result.uuid")
    public Song saveSong(SongRequestDto songRequest) {

        Song newSong = new Song();

        List<Chord> songChords = chordDtoMapper.mapToChords(List.of(songRequest.getChordDtos()));
        newSong.setChords(songChords);
        newSong.setName(songRequest.getSongName());
        newSong.setUuid(UUID.randomUUID());
        newSong.setUserId(userService.currentUserId());

        return newSong;

    }

    public String generateSong(Song song, String format) throws UnsupportedAudioFileException, IOException, AudioFileNotFoundException {

        Writer writer;
        String userName = userService.currentUserName();
        String directory = "Files\\" + userName;
        checkDirectory(directory);

        writer = switch (format) {
            case "WAV" -> new SongWriterWav();
            case "TEXT" -> new SongWriterText();
            default -> null;
        };

        return writer.writeSong(song, directory);

    }


    private void checkDirectory(String directory) throws IOException {
        if (!Files.isDirectory(Paths.get(directory))) {
            Files.createDirectories(Paths.get(directory));
        }
    }


    public Optional<Song> getSingleSong(Long id) {
        Optional<Song> song = songRepository.findById(id);
        if (song.isEmpty()) {
            return Optional.empty();
        }
        Long userId = userService.currentUserId();
        Long songUserId = song.get().getUserId();
        if (Objects.equals(songUserId, userId))
            return song;
        else
            return Optional.empty();
    }

//    public List<Song> getUserSongs() {
//        Long userId = userService.currentUserId();
//        List <Song> songs = songRepository.findAll();
//        return songs.stream().filter(song -> Objects.equals(song.getUserId(), userId)).toList();
//    }

    public List<Song> getUserSongs() {
        Long userId = userService.currentUserId();
        return songRepository.findAllByUserId(userId);
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }


    @Transactional
    public Optional<Song> replaceSong(Long songId, SongRequestDto songDto) {
        if (!songRepository.existsById(songId)) {
            return Optional.empty();
        }
        Song replacedSong = new Song();
        List<Chord> songChords = chordDtoMapper.mapToChords(List.of(songDto.getChordDtos()));
        replacedSong.setChords(songChords);
        replacedSong.setName(songDto.getSongName());
        replacedSong.setUuid(UUID.randomUUID());
        replacedSong.setUserId(userService.currentUserId());
        replacedSong.setId(songId);

        Song updatedEntity = songRepository.save(replacedSong);
        return Optional.of(updatedEntity);
    }


    @Transactional
    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }


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
            Song song = songCache.get(songUuid, Song.class);

            return song;

        }
    }


    public String getSongPath(Song song, String format) {
        return switch (format) {
            case "WAV" -> song.getPathWavFile();
            case "TEXT" -> song.getPathTextFile();
            default -> null;
        };

    }


}
