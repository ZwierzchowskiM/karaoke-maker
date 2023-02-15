package com.karaoke.karaokemaker.repositories;

import com.karaoke.karaokemaker.model.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface SongRepository extends CrudRepository<Song, Long> {

    List<Song> findAll ();
    List<Song> findAllByUserId (Long id);





}

