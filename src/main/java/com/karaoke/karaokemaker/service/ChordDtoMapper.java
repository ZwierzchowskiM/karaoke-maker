package com.karaoke.karaokemaker.service;

import com.karaoke.karaokemaker.dto.ChordDto;
import com.karaoke.karaokemaker.dto.SongDto;
import com.karaoke.karaokemaker.model.Chord;
import com.karaoke.karaokemaker.model.Song;
import com.karaoke.karaokemaker.repositories.ChordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChordDtoMapper {

    ChordService chordService;

    public ChordDtoMapper(ChordService chordService) {
        this.chordService = chordService;
    }

    public List<Chord> mapToChords(List<ChordDto> chordsDto) {
        return chordsDto.stream()
                .map(chord -> mapToChord(chord))
                .toList();
    }


    public Chord mapToChord(ChordDto chordDto) {
        Chord chord = chordService.findChordByParameters(chordDto.getSingleNote(), chordDto.getType(), chordDto.getComplexity(), chordDto.getLength());
        return chord;

    }


}
