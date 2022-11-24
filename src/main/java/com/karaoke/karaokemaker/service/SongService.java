package com.karaoke.karaokemaker.service;


import com.karaoke.karaokemaker.domain.Song;
import com.karaoke.karaokemaker.repositories.SongRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    public void saveSong(Song song) {
        songRepository.save(song);
    }



}
