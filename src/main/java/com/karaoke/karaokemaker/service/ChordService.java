package com.karaoke.karaokemaker.service;

import com.karaoke.karaokemaker.enums.ChordType;
import com.karaoke.karaokemaker.enums.Complexity;
import com.karaoke.karaokemaker.enums.Length;
import com.karaoke.karaokemaker.enums.SingleNote;
import com.karaoke.karaokemaker.model.Chord;
import com.karaoke.karaokemaker.repositories.ChordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ChordService {

    ChordRepository chordRepository;

    public ChordService(ChordRepository chordRepository) {
        this.chordRepository = chordRepository;
    }

    @Transactional
    public Chord saveChord(Chord chord) {
        return chordRepository.save(chord);
    }

    public List<Chord> getAllChords() {
        return chordRepository.findAll();
    }

    public Optional<Chord> getSingleChord(Long id) {
        return chordRepository.findById(id);
    }

    @Transactional
    public void deleteChord(Long id) {
            chordRepository.deleteById(id);
    }

    // czy tutaj brać (0) element z listy???
    @Transactional
    public Chord findChordByParameters(SingleNote note, ChordType type, Complexity complexity, Length length) {
        List<Chord> chordList = (List<Chord>) chordRepository.findAll();
        chordList = chordList
                .stream()
                .filter(chord -> chord.getSingleNote().equals(note))
                .filter(chord -> chord.getType().equals(type))
                .filter(chord -> chord.getComplexity().equals(complexity))
                .filter(chord -> chord.getLength().equals(length)).toList();

        boolean empty = chordList.isEmpty();

        if (empty) {
            return null;
        } else {
            return chordList.get(0);
        }
    }


    public Optional<Chord> replaceChord(Long chordId, Chord chord) {
        if (!chordRepository.existsById(chordId)) {
            return Optional.empty();
        }
        chord.setId(chordId);
        Chord updatedEntity = chordRepository.save(chord);
        return Optional.of(updatedEntity);
    }


}
