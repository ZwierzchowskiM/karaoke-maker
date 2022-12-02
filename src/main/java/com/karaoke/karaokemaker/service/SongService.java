package com.karaoke.karaokemaker.service;


import com.karaoke.karaokemaker.model.Song;
import com.karaoke.karaokemaker.repositories.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class SongService {

    SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
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

}
