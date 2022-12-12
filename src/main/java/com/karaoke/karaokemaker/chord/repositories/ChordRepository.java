package com.karaoke.karaokemaker.chord.repositories;

import com.karaoke.karaokemaker.chord.model.Chord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ChordRepository extends CrudRepository<Chord, Long> {

    List<Chord> findAllBySingleNote(String note);
   // List<Chord> findAllBySingleNoteAndByType(String note, String type);



}

