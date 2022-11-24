package com.karaoke.karaokemaker.service;

import com.karaoke.karaokemaker.domain.Chord;
import com.karaoke.karaokemaker.repositories.ChordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChordService {

    ChordRepository chordRepository;

    public ChordService(ChordRepository chordRepository) {
        this.chordRepository = chordRepository;
    }

    @Transactional
    public void add(Chord chord){
        chordRepository.save(chord);
    }

    @Transactional
    public List<Chord> findChordsByNote(String note) {
        return chordRepository.findAllBySingleNote(note)
                .stream().map(chord -> new Chord(

                        chord.getSingleNote(),
                        chord.getType()
                )).collect(Collectors.toList());
    }
}
