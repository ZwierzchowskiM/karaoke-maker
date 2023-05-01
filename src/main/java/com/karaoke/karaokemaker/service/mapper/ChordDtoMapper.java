package com.karaoke.karaokemaker.service.mapper;

import com.karaoke.karaokemaker.dto.ChordDto;
import com.karaoke.karaokemaker.model.Chord;
import com.karaoke.karaokemaker.service.ChordService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChordDtoMapper {

    ChordService chordService;

    public ChordDtoMapper(ChordService chordService) {
        this.chordService = chordService;
    }

    public List<Chord> mapDtosToChords(List<ChordDto> chordsDto) {
        return chordsDto.stream()
                .map(this::mapToChord)
                .toList();
    }


    private Chord mapToChord(ChordDto chordDto) {
        Chord chord = chordService.findChordByParameters(chordDto.getSingleNote(),
                chordDto.getType(), chordDto.getComplexity(), chordDto.getLength(), chordDto.getBassNote());
        return chord;

    }


}
