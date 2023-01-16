package com.karaoke.karaokemaker.service;

import com.karaoke.karaokemaker.model.Chord;
import com.karaoke.karaokemaker.dto.ChordDto;
import com.karaoke.karaokemaker.model.Song;
import com.karaoke.karaokemaker.repositories.ChordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChordService {

    ChordRepository chordRepository;

    public ChordService(ChordRepository chordRepository) {
        this.chordRepository = chordRepository;
    }

    @Transactional
    public Chord add(Chord chord) {
        chordRepository.save(chord);
        return chord;
    }

    public List<Chord> getChords() {
        return chordRepository.findAll();
    }


    @Transactional
    public void deleteChord(Long id) {
            chordRepository.deleteById(id);
    }


    @Transactional
    public Chord findChordByParameters(String note, String type, int complexity, int length) {
        List<Chord> chordList = chordRepository.findAll()
                .stream()
                .filter(chord -> chord.getSingleNote().equals(note))
                .filter(chord -> chord.getType().equals(type))
                .filter(chord -> chord.getComplexity() == complexity)
                .filter(chord -> chord.getLength() == length)
                .collect(Collectors.toList());

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
        return Optional.of(chord);
    }


}
