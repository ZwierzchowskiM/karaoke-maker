package com.karaoke.karaokemaker.service;

import com.karaoke.karaokemaker.model.Chord;
import com.karaoke.karaokemaker.model.ChordRequest;
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
    public void add(Chord chord) {
        chordRepository.save(chord);
    }

    public Chord mapChordRequestToChord(ChordRequest userRequest) {
        Chord chord = findChordByParameters(userRequest.getSingleNote(), userRequest.getType(),  userRequest.getComplexity(),userRequest.getLenght());
        return chord;
    }


    @Transactional
    private Chord findChordByParameters(String note, String type, int complexity, int lenght) {
        List<Chord> chordList = chordRepository.findAll()
                .stream()
                .filter(chord -> chord.getSingleNote().equals(note))
                .filter(chord -> chord.getType().equals(type))
                .filter(chord -> chord.getComplexity() == complexity)
                .filter(chord -> chord.getLenght() == lenght)
                .map(chord -> new Chord(
                        chord.getSingleNote(),
                        chord.getType(),
                        chord.getLenght(),
                        chord.getPath(),
                        chord.getComplexity()
                )).collect(Collectors.toList());

        boolean empty = chordList.isEmpty();

        if (empty) {
            return null;
        } else {
            return chordList.get(0);
        }
    }


}
