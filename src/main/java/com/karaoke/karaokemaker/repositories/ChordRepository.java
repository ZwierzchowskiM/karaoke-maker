package com.karaoke.karaokemaker.repositories;

import com.karaoke.karaokemaker.domain.Chord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


public interface ChordRepository extends CrudRepository<Chord, Long> {

    List<Chord> findAllBySingleNote(String note);



}

