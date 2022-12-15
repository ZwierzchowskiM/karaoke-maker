package com.karaoke.karaokemaker.repositories;

import com.karaoke.karaokemaker.model.Chord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ChordRepository extends CrudRepository<Chord, Long> {

    List<Chord> findAll();


}

