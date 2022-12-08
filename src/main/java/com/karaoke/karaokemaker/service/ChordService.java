package com.karaoke.karaokemaker.service;

import com.karaoke.karaokemaker.model.Chord;
import com.karaoke.karaokemaker.model.ChordDto;
import com.karaoke.karaokemaker.repositories.ChordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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

    @Transactional
    public List<Chord> findChordsByParameters(String note, String type, String partName, int lenght) {
        List<Chord> chordList = chordRepository.findAllBySingleNote(note)
                .stream()
                .filter(chord -> chord.getType().equals(type))
                .filter(chord -> chord.getPartName().equals(partName))
                .filter(chord -> chord.getLenght() == lenght)
                .map(chord -> new Chord(
                        chord.getSingleNote(),
                        chord.getType(),
                        chord.getLenght(),
                        chord.getPath(),
                        chord.getPartName()
                )).collect(Collectors.toList());
        return chordList;
    }


    public static ChordDto mapper (Chord chord) {

        ChordDto chordDto = new ChordDto();

        chordDto.setSingleNote(chord.getSingleNote());
        chordDto.setType(chord.getType());

        return chordDto;


    }


    List<Chord> readFromDataBaseChord (List<ChordDto> chordsInBar) {

        List<Chord> chords = chordsInBar.stream().map(chord->mapDtoToChord(chord)).collect(Collectors.toList());

        return chords;

    }

    Chord mapDtoToChord (ChordDto chordDto){

        List<Chord> chordsByParameters = findChordsByParameters(chordDto.getSingleNote(),chordDto.getType(),chordDto.getPartName(),chordDto.getLenght());
        Chord chord = chordsByParameters.get(0);

        return chord;


    }



}
