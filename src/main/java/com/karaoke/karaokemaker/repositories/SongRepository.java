package com.karaoke.karaokemaker.repositories;

import com.karaoke.karaokemaker.model.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface SongRepository extends CrudRepository<Song, Long> {

//    @Query("select distinct c from Song c left join fetch c.chords")
    List<Song> findAllByName (String name);
    List<Song> findAll ();
    Optional<Song> findByUuid (UUID uuid);

}

